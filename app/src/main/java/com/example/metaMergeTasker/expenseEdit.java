package com.example.metaMergeTasker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.w3c.dom.Text;

import java.util.jar.Attributes;

// Adam: Expense Manager New/Edit Expense Interface
public class expenseEdit extends AppCompatActivity {

    private EditText itemEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_edit);

        itemEdt = findViewById(R.id.idEdtItemName2);
        Button addBtn = findViewById(R.id.idBtnAdd2);

    }

        // on below line we are adding click listener for our button.
        addBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // on below line we are getting text from edit text
            String item = itemEdt.getText().toString();

            Intent intent=new Intent(expenseEdit.this, expenseMain.class);
            startActivity(intent);

        }
    });
}