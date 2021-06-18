package com.animee.lmgp.common;


import com.animee.lmgp.utils.DBHelper;

public class Common {

    public static State STATE = State.UNUNITED; //初始为未连接

    public static  String DATABASE_NAME = "lmgp.db";  //数据库名称

    public static final int DATABASE_VERSION = 1;         //数据库版本

    public static DBHelper DBHELP;


}
