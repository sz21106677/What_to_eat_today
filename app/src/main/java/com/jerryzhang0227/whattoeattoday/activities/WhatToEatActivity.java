package com.jerryzhang0227.whattoeattoday.activities;

import static com.jerryzhang0227.whattoeattoday.utils.WhatToEat.Pignese;
import static com.jerryzhang0227.whattoeattoday.utils.WhatToEat.eatWhat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.jerryzhang0227.whattoeattoday.R;
import com.jerryzhang0227.whattoeattoday.utils.DatabaseHelper;
import com.jerryzhang0227.whattoeattoday.utils.ReadFile;

public class WhatToEatActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mButton;
    private TextView mTextView;
    private static boolean isClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //隐藏ActionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databasecheck();
    }

    private void databasecheck() {
        //打开数据库
        DatabaseHelper dbsqLiteOpenHelper = new DatabaseHelper(WhatToEatActivity.this, "food.db", null, 1);
        SQLiteDatabase db = dbsqLiteOpenHelper.getWritableDatabase();
        //查询数据库中数据总数
        Cursor cursor = db.rawQuery("select * from foodlist", null);
        int a = cursor.getCount();
        if (a>0){
            initView();
        }else {
            //如果没有数据则提示添加数据并跳转至添加页面
            AlertDialog.Builder builder = new AlertDialog.Builder(WhatToEatActivity.this);
            //创建AlertDialog对象
            AlertDialog alert = builder
                    .setTitle("系统提示：")
                    .setMessage("您还没有在数据库中添加数据，点击确定前往数据库编辑")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(WhatToEatActivity.this, DAOActivity.class));
                            finish();
                        }
                    }).create();
            alert.show();
        }
    }

    private void initView() {
        mButton = (Button) findViewById(R.id.mst_btn);
        mTextView = (TextView) findViewById(R.id.mst_txtv);
        mTextView.setText(Pignese());
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.mst_btn:
                //判断按钮是否被按下，如没有则抽取结果，其他则重置
                if (!isClicked) {
                    eat();
                } else {
                    reset();
                }
                break;
        }
    }

    private void reset() {
        //处理完毕恢复按钮显示内容并执行“猪言猪语”
        mTextView.setText(Pignese());
        mButton.setText("今天吃什么");
        isClicked = false;
    }

    private void eat() {
        //获取抽取结果并显示出来
        String Str = eatWhat();
        mTextView.setText(Str);
        mButton.setText("行");
        isClicked = true;
    }
}