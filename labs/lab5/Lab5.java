import java.util.Properties;
import javax.mail.Folder;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Lab5 {
  final static String PASSWORD = creds.MAIL_PASSWORD;
  final static String USERNAME = creds.MAIL_USERNAME;


  public static void sendEmails(String sendTo, String sendFrom, String host, int port) {
  
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

    } catch (MessagingException e) {throw new RuntimeException(e);}  
  } 

  public static void fetchEmails(String host, int port) {

    try {

    Properties properties = new Properties();
    properties.put("mail.imap.host", host);
    properties.put("mail.imap.port", port);
    properties.put("mail.imap.starttls.enable", "true");
    Session session = Session.getDefaultInstance(properties);

    Store store = session.getStore("imaps");
    store.connect(host, USERNAME, PASSWORD);

    Folder emailFolder = store.getFolder("INBOX");
    emailFolder.open(Folder.READ_ONLY);

    Message[] messages = emailFolder.getMessages();
    int numberOfMessagesToRetrieve = 1;

    for (int i = 0; i < numberOfMessagesToRetrieve; i++) {
      Message message = messages[i];
      System.out.println("---------------------------------");
      System.out.println("Email Number " + (i + 1));
      System.out.println("Subject: " + message.getSubject());
      System.out.println("From: " + message.getFrom()[0]);
      System.out.println("Text: " + message.getContent().toString());

    }

    emailFolder.close(false);
    store.close();

    } catch (Exception e) {
      e.printStackTrace();
  }
}

  public static void main(String[] args) {

    String from = "malcolml@kth.se";
    String to = "malcolm.liljedahl94@gmail.com"; 

    String sendingHost = "smtp.kth.se";
    int sendingPort = 587;

    String recievingHost = "webmail.kth.se";
    int recievingPort = 993;

    fetchEmails(recievingHost, recievingPort);

    //sendEmails(to, from, sendingHost, sendingPort);
  }


  
}