package com.example.android.projectmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    EditText edtEmail, edtPass;
    Button btnLogin;
    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button) findViewById(R.id.buttonLogin);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPass = (EditText) findViewById(R.id.edtPassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edtEmail.getText().toString().trim();
                password = edtPass.getText().toString().trim();
                if (email.length() != 0 && password.length() != 0) {
                    if (email.equals("root@gmail.com") && password.equals("123")) {
                        Toast.makeText(Login.this,"Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.anim_enter,R.anim.anim_out);
                    }
                    else{
                        Toast.makeText(Login.this,"Đăng nhập thất bại, xin kiểm tra lại email và password", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(Login.this,"Bạn chưa điền thông tin đăng nhập", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}