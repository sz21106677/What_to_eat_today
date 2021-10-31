package com.jerryzhang0227.whattoeattoday;

import android.os.Environment;
import android.util.Log;

import java.io.FileWriter;
import java.util.Random;

public class WhatToEat {
    static String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/database/";
    static Random random = new Random();

    static String codeResolve(int eatCodeNum) {
        String eatWhat = null;
        switch (eatCodeNum) {
            case 901:
                eatWhat = "今天吃A饭堂";
                break;
            case 902:
                eatWhat = "今天吃B饭堂";
                break;
            case 903:
                eatWhat = "今天吃C饭堂";
                break;
            case 904:
                eatWhat = "外卖";
                break;
            case 905:
                eatWhat = "今天吃麦当劳!!!";
                break;
        }
        return eatWhat;
    }

    public static String takeOut() {
        String[] takeOut = {"90Go","快猪"};
        String result = null;
        int a = random.nextInt(2);
        result = takeOut[a];
        return "点"+result;
    }

    public static String Pignese() {
        String pigSay;
        String[] pigSays = {"神经病","犯病了是吧","爬","smys","?","铸币","sb是吧","人呢","666","🐮"};
        int result = random.nextInt(10);
        pigSay = pigSays[result];
        Save(pigSay);
        MainActivity.count = 0;
        return pigSay;
    }


    public static String Save(String string) {
        java.io.File fileDir = new java.io.File(path);
        // 如果目录不存在就新建目录
        if (!fileDir.exists()) {
            if (!fileDir.mkdirs()) {
                Log.i("创建目录", "创建目录失败，请检查是否有sdcard读写权限。");
            }
        }
        // 获取内容
        String str = string.trim();
        try {
            FileWriter fw = new FileWriter(path + "content.txt");
            fw.flush();
            fw.write(str);
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }
}
