package org.teliinc.myflash_cards.RecyclierViewHelpers;

/**
 * Created by cteli on 12/13/2015.
 */
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.teliinc.myflash_cards.Model.FlashCard;
import org.teliinc.myflash_cards.R;

import java.util.List;

public class TagAdapter extends RecyclerView.Adapter<TagViewHolder> {

    List<String> Tags;

    public TagAdapter(List<String> Tags) {
        this.Tags = Tags;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public TagViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tag_layout, viewGroup, false);
        TagViewHolder pvh = new TagViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(TagViewHolder tagViewHolder, int i) {
        tagViewHolder.textViewTag.setText(Tags.get(i));
    }

    @Override
    public int getItemCount() {
        return Tags.size();
    }

}