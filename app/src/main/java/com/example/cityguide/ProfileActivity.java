package com.example.cityguide;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    EditText firstName, LastName, UEmail;
    Button btnsave;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        firstName = findViewById(R.id.firstName);
        LastName = findViewById(R.id.lastName);
        UEmail = findViewById(R.id.emailAddress);
        btnsave = findViewById(R.id.saveBtn);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();

        final DocumentReference docref = firebaseFirestore.collection("users").document(userId);


        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!firstName.getText().toString().isEmpty() && !LastName.getText().toString().isEmpty() && !UEmail.getText().toString().isEmpty()) {
                    String fname = firstName.getText().toString();
                    String lname = LastName.getText().toString();
                    String userEmail = UEmail.getText().toString();

                    Map<String, Object> user = new HashMap<>();
                    user.put("FirstName", fname);
                    user.put("LastName", lname);
                    user.put("Emailaddress", userEmail);

                    docref.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            }
                            else {
                                Toast.makeText(ProfileActivity.this, "Error! Data are not inserted.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                } else {
                    Toast.makeText(ProfileActivity.this, "All Detail must be valid.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }
}