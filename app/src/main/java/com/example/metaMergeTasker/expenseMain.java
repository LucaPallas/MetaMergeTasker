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
import java.util.Set;

public class expenseMain extends AppCompatActivity {
    RecyclerView recyclerView;
    ExpenseAdapter adapter;
    ArrayList<Expense> list;
    TextInputLayout exp,amount;
    TextView total;
    Button add;

    public HashSet listToSetConversion(ArrayList<Expense> input) {
        HashSet<Object> output = new HashSet<Object>(Collections.singleton(input.toString()));
        return output;
    }

    public ArrayList setToListConversion(Set input) {
        ArrayList<Expense> output = new ArrayList<>(input);
        Log.d("STATE", input.toString());
        return output;
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

  /*      // Fetch Saved Data
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.metaMergeTasker", Context.MODE_PRIVATE);
        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("expenses", null);

        if (set != null) {
            list = setToListConversion(set);
        }
*/

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
                    total.setText("Total - $"+totalAmount);
                }
  /*
                // Creating Object of SharedPreferences to store data in the phone
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.metaMergeTasker", Context.MODE_PRIVATE);
                HashSet set = listToSetConversion(list);
                sharedPreferences.edit().putStringSet("expenses", set).apply();

   */
            }
        });




    }
}