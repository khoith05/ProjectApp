package com.example.android.projectmanagement;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.projectmanagement.database.DatabaseHelper;
import com.example.android.projectmanagement.database.ProjectSQL;

import java.util.List;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder> {

    private Context context;
    private List<ProjectSQL> employeeSQLList;

    public ProjectAdapter(Context context,List<ProjectSQL> employeeSQLList){
        this.context=context;
        this.employeeSQLList=employeeSQLList;
    }

    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.project_list_row,parent,false);
        return new ProjectViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, int position) {
        final ProjectSQL employeeSQL=employeeSQLList.get(position);

        holder.name.setText(employeeSQL.name);
        holder.des.setText(employeeSQL.description);
        holder.date_end.setText(employeeSQL.date_end);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context,Project_info.class);
                intent.putExtra("id",employeeSQL.id);
                context.startActivity(intent);
            }
        });
        holder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup= new PopupMenu(v.getContext(), v);
                popup.inflate(R.menu.employee_options_menu);
                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.employeeModified:
                                Intent intent =new Intent(context,EditProject.class);
                                intent.putExtra("edit",true);
                                Bundle bundle=new Bundle();
                                bundle.putSerializable("data",employeeSQL);
                                intent.putExtras(bundle);
                                context.startActivity(intent);

                                return true;
                            case R.id.employeeDelete:
                                new AlertDialog.Builder(context)
                                        .setMessage("Bạn có chắc muốn xóa "+employeeSQL.name)
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                DatabaseHelper databaseHelper=new DatabaseHelper(context);
                                                databaseHelper.deleteProject(employeeSQL.id);
                                                employeeSQLList.clear();
                                                employeeSQLList.addAll(databaseHelper.getAlLProject());
                                                notifyDataSetChanged();
                                            }
                                        })
                                        .setNegativeButton("No", null)
                                        .show();
                                return true;
                            default:
                                return false;
                        }

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return employeeSQLList==null ? 0:employeeSQLList.size();
    }

    public class ProjectViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView des;
        TextView date_end;
        public RelativeLayout relativeLayout;
        ImageView menu;
        public ProjectViewHolder(View view){
            super(view);
            this.name=view.findViewById(R.id.name_project);
            this.des=view.findViewById(R.id.description_project);
            this.date_end=view.findViewById((R.id.date_end_project));
            this.relativeLayout=view.findViewById(R.id.projectLayout);
            this.menu=view.findViewById(R.id.kebabProject);
        }
    }
}
