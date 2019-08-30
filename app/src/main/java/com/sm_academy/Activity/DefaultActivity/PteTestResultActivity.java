package com.sm_academy.Activity.DefaultActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sm_academy.Activity.BaseActivity;
import com.sm_academy.Activity.LearnerActivity.LearnerDashBoardActivity;
import com.sm_academy.Database.PreferencesManger;
import com.sm_academy.R;
import com.sm_academy.Utility.Constants;

public class PteTestResultActivity extends BaseActivity {
    TextView percentageView, correctAnswerView, totalQuestionView;
    Button btnDoneTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pte_test_result);

        btnDoneTest = findViewById(R.id.btnDoneTest);

        percentageView = findViewById(R.id.percentageView);
        correctAnswerView = findViewById(R.id.correctAnswerView);
        totalQuestionView = findViewById(R.id.totalQuestionView);
        PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.PT_TAKEN, "true");
        Intent intent = getIntent();
        Log.e("data", intent.getStringExtra("question") + intent.getStringExtra("answer") +
                intent.getStringExtra("percentage"));


        totalQuestionView.setText("Total Questions :" + intent.getStringExtra("question"));
        correctAnswerView.setText("Correct Answer :" + intent.getStringExtra("answer"));
        percentageView.setText("Percentage :" + intent.getStringExtra("percentage"));
        Float per = Float.valueOf(intent.getStringExtra("percentage"));
        Log.e("float per", "" + per);

        if (per <= 29) {
            percentageView.setTextColor(getResources().getColor(R.color.first));
        } else if (29 < per && per < 49) {
            percentageView.setTextColor(getResources().getColor(R.color.second));
        } else if (49 < per && per < 75) {
            percentageView.setTextColor(getResources().getColor(R.color.third));
        } else {
            percentageView.setTextColor(getResources().getColor(R.color.fourth));
        }
        btnDoneTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LearnerDashBoardActivity.class));
            }
        });

    }
}
