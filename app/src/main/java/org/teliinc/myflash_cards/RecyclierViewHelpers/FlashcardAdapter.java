package org.teliinc.myflash_cards.RecyclierViewHelpers;


import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.teliinc.myflash_cards.Model.FlashCard;
import org.teliinc.myflash_cards.R;

import java.util.List;

public class FlashcardAdapter extends RecyclerView.Adapter<FlashcardViewHolder> {

    List<FlashCard> Flashcards;
    RecyclerView rv;
    public FlashcardAdapter(List<FlashCard> Flashcards) {
        this.Flashcards = Flashcards;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.rv = recyclerView;
    }

    @Override
    public FlashcardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.flashcard_layout, viewGroup, false);
        FlashcardViewHolder pvh = new FlashcardViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(FlashcardViewHolder flashcardViewHolder, int i) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(rv.getContext());
        int flashcardType = Integer.parseInt(sharedPref.getString("pref_flashcardType", "0"));
        if (flashcardType == 0)
            flashcardViewHolder.textViewQuestion.setText(Flashcards.get(i).getQuestion());
        else
            flashcardViewHolder.textViewQuestion.setText(Flashcards.get(i).getAnswer());
    }

    @Override
    public int getItemCount() {
        return Flashcards.size();
    }

}