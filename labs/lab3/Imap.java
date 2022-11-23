import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Scanner;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class Imap {

    String host;
    int port;    

    SSLSocket socket;
    SSLSocketFactory factory;
    Scanner sc;
    Socket _socket;

    BufferedReader reader;
    PrintWriter writer;

    int tagNumber = 1;
    public Imap(String host, int port) throws UnknownHostException, IOException {
        this.host = host;
        this.port = port;
        this.sc = new Scanner(System.in);
    }

    public void start_smtp() throws UnknownHostException, IOException {
        System.out.println("Trying to connect to: " + host);

        this._socket = new Socket(this.host, this.port);

        this.reader = new BufferedReader(new InputStreamReader(_socket.getInputStream(), "utf-8"));
        this.writer = new PrintWriter(new OutputStreamWriter(_socket.getOutputStream(), StandardCharsets.UTF_8), true);

        this.read_smtp();
        this.write_raw("EHLO harrylaz.kth.se");
        this.read_smtp();
        this.write_raw("STARTTLS");
        this.read_smtp();

        
        SSLSocketFactory _factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket _sslSocket = (SSLSocket) _factory.createSocket(this._socket, this.host, this.port, false);

        this.reader = new BufferedReader(new InputStreamReader(_sslSocket.getInputStream()));
        this.writer = new PrintWriter(new OutputStreamWriter(_sslSocket.getOutputStream(), StandardCharsets.UTF_8), true);

        this.write_raw("EHLO harrylaz.kth.se");
        this.read_smtp();

        this.write_raw("AUTH LOGIN");
        this.read_smtp();

        this.write_raw(Base64.getEncoder().encodeToString(creds.username.getBytes()));
        this.read_smtp();

        this.write_raw(Base64.getEncoder().encodeToString(creds.password.getBytes()));
        this.read_smtp();

        this.write_raw("MAIL FROM: <harrylaz@kth.se>");
        this.read_smtp();

        this.write_raw("RCPT TO: <malcolml@kth.se>");
        this.read_smtp();

        this.write_raw("DATA");
        this.read_smtp();

        this.write_raw("Hello Malcolm! Detta är Harry om det inte var tydligt nog.");
        this.write_raw(".");
        this.read_smtp();

        this.write_raw("QUIT");
        this.read_smtp();
    }


    public void start_ssl() throws UnknownHostException, IOException {
        System.out.println("Trying to connect to: " + host);
        this.factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        this.socket = (SSLSocket) factory.createSocket(host, port);

        System.out.println("Started handshake.");
        this.socket.startHandshake();
        System.out.println("Handshake done.");

        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), false);

        System.out.println("Connected to: " + host);

        //Reading server greeting.
        this.read_ssl();

    }

    public void send_ssl() throws IOException {
        String command = this.reader.readLine();
        String tag = "a" + this.tagNumber++;
        String request = tag + " " + command;

        System.out.println("C: " + request);

        this.writer.print(request + "\r\n");
        this.writer.flush();

        while (!read_ssl().startsWith(tag)) {}
    }

    private String read_ssl() throws IOException { 
        String response = this.reader.readLine();

        System.out.println("S: " + response);

        return response;
    } 

    public void send() throws IOException {

        System.out.print("C: ");
        String command = this.sc.nextLine();

        if(command.equals("flush")) {
            this.writer.flush();
            this.read_smtp();
            return;
        }

        this.writer.print(command + "\r\n");

        if(command.equals(".")) { 
            this.writer.flush();
            this.read_smtp();
        }
    }

    private void write_raw(String command) {
        System.out.println("C: " + command);
        this.writer.print(command + "\r\n");
        this.writer.flush();
    }

    private void read_smtp() throws IOException {
        while(true) {
            String response = this.reader.readLine();
            System.out.println("S: " + response);
            if (
                Character.isDigit(response.charAt(0)) &&
                Character.isDigit(response.charAt(1)) &&
                Character.isDigit(response.charAt(2)) &&
                Character.isWhitespace(response.charAt(3))
            ) { 
                break; 
            }

        }
    }
}
