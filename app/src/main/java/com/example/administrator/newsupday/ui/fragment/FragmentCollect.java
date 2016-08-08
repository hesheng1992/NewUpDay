package com.example.administrator.newsupday.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.administrator.newsupday.R;

/**
 * Created by Administrator on 2016/7/19 0019.
 */

public class FragmentCollect extends Fragment {
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragmentcollect,container,false);
        listView= (ListView) view.findViewById(R.id.listview);

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
