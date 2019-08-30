package com.sm_academy.Activity.DefaultActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sm_academy.Activity.BaseActivity;
import com.sm_academy.R;

public class PteTestStartActivity extends BaseActivity {
    private Button btnStartTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pte_test_start);

        btnStartTest = findViewById(R.id.btnStartTest);
        btnStartTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProficiencyTestActivity.class));
            }
        });
    }
}
