package org.teliinc.myflash_cards.Activties;

import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.teliinc.myflash_cards.Model.FlashCardTag;
import org.teliinc.myflash_cards.RecyclierViewHelpers.FlashcardAdapter;
import org.teliinc.myflash_cards.Model.FlashCard;
import org.teliinc.myflash_cards.R;
import org.teliinc.myflash_cards.RecyclierViewHelpers.RecyclerItemClickListener;


public class BrowseCards extends BaseMenuClass {

    RecyclerView BrowseCardsView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_cards);

        // Enable Action Bar
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.show();

        BrowseCardsView = (RecyclerView) findViewById(R.id.flash_Card_view);

        BrowseCardsView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        BrowseCardsView.setLayoutManager(mLayoutManager);
        BrowseCardsView.setItemAnimator(new DefaultItemAnimator());

        // Add a OnItemTouchListener
        BrowseCardsView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                    }
                })
        );

        // Check if intent is passed some data based on it being a tag based set
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String tag = extras.getString("TAG");
            mAdapter = new FlashcardAdapter(FlashCardTag.getFlashCardSet(tag));
        } else {
            mAdapter = new FlashcardAdapter(FlashCard.RetreivedFlashCards);
        }
        BrowseCardsView.setAdapter(mAdapter);
    }
}
