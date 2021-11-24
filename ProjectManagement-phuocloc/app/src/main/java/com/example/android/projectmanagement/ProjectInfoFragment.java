package com.example.android.projectmanagement;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.projectmanagement.database.DatabaseHelper;
import com.example.android.projectmanagement.database.EmployeeSQL;
import com.example.android.projectmanagement.database.ProjectSQL;
import com.example.android.projectmanagement.database.TaskSQL;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;


public class ProjectInfoFragment extends Fragment {

    public long projectid;
    public static final int SUM_PROJECT_CODE = 123;
    public DatabaseHelper db;
    public List<TaskSQL> taskSQLList;
    public List<EmployeeSQL> employee;
    public ProjectSQL taskSQL;
    TextInputEditText nameView;
    TextInputEditText desView;
    TextInputEditText startView;
    TextInputEditText endView;
    private RecyclerView recyclerView;


    public ProjectInfoFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ProjectInfoFragment newInstance(long projectid) {
        ProjectInfoFragment fragment = new ProjectInfoFragment();
        fragment.projectid = projectid;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_project_info2, container, false);

        db=new DatabaseHelper(getContext());
        taskSQL=db.getProject(projectid);

        nameView=view.findViewById(R.id.ProjectInfoNameinput);
        nameView.setText(taskSQL.name);
        desView=view.findViewById(R.id.ProjectDesciptionProjectInfo);
        desView.setText(taskSQL.description);
        startView=view.findViewById(R.id.startDateProjectInfo);
        startView.setText(taskSQL.date_start);
        endView=view.findViewById(R.id.endDateProjectInfo);
        endView.setText(taskSQL.date_end);

        taskSQLList=new ArrayList<>();
        taskSQLList.addAll(db.getAllTask(projectid));
        employee = new ArrayList<>();
        for(int i =0;i<taskSQLList.size();i++){
            employee.removeAll(taskSQLList.get(i).choose);
            employee.addAll(taskSQLList.get(i).choose);
        }

        recyclerView=view.findViewById(R.id.ProjectInfoRecycleView);
        SmallEmployeeListApdater2 adapter=new SmallEmployeeListApdater2(null,employee,false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;

    }

}