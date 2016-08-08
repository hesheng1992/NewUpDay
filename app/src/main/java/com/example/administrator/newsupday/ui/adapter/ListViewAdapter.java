package com.example.administrator.newsupday.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.newsupday.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2016/7/18 0018.
 */

public class ListViewAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Set<String> set;

    private List<String> list=new ArrayList<>();
    public List<String> getList(Set<String> set2){
        if (set2!=null){
            for (String se:set2){
                list.add(se);
            }
        }
        return list;
    }


    public ListViewAdapter(Context context,Set<String> set){
        inflater=LayoutInflater.from(context);
        this.set=set;

    }


    @Override
    public int getCount() {
        return set.size();
    }

    @Override
    public Object getItem(int i) {
        return getList(set).get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view=inflater.inflate(R.layout.list_item,null);
        TextView tv= (TextView) view.findViewById(R.id.tv);
//        tv.setText(list.get(i));
        tv.setText(getList(set).get(i));


        return view;
    }
}
