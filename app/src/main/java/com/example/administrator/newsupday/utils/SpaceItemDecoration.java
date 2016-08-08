package com.example.administrator.newsupday.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2016/7/11 0011.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration{
        private int space;

    public SpaceItemDecoration(int spaces){
        this.space=spaces;

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;

        if (parent.getChildLayoutPosition(view)==0){
            outRect.top=space;

        }

    }
}
