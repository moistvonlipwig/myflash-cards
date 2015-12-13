package org.teliinc.myflash_cards.Activties;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import org.teliinc.myflash_cards.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CreateCardActivity extends BaseMenuClass {

    @Bind(R.id.button_create_question) Button button_create;
    @Bind(R.id.editTextQuestion) EditText EditTextQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card);

        ButterKnife.bind(this);
    }
}
