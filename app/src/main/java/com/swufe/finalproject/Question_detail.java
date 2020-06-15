package com.swufe.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Question_detail extends AppCompatActivity {
    TextView tag1;
    TextView tag2;
    TextView question;
    TextView viewNumber;
    TextView follow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);
        tag1 = findViewById(R.id.tag1);
        tag2 = findViewById(R.id.tag2);
        question = findViewById(R.id.Q_detail);
        viewNumber = findViewById(R.id.textView14);
        follow = findViewById(R.id.textView16);

        tag1.setText(getIntent().getStringExtra("tag1"));
        tag2.setText(getIntent().getStringExtra("tag2"));
        question.setText(getIntent().getStringExtra("Acontent"));
        viewNumber.setText("浏览量："+getIntent().getStringExtra("heat"));

        //点击排序后的响应
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                String[] languages = getResources().getStringArray(R.array.sort);
                Toast.makeText(Question_detail.this, "你点击的是:"+languages[pos], 2000).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

    }

    public void  Onclick(View v){
        Intent i = new Intent(Question_detail.this,WriteAnwser.class);
        startActivity(i);
    }
}
