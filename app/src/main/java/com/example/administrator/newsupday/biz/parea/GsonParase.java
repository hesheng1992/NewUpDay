package com.example.administrator.newsupday.biz.parea;

import android.util.Log;

import com.example.administrator.newsupday.entity.ContentlistBean;
import com.example.administrator.newsupday.entity.HotData;
import com.example.administrator.newsupday.entity.PagebeanBean;
import com.example.administrator.newsupday.entity.ShowapiResBodyBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/22 0022.
 */

public class GsonParase {



    public static HotData praserNewsList(String json){

        Gson gson=new Gson();
        HotData hotdata=gson.fromJson(json,HotData.class);

        Log.e("TAG","HOTDAT====="+json.toString());

        return hotdata;

    }



}
