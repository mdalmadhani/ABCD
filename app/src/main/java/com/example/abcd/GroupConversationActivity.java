package com.example.abcd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class GroupConversationActivity extends AppCompatActivity {

    private ListView conversationListView;
    private EditText messageEditText;
    private Button sendMessageButton, homeButton;
    private ArrayList<String> messages;
    private ArrayAdapter<String> adapter;
    private DatabaseReference databaseReference;
    private String groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_conversation);

        conversationListView = findViewById(R.id.conversationListView);
        messageEditText = findViewById(R.id.messageEditText);
        sendMessageButton = findViewById(R.id.sendMessageButton);
        homeButton = findViewById(R.id.homeButton);
        messages = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, messages);
        conversationListView.setAdapter(adapter);

        groupName = getIntent().getStringExtra("groupName");
        databaseReference = FirebaseDatabase.getInstance().getReference("conversations").child(groupName);

        loadMessages();

        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupConversationActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadMessages() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messages.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String message = snapshot.getValue(String.class);
                    messages.add(message);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });
    }

    private void sendMessage() {
        String message = messageEditText.getText().toString().trim();

        if (message.isEmpty()) {
            Toast.makeText(GroupConversationActivity.this, "Please enter a message", Toast.LENGTH_SHORT).show();
            return;
        }

        String messageId = databaseReference.push().getKey();
        if (messageId != null) {
            databaseReference.child(messageId).setValue(message).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    messageEditText.setText(""); // Clear the input field
                    Toast.makeText(GroupConversationActivity.this, "Message Sent", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(GroupConversationActivity.this, "Failed to Send Message", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}