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
import java.util.Iterator;


public class todoMainActivity extends AppCompatActivity {

    static ArrayList<String> todos = new ArrayList<>(); // ADAM: LUCA --> Implement todo class similar to expenses class
    static ArrayAdapter arrayAdapter;

    /****** CODE READY for New ARRAYLIST CLASS *******/
/*
    // Adam: Convert ArrayList to HashMap's for Storage
    public boolean storageSend() {
        // Temp Arrays
        ArrayList<String> storageStatus = new ArrayList<>(), storageContents = new ArrayList<>();

        // Storage Config
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.metaMergeTasker", Context.MODE_PRIVATE);

        // Loop ArrayList<Expenses> list
        for (int i = 0; i < todos.size(); i++) {
            storageStatus.add(String.valueOf(todos.get(i).getTodoStatus()));
            storageContents.add(String.valueOf(todos.get(i).getTodoContents()));
        }
        // Push to storage - Part 1
        HashSet<String> tmpSet1 = new HashSet(storageStatus);
        sharedPreferences.edit().putStringSet("todosPart1", tmpSet1).apply();

        HashSet<String> tmpSet2 = new HashSet(storageContents);
        sharedPreferences.edit().putStringSet("todosPart2", tmpSet2).apply();

        return true; // Return False if we need error reporting?
    }

    // Adam: Rebuild ArrayList from two HashMap Sources
    public boolean storageRecv() {
        todos = new ArrayList<>();

        // Storage Config
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.metaMergeTasker", Context.MODE_PRIVATE);
        HashSet<String> setPart1 = (HashSet<String>) sharedPreferences.getStringSet("todosPart1", null);
        HashSet<String> setPart2 = (HashSet<String>) sharedPreferences.getStringSet("todosPart2", null);

        // Simple NULL Check
        if (setPart1 != null && setPart2 != null) {
            // iterator so we can loop though the hashset
            Iterator itCounter = setPart1.iterator();
            Iterator itCounter2 = setPart2.iterator();

            // Rebuild Array list from two HashSets
            while (itCounter.hasNext()) {
                todos.add(new Todo(Boolean.parseBoolean(itCounter.next().toString()), itCounter2.next().toString()));
            }
        }

        return true; // Return False if we need error reporting?
    }
*/
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

        // Using custom listView layout
        arrayAdapter = new ArrayAdapter(this, layout.list_view, todos);

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