package org.teliinc.myflash_cards.Activties;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.teliinc.myflash_cards.Model.FlashCard;
import org.teliinc.myflash_cards.Model.FlashCardTag;
import org.teliinc.myflash_cards.R;
import org.teliinc.myflash_cards.RecyclierViewHelpers.FlashcardAdapter;
import org.teliinc.myflash_cards.RecyclierViewHelpers.RecyclerItemClickListener;
import org.teliinc.myflash_cards.RecyclierViewHelpers.RecyclerItemTagClickListener;
import org.teliinc.myflash_cards.RecyclierViewHelpers.TagAdapter;

import java.util.ArrayList;

public class BrowseTags extends BaseMenuClass  {

    RecyclerView BrowseTagsView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_tags);

        BrowseTagsView = (RecyclerView) findViewById(R.id.tag_view);

        BrowseTagsView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        BrowseTagsView.setLayoutManager(mLayoutManager);
        BrowseTagsView.setItemAnimator(new DefaultItemAnimator());

        // Add a OnItemTouchListener
        BrowseTagsView.addOnItemTouchListener(
                new RecyclerItemTagClickListener(this, new RecyclerItemTagClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                    }
                })
        );

        mAdapter = new TagAdapter(FlashCardTag.getTags());
        BrowseTagsView.setAdapter(mAdapter);
    }
}
