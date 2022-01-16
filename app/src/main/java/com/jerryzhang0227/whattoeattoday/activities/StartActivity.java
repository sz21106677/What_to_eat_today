package com.jerryzhang0227.whattoeattoday.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.jerryzhang0227.whattoeattoday.R;

import java.net.HttpURLConnection;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mWhattoeatBtn;
    private Button mMathBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_start);
        initView();
    }

    private void initView() {
        mWhattoeatBtn = (Button) findViewById(R.id.whattoeat_btn);
        mWhattoeatBtn.setOnClickListener(this);
        mMathBtn = (Button) findViewById(R.id.math_btn);
        mMathBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        //根据按钮实现跳转页面
        switch (id) {
            case R.id.whattoeat_btn:
                startActivity(new Intent(StartActivity.this, WhatToEatActivity.class));
                break;
            case R.id.math_btn:
                startActivity(new Intent(StartActivity.this, DAOActivity.class));
                break;
            default:
                break;
        }
    }
}