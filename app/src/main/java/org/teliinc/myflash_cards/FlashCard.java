package org.teliinc.myflash_cards;

import java.util.List;

/**
 * Created by cteli on 11/29/2015.
 */
public class FlashCard {

    private String Question;
    private String Answer;
    public static List<FlashCard> RetreivedFlashCards;

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
