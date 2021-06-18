package com.animee.lmgp.parnterfrag;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.animee.lmgp.R;

import com.animee.lmgp.common.Common;
import com.github.mikephil.charting.charts.LineChart;

/**
 * 物质光谱显示界面
 */
public class ParnterAnalysisActivity extends AppCompatActivity implements View.OnClickListener {

    TextView matchName,material_properties,database,danger,title_tv;
    LineChart match_lineChart;                      //曲线图



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parnter_analysis);

        initView();
        getLastData();


    }

    private void getLastData() {
        Intent intent = getIntent();

    }


    private String GetProperties(String table,String matterID){
        String properties = "属性: ";
        //初始化数据库
        SQLiteDatabase db = Common.DBHELP.getReadableDatabase();
        Cursor cursor = db.query(table, new String[]{"matterClassification_ID"}, "matter_ID = ?", new String[]{matterID}, null, null, null);
        int i = 0;
        while (cursor.moveToNext()) {
            Integer id = cursor.getInt(cursor.getColumnIndex("matterClassification_ID"));
            if(i > 0){
                properties += ",";
            }

            i++;
        }
        return properties;
    }

    private void initView() {
        matchName = findViewById(R.id.matchName);
        material_properties = findViewById(R.id.material_properties);
        database = findViewById(R.id.database);
        danger = findViewById(R.id.danger);
        title_tv = findViewById(R.id.title_tv);
        match_lineChart = findViewById(R.id.match_lineChart);


        ImageView title_iv_back = findViewById(R.id.title_iv_back);
        title_iv_back.setOnClickListener(this);
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
