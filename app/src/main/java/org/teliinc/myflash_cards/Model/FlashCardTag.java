package org.teliinc.myflash_cards.Model;

import java.util.Map;
import java.util.Set;

/**
 * Created by cteli on 12/13/2015.
 */
public class FlashCardTag {
    private Set<String> Questions;
    private String tag;
    public static Map<String,FlashCardTag> RetreivedTags;

    public FlashCardTag(String tag, Set<String> questions)
    {
        this.tag = tag;
        this.setQuestions(questions);
    }

    public Set<String> getQuestions()
    {   return Questions;
    }

    public void setQuestions(Set<String> questions) {
        this.Questions = questions;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
