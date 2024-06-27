package com.example.abcd;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;

public class DriverEvaluationActivity extends AppCompatActivity {

    private RatingBar driverRatingBar;
    private Button submitRatingButton;
    private DatabaseReference databaseReference;
    private View homeButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_evaluation);

        driverRatingBar = findViewById(R.id.driverRatingBar);
        submitRatingButton = findViewById(R.id.submitRatingButton);
        homeButton = findViewById(R.id.homeButton);
        databaseReference = FirebaseDatabase.getInstance().getReference("driverEvaluations");

        submitRatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rating = driverRatingBar.getRating();
                submitDriverRating(rating);
            }
        });
    }

    private void submitDriverRating(float rating) {
        String ratingId = databaseReference.push().getKey();
        Map<String, Object> evaluation = new HashMap<>();
        evaluation.put("rating", rating);
        if (ratingId != null) {
            databaseReference.child(ratingId).setValue(evaluation).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(DriverEvaluationActivity.this, "Rating Submitted", Toast.LENGTH_SHORT).show();
                    finish(); // Close the evaluation activity
                } else {
                    Toast.makeText(DriverEvaluationActivity.this, "Rating Submission Failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}