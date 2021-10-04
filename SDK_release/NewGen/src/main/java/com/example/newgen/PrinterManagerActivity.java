/*

Project     : NewGen Retail Sales Automation
Owner       : SkyPos Technology CO.
Platform    : Android
Language    : Java

Module      : Print Manager Module
Description : Payment Type Display

Extension History:


*/

package com.example.newgen;


import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.content.Intent;
import android.device.PrinterManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.device.PrinterManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import java.io.IOException;

public class PrinterManagerActivity extends AppCompatActivity {


    private final static String STR_PRNT_BILL = "prn_bill";
    private final static String STR_PRNT_TEXT = "text";
    private final static String STR_PRNT_BLCOK = "block";
    private final static String STR_PRNT_SALE = "sale";

    private final static String STR_FONT_VALUE_SONG = "simsun";

    private static int _XVALUE = 384;
    private static int _YVALUE = 24;
    private final int _YVALUE6 = 24;

    private static int fontSize = 24;
    private static int fontStyle = 0x0000;
    private static String fontName = STR_FONT_VALUE_SONG;

    private PrinterManager printer;


    private static final String TAG = "PrinterManagerActivity";
    private Button mBtnPrnBill;
    private Button mBtnPrnPic;
    private Button mBtnPrnBarcode;
    private Button mBtnForWard;
    private Button mBtnBack;
    private EditText printInfo;

    /********************** Config Start *************************************/
    // Do we a pre-install app or a 319 test one
    private boolean mIs319 = false;
    private boolean mIsTempEnable = false;
    /********************** Config End **************************************/

    private LinearLayout mLlFactoryTest;

    public final static String PRNT_ACTION = "action.printer.message";

    // Temperature
    private TextView mTvTemp;

    // Product Testing
    private boolean mIsFactoryTest = false;
    private LinearLayout mPrnPicture;
    private LinearLayout mPrnBill;

    private CheckBox mCbFactoryTest;
    private CheckBox mCbPrintBarcode;

    private LinearLayout mBarcodeTypeLayout;
    private FontStylePanel mFontStylePanel;

    private Bitmap mBmpPicture;
    private boolean isPrintText = false;

    private final int DEF_TEMP_THROSHOLD = 50;
    private int mTempThresholdValue = DEF_TEMP_THROSHOLD;

    private int[][] mVoltTempPair = {
            {898, 80},
            {1008, 75},
            {1130, 70},
            {1263, 65},
            {1405, 60},
            {1555, 55},
            {1713, 50},
            {1871, 45},
            {2026, 40},
            {2185, 35},
            {2335, 30},
            {2475, 25},
            {2605, 20},
            {2722, 15},
            {2825, 10},
            {2915, 5},
            {2991, 0},
            {3054, -5},
            {3107, -10},
            {3149, -15},
            {3182, -20},
            {3209, -25},
            {3231, -30},
            {3247, -35},
            {3261, -40},
    };

    private static final String[] mTempThresholdTable = {
            "80", "75", "70", "65", "60",
            "55", "50", "45", "40", "35",
            "30", "25", "20", "15", "10",
            "5", "0", "-5", "-10", "-15",
            "-20", "-25", "-30", "-35", "-40",
    };

    private final static String SPINNER_PREFERENCES_FILE = "SprinterPrefs";
    private final static String SPINNER_SELECT_POSITION_KEY = "spinnerPositions";
    private final static int DEF_SPINNER_SELECT_POSITION = 6;
    private final static String SPINNER_SELECT_VAULE_KEY = "spinnerValue";
    private final static String DEF_SPINNER_SELECT_VAULE = mTempThresholdTable[DEF_SPINNER_SELECT_POSITION];

    private int mSpinnerSelectPosition;
    private String mSpinnerSelectValue;

    private final static int DEF_PRINTER_HUE_VALUE = 0;

    // Printer Status
    private final static int PRNSTS_OK = 0;                // OK
    private final static int PRNSTS_OUT_OF_PAPER = -1;    // Out of paper
    private final static int PRNSTS_OVER_HEAT = -2;        // Over heat
    private final static int PRNSTS_UNDER_VOLTAGE = -3;    // under voltage
    private final static int PRNSTS_BUSY = -4;            // Device is busy
    private final static int PRNSTS_ERR = -256;            // Common error
    private final static int PRNSTS_ERR_DRIVER = -257;    // Printer Driver error

    private ImageView backButton, cashButton, ccButton, multinetButton, googlePayButton, applePayButton, trainTicketButton;

    private int Prn_Str(String msg, int fontSize, int height) {
        return printer.prn_drawText(msg, 0, height, STR_FONT_VALUE_SONG, fontSize, false,
                false, 0);
    }

    private int Prn_Str_Bold(String msg, int fontSize, int height) {
        return printer.prn_drawText(msg, 0, height, STR_FONT_VALUE_SONG, fontSize, true,
                false, 0);
    }

    private float x1,x2;
    static final int MIN_DISTANCE = 150;

    public static Activity PrinterActivity;

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
        PrinterActivity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalize_payment);
        new CustomThread().start();
        // get device Model: SQ27_P3_00WE_YBMoR_AU48_418_S_0_160314_01_3af5ecf
        //	String[] model = Build.ID.split("_");
        //mIsMoR = model[3].equals(MODEL_MOR);

        //getSupportActionBar().hide();

        final String[] cartList = getIntent().getStringArrayExtra("saleData");


        backButton = findViewById(R.id.backButton);
        cashButton = findViewById(R.id.cashButton);
        trainTicketButton = findViewById(R.id.trainTicketButton);
        multinetButton = findViewById(R.id.multinetButton);
        ccButton = findViewById(R.id.creditCardButton);
        googlePayButton = findViewById(R.id.googlePayButton);
        applePayButton = findViewById(R.id.applePayButton);

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

        cashButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        //resetStatusView();
                        cashButton.requestFocus();
                        cashButton.setBackgroundResource(R.drawable.cash_down);

                        break;
                    case MotionEvent.ACTION_UP:
                        doprintwork("sale", cartList);

                        cashButton.setBackgroundResource(R.drawable.cash_up);
                        //setContentView(R.layout.activity_product_barcode_scan);
                        //
                        break;
                }
                return true;
            }
        });
        trainTicketButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        //resetStatusView();
                        trainTicketButton.requestFocus();
                        trainTicketButton.setBackgroundResource(R.drawable.train_ticket_down);

                        break;
                    case MotionEvent.ACTION_UP:
                        doprintwork("trainsale", cartList);

                        trainTicketButton.setBackgroundResource(R.drawable.train_ticket_up);
                        //setContentView(R.layout.activity_product_barcode_scan);
                        //
                        break;
                }
                return true;
            }
        });

        multinetButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        //resetStatusView();
                        multinetButton.requestFocus();
                        multinetButton.setBackgroundResource(R.drawable.multinet_down);

                        break;
                    case MotionEvent.ACTION_UP:
                        doprintwork("multinetsale", cartList);

                        multinetButton.setBackgroundResource(R.drawable.multinet_up);
                        //setContentView(R.layout.activity_product_barcode_scan);
                        //
                        break;
                }
                return true;
            }
        });

        ccButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        //resetStatusView();
                        ccButton.requestFocus();
                        ccButton.setBackgroundResource(R.drawable.cc_down);

                        break;
                    case MotionEvent.ACTION_UP:
                        doprintwork("creditsale", cartList);

                        ccButton.setBackgroundResource(R.drawable.cc_up);
                        //setContentView(R.layout.activity_product_barcode_scan);
                        //

                        break;
                }
                return true;
            }
        });

        googlePayButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        //resetStatusView();
                        googlePayButton.requestFocus();
                        googlePayButton.setBackgroundResource(R.drawable.google_pay_down);

                        break;
                    case MotionEvent.ACTION_UP:
                        doprintwork("googlepaysale", cartList);

                        googlePayButton.setBackgroundResource(R.drawable.google_pay_up);
                        //setContentView(R.layout.activity_product_barcode_scan);
                        //
                        break;
                }
                return true;
            }
        });

        applePayButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        //resetStatusView();
                        applePayButton.requestFocus();
                        applePayButton.setBackgroundResource(R.drawable.apple_pay_down);

                        break;
                    case MotionEvent.ACTION_UP:
                        doprintwork("applepaysale", cartList);

                        applePayButton.setBackgroundResource(R.drawable.apple_pay_up);
                        //setContentView(R.layout.activity_product_barcode_scan);
                        //
                        break;
                }
                return true;
            }
        });

    }

    void doprintwork(String msg, String[] cartList) {

        Intent intentService = new Intent(this, PrintBillService.class);
        intentService.putExtra("SPRT", msg);
        if (isPrintText) {
            intentService.putExtra("font-info", mFontStylePanel.getFontInfo());
            intentService.putExtra("saleData", cartList);
        }
        intentService.putExtra("saleData", cartList);
        startService(intentService);
    }

    private static final int PHOTO_REQUEST_CODE = 200;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PHOTO_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    //通过uri的方式返回，部分手机uri可能为空
                    Bitmap bitmap = null;
                    if (uri != null) {
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        //部分手机可能直接存放在bundle中
                        Bundle bundleExtras = data.getExtras();
                        if (bundleExtras != null) {
                            bitmap = bundleExtras.getParcelable("data");
                        }
                    }
                    if (bitmap != null) {
                        mBmpPicture = Bitmap.createScaledBitmap(bitmap,
                                300, 300 * bitmap.getHeight() / bitmap.getWidth(), true);
                    }
                    mHandler.obtainMessage(PRNPIC).sendToTarget();
                }
                break;
        }
    }


    // return ture if printer's temperature is too high
    private boolean checkTempThreshold() {
        int currentTemp = getCurrentTemp();
        if (currentTemp == PRNSTS_BUSY) {
            Log.e(TAG, "Printer is busy");
            Toast.makeText(getApplicationContext(),
                    "Printer is busy",
                    Toast.LENGTH_SHORT).show();
            return true;
        } else if (currentTemp == PRNSTS_OVER_HEAT) {

            Log.e(TAG, "Printer is overheat");
            Toast.makeText(getApplicationContext(),
                    "Printer is overheat",
                    Toast.LENGTH_SHORT).show();
            return true;
        }

        mTvTemp.setText(String.valueOf(currentTemp));

        if (mIsTempEnable && currentTemp >= mTempThresholdValue) {
            Log.e(TAG, "Printer temperature meets the Threshold: " + mTempThresholdValue);
            Toast.makeText(getApplicationContext(),
                    "Printer temperature meets the Threshold: ",
                    Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }

    private int getCurrentTemp() {
        if (printer == null) {
            printer = new PrinterManager();
        }

        int currentTempVolt = 0;//printer.prn_getTemp() ;

//		Log.d("printer", "---------currentTempVolt---------" + currentTempVolt);

        String tmp = String.valueOf(currentTempVolt);
        // get first 4# or first 3#
        if (tmp.length() >= 4) {
            if (tmp.length() == 4 || tmp.length() == 6) {        // when temperature equals 80
                currentTempVolt = Integer.parseInt(tmp.substring(0, 3));
            } else {
                currentTempVolt = Integer.parseInt(tmp.substring(0, 4));
            }
        }

//		Log.d("printer", "getCurrentTemp =============== " + currentTempVolt);
        if (currentTempVolt < 0)
            return currentTempVolt;
        return voltToTemp(mVoltTempPair, currentTempVolt);
    }

    private int voltToTemp(int[][] table, int voltValue) {
        int left_side = 0;
        int right_side = table.length - 1;
        int mid;

        int realTemp = 0;

        while (left_side <= right_side) {
            mid = (left_side + right_side) / 2;

            if (mid == 0 || mid == table.length - 1 ||
                    (table[mid][0] <= voltValue && table[mid + 1][0] > voltValue)) {
                realTemp = table[mid][1];
                break;
            } else if (voltValue - table[mid][0] > 0)
                left_side = mid + 1;
            else
                right_side = mid - 1;
        }

        return realTemp;
    }

    private final int PRNPIC = 1;
    private final int BARCOD = 2;
    private final int FORWARD = 3;

    private Handler mHandler;

    class CustomThread extends Thread {
        @Override
        public void run() {
            //建立消息循环的步骤
            Looper.prepare();//1、初始化Looper
            mHandler = new Handler() {//2、绑定handler到CustomThread实例的Looper对象
                public void handleMessage(Message msg) {//3、定义处理消息的方法

                }
            };
            Looper.loop();//4、启动消息循环
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        PackageManager pk = getPackageManager();
        PackageInfo pi;
        try {
            pi = pk.getPackageInfo(getPackageName(), 0);
            Toast.makeText(this, "V" + pi.versionName, Toast.LENGTH_SHORT).show();
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return super.onOptionsItemSelected(item);
    }
}
