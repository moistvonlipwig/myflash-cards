package org.teliinc.myflash_cards.Activties;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.ui.auth.core.AuthProviderType;
import com.firebase.ui.auth.core.FirebaseLoginBaseActivity;
import com.firebase.ui.auth.core.FirebaseLoginError;

import org.teliinc.myflash_cards.BuildConfig;
import org.teliinc.myflash_cards.R;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

public class LaunchScreenActivity extends FirebaseLoginBaseActivity {

    // Flashcard Reference
    static public Firebase firebaseRef;
    // Tag for log messages
    final String TAG = "FlashCard";
    // Progress Marker
    int marker = 0;
    // ProgressBar for loading data
    private ProgressDialog progress;
    // String Name of authorization
    private String mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_launch_screen);

        // Setup Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        Timber.plant(new CrashlyticsTree());

        // Setup Firebase
        Firebase.setAndroidContext(this);

        // Get Flashcard Ref
        firebaseRef = new Firebase("https://teliflashcards.firebaseio.com/");

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
        Timber.e(TAG, "Login provider error: " + firebaseError.toString());
        resetFirebaseLoginPrompt();
    }

    @Override
    public void onFirebaseLoginUserError(FirebaseLoginError firebaseError) {
        Timber.e(TAG, "Login user error: " + firebaseError.toString());
        resetFirebaseLoginPrompt();
    }

    @Override
    public void onFirebaseLoggedIn(AuthData authData) {
        Timber.i(TAG, "Logged in to " + authData.getProvider().toString());

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
        Timber.i(TAG, "Logged out");
        mName = "";
        invalidateOptionsMenu();
    }

    void changeIntent() {
        progress.dismiss();
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }

    // Tree for logging
    public class CrashlyticsTree extends Timber.Tree {
        private static final String CRASHLYTICS_KEY_PRIORITY = "priority";
        private static final String CRASHLYTICS_KEY_TAG = "tag";
        private static final String CRASHLYTICS_KEY_MESSAGE = "message";

        @Override
        protected void log(int priority, String tag, String message, Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO) {
                return;
            }

            Crashlytics.setInt(CRASHLYTICS_KEY_PRIORITY, priority);
            Crashlytics.setString(CRASHLYTICS_KEY_TAG, tag);
            Crashlytics.setString(CRASHLYTICS_KEY_MESSAGE, message);

            if (t == null) {
                Crashlytics.logException(new Exception(message));
            } else {
                Crashlytics.logException(t);
            }
        }
    }
}


