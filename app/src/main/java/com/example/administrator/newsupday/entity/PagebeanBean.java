package com.example.administrator.newsupday.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/7/22 0022.
 */

public class PagebeanBean {
    private int allPages;
    private int currentPage;
    private int allNum;
    private int maxResult;
    private List<ContentlistBean> contentlist;//返回一个实体的集合
    /**
     * pubDate : 2016-07-22 10:04:07
     * title : 福州高中生足球邀请赛鸣哨 比赛将于8月2日结束
     * channelName : 社会最新
     * imageurls : [{"height":366,"width":550,"url":"http://www.people.com.cn/mediafile/pic/20160722/81/7162215988128404449.jpg"}]
     * desc : 师大二附中队与福州八中队在比赛中。昨日上午，2016年福州市高中生八校足球邀请赛在福州金山中学鸣哨开赛，福建师大二附中队在揭幕战中与福州八中队狭路相逢，最终以3∶1逆转对手取得开门红。下半场，师大二附中队基本掌握了场上比赛的节奏，战至55分钟，8号黄旭泽门前抢点再进一球，以2∶1实现反超。比赛将于8月2日结束。
     * source : 人民网福建频道
     * channelId : 5572a10bb3cdc86cf39001f8
     * nid : 15064258677839018618
     * link : http://fj.people.com.cn/n2/2016/0722/c234960-28711243.html
     */



    public int getAllPages() {
        return allPages;
    }

    public void setAllPages(int allPages) {
        this.allPages = allPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getAllNum() {
        return allNum;
    }

    public void setAllNum(int allNum) {
        this.allNum = allNum;
    }

    public int getMaxResult() {
        return maxResult;
    }

    public void setMaxResult(int maxResult) {
        this.maxResult = maxResult;
    }

    public List<ContentlistBean> getContentlist() {
        return contentlist;
    }

    public void setContentlist(List<ContentlistBean> contentlist) {
        this.contentlist = contentlist;
    }

    @Override
    public String toString() {
        return "PagebeanBean{" +
                "allPages=" + allPages +
                ", currentPage=" + currentPage +
                ", allNum=" + allNum +
                ", maxResult=" + maxResult +
                ", contentlist=" + contentlist +
                '}';
    }
}
