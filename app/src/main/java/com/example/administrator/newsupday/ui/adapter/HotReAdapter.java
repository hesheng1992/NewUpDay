package com.example.administrator.newsupday.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.newsupday.R;
import com.example.administrator.newsupday.common.LoadImage;
import com.example.administrator.newsupday.entity.Data;
import com.example.administrator.newsupday.entity.HotData;
import com.example.administrator.newsupday.ui.ShowActivity;

import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2016/7/12 0012.
 */

public class HotReAdapter extends RecyclerView.Adapter<HotReAdapter.MyViewHolder> {
    private List<Data> list;
    private LayoutInflater inflater;
    private static Bitmap Inbitmap;
    private Context context;
    private LoadImage loadImage=new LoadImage(context, new LoadImage.ImageLoadListener() {
        @Override
        public void imageLoadOK(Bitmap bitmap, String url) {
            Inbitmap=bitmap;
        }
    });


    public HotReAdapter(Context context,List<Data> list){
        this.context=context;
        this.list=list;
        inflater=LayoutInflater.from(context);

    }
    private RecycleClickListener listener;

    public void Clicklistenr(RecycleClickListener listener){
        this.listener=listener;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.hotrecycler_item,parent,false);
        MyViewHolder holder=new MyViewHolder(view);



        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
//        holder.hot_tv.setText(list.get(position).getContent()+"");
        //设置瀑布流的随机变化的宽高
//        Random rd=new Random();
//        int width= rd.nextInt(60) + 10;
//        int height = rd.nextInt(50) + 10;
//        holder.hot_rl.setMinimumWidth(width);
//        holder.hot_rl.setMinimumHeight(height);

        holder.hot_iv.setImageResource(R.drawable.dage);
        Bitmap bitmap = loadImage.getBitmap(list.get(position).getIcon());
//        if (Inbitmap!=null){
//            holder.hot_iv.setImageBitmap(Inbitmap);
        if (bitmap!=null){
            holder.hot_iv.setImageBitmap(bitmap);
        }
        holder.hot_tv.setText(list.get(position).getTitle());


        holder.hot_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                int id = holder.getLayoutPosition();
//                if (listener!=null){
//                    listener.OnitemClickListener(view,id);
//                }
                Intent intent=new Intent(context, ShowActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("news",list.get(position));
                intent.putExtras(bundle);
                context.startActivity(intent);


            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder{
        private FrameLayout hot_rl;
        private ImageView hot_iv;
        private TextView hot_tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            hot_iv= (ImageView) itemView.findViewById(R.id.hot_iv);
            hot_rl= (FrameLayout) itemView.findViewById(R.id.hot_rl);
            hot_tv= (TextView) itemView.findViewById(R.id.hot_tv);

        }
    }

    //点击item的回调接口
    public interface RecycleClickListener{
        void OnitemClickListener(View view,int posion);

    }

}
