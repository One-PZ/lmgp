package com.animee.lmgp.luckfrag;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.animee.lmgp.R;
import com.animee.lmgp.common.Common;
import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LineChartActivity extends AppCompatActivity implements View.OnClickListener {

    TextView result,match,remark,time;
    LineChart lineChart;                      //曲线图


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luck_linechart);
        initView();

    }





    private void initView() {
        result = findViewById(R.id.result);
        match = findViewById(R.id.match);
        time = findViewById(R.id.time);
        lineChart = findViewById(R.id.lineChart);

        ImageView title_iv_back = findViewById(R.id.title_iv_back);
        title_iv_back.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void DrawLine(String X,String YOne,String YTwo){
        List<Float> dateX;
        List<List<Float>> dateYs = new ArrayList<>();
        List<String> LabName = new ArrayList<>();

        dateX = Arrays.asList(X.
                split(",")).stream().map(s -> Float.parseFloat(s.trim())).collect(Collectors.toList());

        List<Float> List = Arrays.asList(YOne.
                split(",")).stream().map(s -> Float.parseFloat(s.trim())).collect(Collectors.toList());
        List<Float> ListTow = Arrays.asList(YTwo.
                split(",")).stream().map(s -> Float.parseFloat(s.trim())).collect(Collectors.toList());

        dateYs.add(List);
        dateYs.add(ListTow);

        LabName.add("样本光谱");
        LabName.add("标定光谱");

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.title_iv_back:
                finish();
                break;
        }
    }
}
