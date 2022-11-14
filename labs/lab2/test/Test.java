package test;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Test {

    public static void main(String[] args) throws Exception {
        int port = 9090;
        ServerSocket serverSocket = new ServerSocket(port);
        System.err.println("Server is now running on port: " + port);

        Socket clientSocket = serverSocket.accept();
        System.err.println("A new client has connected");

        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

        String string;
        while ((string = in.readLine()) != null) {
            System.out.println(string);
            if (string.isEmpty()) {
                break;
            }
        }

   

        System.err.println("Connection closed");
        out.close();
        in.close();
        clientSocket.close();
    }
}
