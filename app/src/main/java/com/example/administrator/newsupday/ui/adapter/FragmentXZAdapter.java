package com.example.administrator.newsupday.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.newsupday.R;
import com.example.administrator.newsupday.common.LoadImage;
import com.example.administrator.newsupday.entity.Content;
import com.example.administrator.newsupday.entity.ContentlistBean;
import com.example.administrator.newsupday.ui.ShowActivity;
import com.example.administrator.newsupday.ui.base.BaseLoadAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/7/24 0024.
 */

public class FragmentXZAdapter extends BaseLoadAdapter<ContentlistBean> {
    private List<ContentlistBean> list;
    private Context context;
    private DisplayImageOptions options;//图片加载设置
    private LayoutInflater inflater;
    private Bitmap bitmap;
    private LoadImage loadImage=new LoadImage(context, new LoadImage.ImageLoadListener() {
        @Override
        public void imageLoadOK(Bitmap bitmap, String url) {
//            ImageView iv = (ImageView) view.findViewWithTag(url);
//            iv.setImageBitmap(bitmap);

        }
    });



    public FragmentXZAdapter(RecyclerView recyclerView, List<ContentlistBean> contentlistBeen,Context context) {
        super(recyclerView, contentlistBeen);
        this.list=contentlistBeen;
        this.context=context;
        inflater=LayoutInflater.from(context);
        bitmap= BitmapFactory.decodeResource(context.getResources(),R.drawable.xiong);


    }

    @Override
    public RecyclerView.ViewHolder onCreateNormalViewHolder(ViewGroup parent) {
        View view=inflater.inflate(R.layout.recycleview_item,parent,false);
        MyViewHolder holder=new MyViewHolder(view);


        return holder;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeadViewHolder(ViewGroup parent) {
        View view=inflater.inflate(R.layout.header_item,null);
        return new HeaderHodler(view);
    }


    @Override
    public void onBindNormalViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MyViewHolder maholder= (MyViewHolder) holder;
        ContentlistBean contentlistBean = list.get(position);
//            manager.addNews(data);
        maholder.iv.setImageBitmap(bitmap);
        maholder.itemView.setTag(position);
        Bitmap bit=loadImage.getBitmap(list.get(position).getimurl(list.get(position).getImageurls()));
        if (bit!=null){
            maholder.iv.setImageBitmap(bit);
        }


        maholder.tv.setText(list.get(position).getTitle());
        maholder.tv1.setText(list.get(position).getSource());
        maholder.time.setText(list.get(position).getPubDate());
        maholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentlistBean contentlistBean1 = list.get(position);
                String link = contentlistBean1.getLink();
                String getimurl = contentlistBean1.getimurl(contentlistBean1.getImageurls());
                String desc = contentlistBean1.getDesc();
                String pubDate = contentlistBean1.getPubDate();
                String source = contentlistBean1.getSource();
                String channelId = contentlistBean1.getChannelId();
                String channelName = contentlistBean1.getChannelName();
                String title = contentlistBean1.getTitle();
                String nid = contentlistBean1.getNid();
                Content content=new Content(pubDate,title,channelName,desc,source,channelId,nid,link,getimurl);


                Intent intent=new Intent(context, ShowActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("news", (Serializable) content);
//                    Log.e("TAG","新闻详细传递到show"+contentlistBean1.toString());
                intent.putExtras(bundle);
//                    intent.putExtra("url",contentlistBean1.getLink());
                context.startActivity(intent);
//                int layoutPosition = maholder.getLayoutPosition();
//                if (listener!=null){
//                    listener.itemclicklistener(view,layoutPosition);
//
//                }

            }
        });


    }

    @Override
    public void onBindHeadViewHolder(RecyclerView.ViewHolder holder, int position) {
        HeaderHodler headerHodler= (HeaderHodler) holder;
        headerHodler.iv.setImageBitmap(loadImage.getBitmap(list.get(0).getimurl(list.get(0).getImageurls())));
        headerHodler.tv.setText(list.get(0).getTitle());
        headerHodler.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentlistBean contentlistBean = list.get(0);

                String link = contentlistBean.getLink();
                String getimurl = contentlistBean.getimurl(contentlistBean.getImageurls());
                String desc = contentlistBean.getDesc();
                String pubDate = contentlistBean.getPubDate();
                String source = contentlistBean.getSource();
                String channelId = contentlistBean.getChannelId();
                String channelName = contentlistBean.getChannelName();
                String title = contentlistBean.getTitle();
                String nid = contentlistBean.getNid();
                Content content1=new Content(pubDate,title,channelName,desc,source,channelId,nid,link,getimurl);


                Intent intent=new Intent(context, ShowActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("news", (Serializable) content1);
                intent.putExtras(bundle);
                context.startActivity(intent);

            }
        });

    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView iv;
        TextView tv;
        TextView tv1;
        TextView time;
        RelativeLayout item_view;


        public MyViewHolder(View itemView) {
            super(itemView);
            iv= (ImageView) itemView.findViewById(R.id.iv);
            tv= (TextView) itemView.findViewById(R.id.item);
            tv1= (TextView) itemView.findViewById(R.id.item1);
            time= (TextView) itemView.findViewById(R.id.time);
            item_view= (RelativeLayout) itemView.findViewById(R.id.item_view);


        }


    }

    class HeaderHodler extends RecyclerView.ViewHolder{
        TextView tv;
        ImageView iv;


        public HeaderHodler(View HitemView) {
            super(HitemView);
            tv= (TextView) HitemView.findViewById(R.id.header_tv);
            iv= (ImageView) HitemView.findViewById(R.id.header_iv);

        }
    }

}
