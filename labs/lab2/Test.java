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
    
            out.write("HTTP/1.0 200 OK\r\n");
            out.write("Date: Fri, 31 Dec 1999 23:59:59 GMT\r\n");
            out.write("Server: Apache/0.8.4\r\n");
            out.write("Content-Type: text/html\r\n");
            out.write("Expires: Sat, 01 Jan 2000 00:59:59 GMT\r\n");
            out.write("Last-modified: Fri, 09 Aug 1996 14:21:40 GMT\r\n");
            out.write("\r\n");
            out.write("<P>Welcome to the Number Guess Game. Guess a number between 1 and 100.</P>");
            out.write("<input/>");
            out.write("<BUTTON>Guess</BUTTON>");


            System.err.println("Connection closed");
            out.close();
            in.close();
            clientSocket.close();
    }
}
