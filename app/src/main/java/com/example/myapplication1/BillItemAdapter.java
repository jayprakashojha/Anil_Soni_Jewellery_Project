package com.example.myapplication1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
public class BillItemAdapter extends RecyclerView.Adapter<BillItemAdapter.ViewHolder> {

    Context context;
    List<BillItemBean> list;
    private String customerDate;



    public BillItemAdapter(Context context, List<BillItemBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.row_bill_item, parent, false);

        return new ViewHolder(view);
    }

    DatabaseHelper db;
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        BillItemBean item = list.get(position);



// 🔹 1. Type Check
        if (item.getType() != null && !item.getType().isEmpty()) {
            holder.txtType.setVisibility(View.VISIBLE);
            holder.txtType.setText("Type: " + item.getType());
        } else {
            holder.txtType.setVisibility(View.GONE);
        }

// 🔹 2. Weight Check (Weight agar 0.0 hai to hide karein)
        if (item.getWeight() > 0) {
            holder.txtWeight.setVisibility(View.VISIBLE);
            holder.txtWeight.setText("Weight: " + item.getWeight());
        } else {
            holder.txtWeight.setVisibility(View.GONE);
        }

// 🔹 3. Rate Check
        if (item.getRate() > 0) {
            holder.txtRate.setVisibility(View.VISIBLE);
            holder.txtRate.setText("Rate: ₹" + item.getRate());
        } else {
            holder.txtRate.setVisibility(View.GONE);
        }

// 🔹 4. Value Check
        if (item.getValue() > 0) {
            holder.txtValue.setVisibility(View.VISIBLE);
            holder.txtValue.setText("Value: ₹" + item.getValue());
        } else {
            holder.txtValue.setVisibility(View.GONE);
        }

if(item.getMaking()>0) {
    holder.txtMaking.setText("Making: ₹" + item.getMaking());
}
else {
        holder.txtMaking.setVisibility(View.GONE);
    }

            holder.txtType.setVisibility(View.VISIBLE);


    holder.txtType.setText("Date: " + item.getDate());



        if(item.getMaking()>0) {

            holder.txtAmount.setVisibility(View.GONE);

        }
        else {



            holder.txtAmount.setText("Deposited Amount: ₹" + item.getAmount());


        }






        if (item.getType() != null && !item.getType().isEmpty()) {
            holder.btnDeposit.setVisibility(View.VISIBLE);

            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(Color.parseColor("#0D47A1"));
            drawable.setCornerRadius(60);
            holder.btnDeposit.setBackground(drawable);
            holder.btnDeposit.setTextColor(Color.WHITE);

            holder.btnDeposit.setOnClickListener(v -> {
                Intent intent = new Intent(context, activity_deposit.class);
                intent.putExtra("customer_id", item.getCustomerId());
                intent.putExtra("id", item.getId());
                context.startActivity(intent);
            });
        } else {
            holder.btnDeposit.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtType, txtWeight, txtRate, txtValue, txtMaking, txtAmount, txtDate;
        Button btnDeposit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtType = itemView.findViewById(R.id.txtType);
            txtWeight = itemView.findViewById(R.id.txtWeight);
            txtRate = itemView.findViewById(R.id.txtRate);
            txtValue = itemView.findViewById(R.id.txtValue);
            txtMaking = itemView.findViewById(R.id.txtMaking);
            txtAmount = itemView.findViewById(R.id.txtAmount);
            btnDeposit = itemView.findViewById(R.id.btnDeposit);
            txtDate = itemView.findViewById(R.id.etDate);
        }
    }
}