package com.animee.lmgp.starfrag;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.animee.lmgp.R;

import com.animee.lmgp.utils.BitmapUtil;
import com.animee.lmgp.utils.PdfUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * PDF文件浏览中心
 * @author can
 *@since 2016-10-28
 */
public class PdfCenterDialog extends DialogFragment implements OnClickListener{

    private Button btn_close; //关闭识别结果框的按钮
    private ListView lv; //显示pdf文件集合控件
    private String[] strings; //pdf文件数组


    public PdfCenterDialog() {
        super();

    }



    @SuppressWarnings("deprecation")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View views = LayoutInflater.from(getActivity()).inflate(
                R.layout.dialog_pdf_center, null);
        lv = (ListView) views.findViewById(R.id.lv_pdf_center);

        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String path = PdfUtils.getString()[position];
                Intent intent = new Intent(getActivity(),PdfActivity.class);
                intent.putExtra("path", path);
                startActivity(intent);
            }
        });
        btn_close = (Button) views.findViewById(R.id.btn_close_pdf_center);
        btn_close.setOnClickListener(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        AlertDialog show = builder.show();
        show.getWindow().getAttributes().windowAnimations = R.style.dialog_ocr_result; //设置动画
        show.getWindow().setContentView(views);// 自定义布局
        show.getWindow().setLayout(LayoutParams.MATCH_PARENT,
                getActivity().getWindowManager().getDefaultDisplay().getHeight()*2/3);// 宽高
        show.getWindow().setGravity(Gravity.BOTTOM);// 位置 展示位置
        show.getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        return show;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_close_pdf_center: //关闭
                this.dismiss();
                break;
        }
    }

    /**
     * PDF文件适配器
     * @author can
     */
    class MyPdfAdapter extends BaseAdapter{

        private Context ct ;


        public MyPdfAdapter(Context c) {
            super();
            this.ct = c;

        }


        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            VH vh ;
            if(convertView==null){
                convertView = LayoutInflater.from(ct).inflate(R.layout.item_pdf_list, null);
                vh = new VH();
                vh.tv_name = (TextView) convertView
                        .findViewById(R.id.tv_name_pdf_list);
                vh.tv_time = (TextView) convertView
                        .findViewById(R.id.tv_time_pdf_list);
                vh.iv = (ImageView) convertView
                        .findViewById(R.id.iv_pdf_list);
                vh.tv_length = (TextView) convertView
                        .findViewById(R.id.tv_length_pdf_list);
                convertView.setTag(vh);
            }else{
                vh = (VH) convertView.getTag();
            }


            return convertView;
        }

        class VH {
            ImageView iv;
            TextView tv_name,tv_time,tv_length;
        }

    }

}
