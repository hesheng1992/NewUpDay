package com.example.administrator.newsupday.service;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.administrator.newsupday.R;
import com.example.administrator.newsupday.entity.Song;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/21 0021.
 */

public class MyServercie extends Service {
    private MediaPlayer player;
//    private List<Song> list;

    @Override
    public void onCreate() {
        super.onCreate();
        player=MediaPlayer.create(this,R.raw.ab);
        player.setLooping(true);


    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int intExtra = intent.getIntExtra("key",1);
        Log.e("TAG"," 播放音乐的撞他 "+intExtra);
        switch (intExtra){
            case 1:

                Log.e("TAG","kais");
//                player.stop();
                player.reset();
                if (!player.isPlaying()){
                    Log.e("TAG","------plar");
                    player=MediaPlayer.create(this,R.raw.ab);
                    player.start();

                }else {
                    player.stop();
                    player.start();
//                    player.setLooping(true);
                }


                    Log.e("TAG","kais");
                break;
            case 2:
                if (player.isPlaying()){
                    Log.e("TAG","停止");
                    player.stop();
                }
                break;
        }
        player.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                player.reset();
                return true;
            }
        });




        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
