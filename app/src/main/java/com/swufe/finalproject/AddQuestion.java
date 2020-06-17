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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class AddQuestion extends AppCompatActivity {
    TextView submit;
    EditText Tag1;
    EditText Tag2;
    EditText Content;
    EditText price;
    private ArrayList<HashMap<String, Object>> listItems;
    HashMap<String, Object> map;
    List<String> imgList = new ArrayList<String>();
    String photoPath;
    com.swufe.finalproject.Question Q;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        Tag1=findViewById(R.id.editText5);
        Tag2=findViewById(R.id.editText2);
        Content=findViewById(R.id.editText4);
        submit = findViewById(R.id.text1);
        price = findViewById(R.id.editText3);

        Bmob.initialize(this, "5b5e2b1c75af83d4316468eb9ae614c4");
        Q= new com.swufe.finalproject.Question();

    }
    public void onclick(View view){
        Intent i = new Intent(this,QandA.class);
        startActivity(i);
    }
    public void onclick2(View v){
        if(!(Tag1.getText().toString()).equals("")&&!(Tag2.getText().toString()).equals("")&&!(Content.getText().toString()).equals("")&&!(price.getText().toString()).equals("")) {
            Q.setTag1(Tag1.getText().toString());
            Q.setTag2(Tag2.getText().toString());
            Q.setAbstract_content(Content.getText().toString());
            Q.setPrice(Float.parseFloat(price.getText().toString()));
            Q.save(new SaveListener<String>() {
                @Override
                public void done(String objectId, BmobException e) {
                    if (e == null) {
                        Toast.makeText(AddQuestion.this, "新增问答成功", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(AddQuestion.this, QandA.class);
                        startActivity(i);
                    } else {
                        Log.i("worong", "wrong:" + e.getMessage());
                        Toast.makeText(AddQuestion.this, "新增问答失败", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else{
            Toast.makeText(AddQuestion.this, "信息不完善，请重新填写", Toast.LENGTH_SHORT).show();
        }

    }
    public void  onclick3(View v){
        if (ActivityCompat.checkSelfPermission(AddQuestion.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager
                .PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddQuestion.this,
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
                    map.put("path",photoPath.substring(photoPath.lastIndexOf("/")+1,photoPath.length()-1));
                    listItems.add(map);
                    Log.i("photo", "onActivityResult: "+photoPath.substring(photoPath.lastIndexOf("/")+1,photoPath.length()-1));
                    Log.i("photoPath", "-----str---->路径  " + photoPath);
                    Log.i("photoPath", "----uri---->路径  " + Uri.parse(photoPath));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
