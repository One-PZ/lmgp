package com.animee.lmgp.mefrag;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import de.hdodenhof.circleimageview.CircleImageView;

import android.os.Handler;
import android.os.Message;
import android.text.InputFilter;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.animee.lmgp.MainActivity;
import com.animee.lmgp.R;

import com.animee.lmgp.home.GuideActivity;

import com.animee.lmgp.luckfrag.LuckBaseAdapter;
import com.animee.lmgp.starfrag.StarAnalysisActivity;
import com.animee.lmgp.utils.AssetsUtils;
import com.animee.lmgp.utils.NumberUtils;
import com.animee.lmgp.utils.URLContent;

import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends Fragment implements View.OnClickListener {

    TextView mefrag_tv_about;
    TextView mefrag_tv_function,mefrag_tv_update,mefrag_tv_feedback;
    Button change_password,quit;

    Handler handler;
    String account;
    //  保存选择的星座位置
    int selectPos = 0;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_me, container, false);

         account = MainActivity.star_pref.getString("account", "");
        mefrag_tv_about = view.findViewById(R.id.mefrag_tv_about);
        mefrag_tv_about.setText("账号: "+account);

        mefrag_tv_function = view.findViewById(R.id.mefrag_tv_function);
        mefrag_tv_function.setOnClickListener(this::onClick);
        mefrag_tv_update = view.findViewById(R.id.mefrag_tv_update);
        mefrag_tv_update.setOnClickListener(this::onClick);
        mefrag_tv_feedback = view.findViewById(R.id.mefrag_tv_feedback);
        mefrag_tv_feedback.setOnClickListener(this::onClick);
        change_password = view.findViewById(R.id.change_password);
        change_password.setOnClickListener(this::onClick);
        quit = view.findViewById(R.id.quit);
        quit.setOnClickListener(this::onClick);


//        进行初始化设置
       // imgMap = AssetsUtils.getContentlogoImgMap();

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mefrag_tv_function: //检测模式

                MultipleChoice();
                break;
            case R.id.mefrag_tv_update:   //积分时间

                InputBox("积分时间",2,false);
                break;
            case R.id.mefrag_tv_feedback:  //激光功率
                InputBox("激光功率",3,false);
                break;
            case R.id.change_password:     //更改密码
                if(!URLContent.NetworkUnobstructed(getActivity())){
                    Toast.makeText(getContext(),"网络不可用,无法修改密码",Toast.LENGTH_SHORT).show();
                }else {
                    InputBox("原密码",0,true);
                }
                break;
            case R.id.quit:                //退出
                Quit();
                break;
        }
    }

    private void Quit() {
        Intent intent = new Intent(getContext(), GuideActivity.class);
        startActivity(intent);
    }

    private void InputBox(String title,Integer index,boolean ispassword){
        final EditText inputServer = new EditText(getContext());
        if(ispassword){
            inputServer.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        inputServer.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(title).setIcon(android.R.drawable.ic_dialog_info).setView(inputServer)
                .setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String _sign = inputServer.getText().toString();
                if(_sign!=null && !_sign.isEmpty())
                {
                    switch(index){
                        case 0:
                            VerifyPassword(_sign);
                            break;
                        case 1:
                            UpdatePassword(_sign);
                            break;
                        case 2:
                            IntegralTime(_sign);
                            break;
                        case 3:
                            ResetDevice(_sign);
                            break;
                    }
                }
            }
        });
        builder.show();
    }

    private void MultipleChoice(){


        final String[] items = {"普通采集模式","氙灯控制模式","外部触发模式"};
        new AlertDialog.Builder(getContext())
                .setTitle("请选择")
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {

                    }
                }).show();
    }

    private void VerifyPassword(String _sign){

        String passwordBase64 = MainActivity.star_pref.getString("password", "");
        byte[] decode = Base64.decode(passwordBase64, Base64.DEFAULT);
        String password = new String(decode);
        if(_sign.equals(password)){
            InputBox("新密码",1,true);
        }else {
            Toast.makeText(getContext(),"密码错误",Toast.LENGTH_SHORT).show();
        }
    }

    private void UpdatePassword(String _sign){
        String passwordBase64 = MainActivity.star_pref.getString("password", "");
        byte[] decode = Base64.decode(passwordBase64, Base64.DEFAULT);
        String password = new String(decode);
                handler=new Handler(){
                    public void handleMessage(Message msg){
                        switch(msg.what){
                            case -1:
                                Toast.makeText(getContext(),"服务器连接失败!",
                                        Toast.LENGTH_SHORT).show();
                                break;
                            case -2:
                                Toast.makeText(getContext(),"哎呀,出错啦...",
                                        Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                if(msg.obj.toString().trim().equals("true")){
                                    SharedPreferences.Editor editor = MainActivity.star_pref.edit();
                                    byte[] encode = Base64.encode(_sign.getBytes(), Base64.DEFAULT);
                                    editor.putString("password", new String(encode));
                                    editor.commit();
                                    Toast.makeText(getContext(),"修改密码成功",Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getContext(),"修改密码失败",Toast.LENGTH_SHORT).show();
                                }
                                break;
                        }
                    }
                };
                URLContent.UpdatePasswordPost(password,_sign,account,handler);

        }


        public boolean Judge(String _sign){
            if(!NumberUtils.isNumeric(_sign)){
                Toast.makeText(getActivity(), "请输入纯数字", Toast.LENGTH_SHORT).show();
                return false;
            }

            Toast.makeText(getActivity(), "设备未连接", Toast.LENGTH_SHORT).show();
            return false;
        }


    private void  IntegralTime(String _sign){
        if(Judge(_sign)){

        }
    }


    private void  ResetDevice(String _sign){
        if(Judge(_sign)){

        }
    }


}
