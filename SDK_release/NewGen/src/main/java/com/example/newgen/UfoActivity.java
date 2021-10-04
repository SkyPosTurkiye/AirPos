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

import android.device.scanner.configuration.Triggering;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class UfoActivity extends AppCompatActivity {

    private ImageView backButton, manualListButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ufo);
        //getSupportActionBar().hide();

        backButton = findViewById(R.id.backButton);
        manualListButton = findViewById(R.id.manualListButton);

        backButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        //resetStatusView();
                        backButton.requestFocus();
                        backButton.setBackgroundResource(R.drawable.go_back_down);

                        break;
                    case MotionEvent.ACTION_UP:

                        backButton.setBackgroundResource(R.drawable.go_back_up);
                        finish();
                        //
                        break;
                }
                return true;
            }
        });

        manualListButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        //resetStatusView();
                        manualListButton.requestFocus();
                        manualListButton.setBackgroundResource(R.drawable.manual_list_down);

                        break;
                    case MotionEvent.ACTION_UP:

                        manualListButton.setBackgroundResource(R.drawable.manual_list_up);
                        //setContentView(R.layout.activity_product_barcode_scan);
                        //
                        break;
                }
                return true;
            }
        });
    }
}
