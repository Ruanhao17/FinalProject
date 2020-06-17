package com.swufe.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

public class WriteAnwser extends AppCompatActivity {
    String photoPath;
    List<String> imgList = new ArrayList<String>();
    EditText content;
    ListView listView;
    private SimpleAdapter myAdapter;
    private ArrayList<HashMap<String, Object>> listItems;
    HashMap<String, Object> map;
    Answer answer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_anwser);
        content = findViewById(R.id.editText4);
        answer = new Answer();
        listView = (ListView)findViewById(R.id.file_item);
        map = new HashMap<String, Object>();
        listItems=new ArrayList<HashMap<String, Object>>();
        Bmob.initialize(this, "5b5e2b1c75af83d4316468eb9ae614c4");
        init();
    }

    public void onclick(View view){
        Intent i  = new Intent(WriteAnwser.this,Question_detail.class);
        startActivity(i);
    }
    public void  onclick2(View v){
        if (ActivityCompat.checkSelfPermission(WriteAnwser.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager
                .PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(WriteAnwser.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        }
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.
                EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "请选择图片"),
                101);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent datas) {
        super.onActivityResult(requestCode, resultCode, datas);
        if (requestCode == 101 && resultCode == RESULT_OK) {
            if (datas != null) {
                try {
                    Uri uri = datas.getData();
                    // 这里开始的第二部分，获取图片的路径：
                    String[] proj = {MediaStore.Images.Media.DATA};
                    Cursor cursor = null;
                    if (Build.VERSION.SDK_INT < 11) {
                        cursor = managedQuery(uri, proj, null, null,
                                null);
                    } else {
                        CursorLoader cursorLoader = new CursorLoader(this, uri, proj,
                                null, null, null);
                        cursor = cursorLoader.loadInBackground();
                    }
                    // 这个是获得用户选择的图片的索引值
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.
                            Media.DATA);
                    cursor.moveToFirst();
                    // 最后根据索引值获取图片路径
                    photoPath = cursor.getString(column_index);
                    if (!photoPath.equals("") || photoPath != null) {
                        imgList.add(photoPath);
                    }
                    map.put("path",photoPath.substring(photoPath.lastIndexOf("/")+1,photoPath.length()-1)+"g");
                    listItems.add(map);
                    Log.i("photo", "onActivityResult: "+photoPath.substring(photoPath.lastIndexOf("/")+1,photoPath.length()-1));
                    Log.i("photoPath", "-----str---->路径  " + photoPath);
                    Log.i("photoPath", "----uri-0--->路径  " + Uri.parse(photoPath));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void onclick3(View v){
        if(!(content.getText().toString()).equals("")) {
            File file = new File(photoPath);
            Log.i("done", "onclick3: "+photoPath);
            BmobFile bmobFile = new BmobFile(file);
            bmobFile.uploadblock(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if(e==null){
                        //bmobFile.getFileUrl()--返回的上传文件的完整地址
                        Log.i("上传：", "done: "+"上传了图片");
                        answer.setContent(content.getText().toString());
                        answer.setFile(bmobFile);
                        answer.save(new SaveListener<String>() {
                            @Override
                            public void done(String objectId, BmobException e) {
                                if (e == null) {
                                    Toast.makeText(WriteAnwser.this, "上传答案成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.i("上传：", "wrong:" + e.getMessage());
                                    Toast.makeText(WriteAnwser.this, "上传答案失败"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else{
                        Log.i("上传：", "done:上传图片失败： "+e.getMessage());
                    }
                }
                @Override
                public void onProgress(Integer value) {
                    // 返回的上传进度（百分比）
                }
            });
        }
        else{
            Toast.makeText(WriteAnwser.this, "请填写答案！", Toast.LENGTH_SHORT).show();

        }
        Intent i = new Intent(this, Question_detail.class);
        startActivity(i);
    }
    public void init(){
        myAdapter = new SimpleAdapter(WriteAnwser.this,listItems,R.layout.file_item,
                new String[]{"path"},
                new int[]{R.id.textView12});
        listView.setAdapter(myAdapter);
    }
}
