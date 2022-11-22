package com.example.metaMergeTasker;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ViewTask extends AppCompatActivity {
    protected TaskerDbHelper db;
    List<Task> list;
    MyAdapter adapt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);
        db = new TaskerDbHelper(this);
        list = db.getAllTasks();
        adapt = new MyAdapter(this, R.layout.list_inner_view, list);
        ListView listTask = (ListView) findViewById(R.id.listView1);
        listTask.setAdapter(adapt);
    }

    public void addTaskNow(View v) {
        EditText t = (EditText) findViewById(R.id.editText1);
        String s = t.getText().toString();

        if (s.equalsIgnoreCase("")) {
            Toast.makeText(this, "enter the task description first!!",
            Toast.LENGTH_LONG).show();
        } else {
            Task task = new Task(s, 0, 0);
            db.addTask(task);
            Log.d("tasker", "data added");
            t.setText("");
            adapt.add(task);

            // Adam: Here I am forcing a full reload of the task list
            db = new TaskerDbHelper(this);
            list = db.getAllTasks();
            adapt = new MyAdapter(this, R.layout.list_inner_view, list);
            ListView listTask = (ListView) findViewById(R.id.listView1);
            listTask.setAdapter(adapt);

            adapt.notifyDataSetChanged();
        }
    }

    private class MyAdapter extends ArrayAdapter<Task> {
        Context context;
        List<Task> taskList = new ArrayList<Task>();
        int layoutResourceId;
        public MyAdapter(Context context, int layoutResourceId, List<Task> objects) {
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
            Task current = taskList.get(position);

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.list_inner_view, parent, false);
                chk = (CheckBox) convertView.findViewById(R.id.checkBox1);
                convertView.setTag(chk);

                chk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        Task changeTask = (Task) cb.getTag();
                        //changeTask.setStatus(cb.isChecked() == true ? 1 : 0);
                        // Adam: Just testing...
                        if (cb.isChecked()) {
                            changeTask.setStatus(1); // Works sometimes wtf?
                        } else {
                            changeTask.setStatus(0); // Works all the time
                        }
                        db.updateTask(changeTask);
                        db.updateTask(changeTask);

                        Toast.makeText(getApplicationContext(), "Clicked on Checkbox ID: " + current.getId() + " Content: " + cb.getText() + " is " + cb.isChecked(), Toast.LENGTH_LONG).show();
                    }
                });

                chk.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        Task changeTask = (Task) cb.getTag();
                        changeTask.setDeleted(1);
                        db.updateTask(changeTask);

                        Toast.makeText(getApplicationContext(), "Long on Checkbox ID: " + current.getId() + " Content: " + cb.getText(), Toast.LENGTH_LONG).show();

                        return false;
                    }
                });
            } else {
                chk = (CheckBox) convertView.getTag();
            }

            chk.setText(current.getTaskName());
            chk.setChecked(current.getStatus() == 1 ? true : false);
            chk.setTag(current);

            //Adam: This is for debugging
            Log.d("listener", "ID: " + String.valueOf(current.getId()) + " - Status: " + current.getStatus());

            return convertView;
        }
    }
}