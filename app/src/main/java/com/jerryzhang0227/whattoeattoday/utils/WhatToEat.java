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

    public static String Pignese() {
        String pigSay;
        String[] pigSays = {"神经病","犯病了是吧","爬","smys","?","铸币","sb是吧","人呢","666","🐮","牛逼","哭",};
        int result = random.nextInt(pigSays.length);
        pigSay = pigSays[result];
        WhatToEatActivity.count = 0;
        return pigSay;
    }

    public static String eatWhat() {
        String eatStr = "今天吃"+what();
        return eatStr;
    }
    public static String what() {
        DatabaseHelper dbsqLiteOpenHelper = new DatabaseHelper(MyApplication.getContext(), "food.db", null, 1);
        SQLiteDatabase db = dbsqLiteOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select name,weight from foodlist", null);
        Map<String, Integer> map = new HashMap<String, Integer>();
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String foodname = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") int weight = cursor.getInt(cursor.getColumnIndex("weight"));
            map.put(foodname, weight+rand());
        }

        List<Map.Entry<String,Integer>> list = new ArrayList<Map.Entry<String,Integer>>(map.entrySet());
        Collections.sort(list,new Comparator<Map.Entry<String,Integer>>() {
            public int compare(Map.Entry<String,Integer> o2, Map.Entry<String,Integer> o1) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        return list.get(0).getKey();
    }

    private static int rand() {
        int result = random.nextInt(100);
        return result;
    }
}
