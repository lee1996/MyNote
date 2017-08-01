package com.example.leet.myminlist;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.FocusFinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowId;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by leet on 17-7-27.
 */

public class MyEditText extends android.support.v7.widget.AppCompatEditText implements TextWatcher,View.OnFocusChangeListener{
    private Drawable drawable;
    private int coloraccent;
    private boolean hasFocus;
    public MyEditText(Context context) {
        super(context);
        TypedArray typedArray=context.getTheme().obtainStyledAttributes(new int[]{android.R.attr.colorAccent});
        coloraccent=typedArray.getColor(0,0xff00ff);
        typedArray.recycle();
        init(context);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray=context.getTheme().obtainStyledAttributes(new int[]{android.R.attr.colorAccent});
        coloraccent=typedArray.getColor(0,0xff00ff);
        typedArray.recycle();
        init(context);
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray=context.getTheme().obtainStyledAttributes(new int[]{android.R.attr.colorAccent});
        coloraccent=typedArray.getColor(0,0xff00ff);
        typedArray.recycle();
        init(context);
    }

    public void init(Context context){
        drawable=getCompoundDrawables()[2];
        if(drawable==null){
            drawable=getResources().getDrawable(R.drawable.delete);
        }
        DrawableCompat.setTint(drawable,coloraccent);
        drawable.setBounds(0,0,(int)getTextSize(),(int)getTextSize());
        setIcon(false);
        this.setOnFocusChangeListener(this);
        this.addTextChangedListener(this);
    }
    public void setIcon(boolean is){
        Drawable drawable1=is?drawable:null;
        setCompoundDrawables(getCompoundDrawables()[0],getCompoundDrawables()[1],drawable1,getCompoundDrawables()[3]);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(drawable!=null&&event.getAction()==MotionEvent.ACTION_UP){

            int x= (int) event.getX();
            boolean isInnerWidth=(x>(getWidth()-getTotalPaddingRight()))&&(x<(getWidth()-getPaddingRight()));
            Rect rect=drawable.getBounds();
            int height=rect.height();
            int y= (int) event.getY();
            int distance=(getHeight()-height)/2;
            boolean isInnerHeight=(y>distance)&&(y<(distance+height));
            if(isInnerHeight&&isInnerWidth){
                this.setText("");
            }

        }

        return super.onTouchEvent(event);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        if(hasFocus){
            setIcon(text.length()>0);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onFocusChange(View view, boolean b) {
        hasFocus=b;
        if(hasFocus){
            setIcon(getText().length()>0);
        }else{
            setIcon(false);
        }
    }



}
