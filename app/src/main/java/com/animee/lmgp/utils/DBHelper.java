package com.animee.lmgp.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.animee.lmgp.common.Common;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        //CursorFactory设置为null,使用默认值
        super(context, Common.DATABASE_NAME, null, Common.DATABASE_VERSION);
    }

    //数据库第一次被创建时onCreate会被调用
    @Override
    public void onCreate(@Nullable SQLiteDatabase db) {

    }

    //o如果DATABASE_VERSION值被改为2,系统发现现有数据库版本不同,即会调用nUpgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }
}