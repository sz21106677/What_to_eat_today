package com.jerryzhang0227.whattoeattoday;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mButton;
    private TextView mTextView;
    private boolean isClicked;
    private Button mButton2;
    public static int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //第一药：以读写权限为例
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }

    private void initView() {
        mButton = (Button) findViewById(R.id.button);
        mTextView = (TextView) findViewById(R.id.textView);
        mTextView.setText(ReadFile.ReadRecord());
        mButton.setOnClickListener(this);
        mButton2 = (Button) findViewById(R.id.button2);
        mButton2.setOnClickListener(this);
        mButton2.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.button && !isClicked) {
            eat();
        } else if (id == R.id.button && isClicked) {
            reset();
        } else if (id == R.id.button2) {
            eat();
        }
    }

    private void reset() {
        mTextView.setText("");
        mButton.setText("朱萌昊今天吃什么");
        isClicked = false;
        mButton2.setVisibility(View.GONE);
        count = 0;
        WhatToEat.Pignese();
        mTextView.setText(ReadFile.ReadRecord());
    }

    private void eat() {
        if (count < 2){
            String firstStr = WhatToEat.Boom();
            mTextView.setText(firstStr);
            mButton.setText("行");
            isClicked = true;
            mButton2.setVisibility(View.VISIBLE);
            count ++;
        }else{
            mTextView.setText("吃屎吧");
            mButton.setText("555");
            isClicked = true;
            mButton2.setVisibility(View.GONE);
            count = 0;
        }
    }
}