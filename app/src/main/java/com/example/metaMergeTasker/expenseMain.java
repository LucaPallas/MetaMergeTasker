package com.example.metaMergeTasker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class expenseMain extends AppCompatActivity {
    RecyclerView recyclerView;
    ExpenseAdapter adapter;
    ArrayList<Expense> list;
    TextInputLayout exp,amount;
    TextView total;
    Button add;

    // Adam: Convert ArrayList to HashMap's for Storage
    public boolean storageSend(String ex, Double a) {
        // Temp Arrays
        ArrayList<String> storageExpenses = new ArrayList<>(), storageAmounts = new ArrayList<>();

        // Storage Config
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.metaMergeTasker", Context.MODE_PRIVATE);

        // Loop ArrayList<Expenses> list
        for (int i = 0; i < list.size(); i++) {
            storageExpenses.add(String.valueOf(list.get(i).getExpenseName()));
            storageAmounts.add(String.valueOf(list.get(i).getExpenseAmount()));
        }
        // Push to storage - Part 1
        HashSet<String> tmpSet1 = new HashSet(storageExpenses);
        sharedPreferences.edit().putStringSet("expensesPart1", tmpSet1).apply();

        HashSet<String> tmpSet2 = new HashSet(storageAmounts);
        sharedPreferences.edit().putStringSet("expensesPart2", tmpSet2).apply();

        return true; // Return False if we need error reporting?
    }

    // Adam: Rebuild ArrayList from two HashMap Sources
    public boolean storageRecv() {
        list = new ArrayList<>();

        // Storage Config
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.metaMergeTasker", Context.MODE_PRIVATE);
        HashSet<String> setPart1 = (HashSet<String>) sharedPreferences.getStringSet("expensesPart1", null);
        HashSet<String> setPart2 = (HashSet<String>) sharedPreferences.getStringSet("expensesPart2", null);

        // Simple NULL Check
        if (setPart1 != null && setPart2 != null) {
            // iterator so we can loop though the hashset
            Iterator itCounter = setPart1.iterator();
            Iterator itCounter2 = setPart2.iterator();

            // Rebuild Array list from two HashSets
            while (itCounter.hasNext()) {
                list.add(new Expense(itCounter.next().toString(), Double.parseDouble(itCounter2.next().toString())));
            }
        }

        return true; // Return False if we need error reporting?
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);
        list = new ArrayList<>();
        exp = findViewById(R.id.expense);
        amount = findViewById(R.id.amount);
        add = findViewById(R.id.addbutton);
        total = findViewById(R.id.totalamount);

        // Adam: Fetch DATA From Storage
        storageRecv();

        adapter = new ExpenseAdapter(this,list);
        LinearLayoutManager llm = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double totalAmount = 0.00;
                String ex = exp.getEditText().getText().toString();
                Double a = Double.parseDouble(amount.getEditText().getText().toString());
                list.add(new Expense(ex,a));
                adapter.notifyDataSetChanged();
                exp.getEditText().setText("");
                amount.getEditText().setText("");

                for(int i = 0;i <list.size();i++){

                    totalAmount += list.get(i).getExpenseAmount();
                    total.setText("Total Rs. "+totalAmount);
                }
                // Adam: Update stored DATA
                storageSend(ex,a);
            }
        });




    }
}