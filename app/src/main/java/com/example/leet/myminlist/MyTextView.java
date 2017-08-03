package com.example.leet.myminlist;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by leet on 17-8-3.
 */

public class MyTextView extends android.support.v7.widget.AppCompatTextView {
    private int mViewwidth=0;
    private TextPaint textPaint;
    private LinearGradient linearGradient;
    private Matrix matrix;
    private int mTranslate=0;
    public MyTextView(Context context) {
        this(context,null,0);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(matrix!=null){
            mTranslate+=mViewwidth/7;
            if(mTranslate>2*mViewwidth){
                mTranslate=-mViewwidth;
            }
            matrix.setTranslate(mTranslate,0);
            linearGradient.setLocalMatrix(matrix);
            postInvalidateDelayed(50);

        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(mViewwidth==0){
            mViewwidth=getMeasuredWidth();
            if(mViewwidth>0) {
                textPaint = getPaint();
                linearGradient=new LinearGradient(0,0,mViewwidth,0,new int[]{Color.BLACK,Color.WHITE,Color.BLACK,Color.WHITE,Color.BLACK},null, Shader.TileMode.CLAMP);
                textPaint.setShader(linearGradient);
                matrix=new Matrix();

            }
        }
    }
}
