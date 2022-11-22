package com.example.metaMergeTasker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends ArrayAdapter<Task> {
    Context context;
    List<Task> taskList=new ArrayList<Task>();
    int layoutResourceId;

    public MyAdapter(Context context, int layoutResourceId, List<Task> objects) {
        super(context, layoutResourceId, objects);
        this.layoutResourceId = layoutResourceId;
        this.taskList=objects;
        this.context=context;
    }

    /**This method will DEFINe what the view inside the list view will finally look like
     * Here we are going to code that the checkbox state is the status of task and
     * check box text is the task name
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_inner_view, parent, false);
        CheckBox chk=(CheckBox)rowView.findViewById(R.id.checkBox1);
        Task current=taskList.get(position);
        chk.setText(current.getTaskName());
        chk.setChecked(current.getStatus() == 1 ? true:false);

        return rowView;
    }
}