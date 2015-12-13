package org.teliinc.myflash_cards.Activties;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.View;

import org.teliinc.myflash_cards.R;

public class MainActivity extends BaseMenuClass {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Enable Action Bar
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.show();

    }

    public void createCards(View view) {
        Intent intent = new Intent(this, CreateCardActivity.class);
        startActivity(intent);
    }

    public void browseCards(View view) {
        Intent intent = new Intent(this, BrowseCards.class);
        startActivity(intent);
    }
}
