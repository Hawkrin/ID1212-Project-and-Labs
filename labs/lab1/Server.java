import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    

    private static class ClientHandler extends Thread {
        private Socket socket;
        private ArrayList<ClientHandler> threadList;
        private PrintWriter output;

        public ClientHandler(Socket socket, ArrayList<ClientHandler> threads) {

        }
    }
}

