package com.example.administrator.newsupday.common;

import android.text.format.Time;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2016/7/11 0011.
 */

public class TimeUtils {

    public static String getSystime() {
        String systime;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss",Locale.getDefault());
        systime = dateFormat.format(new Date(System.currentTimeMillis()));
        Log.e("TAG","sysyemtime"+systime);
//        Time time=new Time();
//        time.setToNow();
//        int year = time.year;
//        int month = time.month+1;
//        int monthDay = time.monthDay;
//        int minute = time.minute;
//        int hour = time.hour;
//        int second = time.second;
//        String system=""+year+""+month+""+monthDay+""+hour+""+minute+""+second;
//
//        Log.e("TAG","sysyemtime"+system);

        return systime;
    }
}
