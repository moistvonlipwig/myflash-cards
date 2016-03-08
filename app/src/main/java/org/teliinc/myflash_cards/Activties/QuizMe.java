package org.teliinc.myflash_cards.Activties;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import org.parceler.Parcels;
import org.teliinc.myflash_cards.Model.Quiz;
import org.teliinc.myflash_cards.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class QuizMe extends AppCompatActivity {

    private ArrayList<String> al;
    private ArrayAdapter<String> arrayAdapter;
    private int i;

    private Quiz quiz;

    @Bind(R.id.frame)
    SwipeFlingAdapterView flingContainer;
    /*
    CardStack mCardStack;
    CardsDataAdapter mCardAdapter;
    MyCardListener cardListener;
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_me);
        ButterKnife.bind(this);

        // Initialize activity from quiz passed from intent
        quiz = (Quiz) Parcels.unwrap(getIntent().getParcelableExtra("quiz"));

        // TODO : Get quiz data
        al = new ArrayList<>();
        al.add("php");
        al.add("c");
        al.add("python");
        al.add("java");
        al.add("html");
        al.add("c++");
        al.add("css");
        al.add("javascript");

        arrayAdapter = new ArrayAdapter<>(this, R.layout.quiz_item, R.id.helloText, al );


        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Timber.d("LIST", "removed object!");
                al.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
                makeToast(QuizMe.this, "Left!");
                al.add((String)dataObject);
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                makeToast(QuizMe.this, "Right!");
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here
                al.add("XML ".concat(String.valueOf(i)));
                arrayAdapter.notifyDataSetChanged();
                Timber.d("LIST", "notified");
                i++;
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                makeToast(QuizMe.this, "Clicked!");
            }
        });

/*
        mCardStack = (CardStack) findViewById(R.id.container);
        mCardStack.setContentResource(R.layout.flashcard_list);
        mCardStack.setStackMargin(20);

        mCardAdapter = new CardsDataAdapter(getApplicationContext());
        mCardAdapter.add("test1");
        mCardAdapter.add("test2");
        mCardAdapter.add("test3");
        mCardAdapter.add("test4");
        mCardAdapter.add("test5");

        mCardStack.setAdapter(mCardAdapter);

        cardListener = new MyCardListener();
        mCardStack.setListener(cardListener);
*/
    }

    static void makeToast(Context ctx, String s){
        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
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
    /*
    public class CardsDataAdapter extends ArrayAdapter<String> {

        public CardsDataAdapter(Context context) {
            super(context, R.layout.flashcard_list);
        }

        @Override
        public View getView(int position, final View contentView, ViewGroup parent) {
            //supply the layout for your card
            TextView v = (TextView) (contentView.findViewById(R.id.tv_name));
            v.setText(getItem(position));
            return contentView;
        }
    }

    public class MyCardListener implements CardStack.CardEventListener {
        //implement card event interface
        @Override
        public boolean swipeEnd(int direction, float distance) {
            //if "return true" the dismiss animation will be triggered
            //if false, the card will move back to stack
            //distance is finger swipe distance in dp

            //the direction indicate swipe direction
            //there are four directions
            //  0  |  1
            // ----------
            //  2  |  3

            return false;
        }

        @Override
        public boolean swipeStart(int direction, float distance) {

            return true;
        }

        @Override
        public boolean swipeContinue(int direction, float distanceX, float distanceY) {

            return true;
        }

        @Override
        public void discarded(int id, int direction) {
            //this callback invoked when dismiss animation is finished.
            return;
        }

        @Override
        public void topCardTapped() {
            //this callback invoked when a top card is tapped by user.
        }
    }
    */
}
