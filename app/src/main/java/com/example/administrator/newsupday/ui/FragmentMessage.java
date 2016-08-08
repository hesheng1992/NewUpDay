package com.example.administrator.newsupday.ui;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.example.administrator.newsupday.R;
import com.example.administrator.newsupday.common.SharePrefenceUtil;
import com.example.administrator.newsupday.common.TimeUtils;
import com.example.administrator.newsupday.common.Tools;
import com.example.administrator.newsupday.service.MyReciver;
import com.example.administrator.newsupday.ui.adapter.FragmentMesVPAdapter;
import com.example.administrator.newsupday.ui.fragment.FragmentXZ;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2016/7/9 0009.
 */

public class FragmentMessage extends Fragment {
    private ViewPager viewPager;

    private FragmentMesVPAdapter adapter;
    private List<Fragment> list;
    private TabLayout tableLayout;
    private String url="https://route.showapi.com/109-35?channelId=&channelName=&needAllList=0&needContent=0&needHtml=0&page=1&showapi_appid=22276&showapi_timestamp=";
    private String time=TimeUtils.getSystime();
//    private String time="20160723161730";

    private String urlTitle="&title=";


    private String Idkey="&showapi_sign=80ffb144b5cd4533843a30cc02ebb805";

    private String getPath=url+time+urlTitle+""+Idkey;
//    private String path="https://route.showapi.com/109-35?channelId=&channelName=&needAllList=0&needContent=0&needHtml=0&page=1&showapi_appid=22276&showapi_timestamp="+ TimeUtils.getSystime()+ "&title=篮球&showapi_sign=80ffb144b5cd4533843a30cc02ebb805";
    private ImageView iv;
//    private List<String> titles=new ArrayList<>();
    private List<String> title=new ArrayList<>();

    private SharePrefenceUtil sharePrefenceUtil;
    private MyReciver myReciver=new MyReciver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            super.onReceive(context, intent);
            if (intent.getAction().equals("100")){
                handler.sendEmptyMessage(100);
            }


        }
    };

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==100){
                getData();
                Log.e("TAG","handler处理消息");
            }

        }
    };

    private void getData() {
//        List<String> title=new ArrayList<>();
        if (sharePrefenceUtil != null) {
            sharePrefenceUtil=SharePrefenceUtil.getSharePrefenceUtil(getContext());
           List<String> tag = new ArrayList<>();
            Set<String> datafromSP = sharePrefenceUtil.getDatafromSP();
            tag.clear();
            if (datafromSP==null){
                tag.add("军事");
                tag.add("安卓");
                tag.add("互联网");
                tag.add("四川");
                tag.add("星座");
                tag.add("热点");
            }else {
                for (String str:datafromSP){
                    tag.add(str);
                }
            }

            Log.e("TAG","tag_____-_-"+tag.size());
            if (tag.size() > 0) {
                title.clear();
                list.clear();
            for (int j = 0; j < tag.size(); j++) {

                //如果等于军事就加载军事页面

                if (tag.get(j).equals("军事")) {
//                    FragmentXZ fragmentjunshi = FragmentXZ.newistance("http://v.juhe.cn/toutiao/index?type=junshi&key=2b72a9e33bf26ed7600f6d1b235f5129");
                    FragmentXZ fragmentjunshi=FragmentXZ.newistance(url+time+urlTitle+ChangeTitle("汽车")+Idkey);
//                    Log.e("TAG","连接地址"+getPath);
                    list.add(fragmentjunshi);
//                    adapter.gettitle("军事");
                    title.add("军事");

                    //安卓
                } else if (tag.get(j).equals("安卓")) {
                    FragmentXZ fragmentkeji = FragmentXZ.newistance(url+time+urlTitle+ChangeTitle("安卓")+Idkey);
                    list.add(fragmentkeji);
                    title.add("安卓");

                    //互联网
                } else if (tag.get(j).equals("互联网")) {
                    FragmentXZ fragmentinrnet = FragmentXZ.newistance(url+time+urlTitle+ChangeTitle("物联网")+Idkey);
                    list.add(fragmentinrnet);
//                    adapter.gettitle("互联网");
                    title.add("互联网");

                    //四川
                } else if (tag.get(j).equals("四川")) {
                    FragmentXZ fragmentsichuan = FragmentXZ.newistance(url+time+urlTitle+ChangeTitle("NBA")+Idkey);
                    list.add(fragmentsichuan);
//                    adapter.gettitle("四川");
                    title.add("四川");
                    //星座
                } else if (tag.get(j).equals("星座")) {
                    FragmentXZ fragmentXZ = FragmentXZ.newistance(url+time+urlTitle+ChangeTitle("情感")+Idkey);
                    list.add(fragmentXZ);
//                    adapter.gettitle("星座");
                    title.add("星座");

                    //热点
                } else if (tag.get(j).equals("热点")) {
                    FragmentXZ fragmenttop = FragmentXZ.newistance(url+time+urlTitle+ChangeTitle("")+Idkey);
                    list.add(fragmenttop);
//                    adapter.gettitle("热点");
                    title.add("热点");

                }


            }

            adapter = new FragmentMesVPAdapter(getFragmentManager(), list,title);
            viewPager.setAdapter(adapter);
            viewPager.setOffscreenPageLimit(4);
                tableLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
            tableLayout.setupWithViewPager(viewPager);
        }
    }

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragmentmeesage,container,false);
        viewPager= (ViewPager) view.findViewById(R.id.viewpager);
        sharePrefenceUtil=SharePrefenceUtil.getSharePrefenceUtil(getContext());
        iv= (ImageView) view.findViewById(R.id.add_iv);
        iv.setOnClickListener(ivlistener);
        tableLayout= (TabLayout) view.findViewById(R.id.tablayout);
//        titles.add("军事");
//        titles.add("安卓");
//        titles.add("互联网");
//        titles.add("四川");
//        titles.add("星座");
//        titles.add("热点");
        initList();

        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("100");
        intentFilter.setPriority(Integer.MAX_VALUE);
        getActivity().registerReceiver(myReciver,intentFilter);
        return view;
    }

    private void initList() {
        list=new ArrayList<>();
       //初始化Fragment
//        FragmentXZ fragmentXZ=new FragmentXZ();
//        FragmentXZ fragmentXZ=FragmentXZ.newistance("http://v.juhe.cn/toutiao/index?type=yule&key=2b72a9e33bf26ed7600f6d1b235f5129");
//        FragmentXZ fragmentkeji=FragmentXZ.newistance("http://v.juhe.cn/toutiao/index?type=keji&key=2b72a9e33bf26ed7600f6d1b235f5129");
//        FragmentXZ fragmentinrnet=FragmentXZ.newistance("http://v.juhe.cn/toutiao/index?type=caijing&key=2b72a9e33bf26ed7600f6d1b235f5129");
//        FragmentXZ fragmentjunshi=FragmentXZ.newistance("http://v.juhe.cn/toutiao/index?type=junshi&key=2b72a9e33bf26ed7600f6d1b235f5129");
//        FragmentXZ fragmentsichuan=FragmentXZ.newistance("http://v.juhe.cn/toutiao/index?type=guonei&key=2b72a9e33bf26ed7600f6d1b235f5129");
//        FragmentXZ fragmenttop=FragmentXZ.newistance("http://v.juhe.cn/toutiao/index?type=top&key=2b72a9e33bf26ed7600f6d1b235f5129");




//        list.add(fragmentXZ);
//        list.add(fragmentkeji);
//        list.add(fragmentinrnet);
//        list.add(fragmentjunshi);
//        list.add(fragmentsichuan);
//        list.add(fragmenttop);

        getData();

        adapter=new FragmentMesVPAdapter(getFragmentManager(),list,title);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        tableLayout.setupWithViewPager(viewPager);
//        tableLayout.setTabMode(TabLayout.MODE_FIXED);

    }
    private View.OnClickListener ivlistener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(getActivity(),AddActivity.class);
            startActivity(intent);
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().unregisterReceiver(myReciver);
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("TAG","onresummessgae");
        if (Tools.isRun){
            getData();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("TAG","onCreatemessga");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.e("TAG","onDestroymeassge");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("TAG","onPausemessage");
        Tools.isRun=false;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("TAG","onStartmessgae");
//        if (Tools.isRun){
//            getData();
//        }
    }


    //格式转换方法。
    private static String ChangeTitle(String title) {
        try {
            title = URLEncoder.encode(title,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            title="";
        }
        return title;
    }

}
