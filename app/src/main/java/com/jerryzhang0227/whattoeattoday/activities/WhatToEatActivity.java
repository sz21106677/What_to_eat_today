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
    static boolean isClicked;
    static boolean finished;
    public static int count = 0;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databasecheck();
    }

    private void databasecheck() {
        DatabaseHelper dbsqLiteOpenHelper = new DatabaseHelper(WhatToEatActivity.this, "food.db", null, 1);
        SQLiteDatabase db = dbsqLiteOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from foodlist", null);
        int a = cursor.getCount();
        if (a>0){
            initView();
        }else {
            AlertDialog.Builder builder = new AlertDialog.Builder(WhatToEatActivity.this);
            AlertDialog alert = builder
                    .setTitle("系统提示：")
                    .setMessage("您还没有在数据库中添加数据，点击确定前往数据库编辑")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(WhatToEatActivity.this, DAOActivity.class));
                            finish();
                        }
                    }).create();             //创建AlertDialog对象
            alert.show();//显示对话框
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
                if (!isClicked && !finished) {
                    eat();
                } else {
                    reset();
                }
                break;
        }
    }

    private void reset() {
        mTextView.setText(Pignese());
        mButton.setText("今天吃什么");
        count = 0;
        isClicked = false;
        finished = false;
    }

    private void eat() {
        String Str = eatWhat();
        mTextView.setText(Str);
        mButton.setText("行");
        isClicked = true;
        finished = true;
    }
}