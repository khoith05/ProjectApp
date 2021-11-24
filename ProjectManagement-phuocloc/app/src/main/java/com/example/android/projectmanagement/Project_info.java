package com.example.android.projectmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;


public class Project_info extends AppCompatActivity {
    TaskFragment taskFragment;
    SummaryFragment summaryFragment;
    AnalyticsFragment analyticsFragment;
    ProjectInfoFragment projectInfoFragment;
    public long projecid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_info);
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        projecid = extras.getLong("id");
        Toolbar toolbar = (Toolbar) findViewById(R.id.ProjectInfoToolbar);
        setSupportActionBar(toolbar);

        ImageView kebab = findViewById(R.id.kebabProjectInfo);
        kebab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                popupMenu.inflate(R.menu.bottom_drawer);
                Menu menu = popupMenu.getMenu();
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.ProjectDetail:
                                projectInfoFragment = ProjectInfoFragment.newInstance(projecid);
                                getSupportFragmentManager().beginTransaction().replace(R.id.Projectcontainer, projectInfoFragment).commit();
                                return true;
                            case R.id.ProjectTask:
                                taskFragment = TaskFragment.newInstance(projecid);
                                getSupportFragmentManager().beginTransaction().replace(R.id.Projectcontainer, taskFragment).commit();
                                return true;
                            case R.id.ProjectGantt:
                                analyticsFragment = AnalyticsFragment.newInstance(projecid);
                                getSupportFragmentManager().beginTransaction().replace(R.id.Projectcontainer, analyticsFragment).commit();
                                return true;
                            case R.id.ProjectSum:
                                summaryFragment = SummaryFragment.newInstance(projecid);
                                getSupportFragmentManager().beginTransaction().replace(R.id.Projectcontainer, summaryFragment).commit();
                                return true;
                            default:
                                return false;
                        }
                    }
                });

            }
        });
        taskFragment = TaskFragment.newInstance(projecid);
        getSupportFragmentManager().beginTransaction().replace(R.id.Projectcontainer, taskFragment).commit();
        ImageView back = findViewById(R.id.backProjectInfo);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}