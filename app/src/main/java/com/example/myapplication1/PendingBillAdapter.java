package com.example.myapplication1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PendingBillAdapter
        extends RecyclerView.Adapter<PendingBillAdapter.ViewHolder> {

    Context context;
    List<Bean> list;

    public PendingBillAdapter(Context context, List<Bean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.row_pending_bill, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder h, int position) {

        Bean b = list.get(position);

        // ===== OLD CODE (NO CHANGE) =====
        h.txtBillNo.setText("Bill No: " + b.billNo);
        h.txtDescription.setText("Description: " + b.description);
        h.txtCustomer.setText("Customer: " + b.customerName);
        h.txtMobile.setText("Mobile: " + b.mobile);
       h.txtTotal.setText("Total: ₹ " + b.finalAmount);
      //  h.txtDeposit.setText("Deposit: ₹ " + b.firstPayment);
        h.txtDeposit.setText("Deposited Amount: " +b.totalDeposit);
        h.txtPending.setText("Pending: ₹ " + b.pendingAmount);
        // =================================

        // 🔥🔥 CLICKABLE BILL NO (ADDED ONLY) 🔥🔥
        h.txtBillNo.setClickable(true);
        h.txtBillNo.setTextColor(0xFFB8962E); // gold color
        h.txtBillNo.setPaintFlags(
                h.txtBillNo.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG
        );

        h.txtBillNo.setOnClickListener(v -> {

            Intent i = new Intent(context, DepositAmountActivity.class);
            i.putExtra("bill_id", b.id);

            // 🔐 Safety (kabhi kabhi adapter context issue)
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtBillNo, txtDescription, txtCustomer, txtMobile,
                txtTotal, txtDeposit, txtPending;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtBillNo = itemView.findViewById(R.id.txtBillNo);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            txtCustomer = itemView.findViewById(R.id.txtCustomer);
            txtMobile = itemView.findViewById(R.id.txtMobile);
            txtTotal = itemView.findViewById(R.id.txtTotal);
            txtDeposit = itemView.findViewById(R.id.txtDeposit);
            txtPending = itemView.findViewById(R.id.txtPending);
        }
    }
}
