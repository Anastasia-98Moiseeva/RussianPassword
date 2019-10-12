package com.example.russianpassword;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends Activity {

    public static final int MIN_LENGTH = 8;

    private EditText sourceEditText;
    private TextView resultTextView;
    private String[] russians;
    private String[] latin;
    private PasswordHelper conventer;
    private TextView generateTxt;
    private View generateButton;
    private TextView passwordLength;
    private CompoundButton upLetters;
    private CompoundButton numbers;
    private CompoundButton specialSymbols;
    private SeekBar seekPasswordLength;
    private TextView passwordComplexityTxt;
    private ImageView passwordComplexityImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sourceEditText = findViewById(R.id.editTxtPassword);
        resultTextView = findViewById(R.id.textResult);
        russians = getResources().getStringArray(R.array.russians);
        latin = getResources().getStringArray(R.array.latin);
        conventer = new PasswordHelper(russians, latin);
        generateButton = findViewById(R.id.btnGenerate);
        upLetters = findViewById(R.id.checkboxUpLength);
        numbers = findViewById(R.id.checkboxNumbers);
        specialSymbols = findViewById(R.id.checkboxSpecialSimbols);
        seekPasswordLength = findViewById(R.id.seekPasswordLength);
        passwordLength = findViewById(R.id.txtPasswordLength);
        generateTxt = findViewById(R.id.txtGenerate);
        passwordComplexityTxt = findViewById(R.id.txtPasswordСomplexity);
        passwordComplexityImg = findViewById(R.id.imgPasswordСomplexity);

        generateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String str = getResources().getString(R.string.lettersForPassword);
                if (upLetters.isChecked()){
                    str += getResources().getString(R.string.upLettersForPassword);
                }
                if (numbers.isChecked()){
                    str += getResources().getString(R.string.numbersForPassword);
                }
                if (specialSymbols.isChecked()) {
                    str += getResources().getString(R.string.specialSymbolsForPassword);
                }
                String res = conventer.generate(seekPasswordLength.getProgress() + 8, str);
                generateTxt.setText(res);
            }
        });

        seekPasswordLength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int length = MIN_LENGTH + progress;
                String addStr = getResources().getQuantityString(R.plurals.count_length, progress);
                String resStr = getResources().getQuantityString(R.plurals.count_length, length);
                String len = getString(R.string.initialLength);
                passwordLength.setText(len + " " + progress + " " + addStr + " = " + length + " " + resStr);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sourceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                resultTextView.setText(conventer.convert(s));
                String password = String.valueOf(sourceEditText.getText());
                int complexity = conventer.determinePasswordComplexity(password);
                String str = getResources().getStringArray(R.array.complexity)[complexity];
                passwordComplexityTxt.setText(str);
                passwordComplexityImg.getDrawable().setLevel(complexity * 1000);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
