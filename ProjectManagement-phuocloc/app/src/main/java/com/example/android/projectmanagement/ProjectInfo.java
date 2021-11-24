package com.example.android.projectmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.android.projectmanagement.database.DatabaseHelper;
import com.example.android.projectmanagement.database.ProjectSQL;
import com.example.android.projectmanagement.database.TaskSQL;
import com.google.android.material.textfield.TextInputEditText;

public class ProjectInfo extends AppCompatActivity {
    public long projectid;
    public DatabaseHelper db;
    public ProjectSQL taskSQL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_info2);
        Bundle extras=getIntent().getExtras();
        if (extras==null){
            return;
        }
        projectid=extras.getLong("projectid");
        db=new DatabaseHelper(this);
        taskSQL=db.getProject(projectid);
        TextInputEditText nameView=findViewById(R.id.ProjectInfoNameinput);
        nameView.setText(taskSQL.name);
        TextInputEditText desView=findViewById(R.id.ProjectDesciptionProjectInfo);
        desView.setText(taskSQL.description);
        TextInputEditText startView=findViewById(R.id.startDateProjectInfo);
        startView.setText(taskSQL.date_start);
        TextInputEditText endView=findViewById(R.id.endDateProjectInfo);
        endView.setText(taskSQL.date_end);


        ImageView back=findViewById(R.id.BackTaskInfo);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}