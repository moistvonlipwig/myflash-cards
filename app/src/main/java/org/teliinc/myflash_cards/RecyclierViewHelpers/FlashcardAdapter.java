package org.teliinc.myflash_cards.RecyclierViewHelpers;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.teliinc.myflash_cards.Model.FlashCard;
import org.teliinc.myflash_cards.R;

import java.util.List;

public class FlashcardAdapter extends RecyclerView.Adapter<FlashcardViewHolder> {

    List<FlashCard> Flashcards;

    public FlashcardAdapter(List<FlashCard> Flashcards) {
        this.Flashcards = Flashcards;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public FlashcardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.flashcard_layout, viewGroup, false);
        FlashcardViewHolder pvh = new FlashcardViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(FlashcardViewHolder flashcardViewHolder, int i) {
        flashcardViewHolder.textViewQuestion.setText(Flashcards.get(i).getQuestion());
    }

    @Override
    public int getItemCount() {
        return Flashcards.size();
    }

}