package com.jerryzhang0227.whattoeattoday.utils;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.jerryzhang0227.whattoeattoday.MyApplication;
import com.jerryzhang0227.whattoeattoday.activities.DAOADDActivity;
import com.jerryzhang0227.whattoeattoday.activities.DAOActivity;

public class DatabaseHelper extends SQLiteOpenHelper {

    //完成构造
    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //创建数据库
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql="create table foodlist(id integer primary key autoincrement,name varchar(100),weight integer)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
