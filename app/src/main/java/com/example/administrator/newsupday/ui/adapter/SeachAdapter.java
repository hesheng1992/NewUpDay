package com.example.administrator.newsupday.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.provider.LiveFolders;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.newsupday.R;
import com.example.administrator.newsupday.common.LoadImage;
import com.example.administrator.newsupday.entity.ContentlistBean;
import com.example.administrator.newsupday.entity.Data;
import com.example.administrator.newsupday.ui.FragmentSeach;

import java.util.List;

/**
 * Created by Administrator on 2016/7/22 0022.
 */

public class SeachAdapter extends BaseAdapter{
    private List<ContentlistBean> list;
    private Context context;
    private String ettext=null;
    private LoadImage load=new LoadImage(context, new LoadImage.ImageLoadListener() {
        @Override
        public void imageLoadOK(Bitmap bitmap, String url) {

        }
    });

    public SeachAdapter(Context context,List<ContentlistBean> list,String et){
        this.list=list;
        this.context=context;
        this.ettext=et;


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
            convertView= LayoutInflater.from(context).inflate(R.layout.seach_item,null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);

        }

        holder= (ViewHolder) convertView.getTag();
        ContentlistBean item = (ContentlistBean) getItem(i);

        holder.title.setText(item.getTitle());
        holder.iv.setImageBitmap(load.getBitmap(item.getimurl(item.getImageurls())));
        holder.time.setText(item.getPubDate());
        holder.content.setText(item.getDesc());
//        String text = (String) holder.title.getText();
//        int index;
//        ForegroundColorSpan span = new ForegroundColorSpan(Color.GREEN);
//        SpannableStringBuilder builder = new SpannableStringBuilder(holder.title.getText());
//        index=text.indexOf(ettext);
//        if (index!=-1){
//            builder.setSpan(span,index,index+ettext.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//        }






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
