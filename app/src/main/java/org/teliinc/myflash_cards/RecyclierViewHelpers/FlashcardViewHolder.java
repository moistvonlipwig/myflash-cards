package org.teliinc.myflash_cards.RecyclierViewHelpers;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.teliinc.myflash_cards.R;
import org.w3c.dom.Text;

/**
 * Created by cteli on 12/11/2015.
 */
public class FlashcardViewHolder extends RecyclerView.ViewHolder {
    View mView;

    FlashcardViewHolder(View itemView) {
        super(itemView);
    }

    public void setQuestion(String question) {
        ((TextView) itemView.findViewById(R.id.textViewAnswer)).setText(question);
    }
}