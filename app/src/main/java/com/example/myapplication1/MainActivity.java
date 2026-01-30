package com.example.myapplication1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Button: New Customer
        findViewById(R.id.btnNewCustomer).setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, IndexActivity.class))
        );

        // Button: View Customer
        findViewById(R.id.btnViewCustomer).setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, customer_pending_amount.class))
        );



        TextView tvPending = findViewById(R.id.tvPendingAmount);

        tvPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // (Optional) Toast
               // Toast.makeText(MainActivity.this, "Pending Amount clicked!", Toast.LENGTH_SHORT).show();

                // 👉 Activity open karne ka MAIN code
                Intent intent = new Intent(MainActivity.this, PendingAmountActivity.class);
                startActivity(intent);
            }
        });

        TextView downloadBill = findViewById(R.id.downloadBill);

        downloadBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // (Optional) Toast
                Toast.makeText(MainActivity.this, "Download Bill clicked!", Toast.LENGTH_SHORT).show();

                // 👉 Activity open karne ka MAIN code
                Intent intent = new Intent(MainActivity.this, DownloadBillActivity.class);
                startActivity(intent);
            }
        });
    }


}
