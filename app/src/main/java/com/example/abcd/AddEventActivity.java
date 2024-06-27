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

public class AddEventActivity extends AppCompatActivity {

    private EditText eventTitleEditText;
    private EditText eventDescriptionEditText;
    private EditText eventDateEditText;
    private EditText eventTimeEditText;
    private Button saveEventButton, homeButton;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        eventTitleEditText = findViewById(R.id.eventTitleEditText);
        eventDescriptionEditText = findViewById(R.id.eventDescriptionEditText);
        eventDateEditText = findViewById(R.id.eventDateEditText);
        eventTimeEditText = findViewById(R.id.eventTimeEditText);
        saveEventButton = findViewById(R.id.saveEventButton);
        homeButton = findViewById(R.id.homeButton);
        databaseReference = FirebaseDatabase.getInstance().getReference("events");

        saveEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEvent();
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddEventActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void saveEvent() {
        String title = eventTitleEditText.getText().toString().trim();
        String description = eventDescriptionEditText.getText().toString().trim();
        String date = eventDateEditText.getText().toString().trim();
        String time = eventTimeEditText.getText().toString().trim();

        if (title.isEmpty() || description.isEmpty() || date.isEmpty() || time.isEmpty()) {
            Toast.makeText(AddEventActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String eventId = databaseReference.push().getKey();
        Map<String, Object> event = new HashMap<>();
        event.put("title", title);
        event.put("description", description);
        event.put("date", date);
        event.put("time", time);

        if (eventId != null) {
            databaseReference.child(eventId).setValue(event).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(AddEventActivity.this, "Event Saved", Toast.LENGTH_SHORT).show();
                    finish(); // Close the add event activity
                } else {
                    Toast.makeText(AddEventActivity.this, "Failed to Save Event", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}