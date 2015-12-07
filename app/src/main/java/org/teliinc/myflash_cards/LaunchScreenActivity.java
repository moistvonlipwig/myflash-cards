package org.teliinc.myflash_cards;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LaunchScreenActivity extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_screen);

        // TODO : Use AsyncTask to retrieve data and display progress bar
        // Setup Firebase
        Firebase.setAndroidContext(this);

        // Read the data and store in FlashCards
        Firebase flashcardRef = new Firebase("https://teliflashcards.firebaseio.com/FlashCards/");

        FlashCard.RetreivedFlashCards =  new ArrayList<>();
        // Attach an listener to read the data at our posts reference
        flashcardRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // Iterate through the list and store in list
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Map<String,String> map = (Map<String,String>)postSnapshot.getValue();
                    FlashCard f = new FlashCard(map.get("answer"),map.get("question"));

                    // TODO : Get Tags and update RetreivedFlashCards
                    FlashCard.RetreivedFlashCards.add(f);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });


        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(LaunchScreenActivity.this, MainActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
