package com.example.android.projectmanagement;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.projectmanagement.database.DatabaseHelper;
import com.example.android.projectmanagement.database.EmployeeSQL;
import com.example.android.projectmanagement.database.ProjectSQL;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Date;

public class EditProject extends AppCompatActivity {
    boolean edit;
    String title="Chỉnh sửa nhân viên";
    ProjectSQL employeeSQL;
    public Context context;
    TextInputEditText startDate ;
    TextInputEditText endDate ;
    public static final int PICKFILE_RESULT_CODE=123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_project);
        context=this;
        Bundle extras= getIntent().getExtras();
        if (extras != null){
            this.edit=extras.getBoolean("edit");
        }
        startDate = findViewById(R.id.startDateProject);
        endDate = findViewById(R.id.endDateProject);
        ImageView back= (ImageView) findViewById(R.id.crossProject);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.putExtra("success",false);
                setResult(3,intent);
                finish();
            }
        });

        TextInputLayout startInputLayout=findViewById(R.id.startDateProjectLayout);;
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

        TextInputLayout endInputLayout=findViewById(R.id.endDateProjectLayout);;
        endInputLayout.setStartIconOnClickListener(new View.OnClickListener() {

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
                        endDate.setText(DateHelper.IntToDate(dayOfMonth,month,year));
                    }
                });
                datePickerDialog.show();

            }
        });


        DatabaseHelper databaseHelper=new DatabaseHelper(this);
        ImageView check=(ImageView) findViewById(R.id.checkProject);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date startSelect;
                Date endSelect;
                TextInputEditText textInputEditText;

                textInputEditText=(TextInputEditText) findViewById(R.id.ProjectNameInput);
                String name=textInputEditText.getText().toString();
                textInputEditText=(TextInputEditText) findViewById(R.id.ProjectDesciption);
                String phone=textInputEditText.getText().toString();
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

                if (edit){
                    employeeSQL.name=name;
                    employeeSQL.description=phone;
                    employeeSQL.date_start=startDate.getText().toString();
                    employeeSQL.date_end=endDate.getText().toString();
                    databaseHelper.updateProject(employeeSQL);
                    Intent intent= new Intent();
                    intent.putExtra("success",true);
                    setResult(Activity.RESULT_OK,intent);
                    finish();
                }else{
                    databaseHelper.insertProject(name,phone,startDate.getText().toString(),endDate.getText().toString(),"");
                    Intent intent= new Intent();
                    intent.putExtra("success",true);
                    setResult(Activity.RESULT_OK,intent);
                    finish();
                }


            }
        });
        if (this.edit){
            Bundle bundle= getIntent().getExtras();
            if (bundle == null){
                return;
            }
            employeeSQL=(ProjectSQL) bundle.get("data");
            TextInputEditText textInputEditText;

            textInputEditText=(TextInputEditText) findViewById(R.id.ProjectNameInput);
            textInputEditText.setText(employeeSQL.name);
            textInputEditText=(TextInputEditText) findViewById(R.id.ProjectDesciption);
            textInputEditText.setText(employeeSQL.description);
            textInputEditText=(TextInputEditText) findViewById(R.id.startDateProject);
            textInputEditText.setText(employeeSQL.date_start);
            textInputEditText=(TextInputEditText) findViewById(R.id.endDateProject);
            textInputEditText.setText(employeeSQL.date_end);

            this.title="Chỉnh sửa dự án";
        }else{
            this.title="Thêm dự án";
        }

        TextView textView= (TextView) findViewById(R.id.EditProjectTitle);
        textView.setText(this.title);


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( requestCode==PICKFILE_RESULT_CODE){
            if(resultCode== Activity.RESULT_OK){
//                Uri uri=data.getData();
//                try{
//                    InputStream imageStream = getContentResolver().openInputStream(uri);
//                    Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
//                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                    bitmap.compress(Bitmap.CompressFormat.PNG,0,byteArrayOutputStream);
//                    Eimage=byteArrayOutputStream.toByteArray();
//                    imageView.setImageBitmap(bitmap);
//                } catch (FileNotFoundException e){
//                    e.printStackTrace();
//                }




            }
        }
    }
}