package com.example.halomobilkips1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private Button trueButton;
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
        if(userAnswer == correctAnswer) {
            resultMessageId = R.string.correct_answer;
            wynik=wynik+1;
        }else {
            resultMessageId =R.string.incorrect_answer;
            bledy=bledy+1;
        }
        Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();
    }

    private void setNextQuestion(){
        questionTextView.setText(questions[currentIndex].getQuestionId());
    }
    private void showResults(){
        wynikView.setText("Aktualny wynik: " + String.valueOf(wynik) + " oraz popełnionio: " + String.valueOf(bledy) + " błędów");
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        questionTextView = findViewById(R.id.question_text_view);
        wynikView = findViewById(R.id.wynik_View);
        setNextQuestion();
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
                setNextQuestion();

            }
        });


    }}



