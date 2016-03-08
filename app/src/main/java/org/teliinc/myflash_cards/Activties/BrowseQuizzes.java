package org.teliinc.myflash_cards.Activties;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseRecyclerAdapter;

import org.parceler.Parcels;
import org.teliinc.myflash_cards.Model.Quiz;
import org.teliinc.myflash_cards.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BrowseQuizzes extends BaseMenuClass {

    @Bind(R.id.quiz_list)
    RecyclerView quizList;

    // Firebase Variables
    private Firebase mRef;
    private Query mQuizRef;
    private FirebaseRecyclerAdapter<Quiz, QuizHolder> mRecycleViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_quizzes);

        // Butterknife binding
        ButterKnife.bind(this);

        // Initializ ListView
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(false);

        quizList.setHasFixedSize(false);
        quizList.setLayoutManager(manager);

        // Initialize Firebases variables
        mRef = new Firebase("https://teliflashcards.firebaseio.com/Quizzes");
        mQuizRef = mRef.limitToLast(50);

        // Create addapter
        mRecycleViewAdapter = new FirebaseRecyclerAdapter<Quiz, QuizHolder>(Quiz.class, R.layout.quiz_list, QuizHolder.class, mQuizRef) {
            @Override
            public void populateViewHolder(QuizHolder quizView, Quiz quiz, final int position) {
                quizView.setTitle(quiz.getTitle());
                quizView.setDescription(quiz.getDescription());
                // TODO : Extend authorization scheme
                /*
                    if (getAuth() != null && quiz.getUid().equals(getAuth().getUid())) {
                        quizView.setIsSender(true);
                    } else {
                        quizView.setIsSender(false);
                    }
                */
                quizView.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Start new intent
                        Intent intent = new Intent(getBaseContext(),QuizEditActivity.class);
                        intent.putExtra("quiz", Parcels.wrap(mRecycleViewAdapter.getItem(position)));
                        startActivity(intent);
                    }
                });
            }
        };

        // Set adapter
        quizList.setAdapter(mRecycleViewAdapter);
    }

    public static class QuizHolder extends RecyclerView.ViewHolder {
        View mView;

        public QuizHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setTitle(String title) {
            TextView field = (TextView) mView.findViewById(R.id.tv_title);
            field.setText(title);
        }

        public void setDescription(String description) {
            TextView field = (TextView) mView.findViewById(R.id.tv_description);
            field.setText(description);
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mRecycleViewAdapter.cleanup();
    }
}
