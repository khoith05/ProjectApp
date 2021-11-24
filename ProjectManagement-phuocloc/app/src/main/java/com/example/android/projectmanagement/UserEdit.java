package com.example.android.projectmanagement;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import com.example.android.projectmanagement.database.DatabaseHelper;
import com.example.android.projectmanagement.database.UserSQL;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class UserEdit extends AppCompatActivity {
    private static final int GET_IMAGE_REQUEST=5;
    ImageView avatar;
    byte[] avatarByte;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);
        ImageView crossView=findViewById(R.id.crossUser);
        ImageView checkView=findViewById(R.id.checkUser);
        DatabaseHelper db= new DatabaseHelper(this);
        avatar=findViewById(R.id.AvatarUserEdit);
        Button upload=findViewById(R.id.uploadImageUserEdit);
        Button delete= findViewById(R.id.deleteImageUserEdit);
        UserSQL userdata=db.getUser();
        TextInputEditText txtView;
        txtView=findViewById(R.id.UserName);
        txtView.setText(userdata.name);
        txtView=findViewById(R.id.UserEmail);
        txtView.setText(userdata.email);
        txtView=findViewById(R.id.UserAddress);
        txtView.setText(userdata.address);
        txtView=findViewById(R.id.UserPhone);
        txtView.setText(userdata.phone);
        if (userdata.img!=null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(userdata.img, 0, userdata.img.length);
            avatar.setImageBitmap(bitmap);
            avatarByte=userdata.img;
        }
        crossView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        checkView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserSQL userSQL=new UserSQL();
                TextInputEditText textInputEditText;

                textInputEditText=findViewById(R.id.UserName);
                userSQL.name=textInputEditText.getText().toString();
                textInputEditText= findViewById(R.id.UserPhone);
                userSQL.phone=textInputEditText.getText().toString();
                textInputEditText=findViewById(R.id.UserEmail);
                userSQL.email=textInputEditText.getText().toString();
                textInputEditText=findViewById(R.id.UserAddress);
                userSQL.address=textInputEditText.getText().toString();

                userSQL.img=avatarByte;
                db.updateUser(userSQL);
                finish();

            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("image/*");
                chooseFile = Intent.createChooser(chooseFile, "Chọn một ảnh");
                startActivityForResult(chooseFile,GET_IMAGE_REQUEST);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avatar.setImageBitmap(null);
                avatarByte=null;
            }
        });
    }
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( requestCode==GET_IMAGE_REQUEST){
            if(resultCode== Activity.RESULT_OK){
                Uri uri=data.getData();
                try{
                    InputStream imageStream = getContentResolver().openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                    avatar.setImageBitmap(bitmap);


                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG,0,byteArrayOutputStream);
                    avatarByte=byteArrayOutputStream.toByteArray();
                    Log.d("dfgdf","dfgdfgdf");
                } catch (FileNotFoundException e){
                    e.printStackTrace();
                }




            }
        }
    }
}

