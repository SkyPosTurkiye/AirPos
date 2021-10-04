/*

Project     : NewGen Retail Sales Automation
Owner       : SkyPos Technology CO.
Platform    : Android
Language    : Java

Module      : Seat Map Module
Description : Show interactive seat map for preorders.

Extension History:


*/

package com.example.newgen;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SeatMapActivity extends AppCompatActivity {
    private ImageView backButton, manualListButton, seatMap;

    ImageView menuBreakfast, menuKids, menuRavioli, menuSalad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seatmap);
        //getSupportActionBar().hide();

        //menuBreakfast.findViewById(R.id.menuBreakfast);
        //menuRavioli.findViewById(R.id.menuRavioli);
        menuKids = findViewById(R.id.menuKids);
        //menuSalad.findViewById(R.id.menuSalad);

        //menuBreakfast.setImageResource(R.drawable.breakfast);
        menuKids.setImageResource(R.drawable.passangercard);
        //menuSalad.setImageResource(R.drawable.salad);
        //menuRavioli.setImageResource(R.drawable.ravioli);

        backButton = findViewById(R.id.backButton);
        manualListButton = findViewById(R.id.manualListButton);
        seatMap = findViewById(R.id.seatmap);

        seatMap.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        //resetStatusView();
                        break;
                    case MotionEvent.ACTION_UP:

                        loadPhoto(menuKids, 200,400);
                        //
                        break;
                }
                return true;
            }
        });

        backButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        //resetStatusView();
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

    private void loadPhoto(ImageView imageView, int width, int height) {

        ImageView tempImageView = imageView;


        AlertDialog.Builder imageDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);

        View layout = inflater.inflate(R.layout.custom_fullimage_dialog,
                (ViewGroup) findViewById(R.id.layout_root));
        ImageView image = (ImageView) layout.findViewById(R.id.fullimage);
        image.setImageDrawable(tempImageView.getDrawable());
        imageDialog.setView(layout);
        imageDialog.setPositiveButton(getResources().getString(R.string.ok_button), new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        });

        imageDialog.create();
        imageDialog.show();
    }

}
