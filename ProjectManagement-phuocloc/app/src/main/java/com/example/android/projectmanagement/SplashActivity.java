package com.example.android.projectmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    private static final String KEY_FIRST_INSTALL="KEY_FIRST_INSTALL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        MySharePreference mySharePreference=new MySharePreference(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mySharePreference.getBooleanValue(KEY_FIRST_INSTALL)){
                    //Login
                    startActivity(Login.class);
                }
                else{
                    //Onboarding
                    startActivity(OnboardingActivity.class);
                    mySharePreference.putBooleanValue(KEY_FIRST_INSTALL,true);
                }
            }
        },1500);
    }

    private void startActivity(Class<?> cls){
        Intent intent=new Intent(this,cls);
        startActivity(intent);
        finish();
    }
}