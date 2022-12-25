import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    Socket socket;
    InputStream is;
    OutputStream os;
    String name;

    Client(Socket socket) throws IOException {
        this.socket = socket;
        is = socket.getInputStream();
        os = socket.getOutputStream();
        write("Type your name: ");
        name = read();
        System.out.println("Player joined: " + name);
    }

    public String read(){
        String msg = "";
        boolean exit = false;
        while (!exit){
            try {
                if (is.available() > 0) {
                    int d;
                    while ((d = is.read()) != 38) {
                        msg = msg + (char) d;
                    }
                    exit = true;
                }
            } catch (IOException e) {
                System.out.println("Error reading msg");
            }
        }
        return msg;
    }

    public void write(String msg){
        try {
            os.write((msg+"&").getBytes());
            os.flush();
        } catch (IOException e) {
            System.out.println("Error sending");
        }
    }

    public void close() {
        try {
            socket.close();
            os.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 2000);

        OutputStream os = socket.getOutputStream();
        InputStream is = socket.getInputStream();

        Thread read = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if (is.available() > 0) {
                            int d = 0;
                            String msg = "";
                            while ((d = is.read()) != 38) {
                                msg = msg + (char) d;
                            }
                            System.out.println(msg);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        read.start();

        Thread write = new Thread(new Runnable() {
            @Override
            public void run() {
                Scanner sc = new Scanner(System.in);
                while (true) {
                    String msg = sc.nextLine();
                    try {
                        os.write((msg + "&").getBytes());
                        os.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        write.start();
    }
}
