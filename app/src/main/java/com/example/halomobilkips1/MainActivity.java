package com.example.halomobilkips1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private boolean answerWasShown = false;
    private static final String KEY_CURRENT_INDEX = "currentIndex";
    private static final String TAG ="idkLog";
    private static final String QUIZ_TAG ="quiz_log";
    public static final String KEY_EXTRA_ANSWER = "qwertyuiop";
    private static final int REQUEST_CODE_PROMPT=0;
    private Button trueButton;
    private Button promptButton;
    private Button falseButton;
    private Button nextButton;
    private TextView questionTextView;
    private TextView wynikView;
    private Question[] questions = new Question[] {
            new Question(R.string.q_activity, true),
            new Question(R.string.q_find_resources,false),
            new Question(R.string.q_listener,true),
            new Question(R.string.q_resources,true),
            new Question(R.string.q_version,false)
    };
    private int currentIndex =0;
    private int wynik=0;
    private int bledy=0;
    private void checkAnswerCorrectness(boolean userAnswer){
        boolean correctAnswer= questions[currentIndex].isTrueAnswer();
        int resultMessageId=0;
        if(answerWasShown){
            resultMessageId=R.string.answer_was_shown;
        }
            else{
        if(userAnswer == correctAnswer) {
            resultMessageId = R.string.correct_answer;
            wynik=wynik+1;
        }else {
            resultMessageId =R.string.incorrect_answer;
            bledy=bledy+1;
        }}
        Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();
    }

    private void setNextQuestion(){
        questionTextView.setText(questions[currentIndex].getQuestionId());
    }
    private void showResults(){
        wynikView.setText("Aktualny wynik: " + String.valueOf(wynik) + " oraz popełnionio: " + String.valueOf(bledy) + " błędów");
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "+on start +");
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "+on resume + ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"+ on pause +");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "+on stop+");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"+ on destroy +");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(QUIZ_TAG,"Wywyoana zostaa metoda: onSaveInstanceState");
        outState.putInt(KEY_CURRENT_INDEX, currentIndex);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode,data);
        if(resultCode != RESULT_OK){return;}
        if(requestCode == REQUEST_CODE_PROMPT){
            if(data ==null ){return;}
            answerWasShown = data.getBooleanExtra(PromptActivity.KEY_EXTRA_ANSWER_SHOWN, false);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(QUIZ_TAG, "Wywołana została metoda cyklu życia: onCreate");
        setContentView(R.layout.activity_main);
        if(savedInstanceState!=null){
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);
        }
        promptButton = findViewById(R.id.prompt_button);
        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        questionTextView = findViewById(R.id.question_text_view);
        wynikView = findViewById(R.id.wynik_View);
        setNextQuestion();

        promptButton.setOnClickListener((v) ->{
        Intent intent = new Intent(MainActivity.this,PromptActivity.class);
        boolean correctAnswer = questions[currentIndex].isTrueAnswer();
        intent.putExtra(KEY_EXTRA_ANSWER, correctAnswer);
        startActivityForResult(intent, REQUEST_CODE_PROMPT);
        });
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerCorrectness(true);
            }
        });
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkAnswerCorrectness(false);
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentIndex==4)
                    showResults();
                currentIndex = (currentIndex + 1) % questions.length;
                answerWasShown = false;
                setNextQuestion();

            }
        });



    }}



