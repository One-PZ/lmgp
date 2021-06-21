package com.animee.lmgp.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;

import com.animee.lmgp.MainActivity;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class URLContent {

    public static String url = "http://192.168.0.111:8080";


    /**
     * 登陆
     * @param account
     * @param password
     * @param handler
     */
    public static void LoginPost(String account, String password, Handler handler){


        try {
            String urlEd =  "/photo/login";
            // The URL-encoded contend
            // 正文，正文内容其实跟get的URL中 '? '后的参数字符串一致
            // The URL-encoded contend
            // 正文，正文内容其实跟get的URL中 '? '后的参数字符串一致
            String content = "&username=" + URLEncoder.encode(account, "UTF-8");

            content += "&password=" + URLEncoder.encode(password, "UTF-8");

            content += "&remember=" + URLEncoder.encode("on", "UTF-8");

            PostThread(content,urlEd,handler);

        }catch (Exception e){
            Message msg = new Message();
            msg.what=-1;
            msg.obj=e;
            handler.sendMessage(msg);
        }

    }

    /**
     * 修改密码
     * @param oldPwd
     * @param newPwd
     * @param account
     * @param handler
     */
    public static void UpdatePasswordPost(String oldPwd, String newPwd,String account, Handler handler){
              try {
                  String urlEd = "/photo/changePwdByPhone";
                  // The URL-encoded contend
                  // 正文，正文内容其实跟get的URL中 '? '后的参数字符串一致
                  String content = "&oldPwd=" + URLEncoder.encode(oldPwd, "UTF-8");

                  content += "&newPwd=" + URLEncoder.encode(newPwd, "UTF-8");

                  content += "&account=" + URLEncoder.encode(account, "UTF-8");

                  PostThread(content,urlEd,handler);

              }catch (Exception e){
                  Message msg = new Message();
                  msg.what=-1;
                  msg.obj=e;
                  handler.sendMessage(msg);
              }
    }




    public static void UpDateThread(Handler handler) {
        try {
        String urlEd = "/photo/UpdateDatabase";
        String passwordBase64 = MainActivity.star_pref.getString("password", "");
        byte[] decode = Base64.decode(passwordBase64, Base64.DEFAULT);
        String password = new String(decode);
        String account =  MainActivity.star_pref.getString("account", "");

        String content = "&username=" + URLEncoder.encode(account, "UTF-8");

        content += "&password=" + URLEncoder.encode(password, "UTF-8");
            PostThread(content,urlEd,handler);

        }catch (Exception e){
            Message msg = new Message();
            msg.what=-1;
            msg.obj=e;
            handler.sendMessage(msg);
        }
    }



    /**
     * Post方式上传的线程
     * @param content
     * @param urlEd
     * @param handler
     */
    public static void PostThread(String content, String urlEd, Handler handler){
        new Thread(){
            public void run(){
                Message msg= new Message();
                try {
                    URL postUrl = new URL(url + urlEd);
                    // 打开连接
                    HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();

                    // 设置是否向connection输出，因为这个是post请求，参数要放在
                    // http正文内，因此需要设为true
                    connection.setDoOutput(true);
                    // Read from the connection. Default is true.
                    connection.setDoInput(true);
                    // 默认是 GET方式
                    connection.setRequestMethod("POST");

                    // Post 请求不能使用缓存
                    connection.setUseCaches(false);

                    connection.setInstanceFollowRedirects(true);

                    // 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
                    // 意思是正文是urlencoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode
                    // 进行编码
                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    // 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，
                    // 要注意的是connection.getOutputStream会隐含的进行connect。
                    connection.connect();
                    DataOutputStream out = new DataOutputStream(connection
                            .getOutputStream());

                    // DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写到流里面
                    out.writeBytes(content);

                    out.flush();
                    out.close();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    String resultData = "";
                    while ((line = reader.readLine()) != null) {
                        resultData += line + "\n";

                        //打印看看
                        Log.d("USB_Devices", line);
                     }
                    reader.close();
                    connection.disconnect();

                    //发送handler信息
                    msg.what=1;
                    msg.obj= resultData;

                } catch (Exception e) {
                    e.printStackTrace();

                    //使用-1代表程序异常
                    msg.what=-2;
                    msg.obj=e;
                }
                handler.sendMessage(msg);
            }
        }.start();
    }



    /**
     * 网络连接状态
     * @param activity
     * @return
     */
    public static boolean NetworkUnobstructed(Activity activity){
        Context context = activity.getApplicationContext();
   // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // 获取NetworkInfo对象
        NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
        for (int i = 0; i < networkInfo.length;i++ ){
            if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED)
            {
                return true;
            }
        }
        return false;
    }

}
