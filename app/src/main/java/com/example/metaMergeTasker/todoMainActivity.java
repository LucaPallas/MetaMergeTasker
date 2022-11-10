// Adam: Borrowed code from https://www.geeksforgeeks.org/how-to-build-a-simple-notes-app-in-android/

package com.example.metaMergeTasker;

import static com.example.metaMergeTasker.R.id;
import static com.example.metaMergeTasker.R.layout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;


public class todoMainActivity extends AppCompatActivity {

    static ArrayList<String> todos = new ArrayList<>();
    static ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(layout.activity_todo_main_activity);
        Button addNoteBtn = (Button) findViewById(id.addTodoBtn);
        ListView listView = findViewById(id.todoListView);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.metaMergeTasker", Context.MODE_PRIVATE);
        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("todos", null);

        if (set == null) {
            todos.add("Example task");
        } else {
            todos = new ArrayList(set);
        }

        // Using custom listView Provided by Android Studio
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, todos);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Going from MainActivity to todoEditorActivity
                Intent intent = new Intent(getApplicationContext(), todoEditTask.class);
                intent.putExtra("todoId", i);
                startActivity(intent);

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                final int itemToDelete = i;
                // To delete the data from the App
                new AlertDialog.Builder(todoMainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this task?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                todos.remove(itemToDelete);
                                arrayAdapter.notifyDataSetChanged();
                                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.metaMergeTasker", Context.MODE_PRIVATE);
                                HashSet<String> set = new HashSet(todoMainActivity.todos);
                                sharedPreferences.edit().putStringSet("todos", set).apply();
                            }
                        }).setNegativeButton("No", null).show();
                return true;
            }
        });

        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Going from MainActivity to NotesEditorActivity
                Intent intent = new Intent(getApplicationContext(), todoEditTask.class);
                startActivity(intent);
            }
        });
    }
}