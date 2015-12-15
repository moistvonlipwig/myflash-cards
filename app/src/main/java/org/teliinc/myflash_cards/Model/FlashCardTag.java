package org.teliinc.myflash_cards.Model;

import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by cteli on 12/13/2015.
 */
public class FlashCardTag {
    private String tag;
    public List<FlashCard> RetreivedQuestions;
    public static Map<String,FlashCardTag> tagQuestions;

    public FlashCardTag(String tag)
    {
        this.tag = tag;
        RetreivedQuestions = new ArrayList<>();
    }

    public List<FlashCard> getQuestions()
    {   return RetreivedQuestions;
    }

    public void setQuestions(List<FlashCard> questions) {
        this.RetreivedQuestions = questions;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    // Update RetreivedTags based on FlashCard table
    static public void updateFlashcard(String tag, FlashCard flashcard){
        if( tagQuestions.containsKey(tag))
        {
            FlashCardTag f = tagQuestions.get(tag);
            f.RetreivedQuestions.add(flashcard);
        } else {
            FlashCardTag f = new FlashCardTag(tag);
            f.RetreivedQuestions.add(flashcard);
            tagQuestions.put(tag,f);
        }
    }

    static public List<FlashCard> getFlashCardSet(String tag){
        return tagQuestions.get(tag).getQuestions();
    }

    public static List<String> getTags() {
        List tagsList = new ArrayList<>();
        tagsList.addAll(tagQuestions.keySet());
        return tagsList;
    }
}
