package org.teliinc.myflash_cards.Activties;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import org.teliinc.myflash_cards.Model.FlashCard;
import org.teliinc.myflash_cards.Model.Quiz;
import org.teliinc.myflash_cards.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuizMe extends BaseMenuClass {

    @Bind(R.id.frame)
    SwipeFlingAdapterView flingContainer;
    @Bind(R.id.quiz_me_name)
    AutoCompleteTextView quiz_name;
    private List<Quiz> quizzes;
    private ArrayList<String> cards;
    private ArrayAdapter<String> arrayAdapter;
    private int i;

    private ArrayList<String> al;

    static void makeToast(Context ctx, String s) {
        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
    }

    /*
    CardStack mCardStack;
    CardsDataAdapter mCardAdapter;
    MyCardListener cardListener;
*/
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_me);
        ButterKnife.bind(this);

        // Get Quizzes for database and initialize spinner
        // Initialize Firebases variables
        Firebase.setAndroidContext(this);

        // Initialize quiz arrays
        quizzes = new ArrayList<>();
        cards = new ArrayList<>();

        // Iterate through the mQuizRef list
        LaunchScreenActivity.firebaseRef.child("Quizzes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Quiz stored_quiz = (Quiz) child.getValue(Quiz.class);
                    quizzes.add(stored_quiz);
                }
                setquizData();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                makeToast(QuizMe.this, (String)dataObject);
            }
        });
    }

    public void setquizData() {
        ArrayList<String> quiz_titles = new ArrayList<>();
        for (Quiz q : quizzes) {
            quiz_titles.add(q.getTitle());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, quiz_titles);
        quiz_name.setAdapter(adapter);

        Quiz quiz_selected = (Quiz) quizzes.get(0);
        List<FlashCard> flashCards = quiz_selected.getFlashCards();

        for (FlashCard card : flashCards) {
            cards.add(card.getQuestion());
        }
        arrayAdapter = new ArrayAdapter<>(this, R.layout.quiz_item, R.id.quiz_question, cards );
        flingContainer.setAdapter(arrayAdapter);

        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                cards.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
                makeToast(QuizMe.this, "Ok, we try that again!");
                cards.add((String) dataObject);
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                makeToast(QuizMe.this, "Cool you know that!");
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here
                cards.add("New Question ".concat(String.valueOf(i)));
                arrayAdapter.notifyDataSetChanged();
                i++;
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }
        });
    }

    @OnClick(R.id.right)
    public void right() {
        /**
         * Trigger the right event manually.
         */
        flingContainer.getTopCardListener().selectRight();
    }

    @OnClick(R.id.left)
    public void left() {
        flingContainer.getTopCardListener().selectLeft();
    }
}
