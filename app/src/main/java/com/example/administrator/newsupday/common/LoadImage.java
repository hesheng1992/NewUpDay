package com.example.administrator.newsupday.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.util.LruCache;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2016/7/13 0013.
 */

public class LoadImage {
    private Context context;
    private ImageLoadListener listener;

    public LoadImage(Context c,ImageLoadListener listener){
        this.context=c;
        this.listener=listener;

    }



    private LruCache<String,Bitmap> cache=new LruCache<String,Bitmap>(3*1024*1024){
        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getRowBytes()*value.getHeight();
        }
    };


    public class Mytask extends AsyncTask<String,Void,Bitmap>{
        String path=null;

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap=null;

            try {
                path=params[0];
                URL url=new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                InputStream inputStream = conn.getInputStream();
                bitmap= BitmapFactory.decodeStream(inputStream);
                cache.put(params[0],bitmap);
                //将bitmap存储到本地文件里
                saveCacheFile(params[0], bitmap);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }



            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            listener.imageLoadOK(bitmap,path);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


    }

    private void saveCacheFile(String param, Bitmap bitmap) {
//        Log.e("TAG","savecacheFile");
        String name=param.substring(param.lastIndexOf("/")+1);
        File cahe=new File(Environment.getExternalStorageDirectory(), "cache");
        if (!cahe.exists()){
            cahe.mkdirs();
        }
//        File cache=context.getCacheDir();

        File file=new File(cahe,name);
        FileOutputStream fos=null;

        try {
            fos=new FileOutputStream(file);
            //压缩图片
            bitmap.compress(Bitmap.CompressFormat.PNG,100,fos);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
//        Log.e("TAG","savecacheFile---------------");

    }

    public interface ImageLoadListener{
        void imageLoadOK(Bitmap bitmap,String url);
    }


    //得到bitmap的方法,这个是从内存
    public Bitmap getBitmapFromCache(String url){
//        Log.e("TAG","getcache");

        //lrucache就是指的内存，直接lrucache的引用对象点get（地址）就能得到存储在内存里的数据，通过url去匹配；
        Bitmap bitmap=cache.get(url);
        return bitmap;

    }
    //得到bitman，从本地文件中获取
    public Bitmap getBitmapFromCacheFile(String url){
        Bitmap bitmap=null;
        //截取的是url最后的文件名称.
        String name=url.substring(url.lastIndexOf("/")+1);
        //得到缓存的文件
//        Log.e("TAG","==========");
        File cache=new File(Environment.getExternalStorageDirectory(), "cache");
        if (!cache.exists()){
            cache.mkdirs();

        }
//        File cacheDir = context.getCacheDir();
//        if (cacheDir==null){
//
//            return bitmap;
//        }
//        Log.e("TAG"," cachedir"+cache);
        //返回这个文件里的全部数据
        File[] files = cache.listFiles();

        File bitfile=null;
        if (files==null){
            return bitmap;
        }

        for (int i=0;i<files.length;i++){
            if (name.equals(files[i].getName())){
                bitfile=files[i];
                break;
            }
        }

        if (bitfile==null){
            return bitmap;
        }
        //通过得到文件的绝对路劲，找到图片。
        bitmap=BitmapFactory.decodeFile(bitfile.getAbsolutePath());

        return bitmap;
    }

    //得到bitmap从异步加载中获取
    public void getBitmapAsyc(String url){
        Mytask task=new Mytask();
        task.execute(url);
//        Log.e("TAG","异步加载获取图片");

    }

    //得到bitmap通过三种方式判断获取bitmap
    public Bitmap getBitmap(String url){
        Bitmap bitmap=null;
        if (url==null||url.length()<=0){
            return null;
        }
        bitmap=getBitmapFromCache(url);
        if (bitmap!=null){
//            Log.e("TAG","from_cache");
            return bitmap;
        }
        bitmap=getBitmapFromCacheFile(url);
        if (bitmap!=null){
            return bitmap;
        }

        getBitmapAsyc(url);

        return bitmap;
    }


}
