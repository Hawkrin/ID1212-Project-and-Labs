package extra;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Harry
 */

import java.util.ArrayList;
import java.util.List;

public class Questions {

  public String text;
  public List<String> optionList = new ArrayList<String>();
  public List<String> answerList = new ArrayList<String>();
  public int score;

  public Questions(String text, String options, String answer) {

    this.text = text; 

    String[] o = options.split("/");
    for (int i = 0; i < o.length; i++) {
      this.optionList.add(o[i]);
    }

    String[] a = answer.split("/");
    for (int i = 0; i < o.length; i++) {
      this.answerList.add(a[i]);
    }
  }

  public String getText() { return this.text; }
  public List<String> getOptions() { return this.optionList; }
  public List<String> getAnswer() { return this.answerList; }
  
  public void addScore(int score) {
      this.score = score;
  }

}
