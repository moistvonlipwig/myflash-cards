package org.teliinc.myflash_cards.Model;

import org.parceler.Parcel;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by cteli on 11/29/2015.
 */
@Parcel
public class FlashCard {

    public static final int FLASHCARD_CREATED = 1000;
    public String Question;
    public String Answer;
    public List<String> Tags;

    // Needed by parceler
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

    public List<String> getTags() {
        return Tags;
    }

    public void setTags(List<String> tags) {
        Tags = tags;
    }
}
