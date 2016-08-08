package com.example.administrator.newsupday;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.newsupday.ui.LoadActivity;
import com.example.administrator.newsupday.ui.adapter.LeadVPAdapter;

import java.util.ArrayList;
import java.util.List;

public class LeadActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private Button butoon;
    private List<View> list;
    private LeadVPAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead);

        SharedPreferences sp = getSharedPreferences("runconfig", MODE_PRIVATE);
        boolean isFirstRun = sp.getBoolean("isFirstRun", true);
        if (!isFirstRun){
            Intent intent=new Intent(this,LoadActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        viewPager= (ViewPager) findViewById(R.id.viewpager);
        butoon= (Button) findViewById(R.id.tv);
        butoon.setVisibility(View.GONE);
        initList();

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (position==2){
                    butoon.setVisibility(View.VISIBLE);
                    butoon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent=new Intent(LeadActivity.this,LoadActivity.class);
                            startActivity(intent);
                            finish();

                            SharedPreferences sp = getSharedPreferences("runconfig", MODE_PRIVATE);
                            SharedPreferences.Editor edit = sp.edit();
                            edit.putBoolean("isFirstRun", false);
                            edit.commit();


                        }
                    });

                }




            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    private void initList() {
        list=new ArrayList<>();
        list.add(getLayoutInflater().inflate(R.layout.lead_1,null));
        list.add(getLayoutInflater().inflate(R.layout.lead_2,null));
        list.add(getLayoutInflater().inflate(R.layout.lead_3,null));
        adapter=new LeadVPAdapter(list);
        viewPager.setAdapter(adapter);
    }


}
