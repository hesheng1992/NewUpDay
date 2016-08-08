package com.example.administrator.newsupday.entity;

/**
 * Created by Administrator on 2016/7/22 0022.
 */

public class ShowapiResBodyBean {
    private int ret_code;//成功的返回码
    private PagebeanBean pagebean;//第二层

    public int getRet_code() {
        return ret_code;
    }

    public void setRet_code(int ret_code) {
        this.ret_code = ret_code;
    }

    public PagebeanBean getPagebean() {
        return pagebean;
    }

    public void setPagebean(PagebeanBean pagebean) {
        this.pagebean = pagebean;
    }

    @Override
    public String toString() {
        return "ShowapiResBodyBean{" +
                "ret_code=" + ret_code +
                ", pagebean=" + pagebean +
                '}';
    }
}
