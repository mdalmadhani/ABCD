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

public class AddTestActivity extends AppCompatActivity {

    private EditText testTitleEditText;
    private EditText testDateEditText;
    private Button saveTestButton, homeButton;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_test);

        testTitleEditText = findViewById(R.id.testTitleEditText);
        testDateEditText = findViewById(R.id.testDateEditText);
        saveTestButton = findViewById(R.id.saveTestButton);
        homeButton = findViewById(R.id.homeButton);
        databaseReference = FirebaseDatabase.getInstance().getReference("academicItems");

        saveTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTest();
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddTestActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void saveTest() {
        String title = testTitleEditText.getText().toString().trim();
        String date = testDateEditText.getText().toString().trim();

        if (title.isEmpty() || date.isEmpty()) {
            Toast.makeText(AddTestActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String itemId = databaseReference.push().getKey();
        Map<String, Object> test = new HashMap<>();
        test.put("title", title);
        test.put("date", date);
        test.put("type", "test");

        if (itemId != null) {
            databaseReference.child(itemId).setValue(test).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(AddTestActivity.this, "Test Saved", Toast.LENGTH_SHORT).show();
                    finish(); // Close the add test activity
                } else {
                    Toast.makeText(AddTestActivity.this, "Failed to Save Test", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}