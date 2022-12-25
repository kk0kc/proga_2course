import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(2000)) {
            Socket socket;
            while (true) {
                System.out.println("waiting client 1");
                socket = serverSocket.accept();
                Client c1 = new Client(socket);


                System.out.println("waiting client 2");
                socket = serverSocket.accept();
                Client c2 = new Client(socket);

                Game game = new Game(c1, c2);
                game.run();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
