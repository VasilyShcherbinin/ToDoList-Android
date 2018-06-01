package com1032.cw1.vs00162.vs00162_todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Class to create a SplashActivity and launch MainActivity after execution.
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Creating Intent to launch MainActivity.
        Intent splashIntent = new Intent(this, MainActivity.class);
        startActivity(splashIntent);
        finish();
    }
}
