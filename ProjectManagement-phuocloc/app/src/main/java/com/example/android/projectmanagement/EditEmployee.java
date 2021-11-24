package com.example.android.projectmanagement;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.projectmanagement.database.DatabaseHelper;
import com.example.android.projectmanagement.database.EmployeeSQL;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class EditEmployee extends AppCompatActivity {
    boolean edit;
    long id;
    String title="Chỉnh sửa nhân viên";
    byte[] avatarByte;
    ImageView imageView;
    EmployeeSQL employeeSQL;
    public static final int PICKFILE_RESULT_CODE=123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_employee);

        Bundle extras= getIntent().getExtras();
        if (extras != null){
            this.edit=extras.getBoolean("edit");
        }
        imageView = findViewById(R.id.AvatarEmployeeEdit);
        ImageView back= (ImageView) findViewById(R.id.crossEmployee);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.putExtra("success",false);
                setResult(3,intent);
                finish();
            }
        });
        DatabaseHelper databaseHelper=new DatabaseHelper(this);
        ImageView check=(ImageView) findViewById(R.id.checkEmployee);

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextInputEditText textInputEditText;

                textInputEditText=(TextInputEditText) findViewById(R.id.EmployeeName);
                String name=textInputEditText.getText().toString();
                textInputEditText=(TextInputEditText) findViewById(R.id.EmployeePhone);
                String phone=textInputEditText.getText().toString();
                textInputEditText=(TextInputEditText) findViewById(R.id.EmployeeEmail);
                String email=textInputEditText.getText().toString();
                textInputEditText=(TextInputEditText) findViewById(R.id.EmployeeJob);
                String job=textInputEditText.getText().toString();
                textInputEditText=(TextInputEditText) findViewById(R.id.EmployeeAddress);
                String address=textInputEditText.getText().toString();
                textInputEditText=(TextInputEditText) findViewById(R.id.EmployeeSalary);
                String salary=textInputEditText.getText().toString();

                if (edit){
                    employeeSQL.name=name;
                    employeeSQL.phone=phone;
                    employeeSQL.email=email;
                    Log.d("email",email);
                    employeeSQL.job=job;
                    employeeSQL.address=address;
                    employeeSQL.salary=salary;
                    employeeSQL.img=avatarByte;
                    databaseHelper.updateEmployee(employeeSQL);
                    Intent intent= new Intent();
                    intent.putExtra("success",true);
                    setResult(Activity.RESULT_OK,intent);
                    finish();
                }else{
                    databaseHelper.insertEmployee(name,phone,email,job,address,salary,avatarByte);
                    Intent intent= new Intent();
                    intent.putExtra("success",true);
                    setResult(Activity.RESULT_OK,intent);
                    finish();
                }


            }
        });
        if (this.edit){
//            Bundle bundle= getIntent().getExtras();
//            if (bundle == null){
//                return;
//            }
            id= extras.getLong("id");
            employeeSQL=(EmployeeSQL) databaseHelper.getEmpoloyee(id);
            TextInputEditText textInputEditText;

            textInputEditText=(TextInputEditText) findViewById(R.id.EmployeeName);
            textInputEditText.setText(employeeSQL.name);
            textInputEditText=(TextInputEditText) findViewById(R.id.EmployeePhone);
            textInputEditText.setText(employeeSQL.phone);
            textInputEditText=(TextInputEditText) findViewById(R.id.EmployeeEmail);
            textInputEditText.setText(employeeSQL.email);
            textInputEditText=(TextInputEditText) findViewById(R.id.EmployeeJob);
            textInputEditText.setText(employeeSQL.job);
            textInputEditText=(TextInputEditText) findViewById(R.id.EmployeeAddress);
            textInputEditText.setText(employeeSQL.address);
            textInputEditText=(TextInputEditText) findViewById(R.id.EmployeeSalary);
            textInputEditText.setText(employeeSQL.salary);
            if(employeeSQL.img!=null){
                Bitmap bitmap= BitmapFactory.decodeByteArray(employeeSQL.img,0,employeeSQL.img.length);
                imageView.setImageBitmap(bitmap);
                avatarByte=employeeSQL.img;
            }


            this.title="Chỉnh sửa nhân viên";
        }else{
            this.title="Thêm nhân viên";
        }

        TextView textView= (TextView) findViewById(R.id.EditEmployeeTitle);
        textView.setText(this.title);


        Button uploadButton= findViewById(R.id.uploadImageEmployee);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("image/*");
                chooseFile = Intent.createChooser(chooseFile, "Chọn một ảnh");
                startActivityForResult(chooseFile, PICKFILE_RESULT_CODE);

            }
        });

        Button deleteButton= findViewById(R.id.deleteImageEmployee);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setImageBitmap(null);
                avatarByte=null;
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( requestCode==PICKFILE_RESULT_CODE){
            if(resultCode== Activity.RESULT_OK){
                Uri uri=data.getData();
                try{
                    InputStream imageStream = getContentResolver().openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                    imageView.setImageBitmap(bitmap);


                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG,0,byteArrayOutputStream);
                    avatarByte=byteArrayOutputStream.toByteArray();
                } catch (FileNotFoundException e){
                    e.printStackTrace();
                }




            }
        }
    }
}