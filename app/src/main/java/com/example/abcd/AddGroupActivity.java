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

public class AddGroupActivity extends AppCompatActivity {

    private EditText groupNameEditText;
    private Button saveGroupButton, homeButton;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);

        groupNameEditText = findViewById(R.id.groupNameEditText);
        saveGroupButton = findViewById(R.id.saveGroupButton);
        homeButton = findViewById(R.id.homeButton);
        databaseReference = FirebaseDatabase.getInstance().getReference("groups");

        saveGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveGroup();
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddGroupActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void saveGroup() {
        String groupName = groupNameEditText.getText().toString().trim();

        if (groupName.isEmpty()) {
            Toast.makeText(AddGroupActivity.this, "Please enter a group name", Toast.LENGTH_SHORT).show();
            return;
        }

        String groupId = databaseReference.push().getKey();
        if (groupId != null) {
            databaseReference.child(groupId).setValue(groupName).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(AddGroupActivity.this, "Group Saved", Toast.LENGTH_SHORT).show();
                    finish(); // Close the add group activity
                } else {
                    Toast.makeText(AddGroupActivity.this, "Failed to Save Group", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}