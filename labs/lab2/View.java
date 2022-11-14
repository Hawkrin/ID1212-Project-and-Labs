import java.io.PrintWriter;

public class View {

  
    public static void sendRes(PrintWriter pw, Model model) {
        pw.write("HTTP/1.0 200 OK\r\n");
        pw.write("Content-Type: text/html\r\n");
        if (model != null)
            pw.write("Set-Cookie: SESSID=" + model.getCookie() + "\r\n");
        pw.write("\r\n");
        pw.write("<P>Welcome to the Number Guess Game. Guess a number between 1 and 100.</P>");
        pw.write("<form action=/ method='post'>");
        pw.write("<input type='text' name='guess'>");
        pw.write("<input type='submit' value='Guess'>");
        pw.write("</form");
    }

    public static void correctRes(PrintWriter pw, Model model) {
        pw.write("HTTP/1.0 200 OK\r\n");
        pw.write("Content-Type: text/html\r\n");
        pw.write("\r\n");
        pw.write("<P>You got the answer right! With: " + model.getAmountOfTries() + "</P>");
    }

    public static void wrongRes(PrintWriter pw, Model model, int guess) {
        pw.write("HTTP/1.0 200 OK\r\n");
        pw.write("Content-Type: text/html\r\n");
        if (model != null)
            pw.write("Set-Cookie: SESSID=" + model.getCookie() + "\r\n");
        pw.write("\r\n");
        pw.write("<P>Welcome to the Number Guess Game. Guess a number between 1 and 100.</P>");
        if( model.getGuess() > guess) {
            pw.write("<P> Try to guess higher </P>");
        }
        if( model.getGuess() < guess) {
            pw.write("<P> Try to guess lower </P>");
        }
        pw.write("<form action=/ method='post'>");
        pw.write("<input type='text' name='guess'>");
        pw.write("<input type='submit' value='Guess'>");
        pw.write("</form");
    }
}
