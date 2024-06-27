package com.example.abcd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class RegistrationActivity extends AppCompatActivity {

    private EditText regUsername, regPassword, regRepeatPassword;
    private Button registerSubmitButton;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        regUsername = findViewById(R.id.regUsername);
        regPassword = findViewById(R.id.regPassword);
        regRepeatPassword = findViewById(R.id.regRepeatPassword);
        registerSubmitButton = findViewById(R.id.registerSubmitButton);
        auth = FirebaseAuth.getInstance();

        registerSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = regUsername.getText().toString();
                String pass = regPassword.getText().toString();
                String repeatPass = regRepeatPassword.getText().toString();

                if (user.isEmpty() || pass.isEmpty() || repeatPass.isEmpty()) {
                    Toast.makeText(RegistrationActivity.this, "Please enter all the details", Toast.LENGTH_SHORT).show();
                } else if (!pass.equals(repeatPass)) {
                    Toast.makeText(RegistrationActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    auth.createUserWithEmailAndPassword(user, pass).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegistrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            // Redirect to login page after successful registration
                            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish(); // Close the registration activity
                        } else {
                            String errorMessage = "Registration Failed";
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                errorMessage = "This email is already registered";
                            } else if (task.getException() instanceof FirebaseAuthException) {
                                errorMessage = ((FirebaseAuthException) task.getException()).getErrorCode();
                            }
                            Toast.makeText(RegistrationActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
