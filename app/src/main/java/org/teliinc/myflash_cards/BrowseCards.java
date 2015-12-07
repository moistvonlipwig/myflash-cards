package org.teliinc.myflash_cards;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class BrowseCards extends AppCompatActivity {

    RecyclerView BrowseCardsView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_cards);


        BrowseCardsView = (RecyclerView)findViewById(R.id.flash_Card_view);

        BrowseCardsView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        BrowseCardsView.setLayoutManager(mLayoutManager);
        BrowseCardsView.setItemAnimator(new DefaultItemAnimator());

        // Initialize the
        mAdapter = new FlashcardAdapter(FlashCard.RetreivedFlashCards);
        BrowseCardsView.setAdapter(mAdapter);
    }

}
