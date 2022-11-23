package com.example.metaMergeTasker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class expenseAdapter extends RecyclerView.Adapter<expenseAdapter.ViewHolder> {
    private Context context;
    private ArrayList<expenseClass> list;

    public expenseAdapter(Context context, ArrayList<expenseClass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public expenseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activtity_expense_recycler_design,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull expenseAdapter.ViewHolder holder, int position) {
        holder.exp.setText(list.get(position).getExpenseName());
        holder.amount.setText(Double.toString(list.get(position).getExpenseAmount()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView exp,amount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            exp = itemView.findViewById(R.id.expensedesign);
            amount = itemView.findViewById(R.id.amountdesign);


        }
    }
}