package org.example;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class MyServer implements Runnable {
    private Integer port;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private InputStream inputStream;
    private OutputStream outputStream;

    private MyServer() {

    }

    public static MyServer create(Integer port) throws IOException {
        MyServer server = new MyServer();
        server.port = port;
        server.serverSocket = new ServerSocket(port);
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
    private static String getCodingIncodMessage(String message, int key) {
        StringBuilder strBox = new StringBuilder(message.length());
        char tmp;
        for (int i = 0; i < message.length(); i++) {
            tmp = message.charAt(i);
            if (Character.isLetter(message.charAt(i))) {
                tmp += key % 26;
                if (tmp > 'z')
                    tmp = (char)(tmp % 'z' + 'a');
                else if (tmp < 'a')
                    tmp = (char)(tmp + 25);
            }
            strBox.append(tmp);
        }
        return strBox.toString();
    }

    @Override
    public void run() {
        try {
            clientSocket = serverSocket.accept();
            outputStream = clientSocket.getOutputStream();
            inputStream = new BufferedInputStream(clientSocket.getInputStream());


            while (true) {
                byte[] data = readInput(inputStream);
                MyPacket packet = MyPacket.parse(data);

                if (packet.getType() == 2) {
                    break;
                }

                String value1 = packet.getValue(1, String.class);
                String value2 = packet.getValue(2,String.class);

                MyPacket response = MyPacket.create(1);
                response.setValue(1, getCodingIncodMessage(value1, -Integer.parseInt(value2)));
                outputStream.write(response.toByteArray());
                outputStream.flush();
            }
        } catch (IOException e) {
            System.out.println("Something went wrong. Reason: " + e.getMessage());
        }
    }
    public static void main(String[] args) throws IOException {
        MyServer server = MyServer.create(4444);
        System.out.println("waiting...........");

        while (true) {
            server.run();
        }
    }
}
