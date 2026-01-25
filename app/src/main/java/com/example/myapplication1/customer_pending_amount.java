package com.example.myapplication1;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class customer_pending_amount extends AppCompatActivity {

    RecyclerView recyclerBills;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_pending_amount);

        recyclerBills = findViewById(R.id.recyclerBills);
        recyclerBills.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DatabaseHelper(this);

        // ✅ SQLite se data
        List<Bill> billList = dbHelper.getAllBills();

        PendingBillAdapter adapter =
                new PendingBillAdapter(this, billList);
        recyclerBills.setAdapter(adapter);
    }


}
