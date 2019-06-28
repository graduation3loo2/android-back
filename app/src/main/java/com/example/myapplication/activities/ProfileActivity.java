package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.models.User;
import com.example.myapplication.storage.SharedPreferenceManager;

public class ProfileActivity extends AppCompatActivity {

    private TextView textViewwelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textViewwelcome = findViewById(R.id.welcome);

        User user = SharedPreferenceManager.getInstance(this).getUser();
        textViewwelcome.setText("welcome back" + user.getName());
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(!SharedPreferenceManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
        }
    }
}
