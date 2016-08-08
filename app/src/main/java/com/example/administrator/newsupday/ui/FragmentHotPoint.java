package com.example.administrator.newsupday.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.administrator.newsupday.R;
import com.example.administrator.newsupday.biz.parea.ParseNews;
import com.example.administrator.newsupday.common.SharePrefenceUtil;
import com.example.administrator.newsupday.common.VolleyHttp;
import com.example.administrator.newsupday.entity.Data;
import com.example.administrator.newsupday.entity.HotData;
import com.example.administrator.newsupday.ui.adapter.HotReAdapter;
import com.example.administrator.newsupday.utils.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

/**
 * Created by Administrator on 2016/7/9 0009.
 */

public class FragmentHotPoint extends Fragment {

    private RecyclerView hotrecyclerview;
    private HotReAdapter adapter;
    private List<Data> list;
    private SharePrefenceUtil sharePrefenceUtil;
    private ParseNews parseNews=new ParseNews(getContext());
    private Response.Listener<String> listener=new Response.Listener<String>() {
        @Override
        public void onResponse(String s) {
            List<Data> list1 = parseNews.pareNews(s);
            if (list1!=null){
                list=list1;
                adapter=new HotReAdapter(getActivity(),list);
                hotrecyclerview.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                hotrecyclerview.addItemDecoration(new SpaceItemDecoration(10));
                hotrecyclerview.setAdapter(adapter);
            }

        }
    };
    private Response.ErrorListener errorlistener=new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Toast.makeText(getContext(), "连接网络失败", Toast.LENGTH_SHORT).show();
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragmenthotpoint,container,false);
        hotrecyclerview= (RecyclerView) view.findViewById(R.id.houtrecyview);
        sharePrefenceUtil=SharePrefenceUtil.getSharePrefenceUtil(getActivity());
        int color = sharePrefenceUtil.getColor();
        if (color!=0){
            hotrecyclerview.setBackgroundColor(getResources().getColor(color));
        }

        initlist();
        Log.e("TAG","HotPoint-----");
        adapter=new HotReAdapter(getActivity(),list);
        hotrecyclerview.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        hotrecyclerview.addItemDecoration(new SpaceItemDecoration(30));
        hotrecyclerview.setAdapter(adapter);
        adapter.Clicklistenr(new HotReAdapter.RecycleClickListener() {
            @Override
            public void OnitemClickListener(View view, int posion) {
                Toast.makeText(getActivity(),"这是第"+posion+"个瀑布流",Toast.LENGTH_SHORT).show();
            }
        });




        return view;
    }


    private void initlist() {
        String url="http://v.juhe.cn/toutiao/index?type=tiyu&key=2b72a9e33bf26ed7600f6d1b235f5129";
        list=new ArrayList<>();
        VolleyHttp http=new VolleyHttp(getContext());

        http.getJsonObject(url,listener,errorlistener);

    }

    @Override
    public void onResume() {
        super.onResume();
        int color = sharePrefenceUtil.getColor();
        if (color!=0){
            hotrecyclerview.setBackgroundColor(getResources().getColor(color));
        }

    }
}
