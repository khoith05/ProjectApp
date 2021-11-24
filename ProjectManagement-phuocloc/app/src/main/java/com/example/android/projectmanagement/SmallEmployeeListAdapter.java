package com.example.android.projectmanagement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.projectmanagement.database.EmployeeSQL;
import com.google.android.material.chip.Chip;

import java.util.List;

public class SmallEmployeeListAdapter extends RecyclerView.Adapter<SmallEmployeeListAdapter.EmployeeViewHolder> {
    public List<EmployeeSQL> employeeSQLList;
    public EditTask editTask;
    public SmallEmployeeListAdapter(EditTask editTask,List<EmployeeSQL> employeeSQLList){
        this.editTask=editTask;
        this.employeeSQLList=employeeSQLList;
    }
    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.small_employee_task_list_row,parent,false);
        return new EmployeeViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return employeeSQLList==null ? 0: employeeSQLList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        EmployeeSQL employeeSQL=employeeSQLList.get(position);
        holder.chip.setText(employeeSQL.name);
        holder.chip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTask.AddEmployee(employeeSQL);
                employeeSQLList.remove(employeeSQL);
                notifyDataSetChanged();
                editTask.ListChanged();
            }
        });
    }

    public class EmployeeViewHolder extends RecyclerView.ViewHolder{
        public Chip chip;
        public LinearLayout layout;
        public EmployeeViewHolder(View v){
            super(v);
            this.chip=v.findViewById(R.id.EmployeeNameTask);
            this.layout=v.findViewById(R.id.SmallEployeeLayout);
        }
    }
}
