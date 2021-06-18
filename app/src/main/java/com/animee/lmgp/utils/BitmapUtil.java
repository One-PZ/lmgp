package com.animee.lmgp.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.Image;
import android.os.Environment;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import static android.view.View.DRAWING_CACHE_QUALITY_HIGH;


public class BitmapUtil {


    /**
     * 通过文件路径获取文件的创建时间
     */
    public static String getFileCreatedTime(String filePath) {
        String string = "";
        File file = new File(filePath);
        Date date = new Date(file.lastModified());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日 HH:mm:ss");
        string = sdf.format(date);
        return string;
    }

    /**
     *  通过文件路径获取文件的大小,并自动格式化
     */
    public static String getVideoLength(String filePath) {
        String string = "0B";
        DecimalFormat df = new DecimalFormat("#.00");
        File file = new File(filePath);
        long length = file.length();
        if (length == 0) { // 文件大小为0,直接返回0B
            return string;
        }
        if (length < 1024) { // 文件小于1KB,单位为 B
            string = df.format((double) length) + "B";
        } else if (length < 1048576) {// 文件小于1M,单 位为 KB
            string = df.format((double) length / 1024) + "K";
        } else if (length < 1073741824) {// 文件小于1G,单位为 MB
            string = df.format((double) length / 1048576) + "M";
        } else {
            string = df.format((double) length / 1073741824) + "G";
        }
        return string;
    }

                /**
           * 把Bitmap转Byte
           */
             public static byte[] Bitmap2Bytes(Bitmap bm){
                 ByteArrayOutputStream baos = new ByteArrayOutputStream();
                 bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
                 return baos.toByteArray();
             }

    /**
     * View转换为Bitmap图片
     *
     * @param view
     * @return Bitmap
     */
    public static Bitmap convertViewToBitmap(View view) {
        //创建Bitmap,最后一个参数代表图片的质量.
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        //创建Canvas，并传入Bitmap.
        Canvas canvas = new Canvas(bitmap);
        //View把内容绘制到canvas上，同时保存在bitmap.
        view.draw(canvas);
        return bitmap;
    }

    /**
     * 保存Bitmap图片并返回路径
     *
     * @param bitmap
     * @return String
     */
    public static String saveBitmap(Bitmap bitmap) {
        //获取内部存储状态
        String state = Environment.getExternalStorageState();
        //如果状态不是mounted，无法读写
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            //通过Random()类生成数组命名
            Random random = new Random();
            String fileName2 = String.valueOf(random.nextInt(Integer.MAX_VALUE));
            String Path = PdfUtils.ADDRESS + File.separator + "image";
            File file = new File(Path);
            if (!file.exists())
                file.mkdirs();
            String allPath = Path + File.separator+ fileName2 + ".png";
            try {
                File file1 = new File(allPath);
                FileOutputStream out = new FileOutputStream(file1);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                out.close();
                return  allPath;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 该方式原理主要是：View组件显示的内容可以通过cache机制保存为bitmap
     */
    public static Bitmap createBitmapFromView(View view) {
        Bitmap bitmap = null;
        //开启view缓存bitmap
        view.setDrawingCacheEnabled(true);
        //设置view缓存Bitmap质量
        view.setDrawingCacheQuality(DRAWING_CACHE_QUALITY_HIGH);
        //获取缓存的bitmap
        Bitmap cache = view.getDrawingCache();
        if (cache != null && !cache.isRecycled()) {
            bitmap = Bitmap.createBitmap(cache);
            bitmap.copy(Bitmap.Config.ARGB_8888, true);
        }
        //销毁view缓存bitmap
        view.destroyDrawingCache();
        //关闭view缓存bitmap
        view.setDrawingCacheEnabled(false);
        return bitmap;
    }


}
