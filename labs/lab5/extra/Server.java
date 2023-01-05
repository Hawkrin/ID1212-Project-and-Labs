import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Server implements SendMail {
  final static String PASSWORD = creds.MAIL_PASSWORD;
  final static String USERNAME = creds.MAIL_USERNAME;
  private static String REMOTE_NAME = SendMail.class.getName();

    public Server() {}

    @Override
    public void sendEmails(String sendTo, String sendFrom, String host, int port) throws RemoteException {

      Properties properties = new Properties();  
      properties.put("mail.smtp.host", host);  
      properties.put("mail.smtp.user", USERNAME);
      properties.put("mail.smtp.port", port);
      properties.put("mail.smtp.auth", "true");
      properties.put("mail.smtp.starttls.enable" , true);
      properties.put("mail.smtp.ssl.protocols", "TLSv1.2");

      Session session = Session.getDefaultInstance(properties,  
      new javax.mail.Authenticator() {  
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {  
      return new PasswordAuthentication(USERNAME, PASSWORD);  
        }  
      }); 

      try {  
        
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(sendFrom));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sendTo));
        message.setSubject("Testing Subject");
        message.setText("Hello, this is Harry Lazaridis" );

        Transport.send(message);

        System.out.println("Sent message successfully....");

      } catch (MessagingException e) {throw new RuntimeException();}  
        
      }

    public static void main(String args[]) {

        try {
            Server obj = new Server();
            SendMail stub = (SendMail) UnicastRemoteObject.exportObject(obj, 1099);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind(REMOTE_NAME, stub);
            

            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}