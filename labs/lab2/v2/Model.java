package v2;

import java.util.Random;

public class Model {

    String cookie;
    int amountOfTries;
    Integer numberToGuess;

    public Model(String cookie) {
        this.cookie = cookie;
        this.amountOfTries = 0;

        Random rnd = new Random();
        this.numberToGuess = rnd.nextInt(100);
    }

    public Integer gameLogic(Integer guess) {
        if (guess != null) {
            this.amountOfTries++;
            return guess.compareTo(this.numberToGuess);
        }
        return null;
    }

}
