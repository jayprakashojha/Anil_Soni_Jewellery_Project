package com.example.myapplication1;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class IndexActivity extends AppCompatActivity {

    double grandTotal = 0;
    TableLayout tableLayout;
    TextView tvTotalAmount;

    Button btnAddRow;
    EditText etCustomerName, etMobile, etDeposit, etDate, etAddress, etDescription;

    // Calculation fields
    EditText etWeight, etRate, etValue, etMaking, etAmount, etPendingDeposit;
TextView tvBillNo;
    Button btnSaveCustomer;
    DatabaseHelper dbHelper;
    int billNo;
    Spinner spinnerMetal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_index);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ===== findViewById =====
        etCustomerName = findViewById(R.id.etCustomerName);
        etMobile = findViewById(R.id.etMobile);
        etDate = findViewById(R.id.etDate);
        etAddress = findViewById(R.id.etAddress);
        etDescription = findViewById(R.id.etDescription);
        etDeposit = findViewById(R.id.etDeposit);

        etWeight = findViewById(R.id.etWeight);
        etRate = findViewById(R.id.etRate);
        etValue = findViewById(R.id.etValue);
        etMaking = findViewById(R.id.etMaking);
        etAmount = findViewById(R.id.etAmount);
        etPendingDeposit = findViewById(R.id.etPendingDeposit);
        tvBillNo = findViewById(R.id.tvBillNo);
        btnSaveCustomer = findViewById(R.id.btnSaveCustomer);

        dbHelper = new DatabaseHelper(this);

        // ===== Default Date =====
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        etDate.setText(currentDate);

        // ===== Date Picker =====
        etDate.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(
                    IndexActivity.this,
                    (view, y, m, d) -> etDate.setText(
                            String.format(Locale.getDefault(), "%02d-%02d-%04d", d, m + 1, y)
                    ),
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
            );
            dialog.show();
        });

        //====dynamic button ke liye====



        // ===== Spinner =====
         spinnerMetal = findViewById(R.id.spinnerMetal);
        String[] items = {"Select", "Gold", "Silver"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                items
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMetal.setAdapter(adapter);

        Spinner spinner = new Spinner(this);
        String[] metals  = {"Select","Gold","Silver"};
        ArrayAdapter<String>  adapter2 =new ArrayAdapter<String>(
                this,  android.R.layout.simple_spinner_item,
                metals
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);




        spinnerMetal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selected = parent.getItemAtPosition(position).toString();

                if (!selected.equals("Select")) {
                    Toast.makeText(IndexActivity.this,
                            "Selected: " + selected,
                            Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // ===== TextWatcher for Amount Calculation =====
        TextWatcher amountWatcher = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculateAmount();
                calculatePending();
                updateTotalAmount();

            }
            @Override public void afterTextChanged(Editable s) {}


        };

        etWeight.addTextChangedListener(amountWatcher);
        etRate.addTextChangedListener(amountWatcher);
        etMaking.addTextChangedListener(amountWatcher);
        etDeposit.addTextChangedListener(amountWatcher);

        // ===== Save Customer =====
        btnSaveCustomer.setOnClickListener(v -> saveCustomer());

        // ===== View Pending =====
        Button btnViewPending = findViewById(R.id.btnViewPending);
        btnViewPending.setOnClickListener(v ->
                startActivity(new Intent(IndexActivity.this, PendingAmountActivity.class))
        );

        tableLayout = findViewById(R.id.tableJewellery);
        btnAddRow = findViewById(R.id.btnAddRow);

        btnAddRow.setOnClickListener(v -> addNewRow());

         tvTotalAmount = findViewById(R.id.tvTotalAmount);

    }

    // ================= CALCULATIONS =================

    private void addNewRow() {
        TableRow row = new TableRow(this);
        row.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT
        ));

        // ================= TYPE (Spinner) =================
        Spinner spinnerType = new Spinner(this);
        String[] metals = {"Select", "Gold", "Silver"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                metals
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);

        TableRow.LayoutParams spinnerParams = new TableRow.LayoutParams(dpToPx(120), dpToPx(120));
        spinnerType.setLayoutParams(spinnerParams);
        row.addView(spinnerType);

        // ================= WEIGHT =================
        LinearLayout weightLayout = new LinearLayout(this);
        weightLayout.setOrientation(LinearLayout.HORIZONTAL);
        weightLayout.setGravity(Gravity.CENTER_VERTICAL);
        weightLayout.setBackgroundResource(R.drawable.cell_border);
        weightLayout.setLayoutParams(new TableRow.LayoutParams(dpToPx(100), dpToPx(120)));

        EditText etWeight = new EditText(this);
        LinearLayout.LayoutParams etParams = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.MATCH_PARENT, 1f
        );
        etWeight.setLayoutParams(etParams);
        etWeight.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        etWeight.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
        etWeight.setBackground(null);

        TextView gm = new TextView(this);
        gm.setText("gm");
        gm.setGravity(Gravity.CENTER_VERTICAL);
        gm.setPadding(dpToPx(4), 0, dpToPx(4), 0);

        weightLayout.addView(etWeight);
        weightLayout.addView(gm);
        row.addView(weightLayout);

        // ================= RATE =================
        EditText etRate = new EditText(this);
        etRate.setLayoutParams(new TableRow.LayoutParams(dpToPx(80), dpToPx(120)));
        etRate.setBackgroundResource(R.drawable.cell_border);
        etRate.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        row.addView(etRate);

        // ================= VALUE =================
        EditText etValue = new EditText(this);
        etValue.setLayoutParams(new TableRow.LayoutParams(dpToPx(90), dpToPx(120)));
        etValue.setBackgroundResource(R.drawable.cell_border);
        etValue.setEnabled(false);
        row.addView(etValue);

        // ================= MAKING =================
        EditText etMaking = new EditText(this);
        etMaking.setLayoutParams(new TableRow.LayoutParams(dpToPx(90), dpToPx(120)));
        etMaking.setBackgroundResource(R.drawable.cell_border);
        etMaking.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        row.addView(etMaking);

        // ================= AMOUNT =================
        EditText etAmount = new EditText(this);
        etAmount.setLayoutParams(new TableRow.LayoutParams(dpToPx(90), dpToPx(120)));
        etAmount.setBackgroundResource(R.drawable.cell_border);
        etAmount.setEnabled(false);
        row.addView(etAmount);

        // ================= DELETE BUTTON =================
        Button deleteBtn = new Button(this);
        deleteBtn.setText("X");
        deleteBtn.setOnClickListener(v -> tableLayout.removeView(row));
        row.addView(deleteBtn);
        updateTotalAmount();


        // ================= TEXTWATCHER FOR CALCULATION =================
        TextWatcher watcher = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                double weight = getDouble(etWeight);
                double rate = getDouble(etRate);
                double making = getDouble(etMaking);

                double value = weight * rate;
                double amount = value + making;

                etValue.setText(String.format(Locale.getDefault(), "%.2f", value));
                etAmount.setText(String.format(Locale.getDefault(), "%.2f", amount));


            }
            @Override public void afterTextChanged(Editable s) {}
        };

        etWeight.addTextChangedListener(watcher);
        etRate.addTextChangedListener(watcher);
        etMaking.addTextChangedListener(watcher);

        // ================= ADD ROW TO TABLE =================
        tableLayout.addView(row);
    }

    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }




    private void calculateAmount() {
        double weight = getDouble(etWeight);
        double rate = getDouble(etRate);
        double making = getDouble(etMaking);

        double value = weight * rate;
        double amount = value + making;

        etValue.setText(String.format(Locale.getDefault(), "%.2f", value));
        etAmount.setText(String.format(Locale.getDefault(), "%.2f", amount));

    }

    private void calculatePending() {
        double amount = grandTotal;
        double deposit = getDouble(etDeposit);

        double pending = amount - deposit;
        etPendingDeposit.setText(String.format(Locale.getDefault(), "%.2f", pending));
    }

    private double getDouble(EditText et) {
        try {
            return et.getText().toString().isEmpty() ? 0 : Double.parseDouble(et.getText().toString());
        } catch (Exception e) {
            return 0;
        }
    }

    // ================= SAVE CUSTOMER =================

    private void clearAllFields() {

        // Customer details
        etCustomerName.setText("");
        etMobile.setText("");
        etAddress.setText("");
        etDescription.setText("");
        etDeposit.setText("");

        // Calculation fields
        etWeight.setText("");
        etRate.setText("");
        etValue.setText("");
        etMaking.setText("");
        etAmount.setText("");
        etPendingDeposit.setText("");

        // Date ko aaj ki date pe reset
        String currentDate = new SimpleDateFormat(
                "dd-MM-yyyy",
                Locale.getDefault()
        ).format(new Date());
        etDate.setText(currentDate);
    }


    private void saveCustomer() {


       //  billNo = tvBillNo.getText().toString().trim();
        String name = etCustomerName.getText().toString().trim();
        String mobile = etMobile.getText().toString().trim();
        String date = etDate.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String description = etDescription.getText().toString().trim();

        String type = spinnerMetal.getSelectedItem().toString();

        if (type.equals("Select")) {
            Toast.makeText(this, "Please select Metal type", Toast.LENGTH_SHORT).show();
            return;
        }
       // String type = spinnerMetal.getSelectedItem().toString();


        double weight = etWeight.getText().toString().trim().isEmpty()
                ? 0
                : Double.parseDouble(etWeight.getText().toString().trim());
        double rate = etRate.getText().toString().trim().isEmpty()
                ? 0
                : Double.parseDouble(etRate.getText().toString().trim());
        double value = etValue.getText().toString().trim().isEmpty()
                ? 0
                : Double.parseDouble(etValue.getText().toString().trim());
        double making = etMaking.getText().toString().trim().isEmpty()
                ? 0
                : Double.parseDouble(etMaking.getText().toString().trim());

        double amount = etAmount.getText().toString().trim().isEmpty()
                ? 0
                : Double.parseDouble(etAmount.getText().toString().trim());


        double deposit = getDouble(etDeposit);

        double pendingAmount = etPendingDeposit.getText().toString().trim().isEmpty()
                ? 0
                : Double.parseDouble(etPendingDeposit.getText().toString().trim());


        if (name.isEmpty()) {
            etCustomerName.setError("Enter customer name");
            return;
        }

        if (mobile.length() < 10) {
            etMobile.setError("Enter valid mobile number");
            return;
        }
        String billNo = (BillNumberGenerator.getNextBillNo(this));
        tvBillNo.setText(billNo);


        long customerId = dbHelper.insertCustomer(
                name,
                mobile,
                date,
                address,
                description,
                grandTotal,
                deposit,
                pendingAmount,
                billNo
        );

        Toast.makeText(this,
                "Bill Number  = " + tvBillNo.getText().toString(),
                Toast.LENGTH_SHORT).show();

        if (customerId != -1) {
            saveAllRows(customerId);
            Toast.makeText(this, "✅ Customer Data  Saved Successfully", Toast.LENGTH_SHORT).show();
            etCustomerName.setText("");
            etMobile.setText("");
            etAddress.setText("");
            etDescription.setText("");
           // etDeposit.setText("");
           // etPendingDeposit.setText("");
          //  spinnerMetal.setSelection(0);
            //etWeight.setText("");
            //etRate.setText("");
           // etValue.setText("");
            //etMaking.setText("");
            //etAmount.setText("");
            etDeposit.setText("");
            etPendingDeposit.setText("");

        } else {
            Toast.makeText(this, "❌ Failed to Save", Toast.LENGTH_SHORT).show();
            clearAllFields();
        }


    }

    private void saveAllRows(long customerId) {

        int rowCount = tableLayout.getChildCount();

        for (int i = 0; i < rowCount; i++) {

            View rowView = tableLayout.getChildAt(i);

            if (!(rowView instanceof TableRow)) continue;

            TableRow row = (TableRow) rowView;

            // Minimum 6 children hone chahiye
            if (row.getChildCount() < 6) continue;

            // Check correct types before casting
            if (!(row.getChildAt(0) instanceof Spinner)) continue;
            if (!(row.getChildAt(1) instanceof LinearLayout)) continue;
            if (!(row.getChildAt(2) instanceof EditText)) continue;
            if (!(row.getChildAt(3) instanceof EditText)) continue;
            if (!(row.getChildAt(4) instanceof EditText)) continue;
            if (!(row.getChildAt(5) instanceof EditText)) continue;

            Spinner spinnerType = (Spinner) row.getChildAt(0);

            LinearLayout weightLayout = (LinearLayout) row.getChildAt(1);
            if (weightLayout.getChildCount() == 0) continue;

            EditText etWeight = (EditText) weightLayout.getChildAt(0);
            EditText etRate = (EditText) row.getChildAt(2);
            EditText etValue = (EditText) row.getChildAt(3);
            EditText etMaking = (EditText) row.getChildAt(4);
            EditText etAmount = (EditText) row.getChildAt(5);

            String type = spinnerType.getSelectedItem().toString();

            double weight = getDouble(etWeight);
            double rate = getDouble(etRate);
            double value = getDouble(etValue);
            double making = getDouble(etMaking);
            double amount = getDouble(etAmount);

            if (weight == 0 && rate == 0 && making == 0)
                continue;

            dbHelper.insertBillItem(
                    customerId,
                    type,
                    weight,
                    rate,
                    value,
                    making,
                    amount
            );
        }
    }


    private void updateTotalAmount() {
        double total = 0;

        System.out.println("Hello Vanshu");
        int rowCount = tableLayout.getChildCount();

        for (int i = 0; i < rowCount; i++) {
            View rowView = tableLayout.getChildAt(i);
            if (rowView instanceof TableRow) {
                TableRow row = (TableRow) rowView;


                View amountView = row.getChildAt(5);
                if (amountView instanceof EditText) {
                    total += getDouble((EditText) amountView);
                     grandTotal = total;
                }
            }
        }
        tvTotalAmount.setText(String.format(Locale.getDefault(), "Grand Total Amount: %.2f", grandTotal));
    }

}
