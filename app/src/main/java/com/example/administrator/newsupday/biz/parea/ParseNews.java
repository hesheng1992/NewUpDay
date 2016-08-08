package com.example.administrator.newsupday.biz.parea;

import android.content.Context;
import android.util.Log;

import com.example.administrator.newsupday.db.NewsDbManager;
import com.example.administrator.newsupday.entity.Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/13 0013.
 */

public class ParseNews {
    private List<Data> datas=new ArrayList<>();


    private Context context;
    public ParseNews(Context context){
        this.context=context;
    }


    public List<Data> getJsonString(String url){
        List<Data> list=new ArrayList<>();



        Log.e("TAG","开始解析");
        try {
            URL url1=new URL(url);
            HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
            conn.setConnectTimeout(8000);
            InputStream inputStream = conn.getInputStream();
            list = getdata(inputStream);





            conn.disconnect();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



        return list;

    }


    public List<Data> getdata(InputStream inputStream){
        Log.e("TAG","获得解析数据--------");
        List<Data> list=new ArrayList<>();
        byte[] bytes=convertIsToByteArray(inputStream);
        String json=new String(bytes);
        try {
            JSONArray jsonArray=new JSONArray(json);
            for (int i=0;i<jsonArray.length();i++){
                Log.e("TAG","获得解析数据");
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                String title = jsonObject.getString("title");//标题
                String author = jsonObject.getString("author_name");//作者
                String icon=jsonObject.getString("thumbnail_pic_s");
                String date = jsonObject.getString("date");
                String url = jsonObject.getString("url");
                String realtype = jsonObject.getString("realtype");
                Data data=new Data(title,author,date,icon,url);
                list.add(data);
            }

            Log.e("TAG","解析完成");

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return list;

    }


    //将得到的输入流转化为字节数组
    private byte[] convertIsToByteArray(InputStream inputStream) {
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        byte buffer[]=new byte[1024];
        int length=0;
        try {
            while ((length=inputStream.read(buffer))!=-1) {
                baos.write(buffer, 0, length);
            }
            inputStream.close();
            baos.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }




        return baos.toByteArray();

    }


    //传一个json数据进来.
    public List<Data> pareNews(String json){
        List<Data> list=new ArrayList<>();
        try {
            JSONObject jsonObject=new JSONObject(json);
            JSONObject jsonobject1  = (JSONObject) jsonObject.get("result");
            Log.e("TAG","获得解析数据==========");
            JSONArray jsonArray= (JSONArray) jsonobject1.get("data");
            for (int i=0;i<jsonArray.length();i++){
//                Log.e("TAG","获得解析数据-----");
                JSONObject jsonob= (JSONObject) jsonArray.get(i);
                String title = jsonob.getString("title");//标题
                String author = jsonob.getString("author_name");//作者
                String icon=jsonob.getString("thumbnail_pic_s");
                String date = jsonob.getString("date");
                String url = jsonob.getString("url");

                Data data=new Data(title,author,date,icon,url);
                list.add(data);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;

    }



}
