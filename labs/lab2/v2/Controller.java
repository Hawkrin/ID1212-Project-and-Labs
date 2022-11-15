package v2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class Controller extends Thread { 
    Socket client;
    String cookie;
    String method;
    ArrayList<String> Request = new ArrayList<String>();
    ArrayList<Model> models;
    Model currentModel = null;
    Integer guess = null;

    BufferedReader clientReader;

    public Controller(Socket client, ArrayList<Model> models) {
        this.client = client; 
        this.models = models;
    }

    public void run() {
        try {
            //Creating a reader from client.
            this.clientReader = new BufferedReader(new InputStreamReader(this.client.getInputStream()));

            //Listening for request from client
            this.readRequest();
        
            //Checking if request is from correct url.
            if (!this.isRequestBlackList()) { return; }

            //Give client's cookie
            this.giveCookie();

            //Checking if user has a model
            this.userExists();

            //Get guess if it exists
            this.getGuess();

            Integer response = this.currentModel.gameLogic(guess);

            //Creating a view for the client to render html
            View userView = new View(this.client, this.cookie, response, this.currentModel.amountOfTries);
            userView.sendResponse();

            //Closing connection with client.
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getGuess() {
        String guessPosition = this.Request.get(0).split(" ")[1];
        if (guessPosition.length() > 2) {
            this.guess = Integer.valueOf(guessPosition.split("=")[1]);
        }
    }

    public void userExists() {
        //Checking if user already is playing
        for (Model model : this.models) {
            if (model.cookie.equals(this.cookie)) {
                this.currentModel = model;
                return;
            }
        }

        //Creating a new game for user.
        this.currentModel = new Model(this.cookie);
        this.models.add(this.currentModel); 
        return; 
    }

    public void giveCookie() {
        //Checking if user has cookie
        for (String line : this.Request) {
            if (line.split(" ")[0].equals("Cookie:")) {
                this.cookie = line.split(" ")[1].split("=")[1];
                return;
            }
        }

        //User does not have cookie
        this.cookie = String.valueOf(this.models.size() + 1);
        return;
    }

    public boolean isRequestBlackList() {
        String[] blacklist = {
            "/favicon.ico"
        };
        String req = this.Request.get(0).split(" ")[1];

        for(String line : blacklist) {
            if(line.equals(req)){ return false; }
        }
        return true;
    }

    public void printRequest() {
        for (String line : this.Request) { System.out.println(line); }
    }

    public void readRequest() {
        try {
            String s;
            while ((s = this.clientReader.readLine()) != null) {
                this.Request.add(s);

                if(s.isEmpty()) { break; }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkingTypeOfRequest() {
        this.method = this.Request.get(0).split(" ")[0]; 
    }


}
