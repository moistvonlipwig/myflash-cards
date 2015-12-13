package org.teliinc.myflash_cards.Activties;

import android.os.Bundle;

import org.teliinc.myflash_cards.R;
import org.teliinc.myflash_cards.Fragments.SettingsFragment;

public class SettingsActivity extends BaseMenuClass {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Display SettingsFragment as the main content
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new SettingsFragment())
                .commit();
    }
}