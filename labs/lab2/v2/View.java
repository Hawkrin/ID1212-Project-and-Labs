package v2;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class View {

    PrintWriter clientWriter;
    Socket client;
    String cookie;
    Integer response;
    int amountOfTries;
    
    public View(Socket client, String cookie, Integer response, int amountOfTries) {
        try {
            this.client = client;
            this.clientWriter = new PrintWriter(client.getOutputStream(), false);
            this.response = response;
            this.cookie = cookie;
            this.amountOfTries = amountOfTries;
        } catch (IOException e) { e.printStackTrace(); }
    }


    public void sendResponse() {
        
        this.clientWriter.write("HTTP/1.0 200 OK\r\n");
        this.clientWriter.write("Content-Type: text/html\r\n");

        this.clientWriter.write("Set-Cookie: SESSID=" + this.cookie + "\r\n");
        this.clientWriter.write("\r\n");

        if (this.response == null) {
            this.clientWriter.write("<P>Welcome to the Number Guess Game. Guess a number between 1 and 100.</P>");
        }
        
        if (this.response != null) {
            if (this.response == -1) {
                this.clientWriter.write("<P>The number is higher</P>");
            } else if (this.response == 1) {
                this.clientWriter.write("<P>The number is lower</P>");
            } else {
                this.clientWriter.write("<P>You have won!</P>");
            }
            this.clientWriter.write("<P>Number of tries: " + this.amountOfTries + "</P>");
        }
        
        this.clientWriter.write("<form action=/ method='GET'>");
        this.clientWriter.write("<input type='text' name='guess'>");
        this.clientWriter.write("<input type='submit' value='Guess'>");
        this.clientWriter.write("</form");

        this.clientWriter.flush();
    } 
}
