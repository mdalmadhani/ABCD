package com.example.abcd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;

public class AddCourseworkActivity extends AppCompatActivity {

    private EditText courseworkTitleEditText;
    private EditText courseworkDueDateEditText;
    private Button saveCourseworkButton, homeButton;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coursework);

        courseworkTitleEditText = findViewById(R.id.courseworkTitleEditText);
        courseworkDueDateEditText = findViewById(R.id.courseworkDueDateEditText);
        saveCourseworkButton = findViewById(R.id.saveCourseworkButton);
        homeButton = findViewById(R.id.homeButton);
        databaseReference = FirebaseDatabase.getInstance().getReference("academicItems");

        saveCourseworkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCoursework();
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddCourseworkActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void saveCoursework() {
        String title = courseworkTitleEditText.getText().toString().trim();
        String dueDate = courseworkDueDateEditText.getText().toString().trim();

        if (title.isEmpty() || dueDate.isEmpty()) {
            Toast.makeText(AddCourseworkActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String itemId = databaseReference.push().getKey();
        Map<String, Object> coursework = new HashMap<>();
        coursework.put("title", title);
        coursework.put("dueDate", dueDate);
        coursework.put("type", "coursework");

        if (itemId != null) {
            databaseReference.child(itemId).setValue(coursework).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(AddCourseworkActivity.this, "Coursework Saved", Toast.LENGTH_SHORT).show();
                    finish(); // Close the add coursework activity
                } else {
                    Toast.makeText(AddCourseworkActivity.this, "Failed to Save Coursework", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}