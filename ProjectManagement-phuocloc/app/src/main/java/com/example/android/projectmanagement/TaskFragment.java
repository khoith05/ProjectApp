package com.example.android.projectmanagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.projectmanagement.database.DatabaseHelper;
import com.example.android.projectmanagement.database.TaskSQL;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;


public class TaskFragment extends Fragment {

    public long projectid;
    public static final int EDIT_TASK_CODE=7;
    public TaskAdapter adapter;
    public DatabaseHelper db;
    public List<TaskSQL> taskSQLList;
    public TaskFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static TaskFragment newInstance(long projectid) {
        TaskFragment fragment = new TaskFragment();
        fragment.projectid=projectid;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_task, container, false);
        FloatingActionButton fabProject= view.findViewById(R.id.fabProjectTask);
        fabProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),EditTask.class);
                intent.putExtra("edit",false);
                intent.putExtra("projectid",projectid);
                getActivity().startActivityForResult(intent,EDIT_TASK_CODE);
            }
        });
        RecyclerView recyclerView=view.findViewById(R.id.ProjectTaskRecyclerView);
        db=new DatabaseHelper(getContext());
        taskSQLList=new ArrayList<>();
        taskSQLList.addAll(db.getAllTask(projectid));
        adapter=new TaskAdapter(getContext(),taskSQLList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( requestCode==EDIT_TASK_CODE){
            if(resultCode== Activity.RESULT_OK){
                if (data.getBooleanExtra("success",true)){
                    taskSQLList.clear();
                    taskSQLList.addAll(db.getAllTask(projectid));
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        taskSQLList.clear();
        taskSQLList.addAll(db.getAllTask(projectid));
        adapter.notifyDataSetChanged();
    }
}