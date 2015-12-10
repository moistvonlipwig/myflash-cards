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

        // Build Dynamic Menu
        // Add either a "photo" or "finish" button to the action bar, depending on which page
        // is currently selected.
        // TODO : Build dynamic menu
        /*MenuItem item = menu.add(Menu.NONE, R.id.action_flip, Menu.NONE,
                mShowingBack
                        ? R.string.action_photo
                        : R.string.action_info);
        item.setIcon(mShowingBack
                ? R.drawable.ic_action_photo
                : R.drawable.ic_action_info);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);*/

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
                // TODO : Toolbar Display Sections
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
