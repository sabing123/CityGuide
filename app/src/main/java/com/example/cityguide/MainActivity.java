package com.example.cityguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore fstore;

    TextView ufname, ulname, uemail, uphone;
    ImageView img_logut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ufname = findViewById(R.id.ufname);
        ulname = findViewById(R.id.ulname);
        uemail = findViewById(R.id.uemail);
        uphone = findViewById(R.id.uphone);

        img_logut = findViewById(R.id.imglogut);

        img_logut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            finish();
            }
        });



        firebaseAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        DocumentReference docref = fstore.collection("users").document(firebaseAuth.getCurrentUser().getUid());
        docref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    ufname.setText(documentSnapshot.getString("FirstName"));
                    ulname.setText(documentSnapshot.getString("LastName"));
                    uemail.setText(documentSnapshot.getString("Emailaddress"));
                    uphone.setText(firebaseAuth.getCurrentUser().getPhoneNumber());
                }


            }
        });

    }
}