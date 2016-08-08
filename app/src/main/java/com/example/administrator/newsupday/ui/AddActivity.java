package com.example.administrator.newsupday.ui;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.newsupday.MainActivity;
import com.example.administrator.newsupday.R;
import com.example.administrator.newsupday.common.SharePrefenceUtil;
import com.example.administrator.newsupday.common.Tools;
import com.example.administrator.newsupday.service.MyReciver;
import com.example.administrator.newsupday.ui.adapter.ListViewAdapter;
import com.example.administrator.newsupday.ui.honview.HorizontalListView;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar tool;
    private TextView inter;
    private TextView android;
    private TextView hot;
    private TextView sichuan;
    private TextView xingzuo;
    private TextView junshi;
    private HorizontalListView listView;
    private ListViewAdapter adapter;
    private  Set<String> title;

    private SharePrefenceUtil sharePrefenceUtil;
    private static List<String> list=new ArrayList<>();


    public void getList(Set<String> set2){

        for (String s:set2){
            for (String str:list){
                if (str.equals(s)){

                }else {
                    list.add(s);
                }
            }

        }

//
        adapter=new ListViewAdapter(this,set2);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);




    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        sharePrefenceUtil=SharePrefenceUtil.getSharePrefenceUtil(this);
        initView();



    }

    private void initView() {
        tool = (Toolbar) findViewById(R.id.tool);
        listView= (HorizontalListView) findViewById(R.id.listview);

        inter = (TextView) findViewById(R.id.inter);
        android = (TextView) findViewById(R.id.android);
        hot = (TextView) findViewById(R.id.hot);
        sichuan = (TextView) findViewById(R.id.sichuan);
        xingzuo = (TextView) findViewById(R.id.xingzuo);
        junshi = (TextView) findViewById(R.id.junshi);
        title =new HashSet<>();
        int color = sharePrefenceUtil.getColor();
        if (color!=0){
            tool.setBackgroundColor(getResources().getColor(color));
        }

        tool.setNavigationIcon(R.drawable.btn_return);
        tool.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                Tools.isRun=true;
            }
        });






        inter.setOnClickListener(this);
        android.setOnClickListener(this);
        hot.setOnClickListener(this);
        sichuan.setOnClickListener(this);
        xingzuo.setOnClickListener(this);
        junshi.setOnClickListener(this);


        Set<String> tag = sharePrefenceUtil.getDatafromSP();
        if (tag!=null&&tag.size()>0){
            title=tag;
            adapter = new ListViewAdapter(this,title);
            listView.setAdapter(adapter);


        }

        adapter = new ListViewAdapter(this, title);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setVisibility(View.GONE);
                String item = (String) adapter.getItem(i);
                boolean remove = title.remove(item);
                sharePrefenceUtil.setAddData(title);

                getList(title);

                adapter=new ListViewAdapter(AddActivity.this,title);
                adapter.notifyDataSetChanged();
                listView.setAdapter(adapter);


//                title.remove(i);

            }
        });


    }

    @Override
    public void onClick(View view) {
        Log.e("TAG","点击添加");
        switch (view.getId()) {
            case R.id.inter:
                String s = inter.getText().toString();
                set(s);
//                set1.add(s);
                break;
            case R.id.android:
                String s1=android.getText().toString();
                set(s1);
//                set1.add(s1);
                break;
            case R.id.junshi:
                String s2 = junshi.getText().toString();
                set(s2);
//                set1.add(s2);
                break;
            case R.id.sichuan:
                String s3 = sichuan.getText().toString();
                set(s3);
//                set1.add(s3);
                break;
            case R.id.hot:
                String s4 = hot.getText().toString();
                set(s4);
//                set1.add(s4);
                break;
            case R.id.xingzuo:
                String s5 = xingzuo.getText().toString();
                set(s5);
//                set1.add(s5);

                break;
        }
        getList(title);
     sharePrefenceUtil.settag(title);




    }

    public void set(String s){
        if (title.size()<6){
            Log.e("TAG","set----");
            for (int i=0;i<6;i++){
                Log.e("TAG","sssssss"+title.size());
                if (title==null||title.size()<=0){
                    Log.e("TAG","set----_+_+_+_");
                    title.add(s);

                    break;
                }else if (title.contains(s)){
                    Toast.makeText(this, "添加title失败，已经存在", Toast.LENGTH_SHORT).show();
                    break;
                }else {
                    title.add(s);

                    break;
                }

            }
//            sharePrefenceUtil.setAddData(title);

        }else {
            return;
        }


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        int color = sharePrefenceUtil.getColor();
        if (color!=0){
            tool.setBackgroundColor(getResources().getColor(color));
        }

    }
}
