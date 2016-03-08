package org.teliinc.myflash_cards.Model;

import org.parceler.Parcel;

/**
 * Created by cteli on 11/29/2015.
 */
@Parcel
public class FlashCard {

    public static final int FLASHCARD_CREATED = 1000;
    public String Question;
    public String Answer;
    public String Query;

    // Needed by parceler
    public FlashCard() {
        Answer = "";
        Question = "";
    }

    public FlashCard(String ans, String ques) {
        Answer = ans;
        Question = ques;
    }

    public String getQuestion() {
        return Question;
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

    public String getQuery() {
        return Query;
    }

    public void setQuery(String query) {
        Query = query;
    }
}
