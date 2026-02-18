package com.example.myapplication1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BillItemAdapter extends RecyclerView.Adapter<BillItemAdapter.ViewHolder> {

    Context context;
    List<BillItemBean> list;

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

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        BillItemBean item = list.get(position);

        holder.txtType.setText("Type: " + item.getType());
        holder.txtWeight.setText("Weight: " + item.getWeight());
        holder.txtAmount.setText("Amount: ₹" + item.getAmount());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtType, txtWeight, txtAmount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtType = itemView.findViewById(R.id.txtType);
            txtWeight = itemView.findViewById(R.id.txtWeight);
            txtAmount = itemView.findViewById(R.id.txtAmount);
        }
    }
}
