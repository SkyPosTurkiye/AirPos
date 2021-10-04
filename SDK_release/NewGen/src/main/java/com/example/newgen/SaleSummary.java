package com.example.newgen;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SaleSummary extends AppCompatActivity {

    private ImageView nextButton;

    private float x1,x2;
    static final int MIN_DISTANCE = 150;

    public static Activity SaleSummaryActivity;

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;
                if (Math.abs(deltaX) > MIN_DISTANCE)
                {
                    Intent myIntent = new Intent(getApplicationContext(), SeatMapActivity.class);
                    startActivityForResult(myIntent, 0);

                    //Toast.makeText(this, "left2right swipe", Toast.LENGTH_SHORT).show ();
                }
                else
                {
                    // consider as something else - a screen tap for example
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SaleSummaryActivity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_summary);
        //getSupportActionBar().hide();
        final ListView list = findViewById(R.id.list);
        ArrayList<SubjectData> arrayList = new ArrayList<SubjectData>();

        final String[] cartList = getIntent().getStringArrayExtra("saleData");

        arrayList.add(new SubjectData("", "", ""));

        int cartListLength = cartList.length;
        int counter = 0;
        String lastItem = "";

        for (String e : cartList) {
            counter += 1;
            if (e == "" || e == null) {
                lastItem = cartList[counter-3];
                break;
            }

            String productPhoto = "";

            switch (e) {
                case "1 PC GREEN COLA x 10.00 TL":
                    productPhoto = "cc";
                    break;

                case "1 PC COCIO MILKSHAKE x 10.00 TL":
                    productPhoto = "ms";
                    break;

                case "1 PC GOLDBERG TONIC x 10.00 TL":
                    productPhoto = "gb";
                    break;

                case "1 PC STILL WATER HAMIDIYE x 5.00 TL":
                    productPhoto = "hm";
                    break;

                case "1 PC TEREYAGLI CAYELI FASULYE x 40.00 TL":
                    productPhoto = "td";
                    break;

                case "1 PC CEPMIX 30GR  x 8.00 TL":
                    productPhoto = "cm";
                    break;

                default:
                    productPhoto = "";
                    break;            }

            arrayList.add(new SubjectData(e, "", productPhoto));;
        }
        arrayList.remove(arrayList.size()-1);
        arrayList.remove(arrayList.size()-1);
        arrayList.add(new SubjectData("*** GRAND TOTAL TL:" + lastItem + " ***", "", "arrowgreen"));
        arrayList.add(new SubjectData("", "", ""));
        arrayList.add(new SubjectData("", "", ""));

        nextButton = findViewById(R.id.nextButton);

        //arrayList.add(new SubjectData("JAVA", "https://www.tutorialspoint.com/java/", "https://www.tutorialspoint.com/java/images/java-mini-logo.jpg"));
 /*       arrayList.add(new SubjectData("Python", "https://www.tutorialspoint.com/python/", "https://www.tutorialspoint.com/python/images/python-mini.jpg"));
        arrayList.add(new SubjectData("Javascript", "https://www.tutorialspoint.com/javascript/", "https://www.tutorialspoint.com/javascript/images/javascript-mini-logo.jpg"));
        arrayList.add(new SubjectData("Cprogramming", "https://www.tutorialspoint.com/cprogramming/", "https://www.tutorialspoint.com/cprogramming/images/c-mini-logo.jpg"));
        arrayList.add(new SubjectData("Cplusplus", "https://www.tutorialspoint.com/cplusplus/", "https://www.tutorialspoint.com/cplusplus/images/cpp-mini-logo.jpg"));
        arrayList.add(new SubjectData("Android", "https://www.tutorialspoint.com/android/", "https://www.tutorialspoint.com/android/images/android-mini-logo.jpg"));
        arrayList.add(new SubjectData("JAVA", "https://www.tutorialspoint.com/java/", "https://www.tutorialspoint.com/java/images/java-mini-logo.jpg"));
        arrayList.add(new SubjectData("Python", "https://www.tutorialspoint.com/python/", "https://www.tutorialspoint.com/python/images/python-mini.jpg"));
        arrayList.add(new SubjectData("Javascript", "https://www.tutorialspoint.com/javascript/", "https://www.tutorialspoint.com/javascript/images/javascript-mini-logo.jpg"));
        arrayList.add(new SubjectData("Cprogramming", "https://www.tutorialspoint.com/cprogramming/", "https://www.tutorialspoint.com/cprogramming/images/c-mini-logo.jpg"));
        arrayList.add(new SubjectData("Cplusplus", "https://www.tutorialspoint.com/cplusplus/", "https://www.tutorialspoint.com/cplusplus/images/cpp-mini-logo.jpg"));
        arrayList.add(new SubjectData("Android", "https://www.tutorialspoint.com/android/", "https://www.tutorialspoint.com/android/images/android-mini-logo.jpg"));
*/

/*        arrayList.add(new SubjectData("FLIGHT  : XQ9284 ADB-VAN", "https://www.tutorialspoint.com/android/", "arrowblack"));
        arrayList.add(new SubjectData("AIRCRAFT: TC-SNR B737-800", "https://www.tutorialspoint.com/android/", "arrowblue"));
        arrayList.add(new SubjectData("STORE NR: 2353", "https://www.tutorialspoint.com/android/", "arrowpink"));
        arrayList.add(new SubjectData("STORE ID: 231180", "https://www.tutorialspoint.com/android/", "arrowgreen"));
        arrayList.add(new SubjectData("********  STOCK LIST ********", "https://www.tutorialspoint.com/android/", ""));
        arrayList.add(new SubjectData("Coca Cola 33ml Tin", "https://www.tutorialspoint.com/java/", "cc"));
        arrayList.add(new SubjectData("Goldberg Gin", "https://www.tutorialspoint.com/python/", "gb"));
        arrayList.add(new SubjectData("Hamidiye Water", "https://www.tutorialspoint.com/javascript/", "hm"));
        arrayList.add(new SubjectData("Cocio Milkshake", "https://www.tutorialspoint.com/cprogramming/", "ms"));
        arrayList.add(new SubjectData("Cepmix", "https://www.tutorialspoint.com/cplusplus/", "cm"));
        arrayList.add(new SubjectData("Tada Tereyagli Fasulye", "https://www.tutorialspoint.com/android/", "td"));
        arrayList.add(new SubjectData("", "", ""));
        arrayList.add(new SubjectData("", "", ""));*/


        CustomAdapter customAdapter = new CustomAdapter(this, arrayList);
        list.setAdapter(customAdapter);

        nextButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        //resetStatusView();
                        nextButton.setImageResource(R.drawable.next_down);
                        break;
                    case MotionEvent.ACTION_UP:
                        nextButton.setImageResource(R.drawable.next_up);
                        //Intent myIntent = new Intent(getApplicationContext(), ProductScanActivity.class);
                        //startActivityForResult(myIntent, 0);
                        Intent myIntent = new Intent(getApplicationContext(), PrinterManagerActivity.class);
                        myIntent.putExtra("saleData", cartList);
                        startActivityForResult(myIntent, 0);
                        //
                        break;
                }
                return true;
            }
        });
    }
}
