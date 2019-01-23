package com.example.benjamin.simpletodolist;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    public static MyRoomDB DB;

    private List<Task_Table_Entity> tasks;
    private Context context;

    public MyAdapter(List<Task_Table_Entity> tasks, Context context) {
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Task_Table_Entity single_task = tasks.get(position);
        holder.textViewTask.setText(single_task.getTask());
        holder.textViewDueDay.setText(single_task.getDue_day());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(, Main2Activity.class);
            }
        });

    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        public TextView textViewTask, textViewDueDay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTask = itemView.findViewById(R.id.textViewTask);
            textViewDueDay = itemView.findViewById(R.id.textViewDueDay);

            itemView.setOnCreateContextMenuListener(this);
        }


        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, final ContextMenu.ContextMenuInfo contextMenuInfo) {
            final MenuItem Delete = contextMenu.add(Menu.NONE, 1, 1, R.string.delete);
            Delete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    boolean result;
                    switch (item.getItemId()){
                        case 1:
                            Toast.makeText(context, "Task Deleted", Toast.LENGTH_LONG).show();
                            int position = item.getOrder()-1;
                            final Task_Table_Entity current_task = tasks.get(position);
                            DB = Room.databaseBuilder(context, MyRoomDB.class, "tasksDB1").allowMainThreadQueries().build();
                            DB.tasksDao().delete_task(current_task);
                            result = true;
                            break;
                        default:
                            result = false;
                    }
                    return result;
                }
            });
        }

    }

}
