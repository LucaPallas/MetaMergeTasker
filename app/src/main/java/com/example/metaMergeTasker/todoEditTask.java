package com.example.metaMergeTasker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;

public class todoEditTask extends AppCompatActivity {
    int todoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_edit_activity);

        EditText editTodoText = findViewById(R.id.editTodoText);

        // Fetch data that is passed from MainActivity
        Intent intent = getIntent();

        // Accessing the data using key and value
        todoId = intent.getIntExtra("todoId", -1);
        if (todoId != -1) {
            editTodoText.setText(todoMainActivity.todos.get(todoId));
        } else {

            todoMainActivity.todos.add("");
            todoId = todoMainActivity.todos.size() - 1;
            todoMainActivity.arrayAdapter.notifyDataSetChanged();

        }

        editTodoText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // add your code here
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                todoMainActivity.todos.set(todoId, String.valueOf(charSequence));
                todoMainActivity.arrayAdapter.notifyDataSetChanged();

                // Adam: New Storage Code
                // storageSend();

                // Adam: Remove this code and replace with new storage code
                // Creating Object of SharedPreferences to store data in the phone
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.metaMergeTasker", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet(todoMainActivity.todos);
                sharedPreferences.edit().putStringSet("todos", set).apply();
                // Adam: END - REMOVAL
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // add your code here
            }
        });
    }
}
