package com.example.cityguide;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth fauth;
    FirebaseFirestore fstore;

    EditText phoneNumber, CodeEnter;
    Button btnnext;
    ProgressBar progressBar;
    TextView state;
    CountryCodePicker codePicker;

    String verificationId;
    PhoneAuthProvider.ForceResendingToken token;

    Boolean verificationInProgess = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fauth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        phoneNumber = findViewById(R.id.phone);
        CodeEnter = findViewById(R.id.codeEnter);
        progressBar = findViewById(R.id.progressBar);
        btnnext = findViewById(R.id.nextBtn);
        state = findViewById(R.id.state);
        codePicker = findViewById(R.id.ccp);


        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!verificationInProgess) {
                    if (!phoneNumber.getText().toString().isEmpty() && phoneNumber.getText().length() == 10) {
                        String phoneNum = "+" + codePicker.getSelectedCountryCode() + phoneNumber.getText().toString();

                        progressBar.setVisibility(View.VISIBLE);
                        state.setText("Sending OTP");
                        state.setVisibility(View.VISIBLE);
                        requestOTP(phoneNum);

                    } else {
                        phoneNumber.setError("Phone Number is not Valid");
                    }

                } else {

                    String userOTP = CodeEnter.getText().toString();
                    if (!userOTP.isEmpty() && userOTP.length() == 6) {

                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, userOTP);
                        verifyAuth(credential);

                    } else {
                        CodeEnter.setError("Valid OTP is Required. ");
                    }


                }

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (fauth.getCurrentUser() != null) {
            progressBar.setVisibility(View.VISIBLE);
            state.setText("Checking..");
            state.setVisibility(View.VISIBLE);
            chekUserProfile();

        }


    }

    private void verifyAuth(PhoneAuthCredential credential) {
        fauth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Successfully Verify Phone Number.", Toast.LENGTH_SHORT).show();
                    chekUserProfile();
                } else {
                    Toast.makeText(RegisterActivity.this, "Authentication is Failed.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    private void requestOTP(String phoneNum) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNum, 60L, TimeUnit.SECONDS, this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                progressBar.setVisibility(View.INVISIBLE);
                state.setVisibility(View.INVISIBLE);
                CodeEnter.setVisibility(View.VISIBLE);

                verificationId = s;
                token = forceResendingToken;
                btnnext.setText("Verify");
                verificationInProgess = true;


            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
                Toast.makeText(RegisterActivity.this, "OTP Expired - Re - Request OTP", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                verifyAuth(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

                Toast.makeText(RegisterActivity.this, "Cannot Create Account" + e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void chekUserProfile() {
        DocumentReference docref = fstore.collection("users").document(fauth.getCurrentUser().getUid());
        docref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    finish();
                }
            }
        });

    }
}