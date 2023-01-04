import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SendMail extends Remote {
  
    void sendEmails(String sendTo, String sendFrom, String host, int port) throws RemoteException;
}