import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Controller extends Thread {
    Socket client;
    ArrayList<Model> models;

    public Controller(Socket client, ArrayList<Model> models) {
        this.client = client;
        this.models = models;
    }

    public void run() {
        try {
            PrintWriter pw = new PrintWriter(this.client.getOutputStream(), true);
            BufferedReader br = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
            ArrayList<String> Request = new ArrayList<String>();

            Model currentModel = null;

            String line;
            while ((line = br.readLine()) != null) {
                Request.add(line);

                if (line.equals("")) {
                    break; 
                }
           }

            String test = Request.get(0).split(" ")[1];
            if (!test.equals("/")) { System.out.print("Block: " + test); return; }   

            String cookie = getCookie(Request);
            if (cookie == null) {
                //Create new cookie;
                String newCookie = String.valueOf(models.size() + 1);

                //Create new model;
                Model newModel = new Model(newCookie);

                //Insert to models
                models.add(newModel);

                //set current model
                currentModel = newModel;
            } else {
                for (Model model : models) {
                    if (model.getCookie().equals(cookie)) {
                        currentModel = model;
                        break;
                    }
                }
            }

            if (getMethod(Request).equals("POST")) {
                int currentGuess = getGuess(Request, br);
                System.out.println(currentGuess);
                if (currentModel.checkAnswer(currentGuess) == true) {
                    View.correctRes(pw, currentModel); 
                } else {
                    View.wrongRes(pw, currentModel, currentGuess);
                    currentModel.incGuess();
                }

                pw.close();
            } else {
                View.sendRes(pw, currentModel);
                pw.close(); 
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getCookie(ArrayList<String> req) {

        for (String line : req) {
            String[] splited = line.split(" ");
            if (splited[0].equals("Cookie:")) {
                String[] cookie = splited[1].split("=");
                return cookie[1];
            } 
        }

        return null;
    }

    public int getGuess(ArrayList<String> req, BufferedReader br) throws IOException {
        String g = null;
        while ((g = br.readLine()) != null) {
            break;
        }
        return Integer.parseInt(g.split("=")[1]);
    } 

    public String getMethod(ArrayList<String> req) {
        return req.get(0).split(" ")[0];
    }
    
} 