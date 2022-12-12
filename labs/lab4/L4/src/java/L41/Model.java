package L41;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Random;
/**
 *
 * @author Harry
 */
public class Model {
    public Integer value;
    public int guesses;
    
    public Model() {
        this.setRandomNumber();
        this.guesses = 0;
    } 
    
    public int isCorrect(Integer guess) {
        this.guesses++;
        return this.value.compareTo(guess);
    }
    
    
    private void setRandomNumber() {
        Random random = new Random();
        this.value = random.nextInt(100);
    } 
    
    
    private void incrementGuesses() {
        this.guesses++;
    } 
}
