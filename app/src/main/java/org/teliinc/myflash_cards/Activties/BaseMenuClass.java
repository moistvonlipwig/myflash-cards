package org.teliinc.myflash_cards.Activties;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.teliinc.myflash_cards.R;

/**
 * Created by cteli on 12/13/2015.
 */
public class BaseMenuClass extends AppCompatActivity {

    public void showSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

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
            case R.id.action_browse:
                showBrowsePage();
                return true;
            case R.id.action_home:
                showHomePage();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showHomePage() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void showBrowsePage() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        int browseType = Integer.parseInt(sharedPref.getString("pref_browseType", "0"));

        Intent intent;
        if (browseType == 0)
            intent = new Intent(this, BrowseCards.class);
        else
            intent = new Intent(this, BrowseTags.class);
        
        startActivity(intent);
    }

}
