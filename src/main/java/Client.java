import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
    private String host;
    private Integer port;
    private Socket socket;
    private InputStream in;
    private OutputStream out;

    private Client() {

    }

    public static Client initConnection(String host, Integer port) throws IOException {
        Client client = new Client();
        client.host = host;
        client.port = port;
        client.socket = new Socket(host, port);
        client.in = client.socket.getInputStream();
        client.out = client.socket.getOutputStream();

        return client;
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

    private void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }

    public void sendMessage(MyPacket packet) throws IOException {
        out.write(packet.toByteArray());
        out.flush();

        byte[] data = readInput(in);

        MyPacket responsePacket = MyPacket.parse(data);

        if (packet.getType() == 1 && packet.getSubtype() == 2) {
            String response = responsePacket.getValue(1).toString();
            System.out.println("handshake " + response);
            return;
        }

        String response = responsePacket.getValue(1);
        System.out.println(response);

        if (packet.getType() == 1 && packet.getSubtype() == 3) {
            close();
        }
    }
    public static void main(String[] args) throws IOException {
        Client client = Client.initConnection("localhost", 2000);
        MyPacket handshakePacket = MyPacket.create(1, 2);
        client.sendMessage(handshakePacket);

        MyPacket secretKeyPacket = MyPacket.create(4, 7);
        client.sendMessage(secretKeyPacket);

        MyPacket protectedPacket = MyPacket.create(4, 6);
        protectedPacket.setValue(1, "secret");
        client.sendMessage(protectedPacket);

        ObjectMapper objectMapper = new ObjectMapper();
        Book student = new Book("Война и мир", "Толстой", 259);
        MyPacket jsonPacket = MyPacket.create(4, 5);
        jsonPacket.setValue(1, objectMapper.writeValueAsString(student));
        client.sendMessage(jsonPacket);

        MyPacket goodbyePacket = MyPacket.create(1, 3);
        goodbyePacket.setValue(1, "goodbye!");
        client.sendMessage(goodbyePacket);
    }
}