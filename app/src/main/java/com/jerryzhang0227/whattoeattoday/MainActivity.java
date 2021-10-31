package com.jerryzhang0227.whattoeattoday;

import static com.jerryzhang0227.whattoeattoday.WhatToEat.Pignese;
import static com.jerryzhang0227.whattoeattoday.WhatToEat.codeResolve;
import static com.jerryzhang0227.whattoeattoday.WhatToEat.random;
import static com.jerryzhang0227.whattoeattoday.WhatToEat.takeOut;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mButton;
    private Button mButton2;
    private TextView mTextView;
    static boolean isClicked;
    static boolean finished;
    static int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
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
        switch (id) {
            case R.id.button:
                count = 0;
                if (!isClicked && !finished) {
                    eat();
                } else if (isClicked && take){
                    eatTakeOut();
                } else {
                    reset();
                }
                break;
            case R.id.button2:
                count ++;
                eat();
                break;

        }
    }


    boolean take;

    private void eatTakeOut() {
        mTextView.setText(takeOut());
        mButton.setText("确实");
        mButton2.setVisibility(View.GONE);
        count = 0;
        isClicked = !isClicked;
        finished = true;
    }

    boolean isReset;

    private void reset() {
        mTextView.setText("");
        mButton.setText("朱萌昊今天吃什么");
        mButton2.setVisibility(View.GONE);
        count = 0;
        Pignese();
        mTextView.setText(ReadFile.ReadRecord());
        isClicked = false;
        take = false;
        finished = false;
    }

    private void eat() {
        String Str;
        Str = eatWhat();
        Log.i("Str传递值:", Str);
        if (count < 2) {
            if (Str == "今天点外卖" || Str == "那今天必须点外卖") {
                mTextView.setText(Str);;
                mButton.setText("点什么");
                mButton2.setVisibility(View.VISIBLE);
                mButton2.setText("不行");
                take = true;
                isClicked = true;
            } else {
                take = false;
                mTextView.setText(Str);
                mButton.setText("行");
                mButton2.setVisibility(View.VISIBLE);
                isClicked = true;
                finished = true;
            }
        } else {
            take = false;
            mTextView.setText("吃屎吧");
            mButton.setText("对不起,朱哥");
            mButton2.setVisibility(View.GONE);
            isClicked = true;
            finished = true;
        }
    }

    static int eatCode;

    public static String eatWhat() {
        String eatStr = null;
        int result = random.nextInt(100);
        if (0 < result && result <= 31) {
            eatCode = 901;
        }else if (31 < result && result <= 62) {
            eatCode = 902;
        }else if (62 < result && result <= 93) {
            eatCode = 903;
        }else if (93 < result && result <= 99) {
            eatCode = 904;
        }else {
            eatCode = 905;
        }
        eatStr = codeResolve(eatCode);
        Log.i("eatStr传递值:", eatStr);
        return eatStr;
    }

}