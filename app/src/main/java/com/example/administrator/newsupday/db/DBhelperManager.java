package com.example.administrator.newsupday.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/7/20 0020.
 */

public class DBhelperManager extends SQLiteOpenHelper {
    public DBhelperManager(Context context ) {
        super(context, "newsdb.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table lovenews (title text ,pubDate text ,link text ,channelName text ,desc text ,source text,channelId text,nid text,imageurls text)");
        db.execSQL("create table news (title text ,pubDate text ,link text ,channelName text ,desc text ,source text,channelId text,nid text,imageurls text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
//    public DBhelperManager(Context context) {
//        super(context, "newsdb.db", null, 2);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase database) {
//        database.execSQL("create table lovenews (title text ,content text ,link text ,time text ,icon text)");
//        database.execSQL("create table news (title text ,content text ,link text ,time text ,icon text)");
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//
//    }
}
