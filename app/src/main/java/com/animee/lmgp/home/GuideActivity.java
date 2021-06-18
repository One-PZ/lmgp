package com.animee.lmgp.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.animee.lmgp.MainActivity;
import com.animee.lmgp.R;
import com.animee.lmgp.utils.URLContent;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity implements View.OnClickListener {

    //声明变量
    Handler mHandler;
    EditText accountText,passwordText;
    Button login;
    Handler handler;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        pref = getSharedPreferences("login", MODE_PRIVATE);
        iniView();

    }



    private void iniView(){
        accountText = findViewById(R.id.accountText);
        passwordText = findViewById(R.id.passwordText);
        login = findViewById(R.id.login);
        login.setOnClickListener(this::onClick);
        accountText.setText(pref.getString("account", ""));
    }




    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.login:
               Login();
                break;
        }
    }

    /**
     * 登录方法
     */
    public boolean Login() {

        if (isUserNameAndPwdValid()) {
            if(URLContent.NetworkUnobstructed(GuideActivity.this)){
                handler=new Handler(){

                    public void handleMessage(Message msg){
                        switch(msg.what){
                            case -1:
                                Toast.makeText(GuideActivity.this,"服务器连接失败!",
                                        Toast.LENGTH_SHORT).show();
                                break;
                            case -2:
                                Toast.makeText(GuideActivity.this,"哎呀,出错啦...",
                                        Toast.LENGTH_SHORT).show();
                                break;
                            case 1:

                                if(msg.obj.toString().trim().equals("true")){
                                    //连接成功记录
                                    SetEditor();
                                    //实现界面的跳转
                                    Intent intent = new Intent(GuideActivity.this,MainActivity.class);
                                    startActivity(intent);
                                    //关闭当前界面
                                    finish();
                                }else {
                                    System.out.println(msg.obj + "收到这个");
                                    Toast.makeText(GuideActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                                }
                                break;
                        }
                    }
                };
                URLContent.LoginPost(accountText.getText().toString(),passwordText.getText().toString(),handler);
            }else {
                ComparisonEditor();
            }
            };

        return false;
    }

    private void SetEditor(){
        SharedPreferences.Editor editor = pref.edit();
        byte[] encode = Base64.encode(passwordText.getText().toString().getBytes(), Base64.DEFAULT);
        editor.putString("password", new String(encode));
        editor.putString("account", accountText.getText().toString());
        editor.commit();
    }

    /**
     * 没网时的登陆方法
     */
    private void ComparisonEditor(){
        String passwordBase64 = pref.getString("password", "");
        String account = pref.getString("account", "");
        if(passwordBase64 != "" && account != ""){
            byte[] decode = Base64.decode(passwordBase64, Base64.DEFAULT);
            String password = new String(decode);
            if(accountText.getText().toString().equals(account) && passwordText.getText().toString().equals(password)){
                Intent intent = new Intent(GuideActivity.this,MainActivity.class);
                startActivity(intent);
                //关闭当前界面
                finish();
            }else {
                Toast.makeText(GuideActivity.this,"账号密码错误!",
                        Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(GuideActivity.this,"登陆失败!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 判断用户名和密码是否有效
     *
     * @return
     */
    public boolean isUserNameAndPwdValid() {
        // 用户名和密码不得为空
        if (accountText.getText().toString().trim().equals("")) {
            Toast.makeText(this, "账号为空",
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (passwordText.getText().toString().trim().equals("")) {
            Toast.makeText(this, "密码为空",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }





}
