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

import com.google.gson.JsonElement;

import org.parceler.Parcels;
import org.teliinc.myflash_cards.Model.FlashCard;
import org.teliinc.myflash_cards.R;

import java.util.ArrayList;
import java.util.Map;

import ai.api.AIConfiguration;
import ai.api.AIListener;
import ai.api.AIService;
import ai.api.model.AIError;
import ai.api.model.AIResponse;
import ai.api.model.Result;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class CreateCardActivity extends Activity implements AIListener {

    final AIConfiguration config = new AIConfiguration("5841df7b38144c20a77d46dcc2563f32",
            "a589bf42-ba8a-42d4-8e34-411d69bb1215", AIConfiguration.SupportedLanguages.English,
            AIConfiguration.RecognitionEngine.System);

    private final int REQ_CODE_SPEECH_QUESTION = 100;
    private final int REQ_CODE_SPEECH_ANSWER = 150;
    @Bind(R.id.button_create_question)
    Button button_create;
    @Bind(R.id.editTextQuestion)
    EditText EditTextQuestion;
    @Bind(R.id.editTextAnswer)
    EditText EditTextAnswer;
    // Gesture detection
    GestureDetector gestureDetectorAnswer;
    GestureDetector gestureDetectorQuestion;
    // AI Configuration
    private AIService aiService;
    private String query;
    private View current_view;

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

        // Initial AI
        query = "";
        aiService = AIService.getService(this, config);
        aiService.setListener(this);
    }

    @OnClick(R.id.button_create_question)
    public void create_card_question(View view) {
        FlashCard flashCard = new FlashCard(EditTextAnswer.getText().toString(),
                EditTextQuestion.getText().toString());

        // Add tags to flashcard
        flashCard.setQuery(query);

        // Displaying query
        Toast.makeText(this,query,Toast.LENGTH_LONG);

        // Return created flash object to parent
        Intent intent = new Intent(this, QuizCreateActivity.class);
        intent.putExtra("flashcard", Parcels.wrap(flashCard));
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    public void onResult(final AIResponse response) {
        Result result = response.getResult();

        // Get parameters
        String parameterString = "";
        if (result.getParameters() != null && !result.getParameters().isEmpty()) {
            for (final Map.Entry<String, JsonElement> entry : result.getParameters().entrySet()) {
                parameterString += "(" + entry.getKey() + ", " + entry.getValue() + ") ";
            }
            query = query + " " + parameterString;
        }

        // Show results in TextView.
        ((EditText)current_view).setText(result.getResolvedQuery());
    }

    @Override
    public void onListeningStarted() {
    }

    @Override
    public void onListeningCanceled() {
    }

    @Override
    public void onListeningFinished() {
    }

    @Override
    public void onAudioLevel(final float level) {
    }

    @Override
    public void onError(final AIError error) {
        Toast.makeText(this, error.toString(), Toast.LENGTH_LONG);
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
            current_view = myView;
            aiService.startListening();
            return true;
        }
    }
}
