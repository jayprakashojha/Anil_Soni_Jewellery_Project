package com.example.myapplication1;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PendingAmountActivity extends AppCompatActivity {


    TableLayout tableCustomers;
    DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_amount);

        tableCustomers = findViewById(R.id.tableCustomers);
        dbHelper = new DatabaseHelper(this);
        showCustomerTable();

    }

    private void showCustomerTable() {

        // ✅ Header Row
        TableRow header = new TableRow(this);
        header.setBackgroundColor(0xFFC9A24D);

        header.addView(makeHeaderText("ID"));
        header.addView(makeHeaderText("BillNo"));
        header.addView(makeHeaderText("Name"));
        header.addView(makeHeaderText("Mobile"));
        header.addView(makeHeaderText("Date"));
        header.addView(makeHeaderText("Address"));
        header.addView(makeHeaderText("Description"));

        header.addView(makeHeaderText("Making %"));
        header.addView(makeHeaderText("Amount"));
        header.addView(makeHeaderText("Deposit"));
        header.addView(makeHeaderText("Pending Amount"));

        tableCustomers.addView(header);

        Cursor cursor = dbHelper.getAllCustomers();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();
            cursor.close();
            return;
        }

        while (cursor.moveToNext()) {

            int id = cursor.getInt(0);
            String billNo = cursor.getString(14);
            String name = cursor.getString(1);
            String mobile = cursor.getString(2);
            String date = cursor.getString(3);
            String address = cursor.getString(4);
            String description = cursor.getString(5);
            double making = cursor.getDouble(10);
            double amount = cursor.getDouble(11);
            double deposit = cursor.getDouble(12);
            double pendingAmount = cursor.getDouble(13);

            TableRow row = new TableRow(this);
            row.setPadding(5, 5, 5, 5);

            row.addView(makeRowText(String.valueOf(id)));
            row.addView(makeRowText(String.valueOf(billNo)));
            row.addView(makeRowText(name));
            row.addView(makeRowText(mobile));
            row.addView(makeRowText(date));
            row.addView(makeRowText(address));
            row. addView(makeRowText(description));
            row.addView(makeRowText(String.valueOf(making)));
            row.addView(makeRowText(String.valueOf(amount)));
            row.addView(makeRowText(String.valueOf(deposit)));
            row.addView(makeRowText(String.valueOf(pendingAmount)));

            tableCustomers.addView(row);
        }

        cursor.close();
    }

    // ✅ Header TextView Design
    private TextView makeHeaderText(String text) {
        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setPadding(10, 10, 10, 10);
        tv.setTextColor(0xFFFFFFFF);
        tv.setTypeface(null, Typeface.BOLD);
        tv.setGravity(Gravity.CENTER);
        return tv;
    }

    // ✅ Row TextView Design
    private TextView makeRowText(String text) {
        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setPadding(10, 10, 10, 10);
        tv.setTextColor(0xFF000000);
        tv.setGravity(Gravity.CENTER);
        tv.setBackgroundColor(0xFFF6F2E9);
        return tv;
    }

}