package com.example.benjamin.simpletodolist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private List<task> tasks;
    private Context context;

    public MyAdapter(List<task> tasks, Context context) {
        this.tasks = tasks;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        task single_task = tasks.get(position);
        holder.task.setText(single_task.getTask());
        holder.dueDay.setText(single_task.getDueDay());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView task, dueDay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            task = (TextView) itemView.findViewById(R.id.singleTask);
            dueDay = (TextView) itemView.findViewById(R.id.dueDay);
        }
    }
}
