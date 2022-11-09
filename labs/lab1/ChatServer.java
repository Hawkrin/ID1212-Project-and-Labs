import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

class ChatServer {
    public static void main(String[] args) {
        
        ServerSocket server = null;
        final Integer PORT = 9090;
        ArrayList<ClientHandler> clients = new ArrayList<>();

        try {
            server = new ServerSocket(PORT);
            System.out.println("Server is running on port: " + PORT);

            while (true) {
                Socket client = server.accept(); 
                System.out.println("New client connected " + client.getInetAddress().getHostAddress());

                ClientHandler clientSock = new ClientHandler(client, clients);
                clients.add(clientSock);              
                new Thread(clientSock).start();
            }
        } catch (Exception e ) {
            System.out.println(e.getMessage());
        }
    }

    // Implements threading
    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;
        private final ArrayList<ClientHandler> clients;
        
        private PrintWriter printWriter;
        private BufferedReader bufferReader;
        
        public ClientHandler(Socket socket, ArrayList<ClientHandler> clients) {
            this.clientSocket = socket;
            this.clients = clients;
        }

        public void run() {
            try { 
                printWriter = new PrintWriter(clientSocket.getOutputStream(), true); 
                bufferReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String string;
                while ((string = bufferReader.readLine()) != null) {
                    if (string.equals("exit")) {
                        broadcast("has left the server.");
                        clients.remove(this);
                        continue;
                    } else {
                        broadcast(string);
                    } 
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            } 
        }

        private void broadcast(String message) {
            for ( ClientHandler client : clients) {
                if (this == client) {
                    continue;
                } 
                client.printWriter.println(client.clientSocket.getInetAddress() + ": " + message); 
            }
        }
    }
}