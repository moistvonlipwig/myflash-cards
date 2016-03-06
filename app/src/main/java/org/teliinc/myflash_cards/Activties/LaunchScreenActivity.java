package org.teliinc.myflash_cards.Activties;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.auth.core.AuthProviderType;
import com.firebase.ui.auth.core.FirebaseLoginBaseActivity;
import com.firebase.ui.auth.core.FirebaseLoginError;

import org.teliinc.myflash_cards.Model.FlashCard;
import org.teliinc.myflash_cards.Model.FlashCardTag;
import org.teliinc.myflash_cards.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LaunchScreenActivity extends FirebaseLoginBaseActivity {

    // Tag for log messages
    final String TAG = "FlashCard";
    // Flashcard Reference
    static public Firebase firebaseRef;

    // Progress Marker
    int marker = 0;
    // ProgressBar for loading data
    private ProgressDialog progress;
    // String Name of authorization
    private String mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_screen);

        // Setup Firebase
        Firebase.setAndroidContext(this);

        // Get Flashcard Ref
        firebaseRef = new Firebase("https://teliflashcards.firebaseio.com/FlashCards");

        // Initialized the ProgressDialog and Start download
        progress = new ProgressDialog(this);
        progress.setMessage("Downloading FlashCards");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.setMax(100);
        progress.show();
    }

    @Override
    public void onStart() {
        super.onStart();

        //setEnabledAuthProvider(AuthProviderType.FACEBOOK);
        //setEnabledAuthProvider(AuthProviderType.TWITTER);
        setEnabledAuthProvider(AuthProviderType.GOOGLE);
        setEnabledAuthProvider(AuthProviderType.PASSWORD);
        showFirebaseLoginPrompt();
    }

    @Override
    public Firebase getFirebaseRef() {
        // Return your Firebase ref
        return firebaseRef;
    }

    @Override
    public void onFirebaseLoginProviderError(FirebaseLoginError firebaseError) {
        Log.e(TAG, "Login provider error: " + firebaseError.toString());
        resetFirebaseLoginPrompt();
    }

    @Override
    public void onFirebaseLoginUserError(FirebaseLoginError firebaseError) {
        Log.e(TAG, "Login user error: " + firebaseError.toString());
        resetFirebaseLoginPrompt();
    }

    @Override
    public void onFirebaseLoggedIn(AuthData authData) {
        Log.i(TAG, "Logged in to " + authData.getProvider().toString());

        switch (authData.getProvider()) {
            case "password":
                mName = (String) authData.getProviderData().get("email");
                break;
            default:
                mName = (String) authData.getProviderData().get("displayName");
                break;
        }

        invalidateOptionsMenu();
        //new AsyncDataLoad().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        //mRecycleViewAdapter.notifyDataSetChanged();
        changeIntent();
    }

    @Override
    public void onFirebaseLoggedOut() {
        Log.i(TAG, "Logged out");
        mName = "";
        invalidateOptionsMenu();
    }

    void changeIntent() {
        progress.dismiss();
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }
}


