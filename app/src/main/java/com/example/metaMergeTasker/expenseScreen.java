package com.example.metaMergeTasker;

import static java.lang.Math.round;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.material.textfield.TextInputLayout;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class expenseScreen extends AppCompatActivity {
    RecyclerView recyclerView;
    expenseAdapter adapter;
    ArrayList<expenseClass> list;
    TextInputLayout exp,amount;
    TextView total;
    Button add;


    // Adam: Convert ArrayList to HashMap's for Storage
    public boolean storageSend() {
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
                list.add(new expenseClass(itCounter.next().toString(), Double.parseDouble(itCounter2.next().toString())));
            }
        }

        return true; // Return False if we need error reporting?
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_screen);
        recyclerView = findViewById(R.id.recyclerview);
        list = new ArrayList<>();
        exp = findViewById(R.id.expense);
        amount = findViewById(R.id.amount);
        add = findViewById(R.id.addbutton);
        total = findViewById(R.id.totalamount);

        new AlertDialog.Builder(expenseScreen.this)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle("Information")
                .setMessage("Use inputs at bottom to enter an expense name and amount" + "\n\n" + "Use button to add an expense to the list" + "\n\n" + "Ability to delete and edit an expense are currently unavailable")
                .setPositiveButton("OK", null).show();

        // Adam: Fetch DATA From Storage
        storageRecv();

        adapter = new expenseAdapter(this,list);
        LinearLayoutManager llm = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double totalAmount = 0.00;
                String ex = exp.getEditText().getText().toString();
                Double a = Double.parseDouble(amount.getEditText().getText().toString());
                list.add(new expenseClass(ex,a));
                adapter.notifyDataSetChanged();
                exp.getEditText().setText("");
                amount.getEditText().setText("");

                for(int i = 0;i <list.size();i++){

                    totalAmount += list.get(i).getExpenseAmount();
                    total.setText("Total - $"+ round(totalAmount*100d)/100d);
                }
                // Adam: Update stored DATA
                storageSend();
            }
        });
    }
}