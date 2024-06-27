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

public class AcademicMonitoringActivity extends AppCompatActivity {

    private ListView academicListView;
    private Button addTestButton, addCourseworkButton, homeButton;
    private ArrayList<String> academicItems;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic_monitoring);

        academicListView = findViewById(R.id.academicListView);
        addTestButton = findViewById(R.id.addTestButton);
        addCourseworkButton = findViewById(R.id.addCourseworkButton);
        homeButton = findViewById(R.id.homeButton);
        academicItems = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, academicItems);
        academicListView.setAdapter(adapter);

        loadAcademicItems();

        addTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AcademicMonitoringActivity.this, AddTestActivity.class);
                startActivity(intent);
            }
        });

        addCourseworkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AcademicMonitoringActivity.this, AddCourseworkActivity.class);
                startActivity(intent);
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AcademicMonitoringActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadAcademicItems() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("academicItems");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                academicItems.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String item = snapshot.child("title").getValue(String.class);
                    academicItems.add(item);
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