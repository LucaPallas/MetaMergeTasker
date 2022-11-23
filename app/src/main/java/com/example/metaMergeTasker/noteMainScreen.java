// Adam: Borrowed code from https://www.geeksforgeeks.org/how-to-build-a-simple-notes-app-in-android/

package com.example.metaMergeTasker;

import static com.example.metaMergeTasker.R.*;

import android.os.Bundle;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;


public class noteMainScreen extends AppCompatActivity {

    static ArrayList<String> notes = new ArrayList<>();
    static ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(layout.activity_note_main_screen);
        Button addNoteBtn = (Button) findViewById(id.addNoteBtn);
        ListView listView = findViewById(id.noteListView);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.metaMergeTasker", Context.MODE_PRIVATE);
        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("notes", null);

        if (set == null)
        {
            notes.add("Example note");
        }
        else
        {
            notes = new ArrayList(set);
        }

        new AlertDialog.Builder(noteMainScreen.this)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle("Information")
                .setMessage("Tap button at bottom of screen to add a new note" + "\n\n" + "Press back on the action bar to exit text input screen and add the text to the list" + "\n\n" + "Tap on a note to edit the note via the text input screen" + "\n\n" + "Long tap a note to delete it")
                .setPositiveButton("OK", null).show();

        // Using custom listView
        arrayAdapter = new ArrayAdapter(this, R.layout.activity_note_list_view, notes);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                // Going from MainActivity to NotesEditorActivity
                Intent intent = new Intent(getApplicationContext(), noteEditScreen.class);
                intent.putExtra("noteId", i);
                startActivity(intent);

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l)
            {

                final int itemToDelete = i;
                // To delete the data from the App
                new AlertDialog.Builder(noteMainScreen.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                notes.remove(itemToDelete);
                                arrayAdapter.notifyDataSetChanged();
                                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.metaMergeTasker", Context.MODE_PRIVATE);
                                HashSet<String> set = new HashSet(noteMainScreen.notes);
                                sharedPreferences.edit().putStringSet("notes", set).apply();
                            }
                        }).setNegativeButton("No", null).show();
                return true;
            }
        });



        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Going from MainActivity to NotesEditorActivity
                Intent intent = new Intent(getApplicationContext(), noteEditScreen.class);
                startActivity(intent);
            }
        });
    }
}