package com.example.administrator.newsupday.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/7/22 0022.
 */

public class ContentlistBean implements Serializable {
    private String pubDate;//日期
    private String title;//标题
    private String channelName;//频道
    private String desc;//新闻描述
    private String source;//来源
    private String channelId;//频道的id
    private String nid;
    private String link;//连接地址
    private List<ImageurlsBean> imageurls;
    private String imurl;

    public ContentlistBean(String pubDate, String title, String channelName, String desc, String source, String channelId, String nid, String link, List<ImageurlsBean> imageurls) {
        this.pubDate = pubDate;
        this.title = title;
        this.channelName = channelName;
        this.desc = desc;
        this.source = source;
        this.channelId = channelId;
        this.nid = nid;
        this.link = link;
        this.imageurls = imageurls;
        this.imurl=getimurl(imageurls);
    }
    public ContentlistBean(String pubDate, String title, String channelName, String desc, String source, String channelId, String nid, String link, String imageurls) {
        this.pubDate = pubDate;
        this.title = title;
        this.channelName = channelName;
        this.desc = desc;
        this.source = source;
        this.channelId = channelId;
        this.nid = nid;
        this.link = link;
        this.imurl=imageurls;
    }


    /**
     * height : 366
     * width : 550
     * url : http://www.people.com.cn/mediafile/pic/20160722/81/7162215988128404449.jpg
     */



    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<ImageurlsBean> getImageurls() {
        return imageurls;
    }
    public void setImageurls(List<ImageurlsBean> imageurls) {
        this.imageurls = imageurls;
        getimurl(imageurls);

    }

    public String getimurl(List<ImageurlsBean> list){
        if (list!=null&&list.size()>0){
            for (int i=0;i<list.size();i++){
                imurl=list.get(i).getUrl();
        }
            return imurl;
        }
        return null;
    }

    @Override
    public String toString() {
        return "ContentlistBean{" +
                "pubDate='" + pubDate + '\'' +
                ", title='" + title + '\'' +
                ", channelName='" + channelName + '\'' +
                ", desc='" + desc + '\'' +
                ", source='" + source + '\'' +
                ", channelId='" + channelId + '\'' +
                ", nid='" + nid + '\'' +
                ", link='" + link + '\'' +
                ", imageurls=" + imageurls +
                ", imurl='" + imurl + '\'' +
                '}';
    }
}
