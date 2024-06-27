package com.example.abcd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private Button parkMeButton, crowdAroundButton, busMeButton, myCalendarButton,
            academicMonitoringButton, studyGroupsButton, forumsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        parkMeButton = findViewById(R.id.parkMeButton);
        crowdAroundButton = findViewById(R.id.crowdAroundButton);
        busMeButton = findViewById(R.id.busMeButton);
        myCalendarButton = findViewById(R.id.myCalendarButton);
        academicMonitoringButton = findViewById(R.id.academicMonitoringButton);
        studyGroupsButton = findViewById(R.id.studyGroupsButton);

        parkMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ParkMeActivity.class);
                startActivity(intent);
            }
        });

        crowdAroundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CrowdAroundActivity.class);
                startActivity(intent);
            }
        });

        busMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, BusMeActivity.class);
                startActivity(intent);
            }
        });

        myCalendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MyCalendarActivity.class);
                startActivity(intent);
            }
        });

        academicMonitoringButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AcademicMonitoringActivity.class);
                startActivity(intent);
            }
        });

        studyGroupsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, StudyGroupsAndForumsActivity.class);
                startActivity(intent);
            }
        });
    }
}