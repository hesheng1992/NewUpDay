package com.example.administrator.newsupday.common;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;




/**
 * Created by Administrator on 2016/6/3 0003.
 */
public class VolleyHttp {

    private RequestQueue mQueue;


    public VolleyHttp(Context context){

        //初始化队列,要联网需要两个对象，一个就是队列，一个就是请求方式
        mQueue = Volley.newRequestQueue(context);

    }

    public void getJsonObject(String url, Response.Listener<String> listener, Response.ErrorListener errorListener){
        //get的联网方式。
        StringRequest request=new StringRequest(url,listener,errorListener);
        //放入联网请求队列

        mQueue.add(request);

    }

//    public void upLoadImage(String url, File file, Response.Listener<String> listener, Response.ErrorListener errorListener){
//        MultiPosttRequest request=new MultiPosttRequest(url,listener,errorListener);
//        request.buildMultipartEntity("portrait",file);
//        Log.e("TAG","uploadimage");
//
//    }

}
