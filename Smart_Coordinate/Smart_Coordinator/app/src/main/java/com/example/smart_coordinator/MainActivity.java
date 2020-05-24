package com.example.smart_coordinator;

import android.app.Activity;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.support.design.widget.BottomNavigationView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements FragmentCloset.closetBtnListener{

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentCloset fragmentCloset = new FragmentCloset();
    private FragmentCalendar fragmentCalendar = new FragmentCalendar();
    private FragmentWeather fragmentWeather = new FragmentWeather();
    private FragmentCloset_Data fragmentCloset_data = new FragmentCloset_Data();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragmentCloset).commitAllowingStateLoss();

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);

        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        View actionbar = inflater.inflate(R.layout.custom_actionbar, null);
        actionBar.setCustomView(actionbar);

        Toolbar parent = (Toolbar)actionbar.getParent();
        parent.setContentInsetsAbsolute(0,0);
        return true;
    }

    @Override
    public void onButtonClicked() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragmentCloset_data).commit();
        transaction.addToBackStack(null);
    }

    //bottom navigation
        class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch(menuItem.getItemId()) {
                    case R.id.closet:
                        transaction.replace(R.id.frameLayout, fragmentCloset).commitAllowingStateLoss();
                        break;
                    case R.id.calendar:
                        transaction.replace(R.id.frameLayout, fragmentCalendar).commitAllowingStateLoss();
                        break;
                    case R.id.weather:
                        transaction.replace(R.id.frameLayout, fragmentWeather).commitAllowingStateLoss();
                        break;
                }
                return true;
            }
        }


    }
