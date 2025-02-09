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

public class StudyGroupsAndForumsActivity extends AppCompatActivity {

    private ListView groupsListView;
    private Button addGroupButton, homeButton;
    private ArrayList<String> groups;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_groups_and_forums);

        groupsListView = findViewById(R.id.groupsListView);
        addGroupButton = findViewById(R.id.addGroupButton);
        homeButton = findViewById(R.id.homeButton);
        groups = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, groups);
        groupsListView.setAdapter(adapter);

        loadGroups();

        addGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudyGroupsAndForumsActivity.this, AddGroupActivity.class);
                startActivity(intent);
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudyGroupsAndForumsActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        groupsListView.setOnItemClickListener((parent, view, position, id) -> {
            String groupName = groups.get(position);
            Intent intent = new Intent(StudyGroupsAndForumsActivity.this, GroupConversationActivity.class);
            intent.putExtra("groupName", groupName);
            startActivity(intent);
        });
    }

    private void loadGroups() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("groups");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                groups.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String group = snapshot.getValue(String.class);
                    groups.add(group);
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