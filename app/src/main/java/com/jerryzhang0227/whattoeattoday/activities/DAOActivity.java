package com.jerryzhang0227.whattoeattoday.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
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

import javax.xml.transform.Result;

public class DAOActivity extends AppCompatActivity {

    private List<Food> mData = null;
    private Context mContext = DAOActivity.this;
    private FoodAdapter mAdapter = null;
    private ListView list_food;
    private ImageButton mIbtnAddsql;
    private Button mButton;
    MyTask mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dao);
        initView();
        iniData();
        Toast.makeText(mContext,"长按可删除列表项",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume(){
        super.onResume();
        iniData();
    }

    class MyTask extends AsyncTask<LinkedList,Void,LinkedList> {

        @Override
        protected LinkedList doInBackground(LinkedList... linkedLists) {
            try {
                mData = new LinkedList<Food>();
                DatabaseHelper dbsqLiteOpenHelper = new DatabaseHelper(mContext, "food.db", null, 1);
                SQLiteDatabase db = dbsqLiteOpenHelper.getWritableDatabase();
                //打开数据库并遍历查询信息
                Cursor cursor = db.rawQuery("select name,weight from foodlist", null);
                //根据遍历结果将数据插入链表
                while (cursor.moveToNext()) {
                    @SuppressLint("Range") String foodname = cursor.getString(cursor.getColumnIndex("name"));
                    @SuppressLint("Range") int weight = cursor.getInt(cursor.getColumnIndex("weight"));
                    mData.add(new Food(foodname, weight));
                }
                //模拟一次耗时任务
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return (LinkedList) mData;
        }

        @Override
        protected void onPostExecute(LinkedList linkedList) {
            super.onPostExecute(linkedList);
            //新建适配器，并启动
            mAdapter = new FoodAdapter((LinkedList<Food>) mData, mContext);
            list_food.setAdapter(mAdapter);
            Toast.makeText(mContext,"列表创建完成",Toast.LENGTH_SHORT).show();
        }
    }

    private void iniData() {
        //创建一个异步任务，并启动
        mTask = new MyTask();
        mTask.execute();

        //长按删除列表项
        list_food.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //根据列表位置从model中取出食物名
                String defood = mData.get(i).getFoodName();
                //创建一个线程用于删除数据库数据
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DatabaseHelper dbsqLiteOpenHelper = new DatabaseHelper(mContext, "food.db", null, 1);
                        SQLiteDatabase db = dbsqLiteOpenHelper.getWritableDatabase();
                        db.delete("foodlist","name=?",new String[]{defood});
                    }
                }).start();
                //根据i删除列表所在位置，并在数据库中一并删除记录
                mData.remove(i);
                //及时刷新列表
                mAdapter.notifyDataSetChanged();
                Toast.makeText(mContext, "删除了"+defood, Toast.LENGTH_SHORT).show();
                return false;
            }
        });

    }

    private void initView() {
        list_food = (ListView) findViewById(R.id.lsv);
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