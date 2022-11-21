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

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class Imap {
   
    String host;
    int port;    

    SSLSocket socket;
    SSLSocketFactory factory;

    Socket _socket;

    BufferedReader reader;
    PrintWriter writer;

    int tagNumber = 1;
    public Imap(String host, int port) throws UnknownHostException, IOException {
        this.host = host;
        this.port = port;
    }

    public void start() throws UnknownHostException, IOException {
        System.out.println("Trying to connect to: " + host);
        this._socket = new Socket(host, port);

        this.reader = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
        this.writer = new PrintWriter(new OutputStreamWriter(_socket.getOutputStream()), false);

        System.out.println("Connected to: " + host);

        this.read();
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
        this.read();

    }

    public void send(String command) throws IOException {
        String tag = "a" + this.tagNumber++;
        String request = tag + " " + command;

        System.out.println("C: " + request);

        this.writer.print(command + "\r\n");
        this.writer.flush();

        while (!read().startsWith(tag)) {}
    }

    public String read() throws IOException { 
        String response = this.reader.readLine();

        System.out.println("S: " + response);

        return response;
    } 
}
