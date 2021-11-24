package com.example.android.projectmanagement;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.example.android.projectmanagement.database.DatabaseHelper;
import com.example.android.projectmanagement.database.EmployeeSQL;
import com.example.android.projectmanagement.database.TaskSQL;
import com.google.android.material.chip.Chip;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class EditTask extends AppCompatActivity {
    public boolean edit;
    public long projectid;
    public long taskid;
    public TaskSQL taskSQL;
    public DatabaseHelper db;
    public TextInputEditText nameView;
    public TextInputEditText desView;
    public TextInputEditText startDate;
    public TextInputEditText endDate;
    public List<EmployeeSQL> employeeSQLList;
    public List<EmployeeSQL> chooseList;
    public List<EmployeeSQL> unchooseList;
    TextInputLayout endInputLayout;
    TextInputLayout startInputLayout;
    RecyclerView AddrecyclerView;
    RecyclerView ShowrecyclerView;
    SmallEmployeeListApdater2 showAdapter;
    Toolbar toolbar;
    ImageView check;
    ImageView cross;
    public EditTask editTask;
    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        context=this;
        Bundle extras=getIntent().getExtras();
        if(extras==null){
            return;
        }
        edit=extras.getBoolean("edit");
        projectid=extras.getLong("projectid");
        toolbar=findViewById(R.id.EditTaskToolbar);
        setSupportActionBar(toolbar);
        check=findViewById(R.id.checkTask);
        cross=findViewById(R.id.crossTask);
        Chip chip=findViewById(R.id.chipTask);
        nameView=findViewById(R.id.TaskNameinput);
        desView=findViewById(R.id.TaskDesciptionTaskEdit);
        startDate =findViewById(R.id.startDateTask);
        endDate=findViewById(R.id.endDateTask);
        startInputLayout=findViewById(R.id.startDateTaskLayout);
        endInputLayout=findViewById(R.id.endDateTaskLayout);
        db=new DatabaseHelper(this);
        editTask=this;
        context=this;
        ShowrecyclerView=findViewById(R.id.EmployeeTaskRecycleView);
        startInputLayout.setStartIconOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
//                MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
//                materialDateBuilder.setTitleText("Chọn ngày bắt đầu");
//                MaterialDatePicker materialDatePicker = materialDateBuilder.build();
//                materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
//                materialDatePicker.addOnPositiveButtonClickListener(
//                        new MaterialPickerOnPositiveButtonClickListener() {
//                            @Override
//                            public void onPositiveButtonClick(Object selection) {
//                                try {
//                                    String sdate =DateHelper.DateConvert(materialDatePicker.getHeaderText());
//                                    startDate.setText(sdate );
//                                } catch (ParseException e) {
//                                    e.printStackTrace();
//                                }
//
//                            }
//                        });
                DatePickerDialog datePickerDialog=new DatePickerDialog(context);
                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        startDate.setText(DateHelper.IntToDate(dayOfMonth,month,year));
                    }
                });
                datePickerDialog.show();

            }
        });
        endInputLayout.setStartIconOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
//                MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
//                materialDateBuilder.setTitleText("Chọn ngày kết thúc");
//                MaterialDatePicker materialDatePicker = materialDateBuilder.build();
//                materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
//                materialDatePicker.addOnPositiveButtonClickListener(
//                        new MaterialPickerOnPositiveButtonClickListener() {
//                            @Override
//                            public void onPositiveButtonClick(Object selection) {
//                                try {
//                                    String edate =DateHelper.DateConvert(materialDatePicker.getHeaderText());
//                                    endDate.setText( edate);
//                                } catch (ParseException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        });
                DatePickerDialog datePickerDialog=new DatePickerDialog(context);
                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        endDate.setText(DateHelper.IntToDate(dayOfMonth,month,year));
                    }
                });
                datePickerDialog.show();
            }
        });

        chip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView=inflater.inflate(R.layout.add_employee_task,null);
                int width= LinearLayout.LayoutParams.WRAP_CONTENT;
                int height= LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable=true;
                final PopupWindow popupWindow=new PopupWindow(popupView,width,height,focusable);
                popupWindow.showAtLocation(v, Gravity.CENTER,0,0);
                AddrecyclerView=popupView.findViewById(R.id.AddtaskProjectRecycleView);
                LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
                AddrecyclerView.setLayoutManager(layoutManager);
                SmallEmployeeListAdapter adapter=new SmallEmployeeListAdapter(editTask,unchooseList);
                AddrecyclerView.setAdapter(adapter);



            }
        });
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date startSelect;
                Date endSelect;
                try {
                    startSelect=DateHelper.StringToDate(startDate.getText().toString());
                    endSelect=DateHelper.StringToDate(endDate.getText().toString());
                    if (!startSelect.before(endSelect)){
                        endInputLayout.setHelperText("Ngày kết thúc phải sau ngày bắt đầu");
                        return;
                    }
                } catch (ParseException e) {
                    endInputLayout.setHelperText("Vui lòng chọn thời gian");
                    startInputLayout.setHelperText("Vui lòng chọn thời gian");
                    return;
                }

                String state=DateHelper.getState(startDate.getText().toString(),endDate.getText().toString());

                if(edit){
                    taskSQL.name=nameView.getText().toString();
                    taskSQL.start=startDate.getText().toString();
                    taskSQL.end=endDate.getText().toString();
                    taskSQL.desciption=desView.getText().toString();
                    taskSQL.state=state;
                    taskSQL.choose=chooseList;
                    db.updateTask(taskSQL);

                }else{
                    db.insertTask(nameView.getText().toString(),
                            startDate.getText().toString(),
                            endDate.getText().toString(),
                            desView.getText().toString(),
                            state,
                            projectid,
                            chooseList);
                }
                Intent intent=new Intent();
                intent.putExtra("success",true);
                setResult(Activity.RESULT_OK,intent);
                finish();

            }
        });
        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("success",false);
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });
        if (edit){
            taskid=extras.getLong("taskid");
            taskSQL=db.getTask(taskid);
            nameView.setText(taskSQL.name);
            desView.setText(taskSQL.desciption);
            startDate.setText(taskSQL.start);
            endDate.setText(taskSQL.end);
            chooseList=taskSQL.choose;
            unchooseList=taskSQL.getRemainEmployee(this);//change when project complete
        }else {
            taskSQL=new TaskSQL();
            chooseList=new ArrayList<>();
            unchooseList=new ArrayList<>();
            unchooseList.addAll(db.getAlLEmployee());//change when project complete


        }
        showAdapter=new SmallEmployeeListApdater2(editTask,chooseList);
        ShowrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ShowrecyclerView.setAdapter(showAdapter);
    }
    public void AddEmployee(EmployeeSQL employeeSQL){
        this.chooseList.add(employeeSQL);
        this.unchooseList.remove(employeeSQL);

    }
    public void RemoveEmployee(EmployeeSQL employeeSQL){
        this.unchooseList.add(employeeSQL);
        this.chooseList.remove(employeeSQL);
    }
    public void ListChanged(){
        showAdapter.notifyDataSetChanged();
    }
}