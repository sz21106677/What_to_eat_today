package com.jerryzhang0227.whattoeattoday.utils;

import static com.jerryzhang0227.whattoeattoday.utils.ReadFile.Save;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.jerryzhang0227.whattoeattoday.MyApplication;
import com.jerryzhang0227.whattoeattoday.activities.DAOActivity;
import com.jerryzhang0227.whattoeattoday.activities.WhatToEatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class WhatToEat {
    static Random random = new Random();

    //本程序一大特色“猪言猪语”，模仿室友讲话语气更接地气
    public static String Pignese() {
        String pigSay;
        //添加猪言
        String[] pigSays = {"神经病","犯病了是吧","爬","smys","?","铸币","sb是吧","人呢","666","🐮","牛逼","哭",};
        //随机抽取一句话
        int result = random.nextInt(pigSays.length);
        pigSay = pigSays[result];
        return pigSay;
    }

    public static String eatWhat() {
        //根据抽取结果完成字符串拼接
        String eatStr = "今天吃"+what();
        return eatStr;
    }

    public static String what() {
        //打开数据库并遍历数据
        DatabaseHelper dbsqLiteOpenHelper = new DatabaseHelper(MyApplication.getContext(), "food.db", null, 1);
        SQLiteDatabase db = dbsqLiteOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select name,weight from foodlist", null);
        //将数据存入一个Map
        Map<String, Integer> map = new HashMap<String, Integer>();
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String foodname = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") int weight = cursor.getInt(cursor.getColumnIndex("weight"));
            //使权重加上一个随机数
            map.put(foodname, weight+rand());
        }

        //新生成一个List用于对比Map中计算后的权重大小，并排列
        List<Map.Entry<String,Integer>> list = new ArrayList<Map.Entry<String,Integer>>(map.entrySet());
        Collections.sort(list,new Comparator<Map.Entry<String,Integer>>() {
            public int compare(Map.Entry<String,Integer> o2, Map.Entry<String,Integer> o1) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        //返回List中排列第一的数据
        return list.get(0).getKey();
    }

    private static int rand() {
        //定义一个随机整数
        int result = random.nextInt(100);
        return result;
    }
}
