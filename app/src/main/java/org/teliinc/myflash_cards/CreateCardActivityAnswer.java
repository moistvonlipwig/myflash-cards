package org.teliinc.myflash_cards;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.Iterator;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateCardActivityAnswer extends AppCompatActivity {

    @Bind(R.id.button_create_answer)
    Button button_create;
    @Bind(R.id.editTextAnswer)
    EditText editTextAnswer;
    @Bind(R.id.editTextTags)
    EditText editTextTags;

    private String Question;
    private String Answer;
    private boolean WriteStatus;

    private Map<String, Object> TagQuestion, QuestionTag;
    private String CurrentTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card_answer);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Question = extras.getString("QUESTION");
        }
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_create_answer)
    public void create_card_answer(View view) {

        // Initialize Firebase
        Firebase.setAndroidContext(this);
        Firebase myFirebaseRef = new Firebase("https://teliflashcards.firebaseio.com/");

        // Create the flashcard object
        Answer = editTextAnswer.getText().toString();
        FlashCard flashCard = new FlashCard(Answer, Question);

        // Store the data to server
        // Using a CountDownLatch to prevent moving on without saving
        final CountDownLatch done = new CountDownLatch(2);

        // Store the Flashcard question
        List<String> Tags = new ArrayList<String>(Arrays.asList(editTextTags.getText().toString().split(" ")));
        Firebase QuestionRef = myFirebaseRef.child("FlashCards").push();
        QuestionRef.setValue(flashCard, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    WriteStatus = false;
                    Log.d("FireBase Write", "Data could not be saved. " + firebaseError.getMessage());
                } else {
                    Log.d("FireBase Write", "Data saved successfully. Finishing activity...");
                    WriteStatus = true;
                    finish();
                }
            }
        });
        done.countDown();

        // Store the Tag and since we are using NOSQL we have to store the Tags to create the
        // 2-way relationship
        QuestionTag = new HashMap<String, Object>();
        TagQuestion = new HashMap<String, Object>();
        Firebase TagsFirebaseRef = new Firebase("https://teliflashcards.firebaseio.com/Tags/");
        for (String Tag : Tags) {
            CurrentTag = Tag;
            // Create Associated Tags
            TagQuestion.clear();
            // Add current question
            TagQuestion.put(Question, true);
            TagsFirebaseRef.child(CurrentTag).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    // Tag Exists
                    if (snapshot.getValue() != null) {
                        // Add all the existing questions
                        for (DataSnapshot postSnapshot : snapshot.child("Questions").getChildren()) {
                            TagQuestion.put(postSnapshot.getKey().toString(), true);
                        }
                    }
                }

                @Override
                public void onCancelled(FirebaseError arg0) {
                }
            });
            // Create Database record
            TagsFirebaseRef.child(Tag).child("Questions").updateChildren(TagQuestion);

            // Assign tags to question
            QuestionTag.put(Tag, true);
        }


        QuestionRef.child("Tags").setValue(QuestionTag);
        done.countDown();

        try {
            done.await();
        } catch (Exception e) {
            Log.d("Firebase Write error:", e.getMessage());
        }

        // If successful display message and go back to main screen
        if (WriteStatus) {
            Log.d("Saving Card", "Switching to Main");
        } else
            Log.d("Saving Card", "Error State");

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
