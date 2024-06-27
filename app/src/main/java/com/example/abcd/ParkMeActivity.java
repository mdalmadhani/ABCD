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

public class ParkMeActivity extends AppCompatActivity {

    private ListView parkingListView;
    private Button bookButton, homeButton;
    private ArrayList<String> parkingLots;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park_me);

        parkingListView = findViewById(R.id.parkingListView);
        bookButton = findViewById(R.id.bookButton);
        homeButton = findViewById(R.id.homeButton);
        parkingLots = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, parkingLots);
        parkingListView.setAdapter(adapter);

        loadParkingLots();

        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParkMeActivity.this, ParkingBookingActivity.class);
                startActivity(intent);
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParkMeActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadParkingLots() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("parkingLots");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                parkingLots.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String lot = snapshot.getValue(String.class);
                    parkingLots.add(lot);
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