package com.jike.project1_2048.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.GridLayout;
import android.widget.TextView;

import com.jike.project1_2048.R;

public class GridLayout1 extends ActionBarActivity {

    private static final String TAG = "GridLayout1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_layout1);

        GridLayout gl_gridLayout1_content = (GridLayout) findViewById(R.id.gl_gridLayout1_content);
        gl_gridLayout1_content.setColumnCount(5);
        //gl_gridLayout1_content.setUseDefaultMargins(true);

        for (int i=0;i<20;i++){
            TextView textView = new TextView(this);
            textView.setText(""+i/5+i%5+",");
            textView.setBackgroundColor(Color.GRAY);
            textView.setPaddingRelative(10,10,10,10);
            gl_gridLayout1_content.addView(textView);
        }


    }
}
