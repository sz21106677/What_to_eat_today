package com.jerryzhang0227.whattoeattoday.activities;

import static java.lang.Integer.parseInt;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.jerryzhang0227.whattoeattoday.R;
import com.jerryzhang0227.whattoeattoday.utils.DatabaseHelper;

public class DAOADDActivity extends AppCompatActivity {

    private EditText mEtWeight;
    private EditText mEtFoodname;
    private Button mBtnAddsubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daoaddactivity);
        initView();
    }

    private void initView() {
        mEtWeight = (EditText) findViewById(R.id.et_weight);
        mEtFoodname = (EditText) findViewById(R.id.et_foodname);
        mBtnAddsubmit = (Button) findViewById(R.id.btn_addsubmit);
        mBtnAddsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取输入框的值
                String foodname = mEtFoodname.getText().toString().trim();
                String foodweight = mEtWeight.getText().toString().trim();
                //判断是否为空
                if (foodweight.isEmpty()|foodname.isEmpty()) {
                    Toast.makeText(DAOADDActivity.this,"请检查输入",Toast.LENGTH_SHORT).show();
                }else {
                    //打开数据库并插入数据
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            DatabaseHelper dbsqLiteOpenHelper = new DatabaseHelper(DAOADDActivity.this, "food.db", null, 1);
                            SQLiteDatabase db = dbsqLiteOpenHelper.getWritableDatabase();
                            ContentValues values = new ContentValues();
                            values.put("name",foodname);
                            values.put("weight",foodweight);
                            db.insert("foodlist", null, values);
                            finish();
                        }
                    }).start();
                }
            }
        });
    }
}