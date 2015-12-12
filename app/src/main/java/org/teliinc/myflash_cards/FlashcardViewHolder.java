package org.teliinc.myflash_cards;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by cteli on 12/11/2015.
 */
public class FlashcardViewHolder extends RecyclerView.ViewHolder {
    CardView cv;
    TextView textViewQuestion;

    FlashcardViewHolder(View itemView) {
        super(itemView);
        cv = (CardView) itemView.findViewById(R.id.cv);
        textViewQuestion = (TextView) itemView.findViewById(R.id.textViewQuestion);
    }

    public void setQuestion(String question) {
        textViewQuestion.setText(question);
    }
}