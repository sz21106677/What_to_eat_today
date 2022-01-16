package com.jerryzhang0227.whattoeattoday.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.jerryzhang0227.whattoeattoday.R;
import com.jerryzhang0227.whattoeattoday.adapter.FoodAdapter;
import com.jerryzhang0227.whattoeattoday.model.Food;
import com.jerryzhang0227.whattoeattoday.utils.DatabaseHelper;

import java.util.LinkedList;
import java.util.List;

public class DAOActivity extends AppCompatActivity {

    private List<Food> mData = null;
    private Context mContext = DAOActivity.this;
    private FoodAdapter mAdapter = null;
    private ListView list_food;
    private ImageButton mIbtnAddsql;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dao);
        iniData();
        initView();
        Toast.makeText(mContext,"长按可删除列表项",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume(){
        super.onResume();
        iniData();
    }

    private void iniData() {
        list_food = (ListView) findViewById(R.id.lsv);
        //新建一个链表
        mData = new LinkedList<Food>();
        //打开数据库并遍历查询信息
        DatabaseHelper dbsqLiteOpenHelper = new DatabaseHelper(mContext, "food.db", null, 1);
        SQLiteDatabase db = dbsqLiteOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select name,weight from foodlist", null);
        //根据遍历结果将数据插入链表
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String foodname = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") int weight = cursor.getInt(cursor.getColumnIndex("weight"));
            mData.add(new Food(foodname, weight));
        }
        //新建适配器
        mAdapter = new FoodAdapter((LinkedList<Food>) mData, mContext);
        list_food.setAdapter(mAdapter);
        //长按删除列表项
        list_food.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //根据i删除列表所在位置，并在数据库中一并删除记录
                String defood = mData.get(i).getFoodName();
                db.delete("foodlist","name=?",new String[]{defood});
                mData.remove(i);
                //及时刷新列表
                mAdapter.notifyDataSetChanged();
                Toast.makeText(mContext, "删除了"+defood, Toast.LENGTH_SHORT).show();
                return false;
            }
        });

    }

    private void initView() {
        mIbtnAddsql = (ImageButton) findViewById(R.id.ibtn_addsql);
        //跳转至添加数据页面
        mIbtnAddsql.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, DAOADDActivity.class));
            }
        });
        mButton = (Button) findViewById(R.id.button);
        //点击按钮完成并销毁
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}