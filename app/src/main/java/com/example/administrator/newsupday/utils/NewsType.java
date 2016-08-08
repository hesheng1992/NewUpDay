package com.example.administrator.newsupday.utils;

import com.example.administrator.newsupday.entity.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/22 0022.
 */

public class NewsType {

    public List<String> PanduanNews(List<Data> list){
        List<String> stringList=new ArrayList<>();
        String type=null;
        for (Data data:list){
            String link = data.getLink();
//            type=link.substring()

        }

        return stringList;
    }
}
