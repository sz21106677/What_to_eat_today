package com.jerryzhang0227.whattoeattoday;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ReadFile {
    public static String ReadRecord() {
        File readerFile = new File(WhatToEat.path + "/content.txt");
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
