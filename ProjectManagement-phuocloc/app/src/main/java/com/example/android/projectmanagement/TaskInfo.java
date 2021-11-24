package com.example.android.projectmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.android.projectmanagement.database.DatabaseHelper;
import com.example.android.projectmanagement.database.TaskSQL;
import com.google.android.material.textfield.TextInputEditText;

public class TaskInfo extends AppCompatActivity {
    public long taskid;
    public DatabaseHelper db;
    public TaskSQL taskSQL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_info);
        Bundle extras=getIntent().getExtras();
        if (extras==null){
            return;
        }
        taskid=extras.getLong("taskid");
        db=new DatabaseHelper(this);
        taskSQL=db.getTask(taskid);
        TextInputEditText nameView=findViewById(R.id.TaskInfoNameinput);
        nameView.setText(taskSQL.name);
        TextInputEditText desView=findViewById(R.id.TaskDesciptionTaskInfo);
        desView.setText(taskSQL.desciption);
        TextInputEditText startView=findViewById(R.id.startDateTaskInfo);
        startView.setText(taskSQL.start);
        TextInputEditText endView=findViewById(R.id.endDateTaskInfo);
        endView.setText(taskSQL.end);

        RecyclerView recyclerView=findViewById(R.id.TaskInfoRecycleView);
        SmallEmployeeListApdater2 adapter=new SmallEmployeeListApdater2(null,taskSQL.choose,false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        ImageView back=findViewById(R.id.BackTaskInfo);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}