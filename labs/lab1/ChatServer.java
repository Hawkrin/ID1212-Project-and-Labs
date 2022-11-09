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
        }

        catch (IOException e ) {
            System.out.println("A client has left the server");
        }
        finally {
            if (server == null)
                return;
           
            try {
                server.close();
            } catch (IOException e) {
                System.out.println("A client has left the server");
            } 
        }
    }

    // Implements threading
    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;
        private final ArrayList<ClientHandler> clients;
        
        private PrintWriter printWriter;
        private BufferedReader bufferReader;
        // Constructor
        public ClientHandler(Socket socket, ArrayList<ClientHandler> clients) {
            this.clientSocket = socket;
            this.clients = clients;
        }

        public void run() {

            try { 
                // get the outputstream  of client
                printWriter = new PrintWriter(clientSocket.getOutputStream(), true);

                // get the inputstream of client
                bufferReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String string;

                while ((string = bufferReader.readLine()) != null) {
                    System.out.printf(" Sent from the client: %s\n",string);
                    broadcast(string);
                }
            }
            catch (IOException e) {
                System.out.println("A client has left the server");
            }
            finally {
                try {
                    if (printWriter != null) {
                        printWriter.close();
                    }
                    if (bufferReader != null) {
                        bufferReader.close();
                        clientSocket.close();
                    }
                }
                catch (IOException e) {
                    System.out.println("A client has left the server");
                }
            }
        }

        private void broadcast(String message) {
            for ( ClientHandler client : clients) {
                System.out.println("hit");
                client.printWriter.println(message);
            }
        }
    }
}