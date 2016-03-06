package org.teliinc.myflash_cards.Model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by cteli on 11/29/2015.
 */
public class FlashCard {

    private String Question;
    private String Answer;

    public FlashCard()
    {
        Answer = "";
        Question = "";
    }
    public FlashCard(String ans, String ques)
    {
        Answer = ans;
        Question = ques;
    }

    public String getQuestion()
    {   return Question;
    }


    public void setQuestion(String question) {
        Question = question;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }
}
