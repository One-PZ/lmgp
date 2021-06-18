package com.animee.lmgp.starfrag;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.animee.lmgp.DIYView.SpreadView;
import com.animee.lmgp.MainActivity;
import com.animee.lmgp.R;

import com.animee.lmgp.utils.BitmapUtil;
import com.animee.lmgp.utils.FillPdfUtils;
import com.animee.lmgp.utils.NumberUtils;
import com.animee.lmgp.utils.URLContent;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.github.mikephil.charting.charts.LineChart;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;




/**
 * 首页
 */
public class StarFragment extends Fragment implements View.OnClickListener {

    SpreadView spreadView;
    TextView textView;      //显示文本
    Button connectingsBtn;  //连接按钮
    Button tartingBtn;      //开始检测

    LineChart lineChart;                      //曲线图
    LinearLayout linearLayout;
    //结果，匹配率，备注
    TextView result,match,staranalysis_tv_remark;


    EditText editText;
    private ProgressDialog myDialog; // 进度框


    public static Handler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_star, container, false);
        initView(view);
        initProgress();
        handler = new Handler(){
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:

                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                }
            };
        };
        return view;
    }

    /**
     * 初始化进度框
     */
    private void initProgress() {
        myDialog = new ProgressDialog(getContext(), ProgressDialog.THEME_HOLO_LIGHT);
        myDialog.setIndeterminateDrawable(getResources().getDrawable(
                R.drawable.progress_ocr));
        myDialog.setMessage("正在检测...");
        myDialog.setCanceledOnTouchOutside(false);
        myDialog.setCancelable(false);
    }

    /* 初始化控件操作*/
    private void initView(View view) {


        spreadView = view.findViewById(R.id.spreadView);
        textView = view.findViewById(R.id.text);

        lineChart = view.findViewById(R.id.lineChart);


        linearLayout = view.findViewById(R.id.CurveShow);

        result = view.findViewById(R.id.result);
        match = view.findViewById(R.id.match);
        staranalysis_tv_remark = view.findViewById(R.id.staranalysis_tv_remark);

        //已连接
        connectingsBtn = view.findViewById(R.id.connectingsBtn);
        connectingsBtn.setOnClickListener(this::onClick);

        tartingBtn = view.findViewById(R.id.tartingBtn);
        tartingBtn.setOnClickListener(this::onClick);

        editText = view.findViewById(R.id.e2);
        view.findViewById(R.id.buton1).setOnClickListener(this::onClick);
    }

    private void ShowChoise()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),android.R.style.Theme_Holo_Light_Dialog);
        //builder.setIcon(R.drawable.ic_launcher);
        builder.setTitle("选择检测时间");
        //    指定下拉列表的显示数据
        final String[] cities = {"30秒", "50秒", "70秒", "80秒", "100秒"};
        //    设置一个下拉的列表选择项
        builder.setItems(cities, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {


                String time = cities[which].substring(0,2);
                long times = Long.parseLong(time);


                myDialog.show();
               // Toast.makeText(getActivity(), "选择的检测时间为：" + cities[which], Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog r_dialog = builder.create();
        r_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        r_dialog.show();
    }

    //出现异常
    private void InterruptTest(){
        try {
        myDialog.cancel();

            System.out.println("自动重启成功");
        }catch (Exception e){
            System.out.println("自动重启失败");
        }
    }














    /**获取IMEI
     * @param slotId  slotId为卡槽Id，它的值为 0、1；
     * @return
     */
    public static String getIMEI(Context context, int slotId) {
        try {
            TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            Method method = manager.getClass().getMethod("getImei", int.class);
            String imei = (String) method.invoke(manager, slotId);
            return imei;
        } catch (Exception e) {
            return "";
        }
    }







    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.connectingsBtn: //

                break;
            case R.id.buton1:   //
                Python py = Python.getInstance();

                PyObject obj1 = py.getModule("all-in-one").callAttr("allinone",editText.getText());
                break;

        }
    }








}
