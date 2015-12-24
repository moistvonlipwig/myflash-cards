package org.teliinc.myflash_cards.Activties;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import org.teliinc.myflash_cards.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.view.View;

public class CreateCardActivity extends BaseMenuClass {

    @Bind(R.id.button_create_question) Button button_create;
    @Bind(R.id.editTextQuestion) EditText EditTextQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_create_question)
    public void create_card_question(View view) {

        Intent intent = new Intent(this, CreateCardActivityAnswer.class);
        intent.putExtra("QUESTION", EditTextQuestion.getText().toString());
        startActivity(intent);
    }
}
