package com.example.metaMergeTasker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class TaskerDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "taskerManager";

    // tasks table name
    private static final String TABLE_TASKS = "tasks";

    // tasks Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TASKNAME = "taskName";
    private static final String KEY_STATUS = "status";
    private static final String KEY_DELETED = "deleted";

    public TaskerDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_TASKS + " ( "
        + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
        + KEY_TASKNAME+ " TEXT, "
        + KEY_DELETED + " INTEGER, "
        + KEY_STATUS + " INTEGER)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        // Create tables again
        onCreate(db);
    }

    public void addTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TASKNAME, task.getTaskName()); // task name

        // status of task- can be 0 for not done and1 for done
        values.put(KEY_STATUS, task.getStatus());

        // deleted - can be 0 for not deleted and 1 for deleted
        values.put(KEY_DELETED, task.getDeleted());

        // Inserting Row
        db.insert(TABLE_TASKS, null, values);
        db.close(); // Closing database connection
    }

    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<Task>();
        // Select All Query
        // ADAM : Select all entries except for those flagged as deleted
        String selectQuery = "SELECT * FROM " + TABLE_TASKS + " WHERE " + KEY_DELETED + " <> 1";
        Log.d("listener", selectQuery);

        SQLiteDatabase db = this.getReadableDatabase(); // Adam: Made this a read only task
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        Task task;
        if (cursor.moveToFirst()) {
            do {
                task = new Task();
                task.setId(cursor.getInt(0));
                task.setTaskName(cursor.getString(1));
                task.setDeleted(cursor.getInt(2));
                task.setStatus(cursor.getInt(3));

                // Adding task to the list
                taskList.add(task);

            } while (cursor.moveToNext());
        }
        db.close();
        // return tasklist
        return taskList;
        }

        public void updateTask(Task task) {
            // updating row
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_TASKNAME, task.getTaskName());
            values.put(KEY_DELETED, task.getDeleted());
            values.put(KEY_STATUS, task.getStatus());

            //Adam: This is for debugging
            Log.d("listener", "updateTask: " + task.getId() + " - " + values);
            int blah = db.update(TABLE_TASKS, values, "" + KEY_ID + "" + " = " + task.getId(), null);

            //Adam: This is for debugging
            Log.d("listener", "SQL STATUS: " + blah + " [0 = Failed, 1 = Success]");
            db.close();
        }
}