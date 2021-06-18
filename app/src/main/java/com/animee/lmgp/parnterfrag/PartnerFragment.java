package com.animee.lmgp.parnterfrag;

import android.content.Intent;
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

import com.animee.lmgp.luckfrag.LuckAnalysisActivity;
import com.animee.lmgp.luckfrag.LuckBaseAdapter;


/**
 * 光谱库界面
 */
public class PartnerFragment extends Fragment {
    GridView luckGv;
    private LuckBaseAdapter adapter;
    private SearchView search;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //加载页面
        View view =  inflater.inflate(R.layout.fragment_partner, container, false);

        //初始化控件
        luckGv = view.findViewById(R.id.luckfrag_gv);
        search=view.findViewById(R.id.sv);

//       创建适配器对象
        adapter = new LuckBaseAdapter(getContext(), DatabaseHome.MatterclassificationMap);
//        设置适配器
        luckGv.setAdapter(adapter);

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

                Intent intent = new Intent(getContext(), LuckAnalysisActivity.class);
                intent.putExtra("name",adapter.GetId(position).toString());
                intent.putExtra("title", adapter.getItem(position).toString());
                startActivity(intent);
            }
        });
    }

    private void setSerch(){
        //        设置该SearchView默认是否自动缩小为图标
        search.setIconifiedByDefault(false);
//        设置该SearchView显示搜索图标
        search.setSubmitButtonEnabled(true);
//        设置该SearchView内默认显示的搜索文字
        search.setQueryHint("物质名称");
//        为SearchView组件设置事件的监听器
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //            单击搜索按钮时激发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(getContext(), LuckAnalysisActivity.class);
                intent.putExtra("name",query);
                intent.putExtra("title",query);
                startActivity(intent);

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
}
