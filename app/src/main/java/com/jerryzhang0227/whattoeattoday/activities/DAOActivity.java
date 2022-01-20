package com.jerryzhang0227.whattoeattoday.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jerryzhang0227.whattoeattoday.R;
import com.jerryzhang0227.whattoeattoday.adapter.FoodAdapter;
import com.jerryzhang0227.whattoeattoday.model.Food;
import com.jerryzhang0227.whattoeattoday.utils.DatabaseHelper;

import java.util.LinkedList;

public class DAOActivity extends AppCompatActivity {

    private LinkedList<Food> mData = null;
    private final Context mContext = DAOActivity.this;
    private FoodAdapter mAdapter = null;
    private RecyclerView list_food;
    MyTask mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
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

    @SuppressLint("StaticFieldLeak")
    class MyTask extends AsyncTask<LinkedList,Void,LinkedList> {

        @Override
        protected LinkedList doInBackground(LinkedList... LinkedList) {
//            try {
                mData = new LinkedList<>();
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
                cursor.close();
                //模拟一次耗时任务
                //Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            return mData;
        }

        @Override
        protected void onPostExecute(LinkedList linkedList) {
            super.onPostExecute(linkedList);
            //新建适配器，并启动
            mAdapter = new FoodAdapter(mData, mContext);
            list_food.setAdapter(mAdapter);

            //注册自定义长按监听事件
            mAdapter.setmOnRecycleViewItemsOnLongClickListener(position -> {
                //根据列表位置从model中取出食物名
                String defood = mData.get(position).getFoodName();
                //创建一个线程用于删除数据库数据
                new Thread(() -> {
                    DatabaseHelper dbsqLiteOpenHelper = new DatabaseHelper(mContext, "food.db", null, 1);
                    SQLiteDatabase db = dbsqLiteOpenHelper.getWritableDatabase();
                    db.delete("foodlist","name=?",new String[]{defood});
                }).start();
                //根据i删除列表所在位置，并在数据库中一并删除记录
                mData.remove(position);
                //及时刷新列表并播放RecycleView的删除动画
                mAdapter.notifyItemRemoved(position);
                Toast.makeText(mContext, "删除了"+defood, Toast.LENGTH_SHORT).show();
            });
        }
    }

    private void iniData() {
        //创建一个异步任务，并启动
        mTask = new MyTask();
        mTask.execute();
    }

    private void initView() {
        list_food = findViewById(R.id.recycleviewlist);

        //给RecycleView设置表格布局
        LinearLayoutManager layoutManager = new GridLayoutManager(mContext,1);
        list_food.setLayoutManager(layoutManager);
        //添加Android自带的分割线
        list_food.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        ImageButton mIbtnAddsql = (ImageButton) findViewById(R.id.ibtn_addsql);
        //跳转至添加数据页面
        mIbtnAddsql.setOnClickListener(view -> startActivity(new Intent(mContext, DAOADDActivity.class)));
        Button mButton = (Button) findViewById(R.id.button);
        //点击按钮完成并销毁
        mButton.setOnClickListener(view -> finish());
    }
}