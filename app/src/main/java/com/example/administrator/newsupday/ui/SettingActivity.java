package com.example.administrator.newsupday.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.administrator.newsupday.R;
import com.example.administrator.newsupday.common.MyApp;
import com.example.administrator.newsupday.common.SharePrefenceUtil;
import com.example.administrator.newsupday.service.MyServercie;

import java.util.Random;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {


    private CardView cardview;//设置一键换肤；
    private ToggleButton tb;//播放背景音乐
    private int changer=0;
    private View view;

    private SharePrefenceUtil sharePrefenceUtil;


    private int []colorchange=new int[]{
      R.color.yellow,R.color.chengse,R.color.grenn,R.color.qingse,R.color.text,R.color.zise
    ,R.color.red,R.color.back,R.color.toolbar};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        MyApp app= (MyApp) this.getApplication();
//        if (app.theam==0){
//
//        }else {
//            setTheme(app.theam);
//        }
        setContentView(R.layout.activity_setting);
        sharePrefenceUtil=SharePrefenceUtil.getSharePrefenceUtil(this);
        initView();
    }

    private void initView() {
        cardview = (CardView) findViewById(R.id.cardview);
        tb = (ToggleButton) findViewById(R.id.tb);
        view=findViewById(R.id.view);
        int color = sharePrefenceUtil.getColor();
        if (color!=0){
            view.setBackgroundColor(getResources().getColor(color));
        }
        cardview.setOnClickListener(this);
        tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Intent intent=new Intent(SettingActivity.this, MyServercie.class);
                if (b){
//                    Intent intent=new Intent(SettingActivity.this, MyServercie.class);
                    intent.putExtra("key",1);
                    startService(intent);
                }else {
                   intent.putExtra("key",2);
                    startService(intent);
//                    stopService(intent);
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cardview:
//                MyApp app= (MyApp) SettingActivity.this.getApplication();
//                app.theam=R.style.AppTheme_other;
//                recreate();
                Random random=new Random();
                int i = random.nextInt(8)+1;
                changer=colorchange[i];
                sharePrefenceUtil.setColor(changer);
                view.setBackgroundColor(getResources().getColor(changer));
                Log.e("TAG","color===="+changer);
//                finish();
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("TAG","onrestartset");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("TAG","onresumeset");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("TAG","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("TAG","onStop");
    }

    @Override
    public void finish() {
        super.finish();
        Log.e("TAG","finish");
    }
}
