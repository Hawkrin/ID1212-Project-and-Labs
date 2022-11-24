package extra;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;

//Extra
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;

public class Server {

    public static void main(String[] args) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException, KeyManagementException {
        int port = 3001;
        ServerSocket server;
        Socket client;
        ArrayList<Model> models = new ArrayList<Model>();

        //Extra
        SSLServerSocketFactory ssf;
        KeyStore ks;
        InputStream is;
        SSLServerSocket ss;
        SSLSocket sc;

        try {

            ks = KeyStore.getInstance("PKCS12");
            is = new FileInputStream(new File(creds.FILE_PATH));

            char[] pwd = creds.KEYSTORE_PASSWORD.toCharArray();
            System.out.println("Accessing pfx...");
            ks.load(is, pwd);
            System.out.println("Done...");

            SSLContext ctx = SSLContext.getInstance("TLS");
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(ks, pwd);
            ctx.init(kmf.getKeyManagers(), null, null);

            ssf = ctx.getServerSocketFactory();
            for (int i = 0; i < ssf.getSupportedCipherSuites().length; i++) {
                //System.out.println(ssf.getSupportedCipherSuites()[i]);
            }

            ss = (SSLServerSocket) ssf.createServerSocket(port);
            String[] ciphers = {"TLS_RSA_WITH_AES_128_CBC_SHA"};
            ss.setEnabledCipherSuites(ciphers);

            
            //server = new ServerSocket(port);

            System.out.println("Server is starting up, listening on port: " + port + ".");
            System.out.println("Access server at https://localhost:" + port);

            while (true) {
                // System.out.println("Size of models: " + models.size());
                //Listening for client to access the server.
                sc = (SSLSocket) ss.accept();

                //Client has connected.
                //System.out.println("Client has connected: " + client.getInetAddress());

                //Creating new controller thread for client.
                new Controller(sc, models).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
