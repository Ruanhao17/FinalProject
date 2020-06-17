package com.swufe.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.security.auth.login.LoginException;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;


public class QandA extends AppCompatActivity implements OnBannerListener,AdapterView.OnItemClickListener{
    Banner banner;
    ListView myListView;
    public static List<?> images=new ArrayList<>();
    private static final String TAG = "Q&A";
    public String[] tag1;
    public String[] tag2;
    public String[] content;
    public String[] id;
    public int[] reputation;
    public boolean[] is_anwser;
    public int[] heat;
    public float[] price;
    int count;
    private SimpleAdapter myAdapter;
    private ArrayList<HashMap<String, Object>> listItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qa);
        banner=(Banner)findViewById(R.id.banner);
        myListView = (ListView) findViewById(R.id.Qlist);
        String[] urls = getResources().getStringArray(R.array.url);
        Log.i(TAG,"url:"+urls);
        List list = Arrays.asList(urls);
        Log.i(TAG,"list:"+list);



        images = new ArrayList(list);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(1500);
        banner.setImages(images)
                .setImageLoader(new GlideImageLoader())
                .setOnBannerListener(this)
                .start();

        Bmob.initialize(this, "5b5e2b1c75af83d4316468eb9ae614c4");
        BmobQuery<Question> bmobQuery = new BmobQuery<>();
        Question Q = new Question();
        bmobQuery.findObjects(new FindListener<Question>() {
            @Override
            public void done(List<Question> object, BmobException e) {
                if(e==null){
                    count=object.size();
                    tag1=new String[count];
                    tag2=new String[count];
                    content=new String[count];
                    reputation=new int[count];
                    is_anwser=new boolean[count];
                    heat=new int[count];
                    price=new float[count];
                    id =new String[count];

                    for(int i=0;i<count;i++) {

                        id[i] = object.get(i).getObjectId();
                        tag1[i]=object.get(i).getTag1();
                        tag2[i]=object.get(i).getTag2();
                        content[i]=object.get(i).getAbstract_content();
                        reputation[i]=object.get(i).getReputationValue();
                        is_anwser[i]=object.get(i).isIs_anwser();
                        heat[i]=object.get(i).getHeat();
                        price[i]=object.get(i).getPrice();
                        Log.i(TAG,content[i]+reputation[i]);//详细描述
                    }
                    init();
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

    public void OnBannerClick(int position) {
        Toast.makeText(getApplicationContext(),"你点击了："+position,Toast.LENGTH_SHORT).show();
    }


    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView id_content = view.findViewById(R.id.Id);
        String idInfo = id_content.getText().toString();
        Question question = new Question();
        question.setHeat(question.getHeat()+2);
        question.update(idInfo, new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){
                    Toast.makeText(QandA.this, "更新成功"+question.getUpdatedAt(), Toast.LENGTH_SHORT).show();
                }else{;
                    Toast.makeText(QandA.this, "更新失败"+ e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        });
        Log.i("bmob","id="+idInfo);
        Log.i(TAG, "onItemClick: "+question.getHeat()+1);
        Log.i(TAG, "onItemClick: "+question.getHeat()+1);
        Intent i = new Intent(this,Question_detail.class);
        i.putExtra("id",idInfo);

        startActivity(i);
    }
    public void onclick(View v){
        Intent i = new Intent(this,AddQuestion.class);
        startActivity(i);
    }

    /*class MyAadpter extends ArrayAdapter {

        public MyAadpter(Context context, int resourse, List<HashMap<String, Object>> list){
            super(context,resourse,list);
        }
        public View getView(int position, View converView, ViewGroup parent){
            View itemViem = converView;



            if(itemViem == null){
                itemViem = LayoutInflater.from(getContext()).inflate(R.layout.question_items,parent,false);
            }

            TextView tag1 = (TextView) itemViem.findViewById(R.id.itemTag1);
            TextView tag2 = (TextView) itemViem.findViewById(R.id.itemTag2);
            TextView content = (TextView) itemViem.findViewById(R.id.itemContent);
            TextView reputation = (TextView) itemViem.findViewById(R.id.textView6);
            TextView is_anwser = (TextView) itemViem.findViewById(R.id.textView7);
            TextView price = (TextView) itemViem.findViewById(R.id.textView9);
            TextView heat = (TextView) itemViem.findViewById(R.id.textView11);
            tag1.setText(listItems.get(position).get("ItemTag1").toString());
            tag2.setText(listItems.get(position).get("ItemTag2").toString());
            content.setText(listItems.get(position).get("ItemContent").toString());
            reputation.setText(listItems.get(position).get("ItemReputation").toString());
            is_anwser.setText(listItems.get(position).get("ItemIs_anwser").toString()=="true" ? "已解答":"未解答");
            heat.setText(listItems.get(position).get("ItemHeat").toString());
            price.setText(listItems.get(position).get("ItemPrice").toString());

            return itemViem;
        }
    }*/

    private void init() {
        Log.i(TAG,"init______");
        listItems=new ArrayList<HashMap<String, Object>>();
        for(int i=count-1;i>=0;i--){
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemTag1",  tag1[i]);//TAG1
            map.put("ItemTag2",  tag2[i]);//TAG2
            map.put("ItemContent",content[i].length()>=30? content[i].substring(0,30)+"...":content[i]);//详细描述
            map.put("ItemReputation",reputation[i]);
            map.put("ItemIs_anwser",is_anwser[i]==true ? "[已解答]":"[待解答]");
            map.put("ItemHeat",heat[i]);
            map.put("ItemPrice",price[i]);
            map.put("ID",id[i]);
            map.put("Acontent",content[i]);

            listItems.add(map);
        }
        //生成适配器的Item和动态数组对应的元素\
        myAdapter = new SimpleAdapter(this,listItems,R.layout.question_items,
                new String[]{"ItemTag1", "ItemTag2","ItemContent","ItemReputation","ItemIs_anwser","ItemHeat","ItemPrice","Acontent","ID"},
                new int[]{R.id.itemTag1, R.id.itemTag2,R.id.itemContent,R.id.textView6,R.id.textView7,R.id.textView11,R.id.textView9,R.id.Acontent,R.id.Id});
        myListView.setAdapter(myAdapter);
        myListView.setOnItemClickListener(this);
    }
}

