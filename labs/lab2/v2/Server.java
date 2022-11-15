package v2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    public static void main(String[] args) {
        int port = 3001;
        ServerSocket server;
        Socket client;
        ArrayList<Model> models = new ArrayList<Model>();

        try {
            server = new ServerSocket(port);

            System.out.println("Server is starting up, listening on port: " + port + ".");
            System.out.println("Access server at http://localhost:" + port);

            while (true) {
                System.out.println("Size of models: " + models.size());
                //Listening for client to access the server.
                client = server.accept();

                //Client has connected.
                //System.out.println("Client has connected: " + client.getInetAddress());

                //Creating new controller thread for client.
                new Controller(client, models).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
