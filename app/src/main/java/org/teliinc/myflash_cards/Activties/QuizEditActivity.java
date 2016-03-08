package org.teliinc.myflash_cards.Activties;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import org.parceler.Parcels;
import org.teliinc.myflash_cards.Model.FlashCard;
import org.teliinc.myflash_cards.Model.Quiz;
import org.teliinc.myflash_cards.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hugo.weaving.DebugLog;
import timber.log.Timber;


public class QuizEditActivity extends BaseMenuClass {

    @Bind(R.id.question_list)
    SwipeMenuListView listView;
    @Bind(R.id.quiz_title)
    EditText EditTitle;
    @Bind(R.id.quiz_description)
    EditText EditDescription;

    private Quiz quiz;
    private List<FlashCard> mFlashcardList;
    private FlashcardAdapterMenu mFlashcardAdapter;

    private boolean WriteStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_create);

        ButterKnife.bind(this);

        // Initialize activity from quiz passed from intent
        quiz = (Quiz) Parcels.unwrap(getIntent().getParcelableExtra("quiz"));

        EditTitle.setText(quiz.getTitle());
        EditDescription.setText(quiz.getDescription());

        mFlashcardList = quiz.getFlashCards();
        mFlashcardAdapter = new FlashcardAdapterMenu();

        listView.setAdapter(mFlashcardAdapter);

        // Initialize ListView
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // Create different menus depending on the view type
                createMenu1(menu);
            }

            private void createMenu1(SwipeMenu menu) {
                SwipeMenuItem item1 = new SwipeMenuItem(
                        getApplicationContext());
                item1.setBackground(new ColorDrawable(Color.rgb(0xE5, 0x18,
                        0x5E)));
                item1.setWidth(dp2px(90));
                item1.setIcon(R.drawable.ic_action_discard);
                menu.addMenuItem(item1);
                /*
                SwipeMenuItem item2 = new SwipeMenuItem(
                        getApplicationContext());
                item2.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                item2.setWidth(dp2px(90));
                item2.setIcon(R.drawable.ic_action_favorite);
                menu.addMenuItem(item2);
                SwipeMenuItem item3 = new SwipeMenuItem(
                        getApplicationContext());
                item3.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                item3.setWidth(dp2px(90));
                item3.setIcon(R.drawable.ic_action_share);
                menu.addMenuItem(item3);
                */
            }

            // TODO: Edit FlashCard
        };
        // set creator
        listView.setMenuCreator(creator);

        // step 2. listener item click event
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        mFlashcardList.remove(position);
                        mFlashcardAdapter.notifyDataSetChanged();
                        break;
                }
                return false;
            }
        });
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    @DebugLog
    @OnClick(R.id.add_questions)
    public void add_question(View view) {
        Intent intent = new Intent(this, CreateCardActivity.class);
        startActivityForResult(intent, FlashCard.FLASHCARD_CREATED);
    }

    @DebugLog
    @OnClick(R.id.save_quiz)
    public void save_quiz(View view){

        // Ensure Title and Description are filled in
        String title = EditTitle.getText().toString();
        String description = EditDescription.getText().toString();


        if ( TextUtils.isEmpty(title) || TextUtils.isEmpty(description))
        {
            Toast.makeText(this,"Title or Description is Empty",Toast.LENGTH_SHORT);
            return;
        }
        quiz = new Quiz(title,description);
        quiz.setFlashCards(mFlashcardList);

        // Initialize Firebase
        Firebase.setAndroidContext(this);
        Firebase myFirebaseRef = new Firebase("https://teliflashcards.firebaseio.com/");

        // Store the data to server
        // Using a CountDownLatch to prevent moving on without saving
        final CountDownLatch done = new CountDownLatch(1);

        // Store the Quiz
        Firebase QuizRef = myFirebaseRef.child("Quizzes").push();
        QuizRef.setValue(quiz, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    WriteStatus = false;
                    Timber.d("FireBase Write", "Data could not be saved. " + firebaseError.getMessage());
                } else {
                    Timber.d("FireBase Write", "Data saved successfully. Finishing activity...");
                    WriteStatus = true;
                    finish();
                }
            }
        });
        done.countDown();

        try {
            done.await();
        } catch (Exception e) {
            Timber.d("Firebase Write error:", e.getMessage());
        }

        // If successful display message and go back to main screen
        if (WriteStatus) {
            Timber.d("Saving Card", "Switching to Main");
        } else
            Timber.d("Saving Card", "Error State");

        // Just close the activity
        this.finish();
    }

    class FlashcardAdapterMenu extends BaseAdapter {

        @Override
        public int getCount() {
            return mFlashcardList.size();
        }

        @Override
        public FlashCard getItem(int position) {
            return mFlashcardList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getViewTypeCount() {
            // menu type count
            return 3;
        }

        @Override
        public int getItemViewType(int position) {
            // current menu type
            return position % 3;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(getApplicationContext(),
                        R.layout.flashcard_list, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            FlashCard item = getItem(position);
            holder.iv_icon.setImageDrawable(getResources().getDrawable(R.drawable.question,getBaseContext().getTheme()));
            holder.tv_name.setText(item.getQuestion());
            return convertView;
        }

        class ViewHolder {
            ImageView iv_icon;
            TextView tv_name;

            public ViewHolder(View view) {
                iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
                tv_name = (TextView) view.findViewById(R.id.tv_name);
                view.setTag(this);
            }
        }
    }

    // Handle result for activity
    @DebugLog
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FlashCard.FLASHCARD_CREATED) {
            if(resultCode == RESULT_OK){
                FlashCard flashCard = (FlashCard) Parcels.unwrap(data.getParcelableExtra("flashcard"));
                mFlashcardList.add(flashCard);
                mFlashcardAdapter.notifyDataSetChanged();
            }
        }
    }
}
