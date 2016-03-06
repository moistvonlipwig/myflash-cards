package org.teliinc.myflash_cards.Activties;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.teliinc.myflash_cards.R;

import java.util.Map;

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
        Intent intent = new Intent(this, QuizCreateActivity.class);
        startActivity(intent);
    }

    public void browseCards(View view) {
        // Browse card based on settings file
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        int browseType = Integer.parseInt(sharedPref.getString("pref_browseType", "0"));

        Intent intent;
        if (browseType == 0)
            intent = new Intent(this, BrowseCards.class);
        else
            intent = new Intent(this, BrowseTags.class);

        startActivity(intent);
    }

    public void openSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
