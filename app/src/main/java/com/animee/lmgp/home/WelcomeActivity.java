package com.animee.lmgp.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.animee.lmgp.MainActivity;
import com.animee.lmgp.R;
import com.animee.lmgp.common.Common;
import com.animee.lmgp.utils.DBHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class WelcomeActivity extends AppCompatActivity {

    TextView tv;
    int count = 5;
    Handler handler = new Handler(){
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 1) {
                count--;
                if (count == 0) {
                    Intent intent = new Intent();
                    intent.setClass(WelcomeActivity.this, GuideActivity.class);
//                        为了下一次不跳转指引界面，更改值
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("isFirst",false);
                    editor.commit();
                    startActivity(intent);
                    finish();
                }else {
                    tv.setText(String.valueOf(count));
                    handler.sendEmptyMessageDelayed(1,500);
                }
            }

        }
    };
    private SharedPreferences pref;


    public void writeDB(){

        //SQLiteDatabase.openOrCreateDatabase();
       // Common.DATABASE_NAME = getFilesDir()+"\\databases\\"+"lmgp.db";  //此处如果是放在应用包名的目录下,自动放入“databases目录下
        FileOutputStream fout = null;
        InputStream inputStream = null;
        try {

            fout = new FileOutputStream(new File(Common.DATABASE_NAME));
            byte[] buffer = new byte[128];
            int len = 0;
            while ((len = inputStream.read(buffer))!=-1){
                fout.write(buffer, 0, len);
            }
            buffer = null;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fout != null) {
                try {
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        tv = findViewById(R.id.welcome_tv);
        pref = getSharedPreferences("first_pref", MODE_PRIVATE);
        Common.DATABASE_NAME = getFilesDir()+"\\databases\\"+"lmgp.db";  //此处如果是放在应用包名的目录下,自动放入“databases目录下
        //判断是否是第一次进入此应用，如果是第一次进入此应用，导入数据库
        boolean isFirst = pref.getBoolean("isFirst", true);
        if (isFirst) {
            new Thread(){
                public void run() {
                    writeDB();
                }
            }.start();
        }
        handler.sendEmptyMessageDelayed(1,500);
    }
}
