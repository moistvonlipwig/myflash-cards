package org.teliinc.myflash_cards;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.gujun.android.taggroup.TagGroup;

import java.util.Arrays;
import java.util.List;

import me.gujun.android.taggroup.TagGroup;

public class DisplayCardActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {

    private Handler mHandler = new Handler();
    private boolean mShowingBack = false;

    public static final List<String> TAGS = Arrays.asList("cupcake", "donut", "eclair", "froyo",
            "gingerbread", "honeycomb", "icecreamsandwich", "jellybean", "kitkat", "lollipop", "marshmallow");

    public Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_card);

        if (savedInstanceState == null) {
            // If there is no saved instance state, add a fragment representing the
            // front of the card to this activity. If there is saved instance state,
            // this fragment will have already been added to the activity.
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.display_card, new CardFrontFragment())
                    .commit();
        } else {
            mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);
        }


        // Get Data from Bundlet
        extras = getIntent().getExtras();
    }

    @Override
    public void onBackStackChanged() {
        mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);
    }

    private void flipCard() {
        if (mShowingBack) {
            getFragmentManager().popBackStack();
            return;
        }

        // Flip to the back.

        mShowingBack = true;

        // Create and commit a new fragment transaction that adds the fragment for the back of
        // the card, uses custom animations, and is part of the fragment manager's back stack.

        getFragmentManager()
                .beginTransaction()

                        // Replace the default fragment animations with animator resources representing
                        // rotations when switching to the back of the card, as well as animator
                        // resources representing rotations when flipping back to the front (e.g. when
                        // the system Back button is pressed).
                .setCustomAnimations(
                        R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                        R.animator.card_flip_left_in, R.animator.card_flip_left_out)

                        // Replace any fragments currently in the container view with a fragment
                        // representing the next page (indicated by the just-incremented currentPage
                        // variable).
                .replace(R.id.display_card, new CardBackFragment())

                        // Add this transaction to the back stack, allowing users to press Back
                        // to get to the front of the card.
                .addToBackStack(null)

                        // Commit the transaction.
                .commit();

        // Defer an invalidation of the options menu (on modern devices, the action bar). This
        // can't be done immediately because the transaction may not yet be committed. Commits
        // are asynchronous in that they are posted to the main thread's message loop.
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                invalidateOptionsMenu();
            }
        });
    }

    public void flipCard(View view) {
        flipCard();
    }

    /**
     * A fragment representing the front of the card.
     */
    public class CardFrontFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View fragmentView = inflater.inflate(R.layout.fragment_card_front, container, false);
            TextView TextViewQuestion = (TextView) fragmentView.findViewById(R.id.textCardQuestion);
            TextViewQuestion.setText(extras.getString("QUESTION"));
            return fragmentView;
        }
    }

    /**
     * A fragment representing the back of the card.
     */
    public class CardBackFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View fragmentView = inflater.inflate(R.layout.fragment_card_back, container, false);
            TextView TextViewAnswer = (TextView) fragmentView.findViewById(R.id.textCardAnswer);
            TextViewAnswer.setText(extras.getString("ANSWER"));

            TagGroup hashTag = (TagGroup)fragmentView.findViewById(R.id.tagsCard);
            hashTag.setTags(TAGS);

            return fragmentView;
        }
    }
}
