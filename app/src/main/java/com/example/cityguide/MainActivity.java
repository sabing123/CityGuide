package com.example.cityguide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cityguide.StrictMode.strictmodeclass;
import com.example.cityguide.fragement.fragment_home;
import com.example.cityguide.fragement.fragment_hotel;
import com.example.cityguide.fragement.fragment_places;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

//    FirebaseAuth firebaseAuth;
//    FirebaseFirestore fstore;
//    TextView ufname, ulname, uemail, uphone;
//    ImageView img_logut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // load the store fragment by default
        loadFragment(new fragment_home(), 1);
        strictmodeclass.StrictMode();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.action_home:
                    fragment = new fragment_home();
                    loadFragment(fragment, 1);
                    return true;
                case R.id.action_hotel:
                    fragment = new fragment_hotel();
                    loadFragment(fragment, 2);
                    return true;
                case R.id.action_places:
                    fragment = new fragment_places();
                    loadFragment(fragment, 3);
                    return true;

            }
            return false;
        }
    };

    private int position = 0;

    private void loadFragment(Fragment fragment, int position) {

        while (this.position != position) {
            // load fragment
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_container, fragment);
            if (this.position < position) {
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_from_right);
            } else {
                transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_from_left);
            }
            this.position = position;
            transaction.addToBackStack(null);
            transaction.detach(fragment);
            transaction.attach(fragment);
            transaction.commit();
        }

    }
}