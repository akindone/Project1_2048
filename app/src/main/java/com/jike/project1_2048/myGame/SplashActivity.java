package com.jike.project1_2048.myGame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.jike.project1_2048.R;

import net.youmi.android.AdManager;
import net.youmi.android.spot.SpotDialogListener;
import net.youmi.android.spot.SpotManager;

/**
 * 插屏广告demo
 */
public class SplashActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //在该页面停留一下，播放广告和程序初始化
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Thread.currentThread().sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //该API相当于让系统发消息，在主线程里执行。
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(SplashActivity.this, Home.class);
                        startActivity(intent);
                        //有些页面只需要应用打开的时候进去一次，在跳转后就把它销毁
                        finish();
                    }
                });
            }
        }.start();


        //应用启动初始化
        AdManager.getInstance(this).init("651778447f1acfe3", "662fff0808f366b2", true);
        //预加载插屏广告数据
        SpotManager.getInstance(this).loadSpotAds();
        //设置插屏横竖屏展示与展示动画设置
        SpotManager.getInstance(this).setSpotOrientation(SpotManager.ORIENTATION_PORTRAIT);
        SpotManager.getInstance(this).setAnimationType(SpotManager.ANIM_SIMPLE);
        // 展示插屏广告
        SpotManager.getInstance(this).showSpotAds(this);

        // 插屏监听接口（可选）
        SpotManager.getInstance(this).showSpotAds(this, new SpotDialogListener() {
            @Override
            public void onShowSuccess() {
                Log.e("SplashActivity", "onShowSuccess");
            }

            @Override
            public void onShowFailed() {
                Log.e("SplashActivity", "onShowSuccess");
            }

            @Override
            public void onSpotClosed() {
                Log.e("SplashActivity", "onShowSuccess");
            }

            @Override
            public void onSpotClick(boolean b) {
                Log.e("SplashActivity", "onShowSuccess");
            }
        });




    }

    @Override
    public void onBackPressed() {
        // 如果有需要，可以点击后退关闭插播广告。
        if (!SpotManager.getInstance(this).disMiss()) {
            // 弹出退出窗口，可以使用自定义退屏弹出和回退动画,参照demo,若不使用动画，传入-1
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        SpotManager.getInstance(this).onDestroy();
        super.onDestroy();
    }
}
