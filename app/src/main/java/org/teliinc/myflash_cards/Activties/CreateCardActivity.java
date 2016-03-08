package org.teliinc.myflash_cards.Activties;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.parceler.Parcels;
import org.teliinc.myflash_cards.Model.FlashCard;
import org.teliinc.myflash_cards.R;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class CreateCardActivity extends Activity {

    private final int REQ_CODE_SPEECH_QUESTION = 100;
    private final int REQ_CODE_SPEECH_ANSWER = 150;

    @Bind(R.id.button_create_question)
    Button button_create;
    @Bind(R.id.editTextQuestion)
    EditText EditTextQuestion;
    @Bind(R.id.editTextAnswer)
    EditText EditTextAnswer;
    @Bind(R.id.editTextTags)
    EditText EditTextTags;

    // Gesture detection
    GestureDetector gestureDetectorAnswer;
    GestureDetector gestureDetectorQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card);

        ButterKnife.bind(this);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * 0.9), (int) (height * 0.75));

        // Gesture detection
        gestureDetectorAnswer = new GestureDetector(this, new GestureListener(this, EditTextAnswer));
        EditTextAnswer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return gestureDetectorAnswer.onTouchEvent(motionEvent);
            }
        });
        gestureDetectorQuestion = new GestureDetector(this, new GestureListener(this, EditTextQuestion));
        EditTextQuestion.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return gestureDetectorQuestion.onTouchEvent(motionEvent);
            }
        });
    }

    @OnClick(R.id.button_create_question)
    public void create_card_question(View view) {

        FlashCard flashCard = new FlashCard(EditTextAnswer.getText().toString(),
                EditTextQuestion.getText().toString());

        // Add tags to flashcard
        flashCard.setTags(new ArrayList<String>(Arrays.asList(EditTextTags.getText().toString().split(" "))));

        // Return created flash object to parent
        Intent intent = new Intent(this, QuizCreateActivity.class);
        intent.putExtra("flashcard", Parcels.wrap(flashCard));
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    @OnClick(R.id.btnSpeak)
    public void speect_to_question(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        //intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
        //        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        //intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        //intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
        //        getString(R.string.speech_prompt));
        try {
            if (view == EditTextQuestion)
                startActivityForResult(intent, REQ_CODE_SPEECH_QUESTION);
            else
                startActivityForResult(intent, REQ_CODE_SPEECH_ANSWER);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_ANSWER: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String sentence = result.get(0);
                    EditTextAnswer.setText(sentence.substring(7));
                }
                break;
            }
            case REQ_CODE_SPEECH_QUESTION:{
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String sentence = result.get(0);
                    EditTextQuestion.setText(sentence.substring(7));
                }
                break;
            }
        }
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        View myView;

        public GestureListener(Context context, View view) {
            myView = view;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        // event when double tap occurs
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Timber.d("Double Tap");
            speect_to_question(myView);
            return true;
        }
    }

}
