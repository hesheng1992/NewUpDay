package com.example.administrator.newsupday.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.newsupday.R;
import com.example.administrator.newsupday.common.LoadImage;
import com.example.administrator.newsupday.entity.Content;
import com.example.administrator.newsupday.entity.ContentlistBean;
import com.example.administrator.newsupday.entity.Data;

import java.util.List;

/**
 * Created by Administrator on 2016/7/19 0019.
 */

public class CollectAdapter extends BaseAdapter {
    private List<Content> list;
    private Context context;
    private LoadImage load=new LoadImage(context, new LoadImage.ImageLoadListener() {
        @Override
        public void imageLoadOK(Bitmap bitmap, String url) {

        }
    });

    public CollectAdapter(Context context,List<Content> list){
        this.list=list;
        this.context=context;


    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
       if (convertView==null){
           convertView=LayoutInflater.from(context).inflate(R.layout.collect_item,null);
           holder=new ViewHolder(convertView);
           convertView.setTag(holder);

       }
//        Data item = (Data) getItem(i);
//        ContentlistBean item = (ContentlistBean) getItem(i);

        holder= (ViewHolder) convertView.getTag();
        Content item = (Content) getItem(i);
        holder.title.setText(item.getTitle());
//        holder.iv.setImageBitmap(load.getBitmap(item.getImurl());
        holder.iv.setImageBitmap(load.getBitmap(item.getImurl()));
        holder.time.setText(item.getPubDate());
        holder.content.setText(item.getSource());



        return convertView;
    }
    class ViewHolder{
        ImageView iv;
        TextView title;
        TextView time;
        TextView content;
        public ViewHolder(View view){
            iv= (ImageView) view.findViewById(R.id.iv);
            title= (TextView) view.findViewById(R.id.item);
            time= (TextView) view.findViewById(R.id.time);
            content= (TextView) view.findViewById(R.id.item1);

        }

    }

}
