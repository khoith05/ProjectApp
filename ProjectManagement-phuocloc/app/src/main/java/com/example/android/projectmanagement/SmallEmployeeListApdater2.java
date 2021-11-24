package com.example.android.projectmanagement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.projectmanagement.database.EmployeeSQL;
import com.google.android.material.chip.Chip;

import java.util.List;

public class SmallEmployeeListApdater2 extends RecyclerView.Adapter<SmallEmployeeListApdater2.EmployeeViewHolder2>{
    public List<EmployeeSQL> employeeSQLList;
    public EditTask editTask;
    public boolean clickable=true;
    public SmallEmployeeListApdater2(EditTask editTask,List<EmployeeSQL> employeeSQLList){
        this.editTask=editTask;
        this.employeeSQLList=employeeSQLList;
    }

    public SmallEmployeeListApdater2( EditTask editTask,List<EmployeeSQL> employeeSQLList, boolean clickable) {
        this.employeeSQLList = employeeSQLList;
        this.editTask = editTask;
        this.clickable = clickable;
    }

    @NonNull
    @Override
    public EmployeeViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.small_employee_task_list_row,parent,false);
        return new EmployeeViewHolder2(itemView);
    }

    @Override
    public int getItemCount() {
        return employeeSQLList==null ? 0: employeeSQLList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder2 holder, int position) {
        final EmployeeSQL employeeSQL=employeeSQLList.get(position);
        holder.chip.setText(employeeSQL.name);
        if(clickable){
            holder.chip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editTask.RemoveEmployee(employeeSQL);
                    employeeSQLList.remove(employeeSQL);
                    notifyDataSetChanged();
                }
            });
        }

    }

    public class EmployeeViewHolder2 extends RecyclerView.ViewHolder{
        public Chip chip;
        public LinearLayout layout;
        public EmployeeViewHolder2(View v){
            super(v);
            this.chip=v.findViewById(R.id.EmployeeNameTask);
            this.layout=v.findViewById(R.id.SmallEployeeLayout);
        }
    }
}
