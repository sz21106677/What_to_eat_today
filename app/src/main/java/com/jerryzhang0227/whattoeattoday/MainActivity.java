package com.jerryzhang0227.whattoeattoday;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mButton;
    private TextView mTextView;
    private boolean isClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mButton = (Button) findViewById(R.id.button);
        mTextView = (TextView) findViewById(R.id.textView);
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (!isClicked) {
            eat();
        }else if (isClicked) {
            reset();
        }
    }

    private void reset() {
        mTextView.setText("");
        mButton.setText("朱萌昊今天吃什么");
        isClicked = false;
    }

    private void eat() {
        mTextView.setText("朱萌昊今天吃" + WhatToEat.Boom());
        mButton.setText("重置");
        isClicked = true;
    }
}