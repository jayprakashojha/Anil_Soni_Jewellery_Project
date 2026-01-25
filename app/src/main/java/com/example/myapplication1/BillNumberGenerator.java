package com.example.myapplication1;
import android.content.Context;
import android.content.SharedPreferences;
public class BillNumberGenerator
{
    private static final String PREF_NAME = "BillPrefs";
    private static final String KEY_BILL_NO = "last_bill_no";

    public static String getNextBillNo(Context context) {

        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        int lastBillNo = prefs.getInt(KEY_BILL_NO, 0);

        int newBillNo = lastBillNo + 1;

        if (newBillNo > 9999) {
            newBillNo = 1; // reset after 9999
        }

        prefs.edit().putInt(KEY_BILL_NO, newBillNo).apply();

        // 4 digit format (0001, 0023, 9999)
        return String.format("%04d", newBillNo);
    }

}
