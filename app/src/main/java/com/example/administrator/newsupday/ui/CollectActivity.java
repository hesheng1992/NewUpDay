package com.example.administrator.newsupday.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.administrator.newsupday.R;
import com.example.administrator.newsupday.db.NewsDbManager;
import com.example.administrator.newsupday.entity.Content;
import com.example.administrator.newsupday.entity.ContentlistBean;
import com.example.administrator.newsupday.entity.Data;
import com.example.administrator.newsupday.ui.adapter.CollectAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CollectActivity extends AppCompatActivity {

    private ListView listView;
    private CollectAdapter adapter;
    private List<Content> list;
//    private DBManager dbManager;
    private NewsDbManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        listView= (ListView) findViewById(R.id.listview);
        initdata();
        adapter=new CollectAdapter(this,list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Data data = list.get(i);
//                ContentlistBean contentlistBean = list.get(i);
                Content content = list.get(i);

                Intent intent=new Intent(CollectActivity.this,ShowActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("news", (Serializable) content);
                intent.putExtras(bundle);
//                intent.putExtra("url",contentlistBean.getLink());

                startActivity(intent);

            }
        });

    }

    private void initdata() {
        list=new ArrayList<>();
//        dbManager=DBManager.getDbManager(this);
//        manager=NewsDbManager.getDbManager(this);
        manager=new NewsDbManager(this);
        List<Content> data = manager.getData();
        if (data.size()>0&& data!=null){
            Log.e("TAG","查询数据，返回收藏的集合");
            list=data;
        }
    }
}
