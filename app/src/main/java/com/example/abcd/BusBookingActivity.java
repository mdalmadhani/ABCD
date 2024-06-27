package com.example.abcd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;

public class BusBookingActivity extends AppCompatActivity {

    private Spinner timeSpinner;
    private Button confirmBookingButton, homeButton;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_booking);

        timeSpinner = findViewById(R.id.timeSpinner);
        confirmBookingButton = findViewById(R.id.confirmBookingButton);
        homeButton = findViewById(R.id.homeButton);
        databaseReference = FirebaseDatabase.getInstance().getReference("busBookings");

        String[] times = {"8 AM", "9 AM", "10 AM", "11 AM", "12 PM", "1 PM", "2 PM", "3 PM", "4 PM", "5 PM", "6 PM", "7 PM", "8 PM"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, times);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSpinner.setAdapter(adapter);

        confirmBookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedTime = timeSpinner.getSelectedItem().toString();
                bookBusSeat(selectedTime);
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BusBookingActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void bookBusSeat(String time) {
        String bookingId = databaseReference.push().getKey();
        Map<String, Object> booking = new HashMap<>();
        booking.put("time", time);
        booking.put("price", "OMR 0.500"); // Example fixed price

        if (bookingId != null) {
            databaseReference.child(bookingId).setValue(booking).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(BusBookingActivity.this, "Booking Confirmed", Toast.LENGTH_SHORT).show();
                    finish(); // Close the booking activity
                } else {
                    Toast.makeText(BusBookingActivity.this, "Booking Failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}