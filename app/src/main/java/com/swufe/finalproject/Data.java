package com.swufe.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;

import com.google.android.material.tabs.TabLayout;
import com.swufe.finalproject.R;

public class Data extends AppCompatActivity implements View.OnClickListener,Runnable {
    private Button btn1;
    EditText Tag1;
    EditText Tag2;
    EditText Content;
    private final String TAG="Data";
    private String tag1="";
    private String tag2="";
    private String content="";
    public static int count=0;
    Handler handler;
    SearchView mSearchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        com.swufe.finalproject.MyPageAdapter pageAdapter = new com.swufe.finalproject.MyPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pageAdapter);
        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        mSearchView = (SearchView) findViewById(R.id.searchview);
//        mSearchView.setSubmitButtonEnabled(true);
        btn1 = (Button) findViewById(R.id.button);
        btn1.setOnClickListener(this);

    }

    public void onClick(View v){
        Intent i = new Intent(this, com.swufe.finalproject.Use_data_1.class);
        startActivity(i);
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {

    }
}