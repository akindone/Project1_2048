package com.jike.project1_2048.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by wancc on 2016/3/21.
 */
public class MyTextView extends TextView {
    private static final String TAG = "MyTextView";

    public MyTextView(Context context) {
        super(context);
        init(context);
    }


    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void init(Context context) {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        //Log.e(TAG,"onDraw");
        super.onDraw(canvas);
    }
}
