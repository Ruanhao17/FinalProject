package com.swufe.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class AddQuestion extends AppCompatActivity {
    TextView submit;
    EditText Tag1;
    EditText Tag2;
    EditText Content;
    EditText price;
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
                        Toast.makeText(AddQuestion.this, "上传笔记成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.i("worong", "wrong:" + e.getMessage());
                        Toast.makeText(AddQuestion.this, "上传笔记失败", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else{
            Toast.makeText(AddQuestion.this, "信息不完善，请重新填写", Toast.LENGTH_SHORT).show();
        }
        Intent i = new Intent(this, QandA.class);
        startActivity(i);
    }
}
