package com.jerryzhang0227.whattoeattoday;

import android.os.Environment;
import android.util.Log;

import java.io.FileWriter;
import java.util.Random;

public class WhatToEat {
    static String finalResult;
    static String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/database/";

    public static String Boom() {
        Random random = new Random();
        int result = random.nextInt(100);
        if (0 < result && result <= 33) {
            finalResult = "A饭堂";
        }else if (33 < result && result <= 66) {
            finalResult = "B饭堂";
        }else if (66 < result && result <= 99) {
            finalResult = "C饭堂";
        }else {
            finalResult = "麦当劳!!!";
        }if (MainActivity.count == 1) {
            return "那今天必须吃"+finalResult;
        }
        return "今天吃"+finalResult;
    }

    public static String Pignese() {
        Random random = new Random();
        int result = random.nextInt(100);
        if (0 < result && result <= 33) {
            finalResult = "神经病";
        }else if (33 < result && result <= 66) {
            finalResult = "爬";
        }else if (66 < result && result <= 99) {
            finalResult = "铸币";
        }else {
            finalResult = "我爱你";
        }
        Save(finalResult);
        return finalResult;
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
