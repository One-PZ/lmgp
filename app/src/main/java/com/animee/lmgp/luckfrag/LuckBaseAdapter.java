package com.animee.lmgp.luckfrag;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.animee.lmgp.R;

import com.animee.lmgp.utils.AssetsUtils;

import java.security.Key;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


import de.hdodenhof.circleimageview.CircleImageView;

public class LuckBaseAdapter extends BaseAdapter {
    private final Map<String, Bitmap> contentlogoImgMap;
    Context context;
    Map<Integer,String> mDatas;
    Integer[] set;


    public LuckBaseAdapter(Context context, Map<Integer,String> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        contentlogoImgMap = AssetsUtils.getContentlogoImgMap();

        set = mDatas.keySet().toArray(new Integer[0]);

    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(set[position]);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public Integer GetId(int position) {
        return set[position];
    }

    public void Remove(Integer key) {
        mDatas.remove(key);
        set = mDatas.keySet().toArray(new Integer[0]);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_luck_gv,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.starIv.setText(set[position].toString());
        holder.starTv.setText( mDatas.get(set[position]));
        return convertView;
    }

    class ViewHolder{
        TextView starIv;
        TextView starTv;
        public ViewHolder(View view){
            starIv = view.findViewById(R.id.item_luck_iv);
            starTv = view.findViewById(R.id.item_luck_tv);
        }
    }
}
