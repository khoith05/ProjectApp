package com.example.android.projectmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.android.projectmanagement.database.DatabaseHelper;
import com.example.android.projectmanagement.database.EmployeeSQL;

public class EmployeeInfo extends AppCompatActivity {
    long id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar=findViewById(R.id.InfoEmployeeToolbar);
        setActionBar(toolbar);
        setContentView(R.layout.activity_employee_info);
        Bundle extras= getIntent().getExtras();
        if (extras ==null){
            return;
        }
        id=extras.getLong("id");
        DatabaseHelper db= new DatabaseHelper(this);
        EmployeeSQL employeeSQL= (EmployeeSQL) db.getEmpoloyee(id);
        ImageView avatar =findViewById(R.id.EmployeeInfoAvatar);
        Bitmap bitmap = BitmapFactory.decodeByteArray(employeeSQL.img,0 ,employeeSQL.img.length);
        avatar.setImageBitmap(bitmap);

        TextView textView;
        textView =findViewById(R.id.nameEI);
        textView.setText(employeeSQL.name);
        textView=findViewById(R.id.phoneEI);
        textView.setText(employeeSQL.phone);
        textView=findViewById(R.id.emailEI);
        textView.setText(employeeSQL.email);
        textView=findViewById(R.id.jobEI);
        textView.setText(employeeSQL.job);
        textView=findViewById(R.id.addressEI);
        textView.setText(employeeSQL.address);
        textView=findViewById(R.id.salaryEI);
        textView.setText(employeeSQL.salary);
        textView=findViewById(R.id.joinedContentEI);
        textView.setText("");
        textView=findViewById(R.id.joiningContentEI);
        textView.setText("");

        ImageView imageView=findViewById(R.id.BackEmployee);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}