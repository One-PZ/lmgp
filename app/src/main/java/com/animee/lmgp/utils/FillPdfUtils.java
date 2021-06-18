package com.animee.lmgp.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.animee.lmgp.common.Common;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BadPdfFormatException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfFormField;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PushbuttonField;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class FillPdfUtils {

    private static final String imageDemo = "CurveDemo.jpg";

    private static final int PDF_SAVE_START = 1;// 保存PDF文件的开始意图
    private static final int PDF_SAVE_RESULT = 2;// 保存PDF文件的结果开始意图

    Context context;

    private Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case PDF_SAVE_START:
                    Toast.makeText(context, "保存失败", Toast.LENGTH_SHORT)
                            .show();
                    break;

                case PDF_SAVE_RESULT:

                    Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT)
                            .show();
                    break;
            }
            return false;
        }
    });




    //更换图片
    private void ReplacePicture(Image image, AcroFields form) throws IOException, DocumentException {
        if (image != null) {
            //从pdf的form域中取得对象，
            //tmpname为pdf中image的名称
            PushbuttonField pushbuttonField = form.getNewPushbuttonFromField(imageDemo);
            pushbuttonField.setImage(image);//将对象放入pushbuttonField

            PdfFormField editFormField = pushbuttonField.getField();  //生成fromfield

            form.replacePushbuttonField(imageDemo, editFormField);//放入pdf
        }
    }

    //更换图片
    private void ReplacePicture(String path, AcroFields form, PdfStamper stamper) throws IOException, DocumentException {
        // 图片类的内容处理
        if (path == null) {
            return;
        }
        int pageNo = form.getFieldPositions( PdfUtils.ADDRESS + File.separator + "image").get(0).page;
        Rectangle signRect = form.getFieldPositions( PdfUtils.ADDRESS + File.separator + "image").get(0).position;
        float x = signRect.getLeft();
        float y = signRect.getBottom();
        // 根据路径读取图片
        Image image = Image.getInstance(path);
        // 获取图片页面
        PdfContentByte under = stamper.getOverContent(pageNo);
        // 图片大小自适应
        image.scaleToFit(signRect.getWidth(), signRect.getHeight());
        // 添加图片
        image.setAbsolutePosition(x, y);
        under.addImage(image);
    }


    public static String GetDateTimeString(String demo) {
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(demo);
        return sdf.format(date);
    }



    public static Date GetTimeString2Date(String demo) {
        try {
            //时间
            java.text.SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.parse(demo);

        }catch (Exception e){
            return null;
        }
    }

    public static String GetTimeData2String(Date demo) {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(demo);
    }

    public static String Data2String(String dateTime,Context context){
        DateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            date = iso8601Format.parse(dateTime);
        } catch (ParseException e) {
            Log.e("TAG", "Parsing ISO8601 datetime failed", e);
        }

        long when = date.getTime();
        int flags = 0;
        flags |= android.text.format.DateUtils.FORMAT_SHOW_TIME;
        flags |= android.text.format.DateUtils.FORMAT_SHOW_DATE;
        flags |= android.text.format.DateUtils.FORMAT_ABBREV_MONTH;
        flags |= android.text.format.DateUtils.FORMAT_SHOW_YEAR;

        String finalDateTime = android.text.format.DateUtils.formatDateTime(context,
                when + TimeZone.getDefault().getOffset(when), flags);

        return finalDateTime;
    }


}
