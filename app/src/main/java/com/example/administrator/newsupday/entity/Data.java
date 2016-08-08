package com.example.administrator.newsupday.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/7/11 0011.
 */

public class Data implements Serializable {

    private String title;//标题
    private String content;//来源于什么
    private String time;//时间
    private String icon;//图片地址
    private String link;//新闻内容地址

//    private String type;//新闻类型

    public Data(String title, String content, String time, String icon, String link) {
        this.title = title;
        this.content = content;
        this.time = time;
        this.icon = icon;
        this.link = link;
    }
//
//    public Data(String title, String content, String time, String icon, String link) {
//        this.title = title;
//        this.content = content;
//        this.time = time;
//        this.icon = icon;
//        this.link = link;
////        this.type = type;
//    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }




    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Data{" +
                "icon=" + icon +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
