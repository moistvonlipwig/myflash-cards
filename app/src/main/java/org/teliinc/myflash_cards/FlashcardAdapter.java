package org.teliinc.myflash_cards;


import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class FlashcardAdapter extends RecyclerView.Adapter<FlashcardAdapter.FlashcardViewHolder>{

    public int current_card=0;

    public static class FlashcardViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView textViewQuestion;

        FlashcardViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            textViewQuestion= (TextView)itemView.findViewById(R.id.textViewQuestion);
        }
    }

    List<FlashCard> Flashcards;

    FlashcardAdapter(List<FlashCard> Flashcards){
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
        current_card = i;
        flashcardViewHolder.cv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DisplayCardActivity.class);
                intent.putExtra("QUESTION",Flashcards.get(current_card).getQuestion());
                intent.putExtra("ANSWER",Flashcards.get(current_card).getAnswer());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Flashcards.size();
    }

}