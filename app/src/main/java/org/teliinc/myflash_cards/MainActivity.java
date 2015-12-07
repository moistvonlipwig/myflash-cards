package org.teliinc.myflash_cards;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO : For final project
        // Enable Action Bar
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.show();
    }

    /**
     *
     * @param view
     */
    public void openSettings(View view)
    {
        showSettings();
    }

    public void showSettings()
    {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.application_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_settings:
                showSettings();
                return true;
            case R.id.action_sections:
                // TODO : Display Sections
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
