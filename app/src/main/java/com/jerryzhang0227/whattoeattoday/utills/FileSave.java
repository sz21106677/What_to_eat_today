package com.jerryzhang0227.whattoeattoday.utills;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileSave {
    private static final String TAG="FileUtilsDebug";
    private final Context mContext; //上下文
    private FileOutputStream fos; //从内存到文件输出流 写入文件流
    private FileInputStream fis ; //从文件输入内存流 读文件
    public FileSave(Context mContext) {
        this.mContext = mContext;
    }
    /**
     * 将输入框内容写入文件方法
     * @param data 输入框中的字符内容
     * @return true 写入成功 false 写失败
     */
    public boolean writeFile( String data )
    {
        boolean flag=true;
        //通过指定文件打开输出流 参数 1：文件名 参数 2：文件访问模式
        // FileOutputStream 对字节操作 FileWriter 对字符操作
        try {
            fos=mContext.openFileOutput(Config.FILENAME ,
                    Context.MODE_PRIVATE );
            //OutputStreamWriter()将字节流转换成字符流
            BufferedWriter writer=new BufferedWriter(new
                    OutputStreamWriter(fos));
            //字符内容写入到文件
            writer.write(data);
            writer.flush();
        } catch (FileNotFoundException e) {
            flag=false;
            Log.i(TAG,"找不到文件");
        } catch (IOException e) {
            flag=false;
            Log.i(TAG,"IO 操作异常");
        }finally {
            if(fos!=null)
            {
                try {
                    fos.close();
                } catch (IOException e) {
                    Log.i(TAG,"IO 操作异常");
                }
            }
        }
        return flag;
    }
    /**
     * 将文件内容读取到内存
     * @return 读取的内容
     */
    public String readeFile() {
        StringBuilder buffer = new StringBuilder(); //存放所有字符
        try {
            fis = mContext.openFileInput(Config.FILENAME);
            //将字节输入流转换成字符输入流
            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(fis));
            String line; //临时存放每行字符
            //按文本行读取内容
            while ((line = reader.readLine()) != null) {
                //将每行内容追加到 StringBuffer
                buffer.append(line);
            }
        } catch (FileNotFoundException e) {
            Log.i(TAG, "找不到文件");
        } catch (IOException e) {
            Log.i(TAG, "IO 操作异常");
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    Log.i(TAG, "IO 操作异常");
                }
            }
        }
        //将读取字符返回
        return buffer.toString();
    }
}
