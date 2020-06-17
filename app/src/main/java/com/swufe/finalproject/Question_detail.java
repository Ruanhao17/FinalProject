package com.swufe.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class Question_detail extends AppCompatActivity implements AdapterView.OnItemClickListener{
    String TAG = "Question_detail";
    TextView tag1;
    TextView tag2;
    TextView question;
    TextView viewNumber;
    TextView anwserNumber;
    TextView follow;
    ListView myListView;
    public int[] viewNumberList;
    public int[] approved;
    public String[] content;
    public String[] id;
    public String[] name;
    public BmobFile[] files;
    String name1;
    int count;
    private SimpleAdapter myAdapter;
    private ArrayList<HashMap<String, Object>> listItems=new ArrayList<HashMap<String, Object>>();
    HashMap<String, Object> map = new HashMap<String, Object>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);
        tag1 = findViewById(R.id.tag1);
        tag2 = findViewById(R.id.tag2);
        question = findViewById(R.id.Q_detail);
        anwserNumber = findViewById(R.id.AnswerNumber);
        viewNumber = findViewById(R.id.textView14);
        follow = findViewById(R.id.textView16);
        myListView = findViewById(R.id.AnswerList);


        Bmob.initialize(this, "5b5e2b1c75af83d4316468eb9ae614c4");
        BmobQuery<Question> bmobQuery = new BmobQuery<>();
        Question Q = new Question();
        bmobQuery.addWhereEqualTo("objectId",getIntent().getStringExtra("id"));
        bmobQuery.findObjects(new FindListener<Question>() {
            @Override
            public void done(List<Question> object, BmobException e) {
                if(e==null){
                    tag1.setText(object.get(0).getTag1());
                    tag2.setText(object.get(0).getTag2());
                    question.setText(object.get(0).getAbstract_content());
                    viewNumber.setText("浏览量："+object.get(0).getHeat());
                    Log.i("bmob","content"+question);
                }else{
                    Log.i("bmob1","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
        BmobQuery<Answer> AnswerQuery = new BmobQuery<>();
        Answer A = new Answer();
        AnswerQuery.addWhereEqualTo("Question",getIntent().getStringExtra("id"));
        AnswerQuery.findObjects(new FindListener<Answer>() {
            @Override
            public void done(List<Answer> object, BmobException e) {
                if(e==null){
                    count=object.size();
                    anwserNumber.setText("回答数："+count);
                    viewNumberList=new int[count];
                    approved=new int[count];
                    content=new String[count];
                    id =new String[count];
                    name = new String[count];
                    for(int i=0;i<count;i++){

                        /*BmobQuery<_User> UserQuery = new BmobQuery<>();
                        _User user = new _User();
                        UserQuery.addWhereEqualTo("objectId",object.get(i).getUserName().getObjectId());
                        int finalI = i;
                        UserQuery.findObjects(new FindListener<_User>() {
                            @Override
                            public void done(List<_User> object, BmobException e) {
                                if(e==null){
                                    map.put("name",object.get(finalI).getUsername());
                                    Log.i(TAG, "done: "+map.get("name"));
                                }else{
                                    Log.i("bmob2","失败："+e.getMessage()+","+e.getErrorCode());
                                }
                            }
                        });*/

                        id[i] = object.get(i).getObjectId();
                        content[i]=object.get(i).getContent();
                        approved[i]=object.get(i).getApproved();
                        viewNumberList[i]= object.get(i).getViewNumber();

                    }
                    init();
                }else{
                    Log.i("bmob3","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });


        //点击排序后的响应
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                String[] languages = getResources().getStringArray(R.array.sort);
                Toast.makeText(Question_detail.this, "你点击的是:"+languages[pos], Toast.LENGTH_SHORT).show();
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
    public void Onclick2(View v){
        Intent i = new Intent(Question_detail.this,QandA.class);
        startActivity(i);
    }

    private void init() {
        for(int i=count-1;i>=0;i--){
            map.put("viewNumber",  viewNumberList[i]);
            map.put("approved",  approved[i]);
            map.put("ItemContent",content[i].length()>=30? content[i].substring(0,30)+"...":content[i]);//详细描述
            map.put("ID",id[i]);
            map.put("Acontent",content[i]);
            map.put("name","用户"+i);



            listItems.add(map);
        }
        //生成适配器的Item和动态数组对应的元素
        myAdapter = new SimpleAdapter(this,listItems,R.layout.answer_items,
                new String[]{"viewNumber", "approved","ItemContent","name","ID"},
                new int[]{R.id.textView11,R.id.approvedNumber,R.id.itemContent,R.id.name,R.id.AnwserId});
        myListView.setAdapter(myAdapter);
        myListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView id_content = view.findViewById(R.id.AnwserId);
        TextView name = view.findViewById(R.id.name);
        String nameInfo = name.getText().toString();
        String idInfo = id_content.getText().toString();
        Answer answer = new Answer();
        answer.setViewNumber(answer.getViewNumber()+1);
        answer.update(idInfo, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Toast.makeText(Question_detail.this, "更新成功"+answer.getUpdatedAt(), Toast.LENGTH_SHORT).show();
                }else{;
                    Toast.makeText(Question_detail.this, "更新失败"+ e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        });
        Log.i("bmob","id="+idInfo);
        Intent i = new Intent(this,Answer_detail.class);
        i.putExtra("id",idInfo);
        i.putExtra("name",nameInfo);

        startActivity(i);
    }
}
