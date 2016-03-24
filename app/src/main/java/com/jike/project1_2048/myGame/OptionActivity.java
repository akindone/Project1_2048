package com.jike.project1_2048.myGame;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.jike.project1_2048.R;

import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;

public class OptionActivity extends ActionBarActivity implements View.OnClickListener {

    private Button bt_option_back ;
    private Button bt_option_done ;
    private Button bt_option_setline ;
    private Button bt_option_settarget;
    private int line;
    private int target;
    private MyApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        /**
         * 普通广告条
         */
        /*// 实例化广告条
        AdView adView = new AdView(this, AdSize.FIT_SCREEN);

        // 获取要嵌入广告条的布局
        LinearLayout adLayout=(LinearLayout)findViewById(R.id.adLayout);

        // 将广告条加入到布局中
        adLayout.addView(adView);*/

        /**
         *  悬浮布局（适用于游戏）
         */

        // 实例化 LayoutParams（重要）
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);

        // 设置广告条的悬浮位置
        layoutParams.gravity = Gravity.TOP | Gravity.RIGHT; // 这里示例为右上角

        // 实例化广告条
        AdView adView = new AdView(this, AdSize.FIT_SCREEN);

        // 调用 Activity 的 addContentView 函数
        this.addContentView(adView, layoutParams);

       //--------------------------------------------------------------

        //初始化button
        bt_option_back      = (Button) findViewById(R.id.bt_option_back);
        bt_option_done      = (Button) findViewById(R.id.bt_option_done);
        bt_option_setline   = (Button) findViewById(R.id.bt_option_setline);
        bt_option_settarget = (Button) findViewById(R.id.bt_option_settarget);

        bt_option_back     .setOnClickListener(this);
        bt_option_done     .setOnClickListener(this);
        bt_option_setline  .setOnClickListener(this);
        bt_option_settarget.setOnClickListener(this);

        app = (MyApplication) getApplication();
        line=app.getmLine();
        target=app.getmTarget();
        bt_option_setline.setText(line+"");
        bt_option_settarget.setText(target+"");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.bt_option_setline:

                setline();
                break;
            case R.id.bt_option_settarget:

                setTarget();
                break;

            case R.id.bt_option_back:
                Intent intent = new Intent();
                setResult(2,intent);
                finish();
                break;

            case R.id.bt_option_done:
                done();
                break;
        }
    }

    private void done() {
        app.setmLine(line);
        app.setmTarget(target);
        Intent intent = new Intent();
        setResult(1, intent);
        finish();
    }

    private void setTarget() {
        final String[] items={"1024","2048","4096"};
        new AlertDialog.Builder(this)
                .setTitle("选择难度")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        target = Integer.parseInt(items[which]);
                        bt_option_settarget.setText(target + "");
                    }
                }).show();
    }

    private void setline() {
        final String[] items={"4","5","6"};
        new AlertDialog.Builder(this)
                .setTitle("格子数")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        line = Integer.parseInt(items[which]);
                        bt_option_setline.setText(line + "");
                    }
                })
                .show();

    }


}
