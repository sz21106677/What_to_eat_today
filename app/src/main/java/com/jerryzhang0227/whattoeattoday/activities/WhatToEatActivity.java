package com.jerryzhang0227.whattoeattoday.activities;

import static com.jerryzhang0227.whattoeattoday.utils.WhatToEat.Pignese;
import static com.jerryzhang0227.whattoeattoday.utils.WhatToEat.eatWhat;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.jerryzhang0227.whattoeattoday.R;
import com.jerryzhang0227.whattoeattoday.utils.DatabaseHelper;

public class WhatToEatActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mButton;
    private ImageButton mButton2;
    private TextView mTextView;
    private static boolean isClicked = false;
    private static final Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //隐藏ActionBar
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        databasecheck();
    }

    private void databasecheck() {
        //打开数据库
        DatabaseHelper dbsqLiteOpenHelper = new DatabaseHelper(WhatToEatActivity.this, "food.db", null, 1);
        SQLiteDatabase db = dbsqLiteOpenHelper.getWritableDatabase();
        //查询数据库中数据总数
        Cursor cursor = db.rawQuery("select * from foodlist", null);
        int a = cursor.getCount();
        cursor.close();
        if (a>0){
            initView();
        }else {
            //如果没有数据则提示添加数据并跳转至添加页面
            AlertDialog.Builder builder = new AlertDialog.Builder(WhatToEatActivity.this);
            //创建AlertDialog对象
            AlertDialog alert = builder
                    .setTitle("系统提示：")
                    .setMessage("您还没有在数据库中添加数据，点击确定前往数据库编辑")
                    .setPositiveButton("确定", (dialog, which) -> {
                        startActivity(new Intent(WhatToEatActivity.this, DAOActivity.class));
                        finish();
                    }).create();
            alert.show();
        }
    }

    private void initView() {
        mButton = (Button) findViewById(R.id.mst_btn);
        mTextView = (TextView) findViewById(R.id.mst_txtv);
        mTextView.setText(Pignese());
        mButton.setOnClickListener(this);
        mButton2 = (ImageButton) findViewById(R.id.btn_back);
        mButton2.setOnClickListener(view -> finish());
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.mst_btn) {//判断按钮是否被按下，如没有则抽取结果，其他则重置
            if (!isClicked) {
                eat();
            } else {
                reset();
            }
        }
    }

    private void reset() {
        //处理完毕恢复按钮显示内容并执行“猪言猪语”
        mTextView.setText(Pignese());
        mButton.setText("今天吃什么");
        isClicked = false;
    }

    private void eat() {
        Toast.makeText(this,"正在数据库中抽取ing...",Toast.LENGTH_SHORT).show();
        mButton.setText("...");
        mButton.setClickable(false);
        mTextView.setText("我想想");
        //获取抽取结果并显示出来
        new Thread(() -> {
            String Str = eatWhat();
            try {
                //模拟耗时操作
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.post(() -> {
                mTextView.setText(Str);
                mButton.setText("行");
                isClicked = true;
                mButton.setClickable(true);
                Toast.makeText(WhatToEatActivity.this,"抽取完成！",Toast.LENGTH_SHORT).show();
            });
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this,"886",Toast.LENGTH_SHORT).show();
    }
}