package com.swufe.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class WriteAnwser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_anwser);
    }

    public void onclick(View view){
        Intent i  = new Intent(WriteAnwser.this,Question_detail.class);
        startActivity(i);
    }
}
