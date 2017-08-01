package com.example.leet.myminlist;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by leet on 17-7-18.
 */

public class MyDecoration extends RecyclerView.ItemDecoration {
    private Context mContext;
    private Drawable divider;
    private int orientation;
    private static final int HORIZONTAL_LIST= LinearLayoutManager.HORIZONTAL;
    private static final int VERTICAL_LIST=LinearLayoutManager.VERTICAL;
    private static final int[] attrs=new int[]{
      android.R.attr.listDivider
    };
    public MyDecoration(Context context){
        this.mContext=context;
        final TypedArray ta=context.obtainStyledAttributes(attrs);
        this.divider=ta.getDrawable(0);
        ta.recycle();

    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left=parent.getPaddingLeft();
        int right=parent.getWidth()-parent.getPaddingRight();
        int childCount=parent.getChildCount();
        for(int i=0;i<childCount;i++){
            View view=parent.getChildAt(i);
            RecyclerView.LayoutParams params= (RecyclerView.LayoutParams) view.getLayoutParams();
            int top=view.getBottom()+params.bottomMargin;
            int bottom=top+divider.getIntrinsicHeight();
            divider.setBounds(left,top,right,bottom);
            divider.draw(c);
        }

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(0,0,0,divider.getIntrinsicHeight());
    }
}
