package com.jerryzhang0227.whattoeattoday.utils;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class ReadFile {
    static String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/database/";
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
    public static String ReadRecord() {
        File readerFile = new File(path + "/content.txt");
        BufferedReader reader = null;
        String tempString = null;
        StringBuffer sbf = new StringBuffer();
        try {
            reader = new BufferedReader(new FileReader(readerFile));
            while ((tempString = reader.readLine()) != null) {
                sbf.append(tempString);
            }
            reader.close();
            return sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sbf.toString();
    }
}
