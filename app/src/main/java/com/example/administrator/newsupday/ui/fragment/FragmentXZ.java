package com.example.administrator.newsupday.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.newsupday.R;
import com.example.administrator.newsupday.biz.parea.GsonParase;
import com.example.administrator.newsupday.biz.parea.ParseNews;
import com.example.administrator.newsupday.common.SharePrefenceUtil;
import com.example.administrator.newsupday.common.TimeUtils;
import com.example.administrator.newsupday.common.VolleyHttp;
import com.example.administrator.newsupday.db.NewsDbManager;
import com.example.administrator.newsupday.entity.Content;
import com.example.administrator.newsupday.entity.ContentlistBean;
import com.example.administrator.newsupday.entity.Data;
import com.example.administrator.newsupday.entity.HotData;
import com.example.administrator.newsupday.entity.PagebeanBean;
import com.example.administrator.newsupday.entity.ShowapiResBodyBean;
import com.example.administrator.newsupday.ui.adapter.FragmentXZAdapter;
import com.example.administrator.newsupday.ui.adapter.XZAdapter;
import com.example.administrator.newsupday.ui.base.BaseLoadAdapter;
import com.example.administrator.newsupday.utils.SpaceItemDecoration;
import com.tencent.open.utils.SystemUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/9 0009.
 */

public class FragmentXZ extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerView;

    private FragmentXZAdapter adapter;
    private List<ContentlistBean> list;

    private final int ADD_DATA_FLAG=1;
    private final int UPDATE_DATA_FLAG=2;
    private int nowpage=1;//当前数据的页数
    private int all_page;

//    private DBManager dbManager;
    private NewsDbManager manager;
    private SharePrefenceUtil sharePrefenceUtil;

    private SwipeRefreshLayout swipeRefreshLayout;//刷新






    private XZAdapter.itemClickListener listener=new XZAdapter.itemClickListener() {
        @Override
        public void itemclicklistener(View view, int position) {

//            Log.e("TAG","itemlistenr");
            Toast.makeText(getActivity(), "这是第"+position+"个", Toast.LENGTH_SHORT).show();
        }
    };

    private SpaceItemDecoration spaceItemDecoration;

    private ParseNews parseNews=new ParseNews(getContext());

    //这个是来获取主的fragment传递过来的url地址
    private String intenturl;
    private LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());



    public static FragmentXZ newistance(String url){
        FragmentXZ fragmentXZ=new FragmentXZ();

        Bundle bundle=new Bundle();
        bundle.putString("hello",url);
        fragmentXZ.setArguments(bundle);
        return fragmentXZ;

    }





    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==100){

                adapter=new FragmentXZAdapter(recyclerView,list,getActivity());
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);

            }else if (msg.what==200){

                VolleyHttp http1=new VolleyHttp(getContext());
                http1.getJsonObject(intenturl,listenr,errorlistener);
                swipeRefreshLayout.setRefreshing(false);
                nowpage=1;

            }else if (msg.what==300){
                Log.e("TAG","加载更多");
                Toast.makeText(getContext(), "没有更多数据了", Toast.LENGTH_SHORT).show();
            }else if (msg.what==20){
                adapter=new FragmentXZAdapter(recyclerView,list,getActivity());
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
            }
        }
    };
    private Response.Listener<String> listenr=new Response.Listener<String>() {
        @Override
        public void onResponse(String s) {
            Log.e("TAG","获取json数据"+s);
            HotData hotData = GsonParase.praserNewsList(s);
            Log.e("TAG","hotdata"+hotData.getShowapi_res_error()+hotData.getShowapi_res_body());
            ShowapiResBodyBean showapi_res_body = hotData.getShowapi_res_body();

            PagebeanBean pagebean = showapi_res_body.getPagebean();
//            all_page=pagebean.getAllPages();
            List<ContentlistBean> contentlist = pagebean.getContentlist();//实体集合



//            Log.e("TAG","连接网络成功"+s);
//            List<Data> list1 = parseNews.pareNews(s);
//            Log.e("TAG","typepath==="+list1.get(0).getLink()+"\t"+list1.get(1).getLink());
            manager.addNews(contentlist);
            FragmentXZ.this.list = contentlist;
            handler.sendEmptyMessage(100);


        }
    };
    private Response.ErrorListener errorlistener=new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Toast.makeText(getActivity(), "连接网络失败", Toast.LENGTH_SHORT).show();
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.sharesfragment,container,false);
//        dbManager=DBManager.getDbManager(getContext());
//        manager=NewsDbManager.getDbManager(getContext());
        manager=new NewsDbManager(getActivity());
        sharePrefenceUtil=SharePrefenceUtil.getSharePrefenceUtil(getActivity());
        recyclerView= (RecyclerView) view.findViewById(R.id.recyview);
        swipeRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.swipe);
//        swipeRefreshLayout.setColorScheme(R.color.color1, R.color.color2, R.color.color3, R.color.color4);

        inintList();

        int color = sharePrefenceUtil.getColor();
        if (color!=0){
            recyclerView.setBackgroundColor(getResources().getColor(color));
        }



//        adapter=new XZAdapter(getActivity(), FragmentXZ.this.list,recyclerView);
        adapter=new FragmentXZAdapter(recyclerView,FragmentXZ.this.list,getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        swipeRefreshLayout.setColorSchemeResources(R.color.grenn);
        swipeRefreshLayout.setProgressViewOffset(true, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, getResources()
                        .getDisplayMetrics()));
        swipeRefreshLayout.setOnRefreshListener(this);
        adapter.setOnLoadingListener(OnloadLishenr);
//        recyclerView.setOnScrollListener();
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                lastVisibleItem=layoutManager.findLastVisibleItemPosition();
//            }
//
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()) {
////                    handler.sendEmptyMessageDelayed(300, 3000);
//                    swipeRefreshLayout.setRefreshing(true);
//                    handler.sendEmptyMessage(300);
//                }
//
//            }
//        });

        spaceItemDecoration=new SpaceItemDecoration(30);//设置item之间的间距
        recyclerView.addItemDecoration(spaceItemDecoration);//设置item之间的间距

        Animation animation= AnimationUtils.loadAnimation(getActivity(),R.anim.anim_activity_bottom_in);
        recyclerView.setAnimation(animation);
        recyclerView.setHasFixedSize(true);//如果每个item条目的高度是固定的，就可以设置这个，提高性能
        recyclerView.setAdapter(adapter);

//        adapter.setListener(listener);
//

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getArguments();
        intenturl = bundle !=null ? bundle.getString("hello"):"null";

    }


    private void inintList() {
        VolleyHttp http=new VolleyHttp(getActivity());
        list=new ArrayList<>();
        List<ContentlistBean> news = manager.getNews();
        if (news!=null&&news.size()>0){
            list=news;
            Log.e("TAG","从数据库获取数据");
//            adapter=new XZAdapter(getActivity(),list,recyclerView);
            adapter=new FragmentXZAdapter(recyclerView,list,getActivity());
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);
        }else {
            Log.e("TAG","从网络获取数据");
            http.getJsonObject(intenturl,listenr,errorlistener);
        }
//        http.getJsonObject(intenturl,listenr,errorlistener);
    }

    @Override
    public void onRefresh() {
        handler.sendEmptyMessage(200);

    }

    private BaseLoadAdapter.OnLoadingListener OnloadLishenr=new BaseLoadAdapter.OnLoadingListener() {
        @Override
        public void loading() {
            Log.e("TAG","上拉加载监听");
            getDataFromHttp(ADD_DATA_FLAG,nowpage);
        }
    };
    public void getDataFromHttp(int type,int page){
        Log.e("TAG","nowpage"+all_page+"==="+nowpage);
        if(all_page!=0&&nowpage>all_page){
            Log.e("TAG","nowpage"+all_page+"==="+nowpage);
            getDataNoMore();
        }
        final int nowtype=type;
        RequestQueue queue= Volley.newRequestQueue(getActivity());
        StringRequest request=new StringRequest(intenturl, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                HotData hotData = GsonParase.praserNewsList(s);
                ShowapiResBodyBean showapi_res_body = hotData.getShowapi_res_body();
                PagebeanBean pagebean = showapi_res_body.getPagebean();
                all_page = pagebean.getAllPages();
                Log.e("TAG","第一次上拉加载获得的allpage="+all_page);
                List<ContentlistBean> contentlist = pagebean.getContentlist();
                getDataSuccess(contentlist,nowtype);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getActivity(), "连接失败", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);

    }
    public void getDataSuccess(List<ContentlistBean> contentlist,int type){

        if(contentlist==null||contentlist.size()<=0){
            Log.i("msg","meiyoushuju");
            return;
        }
        for(int i=0;i<contentlist.size();i++){
            if(contentlist.get(i).getImageurls() == null||
                    contentlist.get(i).getImageurls().size()<=0||
                    contentlist.get(i).getImageurls().get(0).getUrl()==null){
                contentlist.remove(i);
                i--;
            }
        }

        if (type==ADD_DATA_FLAG){

            adapter.setLoadingComplete();
            int position=list.size();
            if(contentlist != null){
                list.addAll(contentlist);
                Log.i("msg","添加数据");
//                    adapter.notifyItemInserted(position);
                adapter.notifyDataSetChanged();
                handler.sendEmptyMessage(20);
//                adapter=new FragmentXZAdapter(recyclerView,contentlist,getActivity());

//                recyclerView.setAdapter(adapter);
            }
            nowpage++;

        }


    }

    private void getDataNoMore() {
        Log.e("TAG","加载更多");
        adapter.setLoadingNoMore();
        swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void onResume() {
        super.onResume();
        int color = sharePrefenceUtil.getColor();
        if (color!=0){
            recyclerView.setBackgroundColor(getResources().getColor(color));
        }
    }
}
