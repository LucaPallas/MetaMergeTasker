package com.example.metaMergeTasker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.w3c.dom.Text;

import java.util.jar.Attributes;

// Adam: Expense Manager New/Edit Expense Interface
public class expenseEdit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_edit);



        Button name = (Button) findViewById(R.id.expenseName);


        name.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String expenseName = (String) name.getText();
                // Store in array
                Intent intent=new Intent(expenseEdit.this, expenseMain.class);
                startActivity(intent);
            }
        });



    }
}