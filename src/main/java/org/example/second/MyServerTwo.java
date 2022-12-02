package org.example.second;

import org.example.MyPacket;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServerTwo implements Runnable {
    private Integer port;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private InputStream inputStream;
    private OutputStream outputStream;

    private MyServerTwo() {

    }

    public static MyServerTwo create(Integer port) throws IOException {
        MyServerTwo server = new MyServerTwo();
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

                MyPacket response = MyPacket.create(1);
                response.setValue(1, value1);
                outputStream.write(response.toByteArray());
                outputStream.flush();
            }
        } catch (IOException e) {
            System.out.println("Something went wrong. Reason: " + e.getMessage());
        }
    }
    public static void main(String[] args) throws IOException {
        MyServerTwo server = MyServerTwo.create(4444);
        System.out.println("waiting client...........");

        while (true) {
            server.run();
        }
    }
}
