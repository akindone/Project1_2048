package com.jike.project1_2048.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;

/**
 * Created by wancc on 2016/3/21.
 */
public class NumberItem extends FrameLayout {


    private MyTextView mTextView;
    private int number;
    public int getNumber(){return number;}

    public NumberItem(Context context,int number) {
        super(context);
        this.number=number;
        init(context,number);
    }

    public NumberItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void init(Context context,int number) {
        //设置xml属性
        //setBackgroundResource(R.drawable.bg_numberitem);//效果怎么表现成4*4的框的底图了？？？


        //在内部添加textView
        mTextView = new MyTextView(context);
        setTextNumber(number);
//        mTextView.setBackgroundResource(R.drawable.bg_numberitem);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.setMargins(5,5,5,5);
        mTextView.setGravity(Gravity.CENTER);

        addView(mTextView, params);

    }

    public void setTextNumber(int num){
        this.number=num;
        if (num==0) mTextView.setText("");
        else {
            mTextView.setText(num+"");
        }
        switch (num){
            case 0:
                mTextView.setBackgroundColor(0xffD2D2D2);
                //mTextView.setBackgroundColor(0x00000000);
                break;
            case 2:
                mTextView.setBackgroundColor(0xffFDF4F2);
                break;
            case 4:
                mTextView.setBackgroundColor(0xffFDD8A1);
                break;
            case 8:
                mTextView.setBackgroundColor(0xffFB8B5F);
                break;
            case 16:
                mTextView.setBackgroundColor(0xffFB672C);
                break;
            case 32:
                mTextView.setBackgroundColor(0xffFB672C);
                break;
            case 64:
                mTextView.setBackgroundColor(0xffFB672C);
                break;
            case 128:
                mTextView.setBackgroundColor(0xffFB672C);
                break;
            case 256:
                mTextView.setBackgroundColor(0xffFB672C);
                break;
            case 512:
                mTextView.setBackgroundColor(0xffFB672C);
                break;
            case 1024:
                mTextView.setBackgroundColor(0xffFB672C);
                break;
            case 2049:
                mTextView.setBackgroundColor(0xffFB672C);
                break;
            case 4096:
                mTextView.setBackgroundColor(0xffFB672C);
                break;

        }

    }


}
