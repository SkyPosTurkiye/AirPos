/*

Project     : NewGen Retail Sales Automation
Owner       : SkyPos Technology CO.
Platform    : Android
Language    : Java

Module      : Not Found Module
Description : When the read barcode of the product is not found in store, throw and warning.

Extension History:


*/

package com.example.newgen;

import android.content.Intent;
import android.device.scanner.configuration.Triggering;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private ImageView powerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //getSupportActionBar().hide();

        powerButton = findViewById(R.id.powerButton);

        powerButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int action = event.getAction();
                switch (action) {

                    case MotionEvent.ACTION_UP:
                        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivityForResult(myIntent, 0);

                    break;

                    default:
                        break;
                }
                return true;
            }
        });
    }
}
