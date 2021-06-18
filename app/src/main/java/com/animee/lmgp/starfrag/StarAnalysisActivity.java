package com.animee.lmgp.starfrag;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.animee.lmgp.R;

import com.github.mikephil.charting.charts.LineChart;

public class StarAnalysisActivity extends AppCompatActivity implements View.OnClickListener {


    LineChart lineChart;                      //曲线图
    TextView result;                          //结果
    TextView match;                           //匹配率
    EditText staranalysis_tv_remark;          //备注




    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star_analysis);

         initView();//初始化控件




    }



    /* 初始化控件*/
    private void initView() {
        lineChart = (LineChart) findViewById(R.id.lineChart); //曲线图
        lineChart.setOnClickListener(this::onClick);

        result = (TextView) findViewById(R.id.result); //结果
        match = (TextView) findViewById(R.id.match); //匹配率
        staranalysis_tv_remark = (EditText) findViewById(R.id.staranalysis_tv_remark); //备注


        //添加点击事件
        Button save =  findViewById(R.id.save); //保存
        save.setOnClickListener(this::onClick);
        Button cancel = findViewById(R.id.cancel); //取消
        cancel.setOnClickListener(this::onClick);
        ImageView title_iv_back =  findViewById(R.id.title_iv_back); //退出
        title_iv_back.setOnClickListener(this::onClick);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
            case R.id.title_iv_back:
                break;
            case R.id.save:
                break;

        }


    }

}
