package com.animee.lmgp.luckfrag;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SearchView;

import com.animee.lmgp.R;

import com.animee.lmgp.common.Common;

import java.util.HashMap;
import java.util.Map;

/**
 * 历史数据
 */
public class LuckFragment extends Fragment {
    GridView luckGv;

    private LuckBaseAdapter adapter;
    private SearchView search;
    private Map<Integer,String> record;
    private Map<Integer,String> recordCondition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_luck, container, false);

        luckGv = view.findViewById(R.id.luckfrag_gv);
        search=view.findViewById(R.id.sv);
        record = new HashMap();

//



//        设置GridView每一项的监听事件
        setListener();
        setSerch();
        return view;
    }






    /* 创建监听事件*/
    private void setListener() {
        luckGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String record_id = adapter.GetId(position).toString();
                Intent intent = new Intent(getContext(), LineChartActivity.class);
                intent.putExtra("recordId",record_id);
                startActivity(intent);
            }
        });

        luckGv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){//设置事件监听(长按)

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setPositiveButton("确定",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 创建适配器对象
                        // 创建适配器对象
                        Integer record_id = adapter.GetId(position);
                        record.remove(record_id);
                        adapter.Remove(record_id);
                        luckGv.setAdapter(adapter);
                        //初始化数据库
                        SQLiteDatabase db = Common.DBHELP.getReadableDatabase();

                    }
                });
                builder.setNegativeButton("取消",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setMessage("确定删除该记录？");
                builder.setTitle("提示");
                builder.show();
                return true;
               }
            });
    }




    private void setSerch(){
        //        设置该SearchView默认是否自动缩小为图标
        search.setIconifiedByDefault(false);
//        设置该SearchView显示搜索图标
        search.setSubmitButtonEnabled(true);
//        设置该SearchView内默认显示的搜索文字
        search.setQueryHint("查找");
//        为SearchView组件设置事件的监听器
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //            单击搜索按钮时激发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                ////       创建适配器对象
                recordCondition = new HashMap();
                //初始化数据库
                SQLiteDatabase db = Common.DBHELP.getReadableDatabase();

                // 创建适配器对象
                adapter = new LuckBaseAdapter(getContext(), recordCondition);
//        设置适配器
                luckGv.setAdapter(adapter);

                return false;
            }
            //            用户输入时激发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
//                如果newText不是长度为0的字符串
                if (TextUtils.isEmpty(newText)){
                    // 创建适配器对象
                    adapter = new LuckBaseAdapter(getContext(), record);
//        设置适配器
                    luckGv.setAdapter(adapter);
                }
                return true;
            }
        });

    }
}
