package com.jerryzhang0227.whattoeattoday.utils;

import android.annotation.SuppressLint;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jerryzhang0227.whattoeattoday.MyApplication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
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
        return "今天吃"+what();
    }

    public static String what() {
        //打开数据库并遍历数据
        DatabaseHelper dbsqLiteOpenHelper = new DatabaseHelper(MyApplication.getContext(), "food.db", null, 1);
        SQLiteDatabase db = dbsqLiteOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select name,weight from foodlist", null);
        //将数据存入一个Map
        Map<String, Integer> map = new Hashtable<>();
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String foodname = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") int weight = cursor.getInt(cursor.getColumnIndex("weight"));
            //使权重加上一个随机数
            map.put(foodname, weight+rand());
        }
        cursor.close();

        //新生成一个List用于对比Map中计算后的权重大小，并排列
        List<Map.Entry<String,Integer>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, (o2, o1) -> o1.getValue().compareTo(o2.getValue()));
        //返回List中排列第一的数据
        return list.get(0).getKey();
    }

    private static int rand() {
        //定义一个随机整数
        return random.nextInt(100);
    }
}
