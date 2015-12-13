package org.teliinc.myflash_cards.Fragments;

/**
 * Created by cteli on 11/14/2015.
 */
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceFragment;
import android.util.Log;

import org.teliinc.myflash_cards.R;

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    // TODO : Settings
    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
    {
        Log.d("Debug","Preferences changed");
        //Synchronization changed
        if (key.equals("pref_sync"))
        {
            // Change summary
            Log.d("Debug",key);

            CheckBoxPreference syncPref = (CheckBoxPreference) findPreference(key);
            if(syncPref.isChecked())
                syncPref.setSummary("Checked on");
            else
                syncPref.setSummary("Checked off");
        }
    }
}