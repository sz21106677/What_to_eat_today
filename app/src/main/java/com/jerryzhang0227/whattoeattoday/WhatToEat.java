package com.jerryzhang0227.whattoeattoday;

import com.jerryzhang0227.whattoeattoday.utills.FileSave;

import java.util.Random;

public class WhatToEat {
    static String finalResult;
    private static FileSave fileUtils;
    static int a;
    static int b;
    static int c;
    static int d;

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
        }
        return finalResult;
    }
    private void restoreInfo() {
        String info= fileUtils.readeFile();
        if(info.length()>0)
        {

        }
    }
    public static void record() {
        if(finalResult == "A饭堂") {
            a += 1;
        }else if(finalResult == "B饭堂") {
            b += 1;
        }else if(finalResult == "C饭堂") {
            c += 1;
        }else{
            d += 1;
        }

        System.out.println("共出现A"+a+"次");
        System.out.println("共出现B"+b+"次");
        System.out.println("共出现C"+c+"次");
        System.out.println("共出现D"+d+"次");
    }
}
