package com.example.smart_coordinator;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Thread.sleep(3000);
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } catch(Exception e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_start);
    }
}
