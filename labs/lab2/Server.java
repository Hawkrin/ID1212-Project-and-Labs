import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        final int PORT = 9090;

        //Initiating the server to start.
        ServerSocket server = new ServerSocket(PORT);   
        System.out.println("Server is now running on port: " + PORT);
        
        while (true) {
            Socket client = server.accept();
            System.out.println("Client connected: " + client.getInetAddress());
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String s;
            while((s = in.readLine())!=null){
                System.out.println(s);
                if(s.isEmpty()){
                    break;
                }
            }
            OutputStream clientOutput = client.getOutputStream();
            clientOutput.write("HTTP/1.1 200 OK\r\n".getBytes());
            clientOutput.write("\r\n".getBytes());
            clientOutput.write("<b>Hello World!</b>".getBytes());
            clientOutput.write("\r\n\r\n".getBytes());
            clientOutput.flush();

            in.close();
            clientOutput.close();
        }
    }
} 