package org.teliinc.myflash_cards.Model;

import org.parceler.Parcel;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by chris.teli on 3/5/2016.
 */
@Parcel
public class Quiz {
    public List<FlashCard> flashCards;
    public String Title;
    public String Description;

    public Quiz()
    {
        Title = "";
        Description = "";

    }
    public Quiz(String title, String description)
    {
        Title = title;
        Description = description;
    }

    public List<FlashCard> getFlashCards() {
        return flashCards;
    }

    public void setFlashCards(List<FlashCard> flashCards) {
        this.flashCards = flashCards;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
