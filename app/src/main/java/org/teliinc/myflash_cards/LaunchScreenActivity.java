package org.teliinc.myflash_cards;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.view.View;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class LaunchScreenActivity extends AppCompatActivity {

    // ProgressBar for loading data
    private ProgressDialog progress;

    // Flashcard Reference
    Firebase flashcardRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_screen);


        // Setup Firebase
        Firebase.setAndroidContext(this);

        // Initialized the ProgressDialog and Start download
        progress=new ProgressDialog(this);
        progress.setMessage("Downloading FlashCards");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();

        // Download in AsyncTask
        new AsyncDataLoad().execute(0);
    }

    class AsyncDataLoad extends AsyncTask<Integer, Integer, String> {

        // Initialize the progressbar
        protected void onPreExecute() {
            //do initialization of required objects objects here
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Integer... Params) {

            // Get Flashcard Ref
            flashcardRef = new Firebase("https://teliflashcards.firebaseio.com/FlashCards/");

            // Initialize FlashCard List
            // List will be populated from database
            FlashCard.RetreivedFlashCards = new ArrayList<>();

            // Read the data and store in FlashCards
            flashcardRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    // Iterate through the list and store in list
                    int count = 0;
                    progress.setMax((int) snapshot.getChildrenCount());
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        publishProgress(count++);
                        Map<String, String> map = (Map<String, String>) postSnapshot.getValue();
                        FlashCard f = new FlashCard(map.get("answer"), map.get("question"));

                        // TODO : Get Tags and update RetreivedFlashCards
                        FlashCard.RetreivedFlashCards.add(f);
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    System.out.println("The read failed: " + firebaseError.getMessage());
                }

            });
            return "Tast Completed";
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progress.dismiss();
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            startActivity(intent);
            // close this activity
            finish();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progress.setProgress(1);
            Log.i("ProgressUpdate", "Updating" + values[0]);
        }
    }
}


