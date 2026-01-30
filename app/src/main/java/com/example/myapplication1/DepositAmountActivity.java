package com.example.myapplication1;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DepositAmountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_amount);

        int billId = getIntent().getIntExtra("bill_id", -1);

        if (billId == -1) {
            Toast.makeText(this, "Bill not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // 🔥 yahin se DB call karo
        loadBillDetails(billId);
    }

    private void loadBillDetails(int billId) {

        DatabaseHelper db = new DatabaseHelper(this);
        Bill bill = db.getBillById(billId);

        if (bill == null) {
            Toast.makeText(this, "Bill data not found", Toast.LENGTH_SHORT).show();
            return;
        }

        TextView tvBillNo = findViewById(R.id.tvBillNo);
        TextView tvCustomer = findViewById(R.id.tvCustomer);
        TextView tvAmount = findViewById(R.id.tvAmount);
        TextView tvPending = findViewById(R.id.pendingAmount);

        tvBillNo.setText("Bill No: " + bill.billNo);
        tvCustomer.setText("Customer: " + bill.customerName);
        tvAmount.setText("Amount: ₹ " + bill.finalAmount);

        tvPending.setText("Pending Amount: " + bill.pendingAmount);
    }


}