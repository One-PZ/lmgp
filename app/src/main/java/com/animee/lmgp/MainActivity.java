package com.animee.lmgp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.animee.lmgp.common.Common;
import com.animee.lmgp.luckfrag.LuckFragment;
import com.animee.lmgp.mefrag.MeFragment;
import com.animee.lmgp.parnterfrag.PartnerFragment;
import com.animee.lmgp.starfrag.PdfCenterDialog;
import com.animee.lmgp.starfrag.StarFragment;
import com.animee.lmgp.utils.DBHelper;
import com.animee.lmgp.utils.FillPdfUtils;
import com.animee.lmgp.utils.URLContent;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import org.json.JSONArray;
import org.json.JSONObject;


import java.util.HashMap;


public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    RadioGroup mainRg;
//    声明四个按钮对应的Fragment对象
    Fragment starFrag,luckFrag,partnerFrag,meFrag;
    private FragmentManager manager;
    public static SharedPreferences star_pref;

    private static MainActivity mainActivity;

    private ProgressDialog myDialog; // 进度框

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity = this;
        mainRg = findViewById(R.id.main_rg);
//        设置监听点击了哪个单选按钮
        mainRg.setOnCheckedChangeListener(this);

        initProgress();
        //加载数据库
        star_pref =  this.getSharedPreferences("login", Context.MODE_PRIVATE);
        Common.DBHELP = new DBHelper(this);



//        创建碎片对象
        starFrag = new StarFragment();
        luckFrag = new LuckFragment();
        partnerFrag = new PartnerFragment();
        meFrag = new MeFragment();

//        将四个Fragment进行动态加载，一起加载到布局当中。replace       add/hide/show
        addFragmentPage();

        final ImageView imageView = (ImageView) findViewById(R.id.title_iv_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(imageView);
            }
        });

        initPython();

    }

    void initPython(){
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }
    }

    /**
     * 初始化进度框
     */
    private void initProgress() {
        myDialog = new ProgressDialog(this, ProgressDialog.THEME_HOLO_LIGHT);
        myDialog.setIndeterminateDrawable(getResources().getDrawable(
                R.drawable.progress_ocr));
        myDialog.setMessage("正在更新...");
        myDialog.setCanceledOnTouchOutside(false);
        myDialog.setCancelable(false);
    }


    public static MainActivity getMain(){
        return mainActivity;
    }





    /**
     * @des 将主页当中的碎片一起加载进入布局，有用的显示，暂时无用的隐藏
     * */
    private void addFragmentPage() {
//        1.创建碎片管理者对象
        manager = getSupportFragmentManager();
//        2.创建碎片处理事务的对象
        FragmentTransaction transaction = manager.beginTransaction();
//        3.将四个Fragment统一的添加到布局当中
        transaction.add(R.id.main_layout_center,starFrag);
        transaction.add(R.id.main_layout_center,partnerFrag);
        transaction.add(R.id.main_layout_center,luckFrag);
        transaction.add(R.id.main_layout_center,meFrag);
//        4.隐藏后面的三个
        transaction.hide(partnerFrag);
        transaction.hide(luckFrag);
        transaction.hide(meFrag);
//        5.提交碎片改变后的事务
        transaction.commit();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction transaction = manager.beginTransaction();
        switch (checkedId) {
            case R.id.main_rb_star:
                transaction.hide(partnerFrag);
                transaction.hide(luckFrag);
                transaction.hide(meFrag);
                transaction.show(starFrag);
                break;
            case R.id.main_rb_partner:
                transaction.hide(starFrag);
                transaction.hide(luckFrag);
                transaction.hide(meFrag);
                transaction.show(partnerFrag);
                break;
            case R.id.main_rb_luck:
                transaction.hide(starFrag);
                transaction.hide(partnerFrag);
                transaction.hide(meFrag);
                transaction.show(luckFrag);
                break;
            case R.id.main_rb_me:
                transaction.hide(starFrag);
                transaction.hide(luckFrag);
                transaction.hide(partnerFrag);
                transaction.show(meFrag);
                break;
        }
        transaction.commit();
    }

    //弹出按钮框
    private void showPopupMenu(final View view) {
        final PopupMenu popupMenu = new PopupMenu(this,view);
        //menu 布局
        popupMenu.getMenuInflater().inflate(R.menu.menu,popupMenu.getMenu());
        //点击事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.report:
                        break;

                    case R.id.ReadReport:
                        lookPdf();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        //关闭事件
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
             //   Toast.makeText(view.getContext(),"close",Toast.LENGTH_SHORT).show();
            }
        });
        //显示菜单，不要少了这一步
        popupMenu.show();
    }

    /**
     * 查看PDF文件
     */
    private void lookPdf() {
        PdfCenterDialog dialog = new PdfCenterDialog();
        android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setTransition(android.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        dialog.show(ft, "");
    }







    private void Update(){
        myDialog.show();
      Handler handler = new Handler(){
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case -1:
                    case -2:
                        myDialog.cancel();
                      //  Toast.makeText(MainActivity.this,"出错啦！",Toast.LENGTH_SHORT).show();
                        break;
                    case 1:

                        break;
                }
            };
        };
      URLContent.UpDateThread(handler);

    }








}
