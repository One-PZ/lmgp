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

import java.util.List;
import java.util.Map;

public class LuckBaseAdapterByMatter extends BaseAdapter {
    private final Map<String, Bitmap> contentlogoImgMap;
    Context context;


    public LuckBaseAdapterByMatter(Context context) {
        this.context = context;

        contentlogoImgMap = AssetsUtils.getContentlogoImgMap();
    }

    @Override
    public int getCount() {
        return contentlogoImgMap.size();
    }

    @Override
    public Object getItem(int position) {
        return contentlogoImgMap.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
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
