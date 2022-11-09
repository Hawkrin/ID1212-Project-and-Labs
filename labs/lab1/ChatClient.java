import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ChatClient {

    public static void main(String[] args) throws UnknownHostException, IOException {

        PrintWriter printWriter;

        try (Socket socket = new Socket("localhost", 9090)) {

            //writes to the server
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            Scanner scanner = new Scanner(System.in);
            String string = null;


            ServerListener clientSock = new ServerListener(socket);            
            new Thread(clientSock).start();


            while (!"exit".equalsIgnoreCase(string)) {
                
                // reads user ipnut
                string = scanner.nextLine();
    
                // writes to the server
                writer.println(string);
                writer.flush();
            }
            scanner.close();
        }
        catch (IOException e) {
            System.out.println("The server has been shut down");
        }
    }

}

    // Implements threading
    class ServerListener implements Runnable {
        private final Socket clientSocket;
        private static BufferedReader bufferReader;
        

        // Constructor
        public ServerListener(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {

            try { 
            
                // get the inputstream of client
                bufferReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String string;

                while ((string = bufferReader.readLine()) != null) {
                    System.out.printf(" Sent from the client: %s\n",string);
                }
            }
            catch (IOException e) {
                System.out.println("A client has left the server");
            }
            finally {
                try {
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
}

