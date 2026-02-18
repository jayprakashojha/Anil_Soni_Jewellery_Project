package com.example.myapplication1;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BillDetailActivity extends AppCompatActivity {

    TextView txtName, txtBillNo, txtTotal;
    RecyclerView recyclerView;

    DatabaseHelper db;
    long customerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_detail);

        txtName = findViewById(R.id.txtNameDetail);
        txtBillNo = findViewById(R.id.txtBillNoDetail);
        txtTotal = findViewById(R.id.txtTotalDetail);
        recyclerView = findViewById(R.id.recyclerViewItems);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = new DatabaseHelper(this);

        customerId = getIntent().getLongExtra("customerId", -1);

        if (customerId != -1) {

            FullBill fullBill = db.getFullBillByCustomerId(customerId);

            if (fullBill != null && fullBill.getCustomer() != null) {

                CustomerBean customer = fullBill.getCustomer();

                txtName.setText(customer.getCustomerName());
                txtBillNo.setText("Bill No: " + customer.getBillNo());
                txtTotal.setText("Total: " + customer.getGrandTotal());

                BillItemAdapter adapter =
                        new BillItemAdapter(this, fullBill.getItems());

                recyclerView.setAdapter(adapter);
            }
        }
    }
}
