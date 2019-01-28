package com.example.benjamin.simpletodolist;


import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private List<Task> tasks;
    private Context context;
    private OnTaskClickListener listener;
    public List<String> checkedItems = new ArrayList<>();

    public MyAdapter(List<Task> tasks, Context context, OnTaskClickListener listener) {
        this.tasks = tasks;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Date dueDay;
        final Task single_task = tasks.get(position);
        holder.textViewTask.setText(single_task.getTask());

        Calendar current = Calendar.getInstance();
        Calendar d = Calendar.getInstance();
        int year = current.get(Calendar.YEAR);
        int month = current.get(Calendar.MONTH);
        int day = current.get(Calendar.DAY_OF_MONTH);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try {
            dueDay = dateFormat.parse(single_task.getDue_day());
            d.setTime(dueDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int due_Year = d.get(Calendar.YEAR);
        int due_Month = d.get(Calendar.MONTH);
        int due_Day = d.get(Calendar.DAY_OF_MONTH);
        int due_hour = d.get(Calendar.HOUR_OF_DAY);
        int due_min = d.get(Calendar.MINUTE);

        if(due_Year == year && due_Month == month){
            int diff = (due_Day - day);
            holder.textViewDueDay.setText(Main2Activity.hourToString(due_hour) + ": " + Main2Activity.minuteToString(due_min) + "   " + due_Day + "-" + Main2Activity.getMonthInString(due_Month) + "-" + due_Year);
            holder.textViewDueDay.setTextColor(Color.parseColor("#FFE91E63"));
            if(diff == 0){
                holder.textViewDueDay.setText("Today, " + Main2Activity.hourToString(due_hour) + ": " + Main2Activity.minuteToString(due_min));
                holder.textViewDueDay.setTextColor(Color.parseColor("#FFE91E63"));
            }
            if(diff == 1){
                holder.textViewDueDay.setText("Tomorrow, " + Main2Activity.hourToString(due_hour) + ": " + Main2Activity.minuteToString(due_min));
                holder.textViewDueDay.setTextColor(Color.parseColor("#FF1E90FF"));
            }
        }else{
            holder.textViewDueDay.setText(Main2Activity.hourToString(due_hour) + ": " + Main2Activity.minuteToString(due_min) + "   " + due_Day + "-" + Main2Activity.getMonthInString(due_Month) + "-" + due_Year);
            holder.textViewDueDay.setTextColor(Color.parseColor("#FF1E90FF"));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(single_task);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onItemLongClick(single_task);
                return true;
            }
        });

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.checkBox.isChecked()){
                    checkedItems.add(tasks.get(position).getId());
                }
                if (!holder.checkBox.isChecked()){
                    checkedItems.remove(tasks.get(position).getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewTask, textViewDueDay;
        public CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTask = itemView.findViewById(R.id.textViewTask);
            textViewDueDay = itemView.findViewById(R.id.textViewDueDay);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }
}
