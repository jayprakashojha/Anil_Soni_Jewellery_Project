package com.example.myapplication1;

import android.os.Bundle;
import android.util.Log;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class activity_deposit extends AppCompatActivity {

    EditText etAmount, etDate;
    Button btnSubmit;
    DatabaseHelper dbHelper;

    long customerId = 0;
    long billNo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);
        dbHelper = new DatabaseHelper(this);

        // 2. Pehle findViewById karein (Crash se bachne ke liye sequence zaroori hai)
        etAmount = findViewById(R.id.etAmount);
        etDate = findViewById(R.id.etDate);
        btnSubmit = findViewById(R.id.btnSubmit);

        // 3. Ab Intent se data lein (Local 'long' word hata dein)
        customerId = getIntent().getLongExtra("customer_id", 0);
        billNo = getIntent().getLongExtra("id", 0);

        Log.d("DepositAmountActivity", "Received Customer ID: " + customerId + ", ID: " + billNo);

        // 4. Ab Click Listeners lagayein
        etDate.setOnClickListener(v -> showDatePicker());

        btnSubmit.setOnClickListener(v -> {
            String amountStr = etAmount.getText().toString().trim();
            String dateStr = etDate.getText().toString().trim();

            if(amountStr.isEmpty() || dateStr.isEmpty()){
                Toast.makeText(this, "Please enter amount and date", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double amount = Double.parseDouble(amountStr);

                // Database mein save karein
                long result = dbHelper.insertPaymentNew((int) customerId, amount, dateStr);

                if (result != -1) {
                    Toast.makeText(this, "Payment Saved! Bill No: " + result, Toast.LENGTH_SHORT).show();
                    // Optional: Save hone ke baad screen close karne ke liye:
                    // finish();
                } else {
                    Toast.makeText(this, "Database Error!", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid amount format", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDatePicker(){
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Sahi format: YYYY-MM-DD (SQLite ke liye best hai)
                    String date = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
                    etDate.setText(date);
                }, year, month, day);

        datePickerDialog.show();
    }
}