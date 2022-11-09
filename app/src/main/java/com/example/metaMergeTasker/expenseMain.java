package com.example.metaMergeTasker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Adam: Expense Manager Main Interface
public class expenseMain extends AppCompatActivity {


    Button imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_main);

        TextView expenseTitle = (TextView)findViewById(R.id.expenseTitle);

        Button expMang = (Button) findViewById(R.id.addExpenseBtn);

        expMang.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(expenseMain.this, expenseEdit.class);
                startActivity(intent);
            }
        });

        }

        // ADAM: Set textview title here in case we want other UI languages in the future
        //expenseTitle.setText("Expense");

        // App storage
        //SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.metaMergeTasker", Context.MODE_PRIVATE);

        // Adam: Fetch Fields Example
        /*
        genBTN8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String expenseName = (String) expenseName.getText();
                Double expenseCost = (Double) expenseCost.getText();
                // Store in array
                Intent intent=new Intent(expenseEdit.this, expenseMain.class);
                startActivity(intent);
            }
        }); */

        //ADAM: ArrayList of Sets for storage of our expenses
        //List<Set<String>> expenses = new ArrayList<Set<String>>();
        /*ADAM: example to add to the set
        *   ITEMS will need to be added and removed to ensure the correct order....
        *
        * expenses.add(new HashSet<Object>());
        * expenses.get(X).add("Some Object/String/Int"); // Each run will add an entry to row X
        * expenses.get(X).remove("Some Object/String/Int"); // Will delete matching entry
        *
         */


    }
