import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class main {
    public static void main(String[] args) throws UnknownHostException, IOException { 
        Scanner sc = new Scanner(System.in);

        String host = "smtp.kth.se";
        int port = 587;

        Imap imap = new Imap(host, port);
        imap.start();

        while (true) {
            imap.send(sc.nextLine());
        }

    }
}
