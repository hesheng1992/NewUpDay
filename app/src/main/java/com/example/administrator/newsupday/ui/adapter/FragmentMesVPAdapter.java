package com.example.administrator.newsupday.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/9 0009.
 */

public class FragmentMesVPAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> list;

//    private String tabTitles[]=new String[]{
//      "星座","安卓","互联网","军事","四川","热点"
//    };
    private List<String> title=new ArrayList<>();

//    public void gettitle(String s){
//        Log.e("TAG","gettitle======");
//       title.add(s);
//    }
    public FragmentMesVPAdapter(FragmentManager fm,List<Fragment> list,List<String> title) {
        super(fm);
        this.list=list;
        this.title=title;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Log.e("TAG","asdsf"+title.get(position)+title.size());
        return title.get(position);
    }
}
