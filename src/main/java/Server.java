import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Server implements Runnable {
    private Integer port;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private InputStream in;
    private OutputStream out;
    private ThreadPoolExecutor serverPool;

    private Server() {

    }

    public static Server create(Integer port, ThreadPoolExecutor serverPool) throws IOException {
        Server server = new Server();
        server.port = port;
        server.serverSocket = new ServerSocket(port);
        server.serverPool = serverPool;

        return server;
    }

    private byte[] extendArray(byte[] oldArray) {
        int oldSize = oldArray.length;
        byte[] newArray = new byte[oldSize * 2];
        System.arraycopy(oldArray, 0, newArray, 0, oldSize);

        return newArray;
    }

    private byte[] readInput(InputStream stream) throws IOException {
        int b;
        byte[] buffer = new byte[10];
        int counter = 0;

        while ((b = stream.read()) > -1) {
            buffer[counter++] = (byte) b;

            if (counter >= buffer.length) {
                buffer = extendArray(buffer);
            }

            if (counter > 1 && MyPacket.compareEOP(buffer, counter - 1)) {
                break;
            }
        }

        byte[] data = new byte[counter];
        System.arraycopy(buffer, 0, data, 0, counter);

        return data;
    }

    public void run() {
        try {
            clientSocket = serverSocket.accept();
            out = clientSocket.getOutputStream();
            in = clientSocket.getInputStream();

            while (true) {
                byte[] data = readInput(in);
                MyPacket packet = MyPacket.parse(data);

                if (packet.getType() == 1 && packet.getSubtype() == 2) {
                    if (packet.checkHandshakeRequest()) {
                        System.out.println("handshake " + packet.checkHandshakeRequest());
                        MyPacket handshakePacket = MyPacket.create(1, 2);
                        handshakePacket.setValue(1, packet.checkHandshakeResponse());
                        out.write(handshakePacket.toByteArray());
                        out.flush();
                    }

                }
                if (packet.getType() == 1 && packet.getSubtype() == 3) {
                    System.out.println(packet.getValue(1).toString());
                    MyPacket goodbyePacket = MyPacket.create(1, 3);
                    goodbyePacket.setValue(1, "goodbye!");
                    out.write(goodbyePacket.toByteArray());
                    out.flush();

                    synchronized (serverPool) {
                        serverPool.notifyAll();
                    }

                    break;
                }

                if (packet.getType() == 4 && packet.getSubtype() == 7) {
                    System.out.println("secret key");
                    MyPacket handshakePacket = MyPacket.create(1, 2);
                    handshakePacket.setValue(1, "secret key");
                    out.write(handshakePacket.toByteArray());
                    out.flush();
                }

                if (packet.getType() == 4 && packet.getSubtype() == 6) {
                    System.out.println("protected : " + packet.getValue(1));
                    MyPacket protectedPacket = MyPacket.create(4, 6);
                    protectedPacket.setValue(1, "protected: " + packet.getValue(1));
                    out.write(protectedPacket.toByteArray());
                    out.flush();
                }

                if (packet.getType() == 4 && packet.getSubtype() == 5) {
                    System.out.println("json: " + packet.getValue(1));
                    MyPacket protectedPacket = MyPacket.create(4, 6);
                    protectedPacket.setValue(1, "json");
                    out.write(protectedPacket.toByteArray());
                    out.flush();
                }

            }
        } catch (IOException e) {
            System.out.println("Something wrong. " + e.getMessage());
        }
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        ThreadPoolExecutor serverPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        Server server = Server.create(2000, serverPool);

        while (true) {
            synchronized (serverPool) {
                serverPool.execute(server);

                if (serverPool.getActiveCount() >= 2) {
                    serverPool.wait();
                }
            }
        }
    }
}