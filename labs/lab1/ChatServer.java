import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

// Server class
class ChatServer {
    public static void main(String[] args) {
        
        ServerSocket server = null;

        try {

            server = new ServerSocket(9090);

            //getting client requests
            while (true) {

                // receiving incoming clients
                Socket client = server.accept();

                System.out.println("New client connected " + client.getInetAddress().getHostAddress());

                // create a new thread
                ClientHandler clientSock = new ClientHandler(client);

                // This thread will handle the client separately
                new Thread(clientSock).start();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (server != null) {
                try {
                    server.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Implements threading
    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        // Constructor
        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {

            PrintWriter printWriter = null;
            BufferedReader bufferReader = null;

            try {
                    
                // get the outputstream  of client
                printWriter = new PrintWriter(clientSocket.getOutputStream(), true);

                // get the inputstream of client
                bufferReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String string;

                while ((string = bufferReader.readLine()) != null) {
                    
                System.out.printf(" Sent from the client: %s\n",string);
                printWriter.println(string);

                }
            }
            catch (IOException e) {
                e.printStackTrace();
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
                    e.printStackTrace();
                }
            }
        }
    }
}