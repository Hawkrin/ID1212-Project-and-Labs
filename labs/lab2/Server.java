import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    public static void main(String[] args) throws IOException {
        int port = 3009;
        Socket client;

        ArrayList<Model> models = new ArrayList<Model>();

        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("Server is starting up, listening on port: " + port + ".");
            System.out.println("Access server at http://localhost:" + port);

            while (true) {
                client = server.accept();
                new Controller(client, models).start();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("Server has been shutdown!");
        }
        
    }
} 