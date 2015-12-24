package org.teliinc.myflash_cards.Activties;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.teliinc.myflash_cards.Model.FlashCard;
import org.teliinc.myflash_cards.Model.FlashCardTag;
import org.teliinc.myflash_cards.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LaunchScreenActivity extends AppCompatActivity {

    // ProgressBar for loading data
    private ProgressDialog progress;

    // Flashcard Reference
    Firebase firebaseRef;

    // Progress Marker
    int marker = 0;

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
        progress.setMax(100);
        progress.show();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Download in AsyncTask
        // new AsyncDataLoad().execute(0);
        // To run in parallel
        //readData();
        //changeIntent();
        new AsyncDataLoad().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    class AsyncDataLoad extends AsyncTask<Integer, Integer, String> {

        // Initialize the progressbar
        protected void onPreExecute() {
            //do initialization of required objects objects here
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Integer... Params) {

            readData();
            return "Tast Completed";
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            changeIntent();
            // close this activity
            finish();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progress.setProgress(marker++);
            Log.i("ProgressUpdate", "Updating" + values[0]);
        }
    }

    void readData()
    {
        // Get Flashcard Ref
        firebaseRef = new Firebase("https://teliflashcards.firebaseio.com/");
        marker=40;
        progress.setProgress(marker);

        FlashCard.RetreivedFlashCards = new ArrayList<>();
        FlashCardTag.tagQuestions = new HashMap<String, FlashCardTag>();
        marker=80;
        progress.setProgress(marker);

        // Read the flashcard data and store in FlashCards
        firebaseRef.child("FlashCards").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // Iterate through the list and store in list
                int count = 0;
                // This function is called everytime something changes so I have to reset the data
                // TODO : Progress bar does not feel right
                FlashCard.RetreivedFlashCards.clear();
                FlashCardTag.tagQuestions.clear();
                progress.setMax(100 + (int) snapshot.getChildrenCount());
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    progress.setProgress(count++);
                    Map<String, String> map = (Map<String, String>) postSnapshot.getValue();
                    FlashCard f = new FlashCard(map.get("answer"), map.get("question"));

                    // Read the tags and update the flashcard
                    Map<String, String> tags = (Map<String, String>) postSnapshot.child("Tags").getValue();
                    // When object is first created it has no tags
                    if ( tags != null) {
                        f.setTags(tags.keySet());

                        FlashCard.RetreivedFlashCards.add(f);

                        // Iterate through the tags and setup the Tag Hashset
                        for (String tag : tags.keySet()) {
                            FlashCardTag.updateFlashcard(tag, f);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }

        });
    }

    void changeIntent(){
        progress.dismiss();
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }
}


