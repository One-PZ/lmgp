package com.animee.lmgp.luckfrag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.animee.lmgp.MainActivity;
import com.animee.lmgp.R;

import com.animee.lmgp.common.Common;
import com.animee.lmgp.parnterfrag.ParnterAnalysisActivity;
import com.animee.lmgp.utils.DBHelper;
import com.animee.lmgp.utils.LoadDataAsyncTask;
import com.animee.lmgp.utils.NumberUtils;
import com.animee.lmgp.utils.URLContent;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 物质展示页面
 */
public class LuckAnalysisActivity extends AppCompatActivity implements View.OnClickListener {

    GridView luckLv;
    TextView nameTv;
    ImageView backIv;
    List<LuckItemBean>mDatas;
    private LuckBaseAdapterByMatter adapter;

    SearchView searchView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luck_analysis);

        //获取上级数据
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");  //获取上一级界面传递
        String title = intent.getStringExtra("title");


        //初始化
        initView(title);




        if(NumberUtils.isNumeric(name)){
            addDataToListByNum(name);
        }else {

        }


        luckLv.setAdapter(adapter);

        setListener();
        setSerch();
    }

    private void initView(String title) {
        // 查找控件

        nameTv = findViewById(R.id.title_tv);
        backIv = findViewById(R.id.title_iv_back);
        searchView = findViewById(R.id.sv);
        nameTv.setText(title);
        backIv.setOnClickListener(this);
    }

    /* 整理数据到集合当中*/
    private void addDataToListByNum(String name) {
        if(!NumberUtils.isNumeric(name)){
           return;
        }
        //初始化数据库
        SQLiteDatabase db = Common.DBHELP.getReadableDatabase();





    }


    /*
    添加类型
     */
    private void addDataToMatterinmatterclassification(Cursor cursor,String[] matterID){
        int i = 0;
        while (cursor.moveToNext()){
            matterID[i] = cursor.getString(cursor.getColumnIndex("matter_ID"));
            System.out.println(matterID[i]);
        }
    }



    /**
     * 搜索框监听
     */
    private void setSerch(){
        //        设置该SearchView默认是否自动缩小为图标
        searchView.setIconifiedByDefault(false);
//        设置该SearchView显示搜索图标
        searchView.setSubmitButtonEnabled(true);
//        设置该SearchView内默认显示的搜索文字
        searchView.setQueryHint("物质名称");
//        为SearchView组件设置事件的监听器
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //            单击搜索按钮时激发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {

                luckLv.setAdapter(adapter);
                nameTv.setText(query);
                return false;
            }
            //            用户输入时激发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
//                如果newText不是长度为0的字符串
                if (TextUtils.isEmpty(newText)){
                    // luckGv.setAdapter(null);
                }else {


                }
                return true;
            }
        });
    }

    /* 创建监听事件*/
    private void setListener() {
        luckLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(LuckAnalysisActivity.this , ParnterAnalysisActivity.class);

                startActivity(intent);
            }
        });
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
