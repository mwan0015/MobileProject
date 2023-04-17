package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class savedInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_info);

        //this activity is created to store the saved preferences which will be the activity information
        TextView name2,email2;

        name2 = findViewById(R.id.name2);
        email2= findViewById(R.id.email2);

        //we extract the saved preferences info and set it into the activity

        SharedPreferences sp = getApplicationContext().getSharedPreferences("UserPref", Context.MODE_PRIVATE);
        String storedName= sp.getString("name","");
        String storedEmail = sp.getString("email","");

        name2.setText(storedName);
        email2.setText(storedEmail);

    }
}