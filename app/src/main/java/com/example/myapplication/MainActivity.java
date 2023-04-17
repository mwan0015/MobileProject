package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

        private  DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //assign variables for nav view and toolbar
         drawerLayout= findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);

        NavigationView navigationView= findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new homeFragment());

        //to toggle on and off the action bar
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.News_Reader_Open,R.string.News_Reader_Close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //this saved instance states allows us to reload the UI state

        if(savedInstanceState== null){

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new homeFragment());
            navigationView.setCheckedItem(R.id.home);
        }


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        // this method allows us to select items from the menu
        //we use a switch case to jump from fragments and activities

        switch (menuItem.getItemId()){


            //allows us to click between specified menu items

            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new homeFragment()).commit();
                break;


                // for activities we use intents to

             case R.id.News:
                //All the news articles will be loaded on this activity
            Intent intent = new Intent(this, newActivity.class);
            startActivity(intent);
            break;

            case R.id.subscribe1:

                //FOR SUBSCRIBING FOR UPDATES

                Intent intent1 = new Intent(this, subscribe.class);
                startActivity(intent1);
                break;
                //allows to click to see saved preferences which is the user info
            case R.id.user:
                Intent intent2 = new Intent(this, savedInfo.class);
                startActivity(intent2);
                break;



        }

        //

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }
}