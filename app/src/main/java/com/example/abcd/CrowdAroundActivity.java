package com.example.abcd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class CrowdAroundActivity extends AppCompatActivity {

    private ListView spaceListView;
    private Button bookRoomButton, homeButton;
    private ArrayList<String> spaces;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crowd_around);

        spaceListView = findViewById(R.id.spaceListView);
        bookRoomButton = findViewById(R.id.bookRoomButton);
        homeButton = findViewById(R.id.homeButton);
        spaces = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, spaces);
        spaceListView.setAdapter(adapter);

        loadAvailableSpaces();

        bookRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CrowdAroundActivity.this, MeetingRoomBookingActivity.class);
                startActivity(intent);
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CrowdAroundActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadAvailableSpaces() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("availableSpaces");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                spaces.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String space = snapshot.getValue(String.class);
                    spaces.add(space);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });
    }
}