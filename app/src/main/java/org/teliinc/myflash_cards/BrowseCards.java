package org.teliinc.myflash_cards;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.firebase.client.Firebase;


public class BrowseCards extends AppCompatActivity {

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

        BrowseCardsView = (RecyclerView)findViewById(R.id.flash_Card_view);

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
                        // TODO Handle item click
                    }
                })
        );

        mAdapter = new FlashcardAdapter(FlashCard.RetreivedFlashCards);
        BrowseCardsView.setAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_settings:
                // TODO : Toolbar show settings
                return true;
            case R.id.action_sections:
                // TODO : Toolbar Goto Main
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
