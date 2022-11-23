package com.example.metaMergeTasker;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class toDoScreen extends AppCompatActivity {
    protected toDoDbHelper db;
    List<toDoClass> list;
    MyAdapter adapt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_to_do_screen);
        db = new toDoDbHelper(this);
        list = db.getAllTasks();
        adapt = new MyAdapter(this, R.layout.activity_to_do_list_inner_view, list);
        ListView listTask = (ListView) findViewById(R.id.listView1);
        listTask.setAdapter(adapt);

        new AlertDialog.Builder(toDoScreen.this)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle("Information")
                .setMessage("Type task details at bottom of screen" + "\n\n" + "Use button to add task to the list" + "\n\n" +  "Tap on a tasks checkbox to mark it as completed" + "\n\n" + "Long tap on a tasks checkbox to delete the task")
                .setPositiveButton("OK", null).show();

    }

    public void addTaskNow(View v) {
        EditText t = (EditText) findViewById(R.id.editText1);
        String s = t.getText().toString();

        if (s.equalsIgnoreCase("")) {
            Toast.makeText(this, "enter the task description first!!",
            Toast.LENGTH_LONG).show();
        } else {
            toDoClass task = new toDoClass(s, 0, 0);
            db.addTask(task);
            Log.d("tasker", "data added");
            t.setText("");
            adapt.add(task);

            // Adam: Here I am forcing a full reload of the task list
            db = new toDoDbHelper(toDoScreen.this);
            list = db.getAllTasks();
            adapt = new MyAdapter(toDoScreen.this, R.layout.activity_to_do_list_inner_view, list);
            ListView listTask = (ListView) findViewById(R.id.listView1);
            listTask.setAdapter(adapt);

            adapt.notifyDataSetChanged();
        }
    }

    private class MyAdapter extends ArrayAdapter<toDoClass> {
        Context context;
        List<toDoClass> taskList = new ArrayList<toDoClass>();
        int layoutResourceId;
        public MyAdapter(Context context, int layoutResourceId, List<toDoClass> objects) {
            super(context, layoutResourceId, objects);
            this.layoutResourceId = layoutResourceId;
            this.taskList = objects;
            this.context = context;
        }

        /**
         * This method will DEFINE what the view inside the list view will
         * finally look like Here we are going to code that the checkbox state
         * is the status of task and check box text is the task name
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CheckBox chk;
            toDoClass current = taskList.get(position);

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.activity_to_do_list_inner_view, parent, false);
                chk = (CheckBox) convertView.findViewById(R.id.checkBox1);
                convertView.setTag(chk);

                chk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        toDoClass changeTask = (toDoClass) cb.getTag();
                        //changeTask.setStatus(cb.isChecked() == true ? 1 : 0);
                        // Adam: Just testing...
                        if (cb.isChecked()) {
                            changeTask.setStatus(1); // Works sometimes wtf?
                        } else {
                            changeTask.setStatus(0); // Works all the time
                        }
                        db.updateTask(changeTask);
                        // For Debugging
                        //Toast.makeText(getApplicationContext(), "Clicked on Checkbox ID: " + current.getId() + " Content: " + cb.getText() + " is " + cb.isChecked(), Toast.LENGTH_LONG).show();
                    }
                });

                chk.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        CheckBox cb = (CheckBox) v;

                        new AlertDialog.Builder(toDoScreen.this)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("Are you sure?")
                                .setMessage("Do you want to delete this task?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        toDoClass changeToDoClass = (toDoClass) cb.getTag();
                                        changeToDoClass.setDeleted(1);
                                        db.updateTask(changeToDoClass);
                                        // Adam: Dirty Hack for the mean time to now show the item :D
                                        cb.setVisibility(View.INVISIBLE);
                                        // For Debugging
                                        //Toast.makeText(getApplicationContext(), "Long on Checkbox ID: " + current.getId() + " Content: " + cb.getText(), Toast.LENGTH_LONG).show();

                                        // Adam: Here I am forcing a full reload of the task list
                                        db = new toDoDbHelper(toDoScreen.this);
                                        list = db.getAllTasks();
                                        adapt = new MyAdapter(toDoScreen.this, R.layout.activity_to_do_list_inner_view, list);
                                        ListView listTask = (ListView) findViewById(R.id.listView1);
                                        listTask.setAdapter(adapt);

                                        adapt.notifyDataSetChanged();
                                    }
                                }).setNegativeButton("No", null).show();
                        return true;

                    }
                });
            } else {
                chk = (CheckBox) convertView.getTag();
            }

            chk.setText(current.getTaskName());
            // Adam: Simplified this to an if else statement, easier to read...
            //chk.setChecked(current.getStatus() == 1 ? true : false);
            if (current.getStatus() == 1) {
                chk.setChecked(true);
                chk.setText(current.getTaskName());
            } else {
                chk.setChecked(false);
            }
            chk.setTag(current);

            //Adam: This is for debugging
            //Log.d("listener", "ID: " + String.valueOf(current.getId()) + " - Status: " + current.getStatus());

            return convertView;
        }
    }
}