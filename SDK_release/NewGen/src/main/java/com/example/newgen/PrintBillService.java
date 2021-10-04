/*

Project     : NewGen Retail Sales Automation
Owner       : SkyPos Technology CO.
Platform    : Android
Language    : Java

Module      : Print Service Module
Description : Print service for entire printing purpose.

Extension History:


*/

package com.example.newgen;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.device.PrinterManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import java.util.Date;

public class PrintBillService extends IntentService {
    private final static String STR_PRNT_BILL = "prn_bill";
    private final static String STR_PRNT_TEXT = "text";
    private final static String STR_PRNT_BLCOK = "block";
    private final static String STR_PRNT_SALE = "sale";
    private final static String STR_PRNT_TRAIN_SALE = "trainsale";
    private final static String STR_PRNT_CREDIT_CARD_SALE = "creditsale";
    private final static String STR_PRNT_MULTINET_SALE = "multinetsale";
    private final static String STR_PRNT_GOOGLE_PAY_SALE = "googlepaysale";
    private final static String STR_PRNT_APPLE_PAY_SALE = "applepaysale";


    private final static String STR_FONT_VALUE_SONG = "simsun";

    private static int _XVALUE = 384;
    private static int _YVALUE = 24;
    private final int _YVALUE6 = 24;

    private static int fontSize = 24;
    private static int fontStyle = 0x0000;
    private static String fontName = STR_FONT_VALUE_SONG;

    private PrinterManager printer;

    public PrintBillService() {
        super("bill");
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        printer = new PrinterManager();
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    @Override
    @TargetApi(12)
    protected void onHandleIntent(Intent intent) {
        // TODO Auto-generated method stub
        String context = intent.getStringExtra("SPRT");
        final String[] cartList = intent.getStringArrayExtra("saleData");
        if (context == null || context.equals("")) return;

        int ret;
        if (context.equals(STR_PRNT_BILL)) {    // print bill
            printBill();
        } else if (context.equals(STR_PRNT_BLCOK)) {
            printBlock();
        } else if (context.equals(STR_PRNT_SALE)) {
            try {
                printSale(getBaseContext(), cartList);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else if (context.equals(STR_PRNT_TRAIN_SALE)) {
            try {
                printTrainSale(getBaseContext());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else if (context.equals(STR_PRNT_MULTINET_SALE)) {
            try {
                printMultinet(getBaseContext(), cartList);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else if (context.equals(STR_PRNT_GOOGLE_PAY_SALE)) {
            try {
                //printGooglePay(getBaseContext(), cartList);
                //String[] cartlistG = ProductScanActivity.getGrandItems();
                printClose(getBaseContext(), cartList);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else if (context.equals(STR_PRNT_APPLE_PAY_SALE)) {
            try {
                printApplePay(getBaseContext(), cartList);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else if (context.equals(STR_PRNT_GOOGLE_PAY_SALE)) {
            try {
                //String[] cartlistG = ProductScanActivity.getGrandItems();
                printClose(getBaseContext(), cartList);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else if (context.equals(STR_PRNT_CREDIT_CARD_SALE)) {
            try {
                printCreditSale(getBaseContext(), cartList);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {  // print string
            // add by tao.he, for custom print
            Bundle fontInfo = intent.getBundleExtra("font-info");
            android.util.Log.v("tao.he", fontInfo.toString());

            if (fontInfo != null) {
                fontSize = fontInfo.getInt("font-size", 24);
                fontStyle = fontInfo.getInt("font-style", 0);
                fontName = fontInfo.getString("font-name", STR_FONT_VALUE_SONG);
            } else {
                fontSize = 24;
                fontStyle = 0;
                fontName = STR_FONT_VALUE_SONG;
            }
            android.util.Log.v("tao.he", "font-size:" + fontSize);
            android.util.Log.v("tao.he", "font-style:" + fontStyle);
            android.util.Log.v("tao.he", "font-name:" + fontName);

            printer.prn_setupPage(384, -1);
            ret = printer.prn_drawTextEx(context, 5, 0, 384, -1, fontName, fontSize, 0, fontStyle, 0);
            printer.prn_paperForWard(20);
//            ret +=printer.prn_drawTextEx(context, 300, ret,-1,-1, "arial", 25, 1, 0, 0);
            android.util.Log.i("debug", "ret:" + ret);
        }
        // end add
        ret = printer.prn_printPage(0);

        Intent i = new Intent(PrinterManagerActivity.PRNT_ACTION);
        i.putExtra("ret", ret);
        this.sendBroadcast(i);
    }

    public void printBlock() {
        printer.prn_setupPage(_XVALUE, 248);
        /* Black block */
        printer.prn_drawLine(32, 8, 136, 8, 8);
        printer.prn_drawLine(32, 12, 136, 12, 8);
        printer.prn_drawLine(32, 18, 136, 18, 8);
        printer.prn_drawLine(32, 24, 136, 24, 8);
        printer.prn_drawLine(32, 32, 136, 32, 32);

        printer.prn_drawLine(136, 56, 240, 56, 8);
        printer.prn_drawLine(136, 62, 240, 62, 8);
        printer.prn_drawLine(136, 68, 240, 68, 8);
        printer.prn_drawLine(136, 74, 240, 74, 8);
        printer.prn_drawLine(136, 80, 240, 80, 32);

        printer.prn_drawLine(240, 104, 344, 104, 8);
        printer.prn_drawLine(240, 110, 344, 110, 8);
        printer.prn_drawLine(240, 116, 344, 116, 8);
        printer.prn_drawLine(240, 122, 344, 122, 8);
        printer.prn_drawLine(240, 128, 344, 128, 32);

        printer.prn_drawLine(136, 152, 240, 152, 8);
        printer.prn_drawLine(136, 158, 240, 158, 8);
        printer.prn_drawLine(136, 164, 240, 164, 8);
        printer.prn_drawLine(136, 170, 240, 170, 8);
        printer.prn_drawLine(136, 176, 240, 176, 32);

        printer.prn_drawLine(32, 200, 136, 200, 8);
        printer.prn_drawLine(32, 206, 136, 206, 8);
        printer.prn_drawLine(32, 212, 136, 212, 8);
        printer.prn_drawLine(32, 218, 136, 218, 8);
        printer.prn_drawLine(32, 224, 136, 224, 32);
    }

    public void printBill() {
        int height = 66;
        printer.prn_setupPage(384, 780);
        //   printer.prn_drawLine(0,0,384,0,2);

        printer.prn_drawText(("  CORENDON AIR"), 5, 50, (STR_FONT_VALUE_SONG), 48, false, false, 0);
        height += 48;
//    	printer.prn_drawText(("商户名(MERCHANT NAME):"), 0, 100, (STR_FONT_VALUE_SONG), 24 , false, false, 0);
//    	printer.prn_drawText(("  面点王（科技园店）"), 0, 126, (STR_FONT_VALUE_SONG), 24 , false, false, 0);
//
//		printer.prn_drawText(("商户号(MERCHANT NO):"), 0, 152, (STR_FONT_VALUE_SONG), 24,
//				false, false, 0);
//
//		printer.prn_drawText(("  104440358143001"), 0, 178, (STR_FONT_VALUE_SONG), 24,
//				false, false, 0);
//		printer.prn_drawText(("终端号(TERMINAL NO):"), 0, 204, (STR_FONT_VALUE_SONG), 24,
//				false, false, 0);
//		printer.prn_drawText(("  26605406"), 0, 230, (STR_FONT_VALUE_SONG), 24, false,
//				false, 0);
//		printer.prn_drawText(("卡号(CARD NO):"), 0, 256, (STR_FONT_VALUE_SONG), 24, false,
//				false, 0);
//
//		/* Black block */
//		// printer.prn_drawLine(0,380,384,380,500);
//		printer.prn_drawLine(32, 396, 352, 396, 8);
//		printer.prn_drawLine(32, 402, 352, 402, 8);
//		printer.prn_drawLine(32, 408, 352, 408, 8);
//		printer.prn_drawLine(32, 416, 352, 416, 8);
//		printer.prn_drawLine(32, 422, 352, 422, 32);

//		printer.prn_drawText(("  1234 56** ****0789"), 0, height, (STR_FONT_VALUE_SONG), 24,
//				false, false, 0);
//		height += 28;
//
//		printer.prn_drawText(("收单行号:01045840"), 0, height, (STR_FONT_VALUE_SONG), 24, false,
//				false, 0);
//		height += 28;
//
//		printer.prn_drawText(("发卡行名:渤海银行"), 0, height, (STR_FONT_VALUE_SONG), 24, false,
//				false, 0);
//		height += 28;

        printer.prn_drawText(("ABCDEFGHLIJKMNOPQXYZTRSW"), 0, height, (STR_FONT_VALUE_SONG),
                36, false, false, 0);
        height += 40;

        printer.prn_drawText(("ABCDEFGHLIJKMNOPQXYZTRSWGHLIJKMNOPQX"), 0,
                height, (STR_FONT_VALUE_SONG), 24, false, false, 0);
        height += 28;

        printer.prn_drawText(("abcdefghlijkmnopqxyztrsw"), 0, height, (STR_FONT_VALUE_SONG),
                36, false, false, 0);
        height += 40;

        printer.prn_drawText(("abcdefghlijkmnopqxyztrswefghlijkmn"), 0,
                height, (STR_FONT_VALUE_SONG), 24, false, false, 0);
        height += 28;

        printer.prn_drawText(("囎囏囐囑囒囓囔囕囖墼囏"), 0, height, (STR_FONT_VALUE_SONG), 36, false,
                false, 0);
        height += 42;

        printer.prn_drawText(("囎囏囐囑囒囓囔囕囖墼墽墾孽囎囏囓囔"), 0, height, (STR_FONT_VALUE_SONG),
                24, false, false, 0);
        height += 28;

        printer.prn_drawText(("HHHHHHHHHHHHHHHHHHHHHHHH"), 0, height, (STR_FONT_VALUE_SONG),
                36, false, false, 0);
        height += 40;

        printer.prn_drawText(("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH"),
                0, height, (STR_FONT_VALUE_SONG), 24, false, false, 0);
        height += 32;

        printer.prn_drawText(("☆★○●▲△▼☆★○●▲☆★○"), 0, height, STR_FONT_VALUE_SONG, 36, false,
                false, 0);
        height += 40;

        printer.prn_drawText(("ぱばびぶづぢだざじずぜぞ"), 0, height, (STR_FONT_VALUE_SONG), 36, false,
                false, 0);
        height += 48;

        printer.prn_drawText(("㊣㈱卍▁▂▃▌▍▎▏※※㈱㊣"), 0, height, (STR_FONT_VALUE_SONG), 36, false,
                false, 0);
        height += 50;

        printer.prn_drawBarcode("12345678ABCDEF", 32, height, 20, 2, 70, 0);
//		height += 80;
//
//		printer.prn_drawBarcode("12345678ABCDEF", 320, height, 20, 2, 50, 3);
    }

    public void printSale(Context context, String[] cartList) throws Exception {

        int height = 90;
        printer.prn_open();
        printer.prn_setupPage(_XVALUE, -1);
        printer.prn_clearPage();
        printer.prn_drawText(("SATIS FISI"), 70, 50, (STR_FONT_VALUE_SONG), 48, false, false, 0);
        height += 50;

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        opts.inDensity = getResources().getDisplayMetrics().densityDpi;
        opts.inTargetDensity = getResources().getDisplayMetrics().densityDpi;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.selogo, opts);


//		Bitmap bitmap = getLogoBitmap(context, R.drawable.unionpay_logo);
        printer.prn_drawBitmap(bitmap, 84, height);
        height += 80;

        Prn_Str("FLIGHT NO  : XC272", _YVALUE6, height);
        height += _YVALUE;

        Prn_Str("FLIGHT DATE：23/11/2020", _YVALUE6, height);
        height += _YVALUE;
        Prn_Str("CREW ID    ：" + "25778987" + "\tBARSET：" + "001" + "\n", _YVALUE6,
                height);

        String send = "NEXGEN01";
        String receive = "NEXTGENREP";

        height += _YVALUE;
        Prn_Str("CODE：" + send, _YVALUE6, height);
        printer.prn_drawText("KEY：" + receive, 190, height, STR_FONT_VALUE_SONG, _YVALUE6,
                false, false, 0);


        height += _YVALUE;
        Prn_Str("BATCH：", _YVALUE6, height);
        printer.prn_drawText("000001", 90, height, STR_FONT_VALUE_SONG, _YVALUE, false, false,
                0);

        printer.prn_drawText("SQUENCE：" + "234567", 200, height, STR_FONT_VALUE_SONG, _YVALUE6,
                false, false, 0);

        height += _YVALUE;
        Prn_Str("PROID：", _YVALUE6, height);
        printer.prn_drawText("000001", 90, height - 3, STR_FONT_VALUE_SONG, _YVALUE, false,
                false, 0);

        printer.prn_drawText("REPLY：" + "123456", 200, height, STR_FONT_VALUE_SONG, _YVALUE6,
                false, false, 0);
        height += _YVALUE;
        Prn_Str("VALID：" + "12345678901" + "\n", _YVALUE6, height);

        height += _YVALUE;
        Prn_Str("P.DATE：20160602", _YVALUE6, height);
        height += _YVALUE;

        Prn_Str("VAT：% 8.0", _YVALUE, height);

        height += _YVALUE;
        Prn_Str("------------PRODUCTS----------\n",
                _YVALUE, height);

        int itemCountInArray = 0;
        for (String e : cartList) {
            if (e == "" || e == null){
                break;
            }
            itemCountInArray += 1;
        }

        for(int i=0;i<itemCountInArray-2;i++){
            height += _YVALUE;
            Prn_Str(cartList[i], _YVALUE6, height);
        }

        height += _YVALUE;
        Prn_Str("--------------------------------------------------------\n",
                _YVALUE, height);

        height += _YVALUE;
        Prn_Str("TOTAL TL：" + cartList[itemCountInArray-2] + "\n \n", _YVALUE, height);
        height += _YVALUE;

        height += _YVALUE;
        Prn_Str("CASH SALE", _YVALUE6, height);
        try {// 电子签名

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        height += _YVALUE + 80;
        Prn_Str("\t\tTHANK FOR FOR YOUR PURCHASE\n \n \n", 20, height);

        height += _YVALUE + 10;
        Prn_Str("  \t        HAVE A NICE FLIGHT!\n", 20, height);

        height += _YVALUE;
        Prn_Str("\t        --END RECEIPT--", _YVALUE6, height);
        height += _YVALUE * 3;
        Prn_Str("\n", _YVALUE, height);
        Prn_Str("", _YVALUE, height);
        Prn_Str("", _YVALUE, height);
        Prn_Str("", _YVALUE, height);
        Prn_Str("", _YVALUE, height);
        Prn_Str("", _YVALUE, height);
        Prn_Str("", _YVALUE, height);

        printer.close();
//		int iRet = printer.prn_printPage(0);

    }

    public void printTrainSale(Context context) throws Exception {

        int height = 90;
        printer.prn_open();
        printer.prn_setupPage(_XVALUE, -1);
        printer.prn_clearPage();
        height += 50;

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        opts.inDensity = getResources().getDisplayMetrics().densityDpi;
        opts.inTargetDensity = getResources().getDisplayMetrics().densityDpi;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.catlogo, opts);

        printer.prn_drawBitmap(bitmap, 40, height);
        height += 80;

        Prn_Str("FLIGHT NO  : XC272", _YVALUE6, height);
        height += _YVALUE;

        Date today = new Date();

        Prn_Str("FLIGHT DATE：" + today.toString(), _YVALUE6, height);
        height += _YVALUE;
        Prn_Str("CREW ID    ：" + "25778987" + "\tBARSET：" + "001" + "\n", _YVALUE6,
                height);

        String send = "NEXGEN01";
        String receive = "NEXTGENREP";

        height += _YVALUE;
        Prn_Str("CODE：" + send, _YVALUE6, height);
        printer.prn_drawText("KEY：" + receive, 190, height, STR_FONT_VALUE_SONG, _YVALUE6,
                false, false, 0);

        height += _YVALUE;
        String cardNo = "4348 3448 82** **91";

        Prn_Str("CARD NO：", _YVALUE6, height);
        height += _YVALUE;
        Prn_Str_Bold(cardNo, _YVALUE, height);

        height += _YVALUE;
        Prn_Str("APPROV NO: 345244 ", _YVALUE6, height);
        height += _YVALUE;
        Prn_Str("BATCH：", _YVALUE6, height);
        printer.prn_drawText("000001", 90, height, STR_FONT_VALUE_SONG, _YVALUE, false, false,
                0);

        printer.prn_drawText("SQUENCE：" + "234567", 200, height, STR_FONT_VALUE_SONG, _YVALUE6,
                false, false, 0);

        height += _YVALUE;
        Prn_Str("PROID：", _YVALUE6, height);
        printer.prn_drawText("000001", 90, height - 3, STR_FONT_VALUE_SONG, _YVALUE, false,
                false, 0);

        printer.prn_drawText("REPLY：" + "123456", 200, height, STR_FONT_VALUE_SONG, _YVALUE6,
                false, false, 0);
        height += _YVALUE;
        Prn_Str("VALID：" + "12345678901" + "\n", _YVALUE6, height);

        height += _YVALUE;
        Prn_Str("P.DATE：20160602", _YVALUE6, height);
        height += _YVALUE;

        Prn_Str("VAT：% 8.0", _YVALUE, height);

        height += _YVALUE;
        Prn_Str("------------PRODUCTS----------\n",
                _YVALUE, height);

        height += _YVALUE;
        Prn_Str("1 PC  SINGLE CAT TRAIN TICKET   10.00", _YVALUE6, height);

        height += _YVALUE;
        Prn_Str("--------------------------------------------------------\n",
                _YVALUE, height);

        height += _YVALUE;
        Prn_Str("TOTAL EUR：10.00\n \n \n", _YVALUE, height);
        height += _YVALUE;
        try {

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        printer.prn_drawBarcode("CAT TRAINS", 25, 5, 55, 3, 60, 0);


        height += _YVALUE + 80;
        Prn_Str("\t\tTHANK FOR FOR YOUR PURCHASE\n \n \n", 20, height);

        height += _YVALUE + 10;
        Prn_Str("  \t   HAVE A NICE FLIGHT!\n", 20, height);

        height += _YVALUE;
        Prn_Str("\t  --END RECEIPT--", _YVALUE6, height);
        height += _YVALUE * 3;
        Prn_Str("\n", _YVALUE, height);
        Prn_Str("", _YVALUE, height);
        Prn_Str("", _YVALUE, height);
        Prn_Str("", _YVALUE, height);
        Prn_Str("", _YVALUE, height);
        Prn_Str("", _YVALUE, height);
        Prn_Str("", _YVALUE, height);

        printer.close();

    }

    public void printMultinet(Context context, String[] cartList) throws Exception {

        int height = 90;
        printer.prn_open();
        printer.prn_setupPage(_XVALUE, -1);
        printer.prn_clearPage();
        height += 50;

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        opts.inDensity = getResources().getDisplayMetrics().densityDpi;
        opts.inTargetDensity = getResources().getDisplayMetrics().densityDpi;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.multineticon, opts);

        printer.prn_drawBitmap(bitmap, 40, height);
        height += 80;

        Prn_Str("FLIGHT NO  : XC272", _YVALUE6, height);
        height += _YVALUE;

        Date today = new Date();

        Prn_Str("FLIGHT DATE：" + today.toString(), _YVALUE6, height);
        height += _YVALUE;
        Prn_Str("CREW ID    ：" + "25778987" + "\tBARSET：" + "001" + "\n", _YVALUE6,
                height);

        String send = "NEXGEN01";
        String receive = "NEXTGENREP";

        height += _YVALUE;
        Prn_Str("CODE：" + send, _YVALUE6, height);
        printer.prn_drawText("KEY：" + receive, 190, height, STR_FONT_VALUE_SONG, _YVALUE6,
                false, false, 0);

        height += _YVALUE;
        String cardNo = "4348 3448 82** **91";

        Prn_Str("CARD NO：", _YVALUE6, height);
        height += _YVALUE;
        Prn_Str_Bold(cardNo, _YVALUE, height);

        height += _YVALUE;
        Prn_Str("APPROV NO: 345244 ", _YVALUE6, height);
        height += _YVALUE;
        Prn_Str("BATCH：", _YVALUE6, height);
        printer.prn_drawText("000001", 90, height, STR_FONT_VALUE_SONG, _YVALUE, false, false,
                0);

        printer.prn_drawText("SQUENCE：" + "234567", 200, height, STR_FONT_VALUE_SONG, _YVALUE6,
                false, false, 0);

        height += _YVALUE;
        Prn_Str("PROID：", _YVALUE6, height);
        printer.prn_drawText("000001", 90, height - 3, STR_FONT_VALUE_SONG, _YVALUE, false,
                false, 0);

        printer.prn_drawText("REPLY：" + "123456", 200, height, STR_FONT_VALUE_SONG, _YVALUE6,
                false, false, 0);
        height += _YVALUE;
        Prn_Str("VALID：" + "12345678901" + "\n", _YVALUE6, height);

        height += _YVALUE;
        Prn_Str("P.DATE：20160602", _YVALUE6, height);
        height += _YVALUE;

        Prn_Str("VAT：% 8.0", _YVALUE, height);

        height += _YVALUE;
        Prn_Str("------------PRODUCTS----------\n",
                _YVALUE, height);

        int itemCountInArray = 0;
        for (String e : cartList) {
            if (e == "" || e == null){
                break;
            }
            itemCountInArray += 1;
        }

        for(int i=0;i<itemCountInArray-2;i++){
            height += _YVALUE;
            Prn_Str(cartList[i], _YVALUE6, height);
        }

        height += _YVALUE;
        Prn_Str("--------------------------------------------------------\n",
                _YVALUE, height);

        height += _YVALUE;
        Prn_Str("TOTAL TL：" + cartList[itemCountInArray-2] + "\n \n", _YVALUE, height);
        height += _YVALUE;
        try {

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        printer.prn_drawBarcode("MULTINET SALE", 25, 5, 55, 3, 60, 0);


        height += _YVALUE + 80;
        Prn_Str("\t\tTHANK FOR FOR YOUR PURCHASE\n \n \n", 20, height);

        height += _YVALUE + 10;
        Prn_Str("  \t   HAVE A NICE FLIGHT!\n", 20, height);

        height += _YVALUE;
        Prn_Str("\t  --END RECEIPT--", _YVALUE6, height);
        height += _YVALUE * 3;
        Prn_Str("\n", _YVALUE, height);
        Prn_Str("", _YVALUE, height);
        Prn_Str("", _YVALUE, height);
        Prn_Str("", _YVALUE, height);
        Prn_Str("", _YVALUE, height);
        Prn_Str("", _YVALUE, height);
        Prn_Str("", _YVALUE, height);

        printer.close();

    }

    public void printClose(Context context, String[] cartList) throws Exception {

        int height = 90;
        printer.prn_open();
        printer.prn_setupPage(_XVALUE, -1);
        printer.prn_clearPage();
        height += 50;

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        opts.inDensity = getResources().getDisplayMetrics().densityDpi;
        opts.inTargetDensity = getResources().getDisplayMetrics().densityDpi;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.multineticon, opts);

        //printer.prn_drawBitmap(bitmap, 40, height);
        //height += 80;

        Prn_Str("***   FLIGHT CLOSE   ***", _YVALUE6, height);
        height += 50;
        height += _YVALUE;

        Prn_Str("FLIGHT NO  : XC272", _YVALUE6, height);
        height += _YVALUE;

        Date today = new Date();

        Prn_Str("FLIGHT DATE：" + today.toString(), _YVALUE6, height);
        height += _YVALUE;
        Prn_Str("CREW ID    ：" + "001980" + "\tBARSET：" + "001" + "\n", _YVALUE6,
                height);

        String send = "NEXGEN01";
        String receive = "NEXTGENREP";

        height += _YVALUE;
        Prn_Str("CODE：" + send, _YVALUE6, height);
        printer.prn_drawText("KEY：" + receive, 190, height, STR_FONT_VALUE_SONG, _YVALUE6,
                false, false, 0);

        height += _YVALUE;
        height += 50;
        Prn_Str("-------ALL SOLD PRODUCTS--------\n",
                _YVALUE, height);

        int itemCountInArray = 0;
        for (String e : cartList) {
            if (e == "" || e == null){
                break;
            }
            itemCountInArray += 1;
        }

        for(int i=0;i<itemCountInArray-2;i++){
            height += _YVALUE;
            Prn_Str(cartList[i], _YVALUE6, height);
        }

        height += _YVALUE;
        Prn_Str("--------------------------------------------------------\n",
                _YVALUE, height);

        height += _YVALUE;
        Prn_Str("TOTAL TL：" + cartList[itemCountInArray-2] + "\n \n", _YVALUE, height);
        height += _YVALUE;
        try {

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        printer.prn_drawBarcode("TOTAL-INFLIGHT SALE", 25, 5, 55, 3, 60, 0);


        height += _YVALUE + 80;
        Prn_Str("\t\tFLIGHT CLOSED\n \n \n", 20, height);

        height += _YVALUE + 10;
        Prn_Str("  \t   PLS SCAN NEW BAR QR CODE!\n", 20, height);

        height += _YVALUE;
        Prn_Str("\t  --APP CLOSE--", _YVALUE6, height);
        height += _YVALUE * 3;
        Prn_Str("\n", _YVALUE, height);
        Prn_Str("", _YVALUE, height);
        Prn_Str("", _YVALUE, height);
        Prn_Str("", _YVALUE, height);
        Prn_Str("", _YVALUE, height);
        Prn_Str("", _YVALUE, height);
        Prn_Str("", _YVALUE, height);

        printer.close();

    }

    public void printGooglePay(Context context, String[] cartList) throws Exception {

        int height = 90;
        printer.prn_open();
        printer.prn_setupPage(_XVALUE, -1);
        printer.prn_clearPage();
        height += 50;

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        opts.inDensity = getResources().getDisplayMetrics().densityDpi;
        opts.inTargetDensity = getResources().getDisplayMetrics().densityDpi;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gpayicon, opts);

        printer.prn_drawBitmap(bitmap, 40, height);
        height += 80;

        Prn_Str("FLIGHT NO  : XQ9284", _YVALUE6, height);
        height += _YVALUE;

        Date today = new Date();

        Prn_Str("FLIGHT DATE：" + today.toString(), _YVALUE6, height);
        height += _YVALUE;
        Prn_Str("CREW ID    ：" + "25778987" + "\tBARSET：" + "001" + "\n", _YVALUE6,
                height);

        String send = "NEXGEN01";
        String receive = "NEXTGENREP";

        height += _YVALUE;
        Prn_Str("CODE：" + send, _YVALUE6, height);
        printer.prn_drawText("KEY：" + receive, 190, height, STR_FONT_VALUE_SONG, _YVALUE6,
                false, false, 0);

        height += _YVALUE;
        String cardNo = "4348 3448 82** **91";

        Prn_Str("CARD NO：", _YVALUE6, height);
        height += _YVALUE;
        Prn_Str_Bold(cardNo, _YVALUE, height);

        height += _YVALUE;
        Prn_Str("APPROV NO: 345244 ", _YVALUE6, height);
        height += _YVALUE;
        Prn_Str("BATCH：", _YVALUE6, height);
        printer.prn_drawText("000001", 90, height, STR_FONT_VALUE_SONG, _YVALUE, false, false,
                0);

        printer.prn_drawText("SQUENCE：" + "234567", 200, height, STR_FONT_VALUE_SONG, _YVALUE6,
                false, false, 0);

        height += _YVALUE;
        Prn_Str("PROID：", _YVALUE6, height);
        printer.prn_drawText("000001", 90, height - 3, STR_FONT_VALUE_SONG, _YVALUE, false,
                false, 0);

        printer.prn_drawText("REPLY：" + "123456", 200, height, STR_FONT_VALUE_SONG, _YVALUE6,
                false, false, 0);
        height += _YVALUE;
        Prn_Str("VALID：" + "12345678901" + "\n", _YVALUE6, height);

        height += _YVALUE;
        Prn_Str("P.DATE：20160602", _YVALUE6, height);
        height += _YVALUE;

        Prn_Str("VAT：% 8.0", _YVALUE, height);

        height += _YVALUE;
        Prn_Str("------------PRODUCTS----------\n",
                _YVALUE, height);

        int itemCountInArray = 0;
        for (String e : cartList) {
            if (e == "" || e == null){
                break;
            }
            itemCountInArray += 1;
        }

        for(int i=0;i<itemCountInArray-2;i++){
            height += _YVALUE;
            Prn_Str(cartList[i], _YVALUE6, height);
        }

        height += _YVALUE;
        Prn_Str("--------------------------------------------------------\n",
                _YVALUE, height);

        height += _YVALUE;
        Prn_Str("TOTAL TL：" + cartList[itemCountInArray-2] + "\n \n", _YVALUE, height);
        height += _YVALUE;
        try {

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        printer.prn_drawBarcode("GOOGLE PAY SALE", 25, 5, 55, 3, 60, 0);


        height += _YVALUE + 80;
        Prn_Str("\t\tTHANK FOR FOR YOUR PURCHASE\n \n \n", 20, height);

        height += _YVALUE + 10;
        Prn_Str("  \t   HAVE A NICE FLIGHT!\n", 20, height);

        height += _YVALUE;
        Prn_Str("\t  --END RECEIPT--", _YVALUE6, height);
        height += _YVALUE * 3;
        Prn_Str("\n", _YVALUE, height);
        Prn_Str("", _YVALUE, height);
        Prn_Str("", _YVALUE, height);
        Prn_Str("", _YVALUE, height);
        Prn_Str("", _YVALUE, height);
        Prn_Str("", _YVALUE, height);
        Prn_Str("", _YVALUE, height);

        printer.close();

    }

    public void printApplePay(Context context, String[] cartList) throws Exception {

        int height = 90;
        printer.prn_open();
        printer.prn_setupPage(_XVALUE, -1);
        printer.prn_clearPage();
        height += 50;

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        opts.inDensity = getResources().getDisplayMetrics().densityDpi;
        opts.inTargetDensity = getResources().getDisplayMetrics().densityDpi;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.applepayicon, opts);

        printer.prn_drawBitmap(bitmap, 40, height);
        height += 80;

        Prn_Str("FLIGHT NO  : XQ9284", _YVALUE6, height);
        height += _YVALUE;

        Date today = new Date();

        Prn_Str("FLIGHT DATE：" + today.toString(), _YVALUE6, height);
        height += _YVALUE;
        Prn_Str("CREW ID    ：" + "25778987" + "\tBARSET：" + "001" + "\n", _YVALUE6,
                height);

        String send = "NEXGEN01";
        String receive = "NEXTGENREP";

        height += _YVALUE;
        Prn_Str("CODE：" + send, _YVALUE6, height);
        printer.prn_drawText("KEY：" + receive, 190, height, STR_FONT_VALUE_SONG, _YVALUE6,
                false, false, 0);

        height += _YVALUE;
        String cardNo = "4348 3448 82** **91";

        Prn_Str("CARD NO：", _YVALUE6, height);
        height += _YVALUE;
        Prn_Str_Bold(cardNo, _YVALUE, height);

        height += _YVALUE;
        Prn_Str("APPROV NO: 345244 ", _YVALUE6, height);
        height += _YVALUE;
        Prn_Str("BATCH：", _YVALUE6, height);
        printer.prn_drawText("000001", 90, height, STR_FONT_VALUE_SONG, _YVALUE, false, false,
                0);

        printer.prn_drawText("SQUENCE：" + "234567", 200, height, STR_FONT_VALUE_SONG, _YVALUE6,
                false, false, 0);

        height += _YVALUE;
        Prn_Str("PROID：", _YVALUE6, height);
        printer.prn_drawText("000001", 90, height - 3, STR_FONT_VALUE_SONG, _YVALUE, false,
                false, 0);

        printer.prn_drawText("REPLY：" + "123456", 200, height, STR_FONT_VALUE_SONG, _YVALUE6,
                false, false, 0);
        height += _YVALUE;
        Prn_Str("VALID：" + "12345678901" + "\n", _YVALUE6, height);

        height += _YVALUE;
        Prn_Str("P.DATE：20160602", _YVALUE6, height);
        height += _YVALUE;

        Prn_Str("VAT：% 8.0", _YVALUE, height);

        height += _YVALUE;
        Prn_Str("------------PRODUCTS----------\n",
                _YVALUE, height);

        int itemCountInArray = 0;
        for (String e : cartList) {
            if (e == "" || e == null){
                break;
            }
            itemCountInArray += 1;
        }

        for(int i=0;i<itemCountInArray-2;i++){
            height += _YVALUE;
            Prn_Str(cartList[i], _YVALUE6, height);
        }

        height += _YVALUE;
        Prn_Str("--------------------------------------------------------\n",
                _YVALUE, height);

        height += _YVALUE;
        Prn_Str("TOTAL TL：" + cartList[itemCountInArray-2] + "\n \n", _YVALUE, height);
        height += _YVALUE;
        try {

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        printer.prn_drawBarcode("APPLE PAY SALE", 25, 5, 55, 3, 60, 0);


        height += _YVALUE + 80;
        Prn_Str("\t\tTHANK FOR FOR YOUR PURCHASE\n \n \n", 20, height);

        height += _YVALUE + 10;
        Prn_Str("  \t   HAVE A NICE FLIGHT!\n", 20, height);

        height += _YVALUE;
        Prn_Str("\t  --END RECEIPT--", _YVALUE6, height);
        height += _YVALUE * 3;
        Prn_Str("\n", _YVALUE, height);
        Prn_Str("", _YVALUE, height);
        Prn_Str("", _YVALUE, height);
        Prn_Str("", _YVALUE, height);
        Prn_Str("", _YVALUE, height);
        Prn_Str("", _YVALUE, height);
        Prn_Str("", _YVALUE, height);

        printer.close();

    }

    public void printCreditSale(Context context, String[] cartList) throws Exception {

        int height = 90;
        printer.prn_open();
        printer.prn_setupPage(_XVALUE, -1);
        printer.prn_clearPage();
        height += 50;

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        opts.inDensity = getResources().getDisplayMetrics().densityDpi;
        opts.inTargetDensity = getResources().getDisplayMetrics().densityDpi;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.creditcardicon, opts);

        printer.prn_drawBitmap(bitmap, 40, height);
        height += 80;

        Prn_Str("FLIGHT NO  : XQ9284", _YVALUE6, height);
        height += _YVALUE;

        Date today = new Date();

        Prn_Str("FLIGHT DATE：" + today.toString(), _YVALUE6, height);
        height += _YVALUE;
        Prn_Str("CREW ID    ：" + "25778987" + "\tBARSET：" + "001" + "\n", _YVALUE6,
                height);

        String send = "NEXGEN01";
        String receive = "NEXTGENREP";

        height += _YVALUE;
        Prn_Str("CODE：" + send, _YVALUE6, height);
        printer.prn_drawText("KEY：" + receive, 190, height, STR_FONT_VALUE_SONG, _YVALUE6,
                false, false, 0);

        height += _YVALUE;
        String cardNo = "4348 3448 82** **91";

        Prn_Str("CARD NO：", _YVALUE6, height);
        height += _YVALUE;
        Prn_Str_Bold(cardNo, _YVALUE, height);

        height += _YVALUE;
        Prn_Str("APPROV NO: 345244 ", _YVALUE6, height);
        height += _YVALUE;
        Prn_Str("BATCH：", _YVALUE6, height);
        printer.prn_drawText("000001", 90, height, STR_FONT_VALUE_SONG, _YVALUE, false, false,
                0);

        printer.prn_drawText("SQUENCE：" + "234567", 200, height, STR_FONT_VALUE_SONG, _YVALUE6,
                false, false, 0);

        height += _YVALUE;
        Prn_Str("PROID：", _YVALUE6, height);
        printer.prn_drawText("000001", 90, height - 3, STR_FONT_VALUE_SONG, _YVALUE, false,
                false, 0);

        printer.prn_drawText("REPLY：" + "123456", 200, height, STR_FONT_VALUE_SONG, _YVALUE6,
                false, false, 0);
        height += _YVALUE;
        Prn_Str("VALID：" + "12345678901" + "\n", _YVALUE6, height);

        height += _YVALUE;
        Prn_Str("P.DATE：20160602", _YVALUE6, height);
        height += _YVALUE;

        Prn_Str("VAT：% 8.0", _YVALUE, height);

        height += _YVALUE;
        Prn_Str("------------PRODUCTS----------\n",
                _YVALUE, height);

        int itemCountInArray = 0;
        for (String e : cartList) {
            if (e == "" || e == null){
                break;
            }
            itemCountInArray += 1;
        }

        for(int i=0;i<itemCountInArray-2;i++){
            height += _YVALUE;
            Prn_Str(cartList[i], _YVALUE6, height);
        }

        height += _YVALUE;
        Prn_Str("--------------------------------------------------------\n",
                _YVALUE, height);

        height += _YVALUE;
        Prn_Str("TOTAL TL：" + cartList[itemCountInArray-2] + "\n \n", _YVALUE, height);
        height += _YVALUE;
        try {

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        printer.prn_drawBarcode("CREDIT CARD SALE", 25, 5, 55, 3, 60, 0);


        height += _YVALUE + 80;
        Prn_Str("\t\tTHANK FOR FOR YOUR PURCHASE\n \n \n", 20, height);

        height += _YVALUE + 10;
        Prn_Str("  \t   HAVE A NICE FLIGHT!\n", 20, height);

        height += _YVALUE;
        Prn_Str("\t  --END RECEIPT--", _YVALUE6, height);
        height += _YVALUE * 3;
        Prn_Str("\n", _YVALUE, height);
        Prn_Str("", _YVALUE, height);
        Prn_Str("", _YVALUE, height);
        Prn_Str("", _YVALUE, height);
        Prn_Str("", _YVALUE, height);
        Prn_Str("", _YVALUE, height);
        Prn_Str("", _YVALUE, height);

        printer.close();

    }

    // 银联logo 转成Bitmap
    @SuppressWarnings("static-access")
    private Bitmap getLogoBitmap(Context context, int id) {
        BitmapDrawable draw = (BitmapDrawable) context.getResources()
                .getDrawable(id);
        Bitmap bitmap = draw.getBitmap();
        return bitmap;
    }

    private int Prn_Str(String msg, int fontSize, int height) {
        return printer.prn_drawText(msg, 0, height, STR_FONT_VALUE_SONG, fontSize, false,
                false, 0);
    }

    private int Prn_Str_Bold(String msg, int fontSize, int height) {
        return printer.prn_drawText(msg, 0, height, STR_FONT_VALUE_SONG, fontSize, true,
                false, 0);
    }

    private void sleep() {
        //延时1秒
        try {
            Thread.currentThread();
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
