package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class subscribe extends AppCompatActivity {

    EditText name,email;
    Button btn;

    Button btn1;
    Button btn2;
    SharedPreferences sp;
    String nameEntered, emailEntered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe);

            //declaring xml tags
        name = findViewById(R.id.name1);
        email= findViewById(R.id.email1);
        btn = findViewById(R.id.subscribeButton);
        btn1= findViewById(R.id.returnButton);
        btn2= findViewById(R.id.viewInfo);

        //creating instance of shared preferences

        sp = getSharedPreferences("UserPref", Context.MODE_PRIVATE);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                nameEntered = name.getText().toString(); //this variable is used to store the details entered
                emailEntered = email.getText().toString();

                SharedPreferences.Editor editor = sp.edit();//creating shared preference edit

                editor.putString("name",nameEntered); //this allows us to store the information in the shared preferences
                editor.putString("email",emailEntered);
                editor.commit();

                Toast.makeText(subscribe.this,"Subscription successful",Toast.LENGTH_LONG).show();

            }
        });


        btn1.setOnClickListener(new View.OnClickListener() {// button to return us to the daily new articles
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(subscribe.this, newActivity.class);
                startActivity(intent);

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {// To verify subscriptions
            //this button takes to the activity where we can view shared preferences
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(subscribe.this, savedInfo.class);
                startActivity(intent3);

            }
        });
    }
}