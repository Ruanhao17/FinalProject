package com.swufe.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.swufe.finalproject.App_data;
import com.swufe.finalproject.App_detail;
import com.swufe.finalproject.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class FirstFragment extends Fragment implements AdapterView.OnItemClickListener{
    private ListView listView;
    private String TAG="first";
    Handler handler;
    ArrayAdapter adapter;
    public String[] tag1;
    public String[] tag2;
    public String[] content;
    public String[] picture;
    int count;
    private ArrayList<HashMap<String, String>> listItems; // 存放文字、图片信息
    private SimpleAdapter listItemAdapter; // 适配器
    private int msgWhat = 7;
    List<String> items;
    public FirstFragment(){

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View  view= inflater.inflate(R.layout.fragment_first,null);
        listView = (ListView) view.findViewById(R.id.listview);

        Bmob.initialize(this.getActivity(), "5b5e2b1c75af83d4316468eb9ae614c4");
        App_data da = new App_data();
        BmobQuery<App_data> bmobQuery = new BmobQuery<App_data>();
        bmobQuery.findObjects(new FindListener<App_data>() {
            @Override
            public void done(List<App_data> object, BmobException e) {
                if(e==null){
                    count=object.size();
                    tag1=new String[count];
                    tag2=new String[count];
                    content=new String[count];
                    picture=new String[count];
                    for(int i=0;i<count;i++) {

                        Log.i(TAG,object.get(i).getObjectId() );
                        tag1[i]=object.get(i).getTag1();
                        tag2[i]=object.get(i).getTag2();
                        content[i]=object.get(i).getContent();
                        Log.i(TAG,content[i]);//详细描述

                    }
                    init();
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });

        return view;
    }




    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
// TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);



}

    private void initListView() {

    }
    private void init() {
        listItems=new ArrayList<HashMap<String, String>>();
        for(int i=count-1;i>=0;i--){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("ItemTag1",  tag1[i]);//TAG1
            map.put("ItemTag2",  tag2[i]);//TAG2
            map.put("ItemContent",  content[i]);//详细描述
            Log.i(TAG,content[i]);//详细描述
            listItems.add(map);
        }
        //生成适配器的Item和动态数组对应的元素
        listItemAdapter = new SimpleAdapter(this.getActivity(), listItems,//listTtems数据源
                R.layout.list_item1,//ListItem的XML布局实现
                new String[]{"ItemTag1", "ItemTag2","ItemContent"},
                new int[]{R.id.itemTag1, R.id.itemTag2,R.id.itemContent}
        );
        listView.setAdapter(listItemAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * Callback method to be invoked when an item in this AdapterView has
             * been clicked.
             * <p>
             * Implementers can call getItemAtPosition(position) if they need
             * to access the data associated with the selected item.
             *
             * @param parent   The AdapterView where the click happened.
             * @param view     The view within the AdapterView that was clicked (this
             *                 will be a view provided by the adapter)
             * @param position The position of the view in the adapter.
             * @param id       The row id of the item that was clicked.
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG,"能工作");
            }
                                            //实现事件处理方法
                                        }
        );//对listview的监听
    }


    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     * <p>
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param listv   The AdapterView where the click happened.
     * @param view     The view within the AdapterView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     *
     * @param id       The row id of the item that was clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> listv, View view, int position, long id) {
        Intent detail = new Intent(this.getActivity(), App_detail.class);
        startActivity(detail);
        Log.i(TAG,"onItemClick: listv="+listv);
        Log.i(TAG,"onItemClick: view="+view);
        Log.i(TAG,"onItemClick: position="+position);
        Log.i(TAG,"onItemClick: id="+id);
        HashMap<String,String> map=(HashMap<String,String>) listv.getItemAtPosition(position);
        String Tag1Str=map.get("ItemTag1");
        String Tag2Str =map.get("ItemTag2");
        String contentStr =map.get("ItemContent");
        Log.i(TAG,"onItemClick: Tag1Str="+Tag1Str);
        Log.i(TAG,"onItemClick: Tag2Str="+Tag2Str);
        TextView title=view.findViewById(R.id.itemTag1);
        String title2=String.valueOf(title.getText());
        Log.i(TAG,"onItemClick: title2="+title2);
    }
}

