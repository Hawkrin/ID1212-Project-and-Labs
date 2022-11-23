import java.io.IOException;
import java.net.UnknownHostException;

public class main {
    public static void main(String[] args) throws UnknownHostException, IOException { 

        String host = "webmail.kth.se";
        int port = 993;
        // String host = "smtp.kth.se";
        // int port = 587;

        Imap imap = new Imap(host, port);
        imap.start_ssl();
        // imap.start();

        while (true) {
            imap.send();
        }

    }
}
