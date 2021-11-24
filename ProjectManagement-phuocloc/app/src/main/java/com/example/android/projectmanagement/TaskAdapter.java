package com.example.android.projectmanagement;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.projectmanagement.database.DatabaseHelper;
import com.example.android.projectmanagement.database.TaskSQL;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    public List<TaskSQL> taskSQLList;
    public Context context;

    public TaskAdapter(Context context,List<TaskSQL> taskSQLList) {
        this.context=context;
        this.taskSQLList = taskSQLList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_row,parent,false);
        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        TaskSQL taskSQL=(TaskSQL) taskSQLList.get(position);
        holder.title.setText(taskSQL.name);
        holder.des.setText(taskSQL.desciption);
        holder.taskkebab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup= new PopupMenu(v.getContext(), v);
                popup.inflate(R.menu.task_menu);
                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        DatabaseHelper db=new DatabaseHelper(context);
                        switch (item.getItemId()){
                            case R.id.TaskModified:
                                Intent intent =new Intent(context,EditTask.class);
                                intent.putExtra("edit",true);
                                intent.putExtra("taskid",taskSQL.id);
                                intent.putExtra("projectid",taskSQL.projectid);
                                context.startActivity(intent);
                                return true;
                            case R.id.TaskComplete:
                                taskSQL.state=TaskSQL.complete;
                                taskSQL.end=DateHelper.getCurrent();
                                db.updateTask(taskSQL);
                                notifyDataSetChanged();
                                return true;
                            case R.id.TaskDelete:
                                db.deleteTask(taskSQL.id);
                                taskSQLList.remove(taskSQL);
                                notifyDataSetChanged();
                                return true;
                            default:
                                return false;
                        }
                    }
                });
            }
        });
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, TaskInfo.class);
                intent.putExtra("taskid",taskSQL.id);
                context.startActivity(intent);
            }
        });


        if(taskSQL.state.equals(TaskSQL.complete)){
            holder.layout.setBackground(context.getDrawable(R.drawable.boder1));
            holder.remain.setText("Đã hoàn thành");
        }else {
            String state=DateHelper.getState(taskSQL.start,taskSQL.end);
            if(!taskSQL.state.equals(state)){
                taskSQL.state=state;
                DatabaseHelper db=new DatabaseHelper(context);
                db.updateTask(taskSQL);
            }
            long day;
            String current=DateHelper.getCurrent();
            if(taskSQL.state.equals(TaskSQL.late)){
                holder.layout.setBackground(context.getDrawable(R.drawable.boder2));
                day=DateHelper.getDaysBetweenDates(taskSQL.end,current);
                holder.remain.setText("Đã trễ "+String.valueOf(day)+" ngày");
            }else if(taskSQL.state.equals(TaskSQL.processing)){
                holder.layout.setBackground(context.getDrawable(R.drawable.boder3));
                day=DateHelper.getDaysBetweenDates(current,taskSQL.end);
                holder.remain.setText("Còn lại "+String.valueOf(day)+" ngày");
            }else if (taskSQL.state.equals(TaskSQL.notstart)){
                holder.layout.setBackground(context.getDrawable(R.drawable.boder4));
                day=DateHelper.getDaysBetweenDates(current,taskSQL.end);
                holder.remain.setText("Còn "+String.valueOf(day)+" ngày trước khi bắt đầu");
            }
        }
    }

    @Override
    public int getItemCount() {
        return taskSQLList==null ? 0: taskSQLList.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder{
        public RelativeLayout layout;
        ImageView taskkebab;
        TextView title;
        TextView des;
        TextView remain;
        public TaskViewHolder(View view){
            super(view);
            this.taskkebab=view.findViewById(R.id.TaskKebab);
            this.title=view.findViewById(R.id.TaskTitle);
            this.des=view.findViewById(R.id.TaskDesciption);
            this.layout=view.findViewById(R.id.TaskLayout);
            this.remain=view.findViewById(R.id.remainDayTask);

        }
    }
}
