import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ChatClient {

    public static void main(String[] args) throws UnknownHostException, IOException {
        // establish a connection by providing host and port
        // number
        try (Socket socket = new Socket("localhost", 9091)) {

            //writes to the server
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            //reads from the server 
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            Scanner scanner = new Scanner(System.in);
            String string = null;
    
            while (!"exit".equalsIgnoreCase(string)) {
                
                // reads user ipnut
                string = scanner.nextLine();
    
                // writes to the server
                writer.println(string);
                writer.flush();
    
                // displaying server reply
                System.out.println("Server replied " + reader.readLine());
            }
            scanner.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

