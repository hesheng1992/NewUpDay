package com.example.administrator.newsupday.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.example.administrator.newsupday.R;
import com.example.administrator.newsupday.common.LoadImage;
import com.example.administrator.newsupday.db.NewsDbManager;
import com.example.administrator.newsupday.entity.Content;
import com.example.administrator.newsupday.entity.ContentlistBean;
import com.example.administrator.newsupday.entity.Data;
import com.example.administrator.newsupday.ui.ShowActivity;

import java.io.Serializable;
import java.util.List;


/**
 * Created by Administrator on 2016/7/10 0010.
 */

public class XZAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private LayoutInflater inflater;
    private Context context;
//    private List<Data> list;
    private List<ContentlistBean> list;
    private itemClickListener listener;
    private RecyclerView view;
//    private NewsDbManager manager=new NewsDbManager(context);
    private Bitmap bitmap;
    private static final int ITEM_VIEW_TYPE_HEADER = 0;
    private static final int ITEM_VIEW_TYPE_ITEM = 1;




    private LoadImage loadImage=new LoadImage(context, new LoadImage.ImageLoadListener() {
        @Override
        public void imageLoadOK(Bitmap bitmap, String url) {
//            ImageView iv = (ImageView) view.findViewWithTag(url);
//            iv.setImageBitmap(bitmap);

        }
    });


    public XZAdapter(Context context, List<ContentlistBean> list, RecyclerView view){
        this.view=view;
        this.context=context;
        inflater=LayoutInflater.from(context);
        this.list=list;
        bitmap= BitmapFactory.decodeResource(context.getResources(),R.drawable.xiong);

    }

    public void itemlistener(itemClickListener listener){
        this.listener=listener;

    }


    public boolean isHeader(int position) {
        return position == 0;
    }






    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==ITEM_VIEW_TYPE_HEADER){
            return new HeaderHodler(inflater.inflate(R.layout.header_item,parent,false));
        }

        View view=inflater.inflate(R.layout.recycleview_item,parent,false);
        MyViewHolder holder=new MyViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
//        final MyViewHolder maholder= (MyViewHolder) holder;

        if (holder instanceof HeaderHodler){
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


        }else if (holder instanceof MyViewHolder){
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
                    int layoutPosition = maholder.getLayoutPosition();
                    if (listener!=null){
                        listener.itemclicklistener(view,layoutPosition);

                    }

                }
            });

        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    @Override
    public int getItemViewType(int position) {
        return isHeader(position) ? ITEM_VIEW_TYPE_HEADER : ITEM_VIEW_TYPE_ITEM;

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

    //item的接口点击事件
    public  interface itemClickListener{
        void itemclicklistener(View view,int position);
    }

    //添加一个集合到数据.
    public void addData(List<ContentlistBean> list1) {
        if (list1 != null) {
            list.addAll(list1);
        }

    }

    public itemClickListener getListener() {
        return listener;
    }

    public void setListener(itemClickListener listener) {
        this.listener = listener;
    }




//    public RecyclerView mRecyclerView;
//    private OnLoadingListener mOnLoadingListener;
//    //是否正在加载
//    public boolean mIsLoading = false;
//    //首次进入
//    public boolean mFirstEnter = true;
//
//    private boolean canScrollDown(RecyclerView recyclerView) {
//        return ViewCompat.canScrollVertically(recyclerView, 1);
//    }
//    /**
//     * 显示加载
//     */
//    private void notifyLoading() {
//        if (list.size() != 0 && list.get(list.size()-1) != null) {
//            list.add(null);
//            Log.i("add","添加最后一行");
//            notifyItemInserted(list.size() - 1);
//
//        }
//    }
//
//    /**
//     * 加载更多接口
//     */
//    public interface OnLoadingListener {
//        void loading();
//    }
//    public void setOnLoadingListener(OnLoadingListener onLoadingListener) {
//        setScrollListener(mRecyclerView);
//        mOnLoadingListener = onLoadingListener;
//    }
//
//    /**
//     * 监听滚动事件
//     *
//     * @param recyclerView recycleView
//     */
//    private void setScrollListener(RecyclerView recyclerView) {
//        if(recyclerView == null) {
//            Log.e("TAG", "recycleView 为空");
//            return;
//        }
//
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//                if (!canScrollDown(recyclerView)) {
//                    Log.i("add","滑到底部");
//                    //首次进入不加载
//                    if (!mIsLoading && !mFirstEnter) {
//
//                        notifyLoading();
//
//                        mIsLoading = true;
//
//                        if (mLoadingViewHolder != null) {
//                            mLoadingViewHolder.progressBar.setVisibility(View.VISIBLE);
//                            mLoadingViewHolder.tvLoading.setText("正在加载...");
//                        }
//
//                        if (mOnLoadingListener != null) {
//                            mOnLoadingListener.loading();
//                        }
//                    }
//                }
//
//                if (mFirstEnter) {
//                    mFirstEnter = false;
//                }
//            }
//        });
//    }
//    /**
//     * 加载布局
//     */
//    private class LoadingViewHolder extends RecyclerView.ViewHolder {
//        public ProgressBar progressBar;
//        public TextView tvLoading;
//        public RelativeLayout llyLoading;
//
//        public LoadingViewHolder(View view) {
//            super(view);
//
//            progressBar = (ProgressBar) view.findViewById(R.id.progress_loading);
//            tvLoading = (TextView) view.findViewById(R.id.tv_loading);
//            llyLoading = (RelativeLayout) view.findViewById(R.id.lly_loading);
//        }
//    }



}
