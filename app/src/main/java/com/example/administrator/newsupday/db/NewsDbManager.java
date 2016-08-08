package com.example.administrator.newsupday.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.administrator.newsupday.entity.Content;
import com.example.administrator.newsupday.entity.ContentlistBean;
import com.example.administrator.newsupday.entity.Data;
import com.example.administrator.newsupday.entity.ImageurlsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/20 0020.
 */

public class NewsDbManager {
    private Context context;
    private DBhelperManager dBhelperManager;
//    private static NewsDbManager newsDbManager;

    public NewsDbManager(Context context1){
        this.context=context1;
        dBhelperManager=new DBhelperManager(context1);
    }

//    public static NewsDbManager getDbManager(Context context){
//
//        if (newsDbManager==null){
//            newsDbManager=new NewsDbManager(context);
//            Log.e("TAG", "大爷，你走到这里来了么" );
//        }
//        return newsDbManager;
//
//    }
    //收藏新闻
    public boolean addData(Content data){
        SQLiteDatabase database = dBhelperManager.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from lovenews where link=?",new String[]{data.getLink()});

        if (cursor.moveToFirst()){
            cursor.close();
            return false;
        }
        cursor.close();
        ContentValues values=new ContentValues();
//        values.put("title",data.getTitle());
//        values.put("content",data.getContent());
//        values.put("link",data.getLink());
//        values.put("time",data.getTime());
//        values.put("icon",data.getIcon());
        values.put("title", data.getTitle());
        values.put("pubDate", data.getPubDate());
        values.put("link", data.getLink());
        values.put("channelName", data.getChannelName());
        values.put("desc", data.getDesc());
        values.put("source", data.getSource());
        values.put("channelId", data.getChannelId());
        values.put("nid", data.getNid());
        values.put("imageurls",data.getImurl());

        database.insert("lovenews",null,values);
        database.close();
        return true;

    }

    //查询收藏新闻
    public List<Content> getData(){
        List<Content> list=new ArrayList<>();
        SQLiteDatabase database = dBhelperManager.getReadableDatabase();

        Cursor cursor = database.rawQuery("select * from lovenews", null);
        if (cursor!=null){
            while (cursor.moveToNext()){
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String pubDate = cursor.getString(cursor.getColumnIndex("pubDate"));
                String channelName = cursor.getString(cursor.getColumnIndex("channelName"));
                String desc = cursor.getString(cursor.getColumnIndex("desc"));
                String source = cursor.getString(cursor.getColumnIndex("source"));
                String channelId = cursor.getString(cursor.getColumnIndex("channelId"));
                String nid = cursor.getString(cursor.getColumnIndex("nid"));
                String link = cursor.getString(cursor.getColumnIndex("link"));
                String imageurls = cursor.getString(cursor.getColumnIndex("imageurls"));
               Content content=new Content(pubDate,title,channelName,desc,source,channelId,nid,link,imageurls);
                list.add(content);


            }
            cursor.close();
            database.close();


        }
        return list;
    }

    //保存新闻
    public boolean addNews(List<ContentlistBean> list) {
        for (ContentlistBean data : list) {
            if (dBhelperManager == null) {
                Log.e("TAG", "addnews======");
                dBhelperManager = new DBhelperManager(context);
            }
            SQLiteDatabase database = dBhelperManager.getReadableDatabase();
            Log.e("TAG", "addnews======");
            Cursor cursor = database.rawQuery("select * from news where link=?", new String[]{data.getLink()});
            if (cursor.moveToFirst()) {
                cursor.close();
                return false;
            }
            cursor.close();
            ContentValues values = new ContentValues();
            values.put("title", data.getTitle());
            values.put("pubDate", data.getPubDate());
            values.put("link", data.getLink());
            values.put("channelName", data.getChannelName());
            values.put("desc", data.getDesc());
            values.put("source", data.getSource());
            values.put("channelId", data.getChannelId());
            values.put("nid", data.getNid());
            values.put("imageurls",data.getimurl(data.getImageurls()));

            Log.e("TAG", "插入数据");
            database.insert("news", null, values);
            database.close();
        }
        return true;


    }

    //查询新闻，返回新闻集合
    public List<ContentlistBean> getNews(){
        List<ContentlistBean> list=new ArrayList<>();
        if (dBhelperManager!=null) {

            SQLiteDatabase database = dBhelperManager.getReadableDatabase();

            Cursor cursor = database.rawQuery("select * from news", null);
            if (cursor != null) {
                while (cursor.moveToNext()) {

                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    String pubDate = cursor.getString(cursor.getColumnIndex("pubDate"));
                    String channelName = cursor.getString(cursor.getColumnIndex("channelName"));
                    String desc = cursor.getString(cursor.getColumnIndex("desc"));
                    String source = cursor.getString(cursor.getColumnIndex("source"));
                    String channelId = cursor.getString(cursor.getColumnIndex("channelId"));
                    String nid = cursor.getString(cursor.getColumnIndex("nid"));
                    String link = cursor.getString(cursor.getColumnIndex("link"));
                    String imageurls = cursor.getString(cursor.getColumnIndex("imageurls"));
                    ContentlistBean data = new ContentlistBean(pubDate,title,channelName,desc,source,channelId,nid,link,imageurls);
                    list.add(data);


                }
                cursor.close();
                database.close();


            }
            return list;
        }
        return null;
    }


    public List<ContentlistBean> SeachgetData(String title){
        List<ContentlistBean> datas=new ArrayList<>();
        SQLiteDatabase db = dBhelperManager.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from news where title like ?", new String[]{'%' + title + '%'});
        if (cursor!=null){
            while (cursor.moveToNext()){
                Log.e("TAG","nnnnnnnnn");
                String title1 = cursor.getString(cursor.getColumnIndex("title"));
                String pubDate = cursor.getString(cursor.getColumnIndex("pubDate"));
                String channelName = cursor.getString(cursor.getColumnIndex("channelName"));
                String desc = cursor.getString(cursor.getColumnIndex("desc"));
                String source = cursor.getString(cursor.getColumnIndex("source"));
                String channelId = cursor.getString(cursor.getColumnIndex("channelId"));
                String nid = cursor.getString(cursor.getColumnIndex("nid"));
                String link = cursor.getString(cursor.getColumnIndex("link"));
                String imageurls = cursor.getString(cursor.getColumnIndex("imageurls"));
                ContentlistBean data = new ContentlistBean(pubDate,title1,channelName,desc,source,channelId,nid,link,imageurls);
                datas.add(data);
            }

        }
        cursor.close();
        db.close();
        return datas;

    }





}
