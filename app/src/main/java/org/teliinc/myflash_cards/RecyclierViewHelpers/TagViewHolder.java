package org.teliinc.myflash_cards.RecyclierViewHelpers;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.teliinc.myflash_cards.R;

/**
 * Created by cteli on 12/13/2015.
 */
public class TagViewHolder extends RecyclerView.ViewHolder {
    CardView cv;
    TextView textViewTag;

    TagViewHolder(View itemView) {
        super(itemView);
        cv = (CardView) itemView.findViewById(R.id.cv_tag);
        textViewTag = (TextView) itemView.findViewById(R.id.textViewTag);
    }

    public void setQuestion(String question) {
        textViewTag.setText(question);
    }
}
