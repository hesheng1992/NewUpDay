package com.example.administrator.newsupday.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2016/7/19 0019.
 */

public class SharePrefenceUtil {
    private static SharePrefenceUtil sharePrefenceUtil;
    private Context context;
    private SharePrefenceUtil(Context context1){
        this.context=context1;
    }
    public static SharePrefenceUtil getSharePrefenceUtil(Context context){

        if (sharePrefenceUtil==null){
            synchronized (SharePrefenceUtil.class){
                if (sharePrefenceUtil==null){


                    sharePrefenceUtil=new SharePrefenceUtil(context);
                }

            }

        }


        return sharePrefenceUtil;
    }

    public void settag(Set<String> set){
        SharedPreferences sp = context.getSharedPreferences("news", context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putStringSet("newtag",set);
        Log.e("TAG","settag-----"+set.size());
        edit.commit();
    }
    public  List<String> getTag(){
        List<String> list=new ArrayList<>();
        SharedPreferences newskey = context.getSharedPreferences("news", context.MODE_PRIVATE);

            Set<String> newtag = newskey.getStringSet("newtag", null);
//            Log.e("TAG", "gettag----"+newtag.size());
            if (newtag != null) {
                for (String s : newtag) {
                    list.add(s);
                }

            }else {
                list.add("军事");
                list.add("安卓");
                list.add("互联网");
                list.add("四川");
                list.add("星座");
                list.add("热点");

            }
            return list;



    }

    public void setAddData(Set<String> set ){
        SharedPreferences title = context.getSharedPreferences("title", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = title.edit();
        edit.putStringSet("set",set);
        edit.commit();
    }
    public Set getDatafromSP(){
        SharedPreferences title = context.getSharedPreferences("title", Context.MODE_PRIVATE);
        Set<String> set1 = title.getStringSet("set", null);
        return set1;



    }

    public void setColor(int color){
        SharedPreferences colors = context.getSharedPreferences("colors", context.MODE_PRIVATE);
        SharedPreferences.Editor edit = colors.edit();
        edit.putInt("color",color);
        edit.commit();

    }
    public int getColor(){
        SharedPreferences colors = context.getSharedPreferences("colors", context.MODE_PRIVATE);
        int color = colors.getInt("color", 0);
        return color;


    }





}
