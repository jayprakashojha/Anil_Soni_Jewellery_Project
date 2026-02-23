package com.example.myapplication1;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BillDetailActivity extends AppCompatActivity {

    TextView txtName, txtBillNo, txtTotal,txtTotalDeposit,textPending,txtDeposit;
    double totalDepositedAmount,totalPending;
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
        txtDeposit = findViewById(R.id.txtDeposit);
        txtTotalDeposit = findViewById(R.id.txtTotalDeposit);
        textPending = findViewById(R.id.textPendingAmount);

        recyclerView = findViewById(R.id.recyclerViewItems);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = new DatabaseHelper(this);

        customerId = getIntent().getLongExtra("customerId", -1);

        if (customerId != -1) {

            FullBill fullBill = db.getFullBillByCustomerId(customerId);
            List<BillItemBean> items = db.getItemsByCustomerId(customerId);

            if (items != null) {

                for (BillItemBean item : items) {
                    totalDepositedAmount += item.getAmount();

                }
            } else {
                Log.d("DB_CHECK", "List is null! Check your Database query.");
            }

            if (fullBill != null && fullBill.getCustomer() != null) {

                CustomerBean customer = fullBill.getCustomer();
                double finalDeposited = totalDepositedAmount + customer.getTotalDeposit() ;

                txtName.setText("Name: " + customer.getCustomerName());
                txtBillNo.setText("Bill No: " + customer.getBillNo());
                txtTotal.setText("Total Amount: " + customer.getGrandTotal());
                txtDeposit.setText("First Deposit:" +customer.getTotalDeposit());
                txtTotalDeposit.setText("Total Deposit: " +  finalDeposited);
                totalPending=customer.getGrandTotal()-finalDeposited;
                textPending.setText("Pending Amount: " + totalPending);

                db.updatePendingAmount(customer.getBillNo(), totalPending);

                BillItemAdapter adapter =
                        new BillItemAdapter(this, fullBill.getItems());

                recyclerView.setAdapter(adapter);
            }
        }
    }
}
