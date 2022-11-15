package v1;
import java.util.Random;

public class Model {

    String cookie;
    int amountOfTries = 0;
    int numberToGuess;

    public Model(String cookie) {
        this.cookie = cookie;
        
        Random rnd = new Random();
        numberToGuess = rnd.nextInt(100);
    }


    public boolean checkAnswer(int guess) { return guess == numberToGuess; }
    public void incGuess() { this.amountOfTries++; }
    public String getCookie() { return this.cookie; }
    public int getGuess() { return this.numberToGuess; }
    public int getAmountOfTries() { return this.amountOfTries; }

}
