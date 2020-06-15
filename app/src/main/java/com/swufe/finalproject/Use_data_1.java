package com.swufe.finalproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.swufe.finalproject.R;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class Use_data_1 extends AppCompatActivity implements View.OnClickListener {
    private Button btn1;
    EditText Tag1;
    EditText Tag2;
    EditText Content;
    com.swufe.finalproject.User_data da;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_data_1);
        Tag1=findViewById(R.id.editText5);
        Tag2=findViewById(R.id.editText2);
        Content=findViewById(R.id.editText);
        btn1 = (Button) findViewById(R.id.button2);
        btn1.setOnClickListener(this);
        Bmob.initialize(this, "5b5e2b1c75af83d4316468eb9ae614c4");
        da= new com.swufe.finalproject.User_data();
    }
    public void onClick(View v){
        if(!(Tag1.getText().toString()).equals("")||!(Tag2.getText().toString()).equals("")||!(Content.getText().toString()).equals("")) {
            da.setTag1(Tag1.getText().toString());
            da.setTag2(Tag2.getText().toString());
            da.setContent(Content.getText().toString());
            da.save(new SaveListener<String>() {
                @Override
                public void done(String objectId, BmobException e) {
                    if (e == null) {
                        Toast.makeText(Use_data_1.this, "上传笔记成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.i("worong", "wrong:" + e.getMessage());
                        Toast.makeText(Use_data_1.this, "上传笔记失败", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else{
            Toast.makeText(Use_data_1.this, "信息不完善，不能上传", Toast.LENGTH_SHORT).show();
        }
        Intent i = new Intent(this, Data.class);
        startActivity(i);
    }
    public static final String DOCX = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
    public static final String XLS = "application/vnd.ms-excel application/x-excel";
    public static final String XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static final String PPT = "application/vnd.ms-powerpoint";
    public static final String PPTX = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
    public static final String PDF = "application/pdf";
    public static final String DOC = "application/msword";

    public void Onclick2(View v){
        /*Intent intent = new Intent();
        intent.setType("application/msword|application/vnd.openxmlformats-officedocument.wordprocessingml.document" +
                "|application/vnd.ms-powerpoint|application/vnd.openxmlformats-officedocument.presentationml.presentation|application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, 1);*/
        try {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            //设置doc,docx,ppt,pptx,pdf 5种类型
            intent.setType("application/msword|application/vnd.openxmlformats-officedocument.wordprocessingml.document" +
                    "|application/vnd.ms-powerpoint|application/vnd.openxmlformats-officedocument.presentationml.presentation|application/pdf");
            //在API>=19之后设置多个类型采用以下方式，setType不再支持多个类型
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                intent.putExtra(Intent.EXTRA_MIME_TYPES,
                        new String[]{DOC,DOCX, PPT, PPTX,PDF});
            }
            startActivityForResult(intent, 1);
        } catch (ActivityNotFoundException e) {
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {        //处理特定的数据结果
        Log.i("user","loading");
        if (requestCode == 1) {
            Uri uri = intent.getData();
            String path = getPath(uri,null);
            Log.i("user","path="+"/手机存储"+path.substring(10));
            BmobFile bmobFile = new BmobFile(new File(path));
            bmobFile.uploadblock(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if(e==null){
                        //bmobFile.getFileUrl()--返回的上传文件的完整地址
                        Toast.makeText(Use_data_1.this, "上传文件成功"+bmobFile.getFileUrl(), Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Use_data_1.this, "上传文件失败"+ e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onProgress(Integer value) {
                    // 返回的上传进度（百分比）
                }
            });
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }
    private String getPath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }

            cursor.close();
        }
        return path;
    }

}
