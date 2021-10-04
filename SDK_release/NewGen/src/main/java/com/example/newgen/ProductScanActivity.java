/*

Project     : NewGen Retail Sales Automation
Owner       : SkyPos Technology CO.
Platform    : Android
Language    : Java

Module      : Barcode Scanner Module
Description : Scanning of product barcodes and displaying the product image on screen.

Extension History:


*/

package com.example.newgen;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.device.ScanManager;
import android.device.scanner.configuration.PropertyID;
import android.device.scanner.configuration.Triggering;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductScanActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {


    boolean crewIdScanned = false;
    String scannedText = "";
    private final static String SCAN_ACTION = ScanManager.ACTION_DECODE;//default action
    private ImageView scanButton, nextButton, crewPhoto, paymentButton;
    private TextView barcode_result, decode_length, decode_symbology, displayedName;
    private EditText scanResult;
    private EditText scanResult2;
    private EditText crewName;
    private CheckBox continuousScan;
    private RadioGroup mRadioGroup;
    private ScanManager mScanManager;

    Double totalAmount = 0.00;
    String[] soldItems = new String[100];
    boolean saleExist = false;
    int soldItemCount = 0;

    static Double grandTotalAmount = 0.00;
    public static String[] grandSoldItems = new String[100];
    static int grandSoldItemCount = 0;

    public static String[] getGrandItems(){
        grandSoldItems[grandSoldItemCount+1] = "Total In-Fliht Sales:" + grandTotalAmount.toString();
        grandSoldItems[grandSoldItemCount+2] = "Sales Crew Code:" + MainActivity.getCrewCode();
        return grandSoldItems;
    }


    private float x1,x2;
    static final int MIN_DISTANCE = 150;

    public static Activity ProductScanActivity;

    private final static String STR_FONT_VALUE_SONG = "simsun";
    private static String fontName = STR_FONT_VALUE_SONG;

    int[] idbuf = new int[]{PropertyID.WEDGE_INTENT_ACTION_NAME, PropertyID.WEDGE_INTENT_DATA_STRING_TAG};
    int[] idmodebuf = new int[]{PropertyID.WEDGE_KEYBOARD_ENABLE, PropertyID.TRIGGERING_MODES, PropertyID.LABEL_APPEND_ENTER};
    String[] action_value_buf = new String[]{ScanManager.ACTION_DECODE, ScanManager.BARCODE_STRING_TAG};
    int[] idmode;
    private Spinner mAppendCharType;
    private int mAppendCharValue;
    private BroadcastReceiver mScanReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub

            byte[] barcode = intent.getByteArrayExtra(ScanManager.DECODE_DATA_TAG);
            int barcodelen = intent.getIntExtra(ScanManager.BARCODE_LENGTH_TAG, 0);
            byte temp = intent.getByteExtra(ScanManager.BARCODE_TYPE_TAG, (byte) 0);
            String result = intent.getStringExtra(action_value_buf[1]);
            /*if(barcodelen != 0)
                barcodeStr = new String(barcode, 0, barcodelen);
            else
                barcodeStr = intent.getStringExtra("barcode_string");*/
            if (result != null) {
                barcode_result.setText("" + result);
                crewName.setText("aaaaa");
                decode_length.setText("" + result.length());
            }
        }

    };

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

    private List<Product> getProductDataFromJson(JSONObject jSon) {

        String tagProduct = "Items";
        String tagName = "Name";
        String tagBarcode = "Barcode";
        String tagPrice = "Price";
        String tagQuantity = "Quantity";
        String tagItemId = "ItemId";

        List<Product> list = new ArrayList<Product>();
        Product product = null;

        try {
            /** root -> booklist */
            JSONObject jSonRoot = jSon.getJSONObject("store");

            /** array -> book */
            JSONArray jSonArrayProduct = jSonRoot.getJSONArray(tagProduct);

            /** book count */
            int length = jSonArrayProduct.length();

            for (int i = 0; i < length; i++) {
                JSONObject jSonProduct = (JSONObject) jSonArrayProduct.get(i);

                String name = jSonProduct.getString(tagName);
                String barcode = jSonProduct.getString(tagBarcode);
                Double price = jSonProduct.getDouble(tagPrice);
                Integer quantity = jSonProduct.getInt(tagQuantity);
                Integer itemId = jSonProduct.getInt(tagItemId);

                product = new Product(name, barcode, price, quantity, itemId);
                list.add(product);
            }
        } catch (Exception e) {
            AlertDialog alertDialog111 = new AlertDialog.Builder(ProductScanActivity.this).create();
            alertDialog111.setTitle("Houston We Have a Problem");
            alertDialog111.setMessage("Problem is: " + e.getMessage());
            alertDialog111.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog111.show();
            e.printStackTrace();
            //list = null;
        }
        return list;
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ProductScanActivity = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_barcode_scan);
        //getSupportActionBar().hide();
        mScanManager = new ScanManager();
        mScanManager.openScanner();
        action_value_buf = mScanManager.getParameterString(idbuf);
        idmode = mScanManager.getParameterInts(idmodebuf);
        continuousScan = findViewById(R.id.continuousScan);
        continuousScan.setChecked(idmode[1] == Triggering.CONTINUOUS.toInt());
        continuousScan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mScanManager.setTriggerMode(b ? Triggering.CONTINUOUS : Triggering.HOST);
                idmode[1] = b ? Triggering.CONTINUOUS.toInt() : Triggering.HOST.toInt();
                scanButton.setBackgroundResource(R.drawable.scan_button);
            }
        });
        scanResult = findViewById(R.id.scanResult);
        scanResult2 = findViewById(R.id.scanResult2);
        crewName = findViewById(R.id.crewName);
        mRadioGroup = findViewById(R.id.mode_output);
        mRadioGroup.setOnCheckedChangeListener(this);
        RadioButton keyboardMode = findViewById(R.id.keyboard_output);
        keyboardMode.setChecked(idmode[0] == 1);
        RadioButton intentMode = findViewById(R.id.intent_output);
        if (idmode[0] == 0) {
            scanResult.setVisibility(View.GONE);
            scanResult2.setVisibility(View.GONE);
            intentMode.setChecked(true);
        }
        decode_symbology = findViewById(R.id.symbology_result);
        decode_length = findViewById(R.id.length_result);
        barcode_result = findViewById(R.id.barcode_result);
        displayedName = findViewById(R.id.displayedName);
        scanButton = findViewById(R.id.scanButton);
        nextButton = findViewById(R.id.nextButton);
        crewPhoto = findViewById(R.id.crewPhoto);
        scanButton.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        //resetStatusView();
                        scanResult.requestFocus();
                        if (idmode[1] == Triggering.CONTINUOUS.toInt()) {
                            scanButton.setBackgroundResource(R.drawable.scan_button_down);
                        /*if(vibrator != null)
                            vibrator.vibrate(VIBRATE_DURATION);*/
                            mScanManager.startDecode();
                        } else {
                            scanButton.setBackgroundResource(R.drawable.scan_button_down);
                        /*if(vibrator != null)
                            vibrator.vibrate(VIBRATE_DURATION);*/
                            mScanManager.startDecode();
                            scannedText = scanResult.getText().toString();

                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (idmode[1] == Triggering.CONTINUOUS.toInt()) {

                        } else {
                            mScanManager.stopDecode();
                        }
                        scanButton.setBackgroundResource(R.drawable.scan_button);
                        scannedText = crewName.getEditableText().toString();

                        crewPhoto.setImageResource(R.drawable.barcode_scanner);
                        displayedName.setText("");

                        crewIdScanned = false;

                        if (scannedText.equals("4025127027903")) {
                            crewPhoto.setImageResource(R.drawable.goldbergtonic);
                            displayedName.setText("GOLDBERG TONIC WATER 150ML");
                            totalAmount = totalAmount + 10.00;
                            soldItems[soldItemCount] = "1 PC GOLDBERG TONIC x 10.00 TL";
                            soldItemCount += 1;
                            saleExist = true;
                            crewIdScanned = true;

                            grandSoldItems[grandSoldItemCount] = "1 PC GOLDBERG TONIC x 10.00 TL";
                            grandSoldItemCount += 1;
                            grandTotalAmount = grandTotalAmount + 10.00;
                        }
                        if (scannedText.equals("5206542000675")) {
                            crewPhoto.setImageResource(R.drawable.greencola);
                            displayedName.setText("GREEN COLA 330ML IN TIN");
                            totalAmount = totalAmount + 10.00;
                            soldItems[soldItemCount] = "1 PC GREEN COLA x 10.00 TL";
                            soldItemCount += 1;
                            saleExist = true;
                            crewIdScanned = true;

                            grandSoldItems[grandSoldItemCount] = "1 PC GREEN COLA x 10.00 TL";
                            grandSoldItemCount += 1;
                            grandTotalAmount = grandTotalAmount + 10.00;
                        }
                        if (scannedText.equals("5711953009426")) {
                            crewPhoto.setImageResource(R.drawable.cociomilkshake);
                            displayedName.setText("COCIO MILKSHAKE 180ML RASPBERRY & WHITE CHOCOLATE");
                            totalAmount = totalAmount + 10.00;
                            soldItems[soldItemCount] = "1 PC COCIO MILKSHAKE x 10.00 TL";
                            soldItemCount += 1;
                            saleExist = true;
                            crewIdScanned = true;

                            grandSoldItems[grandSoldItemCount] = "1 PC COCIO MILKSHAKE x 10.00 TL";
                            grandSoldItemCount += 1;
                            grandTotalAmount = grandTotalAmount + 10.00;

                        }
                        if (scannedText.equals("8693202010698")) {
                            crewPhoto.setImageResource(R.drawable.hamidiye20cl);
                            displayedName.setText("STILL WATER HAMIDIYE 200ML");
                            totalAmount = totalAmount + 5.00;
                            soldItems[soldItemCount] = "1 PC STILL WATER HAMIDIYE x 5.00 TL";
                            soldItemCount += 1;
                            saleExist = true;
                            crewIdScanned = true;

                            grandSoldItems[grandSoldItemCount] = "1 PC STILL WATER HAMIDIYE x 5.00 TL";
                            grandSoldItemCount += 1;
                            grandTotalAmount = grandTotalAmount + 5.00;
                        }
                        if (scannedText.equals("8694918022739")) {
                            crewPhoto.setImageResource(R.drawable.tadafasulye);
                            displayedName.setText("TADA TEREYAGLI CAYELI FASULYE 250GR");
                            totalAmount = totalAmount + 40.00;
                            soldItems[soldItemCount] = "1 PC TEREYAGLI CAYELI FASULYE x 40.00 TL";
                            soldItemCount += 1;
                            saleExist = true;
                            crewIdScanned = true;

                            grandSoldItems[grandSoldItemCount] = "1 PC TEREYAGLI CAYELI FASULYE x 40.00 TL";
                            grandSoldItemCount += 1;
                            grandTotalAmount = grandTotalAmount + 40.00;
                        }
                        if (scannedText.equals("8680923210247")) {
                            crewPhoto.setImageResource(R.drawable.cepmix);
                            displayedName.setText("CEPMIX 30GR KURU INCIR & FINDIK");
                            totalAmount = totalAmount + 8.00;
                            soldItems[soldItemCount] = "1 PC CEPMIX 30GR  x 8.00 TL";
                            soldItemCount += 1;
                            saleExist = true;
                            crewIdScanned = true;

                            grandSoldItems[grandSoldItemCount] = "1 PC CEPMIX 30GR  x 8.00 TL";
                            grandSoldItemCount += 1;
                            grandTotalAmount = grandTotalAmount + 8.00;

                        }
                        if (!crewIdScanned) {
                            Intent myIntent = new Intent(getApplicationContext(), UfoActivity.class);
                            startActivityForResult(myIntent, 0);
                        }
                        crewName.setText("");
                        //
                        break;
                }
                return true;
            }
        });
        // Next Button OnTouchListener
        nextButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        //resetStatusView();
                        nextButton.setImageResource(R.drawable.payment_down);
                        break;
                    case MotionEvent.ACTION_UP:
                        if (crewIdScanned) {
                            nextButton.setImageResource(R.drawable.payment_up);
                            Intent myIntent = new Intent(getApplicationContext(), SaleSummary.class);
                            soldItems[soldItemCount] = totalAmount.toString();
                            soldItemCount += 1;
                            soldItems[soldItemCount] = Integer.toString(soldItemCount);
                            myIntent.putExtra("saleData", soldItems);
                            startActivityForResult(myIntent, 0);
                        } else {
                            nextButton.setImageResource(R.drawable.payment_up);
                            Intent myIntent = new Intent(getApplicationContext(), SaleSummary.class);
                            startActivityForResult(myIntent, 0);

                        }

                        //
                        break;
                }
                return true;
            }
        });


        scanResult.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // TODO Auto-generated method stub
                if (KeyEvent.KEYCODE_ENTER == keyCode && event.getAction() == KeyEvent.ACTION_UP) {
                    int height = 66;
                    scannedText = scanResult.getText().toString();
                    //printer.prn_drawText("dsadsaf", 0, height, (STR_FONT_VALUE_SONG),
                    //        12, false, false, 0);
                    //printer.prn_open();
                    barcode_result.setText("" + scanResult.getText());
                    decode_length.setText("" + scanResult.getText().length());
                    //crewName.setText("222" + scanResult.getText());
                    //crewPhoto.setImageResource(R.drawable.suleyman);

                    //crewName.setText("" + scanResult.getText() + " Code set 1");
                    //scanResult.setText("");
                    scanResult.requestFocus();
                    //Toast.makeText(MainActivity.this, "EditorAction " + event.toString(), Toast.LENGTH_LONG).show();
                    return true;
                } else if (KeyEvent.KEYCODE_TAB == keyCode && event.getAction() == KeyEvent.ACTION_DOWN) {
                    barcode_result.setText("" + scanResult.getText());
                    decode_length.setText("" + scanResult.getText().length());
                    scannedText = scanResult.getText().toString();
                    //crewName.setText("333" + scanResult.getText());

                    //crewName.setText("" + scanResult.getText() + " Code set 2");
                    //scanResult.setText("");
                    //Toast.makeText(MainActivity.this, "EditorAction " + event.toString(), Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });

        mAppendCharType = findViewById(R.id.spinner_barcode);
        ArrayAdapter mBarcodeTypeAdapter = ArrayAdapter.createFromResource(
                this, R.array.appendCharType,
                android.R.layout.simple_spinner_item);

        mBarcodeTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mAppendCharType.setAdapter(mBarcodeTypeAdapter);
        mAppendCharValue = idmode[2];
        mAppendCharType.setSelection(mAppendCharValue);
        mAppendCharType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                mAppendCharValue = position;
                int[] idappend = new int[1];
                idappend[0] = PropertyID.LABEL_APPEND_ENTER;
                idmode[0] = mAppendCharValue;
                mScanManager.setParameterInts(idappend, idmode);
                if (mAppendCharValue == 2) {
                    scanResult2.requestFocus();
                    idappend[0] = PropertyID.WEDGE_KEYBOARD_TYPE;
                    idmode[0] = 3;
                    mScanManager.setParameterInts(idappend, idmode);
                } else if (mAppendCharValue == 3) {
                    idappend[0] = PropertyID.WEDGE_KEYBOARD_TYPE;
                    idmode[0] = 2;
                    mScanManager.setParameterInts(idappend, idmode);
                    scanResult.requestFocus();
                } else {
                    idappend[0] = PropertyID.WEDGE_KEYBOARD_TYPE;
                    idmode[0] = 0;
                    mScanManager.setParameterInts(idappend, idmode);
                }
                //scannedText = scanResult.getText().toString();
                //crewName.setText(scannedText);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        scanResult2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    barcode_result.setText("" + scanResult2.getText());

                    decode_length.setText("" + scanResult2.getText().length());
                    //crewName.setText("444" + scanResult2.getText());

                    //crewName.setText("" + scanResult2.getText() + " Code set 3");

                    //scanResult2.setText("");

                    Toast.makeText(ProductScanActivity.this, "EditorAction DNOE event", Toast.LENGTH_LONG).show();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 138 || keyCode == 120 || keyCode == 520 || keyCode == 521 || keyCode == 522) {
            if (mAppendCharValue != 2)
                scanResult.requestFocus();

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        unregisterReceiver(mScanReceiver);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        IntentFilter filter = new IntentFilter();
        action_value_buf = mScanManager.getParameterString(idbuf);
        filter.addAction(action_value_buf[0]);
        registerReceiver(mScanReceiver, filter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.activity_main, menu);
        MenuItem settings = menu.add(0, 1, 0, R.string.menu_settings).setIcon(R.drawable.ic_action_settings);
        // 绑定到actionbar
        //SHOW_AS_ACTION_IF_ROOM 显示此项目在动作栏按钮如果系统决定有它。 可以用1来代替
        MenuItem version = menu.add(0, 2, 0, R.string.menu_about).setIcon(android.R.drawable.ic_menu_info_details);
        settings.setShowAsAction(1);
        version.setShowAsAction(0);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case 1:
                try {
                    Intent intent = new Intent("android.intent.action.SCANNER_SETTINGS");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
                break;
            case 2:
                PackageManager pk = getPackageManager();
                PackageInfo pi;
                try {
                    pi = pk.getPackageInfo(getPackageName(), 0);
                    Toast.makeText(this, "V" + pi.versionName, Toast.LENGTH_SHORT).show();
                } catch (PackageManager.NameNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.intent_output:
                idmode[0] = 0;
                mScanManager.setParameterInts(idmodebuf, idmode);
                scanResult.setVisibility(View.GONE);
                break;
            case R.id.keyboard_output:
                idmode[0] = 1;
                mScanManager.setParameterInts(idmodebuf, idmode);
                scanResult.setVisibility(View.VISIBLE);
                scanResult.requestFocus();
                break;
        }
    }
}
