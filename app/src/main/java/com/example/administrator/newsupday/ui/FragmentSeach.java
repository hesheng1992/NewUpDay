package com.example.administrator.newsupday.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.administrator.newsupday.R;
import com.example.administrator.newsupday.db.NewsDbManager;
import com.example.administrator.newsupday.entity.Content;
import com.example.administrator.newsupday.entity.ContentlistBean;
import com.example.administrator.newsupday.entity.Data;
import com.example.administrator.newsupday.ui.adapter.SeachAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/9 0009.
 */

public class FragmentSeach extends Fragment {
    private EditText et;
    private ListView listView;
    private SeachAdapter adapter;
    private List<ContentlistBean> list;
    private static String ettext=null;
    private NewsDbManager manager;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
            if (msg.what==100){

                adapter=new SeachAdapter(getActivity(),list,ettext);
                adapter.notifyDataSetChanged();
                listView.setAdapter(adapter);

            }else if (msg.what==200){
                list.clear();
                adapter=new SeachAdapter(getActivity(),list,ettext);
                adapter.notifyDataSetChanged();
                listView.setAdapter(adapter);
            }

        }
    };


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragmentseach,container,false);
        manager=new NewsDbManager(getActivity());
        et= (EditText) view.findViewById(R.id.et);
        list=new ArrayList<>();
        listView= (ListView) view.findViewById(R.id.listview);
        adapter=new SeachAdapter(getActivity(),list,ettext);
        listView.setAdapter(adapter);
        et.addTextChangedListener(tv);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ContentlistBean contentlistBean = list.get(i);
                String link = contentlistBean.getLink();
                String getimurl = contentlistBean.getimurl(contentlistBean.getImageurls());
                String desc = contentlistBean.getDesc();
                String pubDate = contentlistBean.getPubDate();
                String source = contentlistBean.getSource();
                String channelId = contentlistBean.getChannelId();
                String channelName = contentlistBean.getChannelName();
                String title = contentlistBean.getTitle();
                String nid = contentlistBean.getNid();
                Content content=new Content(pubDate,title,channelName,desc,source,channelId,nid,link,getimurl);







                Intent intent=new Intent(getActivity(),ShowActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("news",content);
                intent.putExtras(bundle);
                startActivity(intent);
                Log.e("TAG","点击事件");
            }
        });
        return view;
    }
    private TextWatcher tv=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (charSequence.length()>0){
                String s = charSequence.toString();
                ettext=s;
                list = manager.SeachgetData(s);
                handler.sendEmptyMessage(100);
            }else {
                handler.sendEmptyMessage(200);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

}
