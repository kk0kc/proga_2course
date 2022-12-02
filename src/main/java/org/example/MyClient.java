package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class MyClient {

    private String host;
    private Integer port;

    private Socket socket;

    private InputStream inputStream;
    private OutputStream outputStream;

    private MyClient() {

    }

    public static MyClient initConnection(String host, Integer port) throws IOException {
        MyClient client = new MyClient();
        client.host = host;
        client.port = port;
        client.socket = new Socket(host, port);
        client.inputStream = client.socket.getInputStream();
        client.outputStream = client.socket.getOutputStream();

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

    public void sendMessage(MyPacket packet) throws IOException {
        outputStream.write(packet.toByteArray());
        outputStream.flush();

        byte[] data = readInput(inputStream);
        MyPacket responsePacket = MyPacket.parse(data);

        String value1 = responsePacket.getValue(1, String.class);
        System.out.println("Encoded Message: " + value1);
    }

    public static void main(String[] args) throws IOException {
        MyClient client = MyClient.initConnection("localhost", 4444);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter message: ");
        MyPacket packet1 = MyPacket.create(1);
        packet1.setValue(1,scanner.nextLine());
        System.out.println("Enter number to encode : ");
        packet1.setValue(2,scanner.nextLine());
        client.sendMessage(packet1);


        MyPacket endPacket = MyPacket.create(2);
        client.sendMessage(endPacket);
    }
}
