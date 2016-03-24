package com.jike.project1_2048.myGame;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by wancc on 2016/3/22.
 */
public class MyApplication extends Application {
    private static final String TAG ="MyApplication";
    private int mLine;
    private int mTarget;

    private SharedPreferences.Editor editor;
    private SharedPreferences config;

    @Override
    public void onCreate() {
        Log.e(TAG,"onCreate");
        super.onCreate();
        config = getSharedPreferences("config", MODE_PRIVATE);

        editor = config.edit();

        mLine=config.getInt("Line", 4);
        mTarget=config.getInt("Target", 2048);


    }

    public int getmLine() {
        return config.getInt("Line", 4);
    }

    public int getmTarget() {
        return config.getInt("Target", 2048);
    }

    public int getHighestScore(){ return config.getInt("HighestScore",0);}

    public void setHighestScore(int highestScore){
        editor.putInt("HighestScore",highestScore);
        editor.commit();//千万不要忘了提交
        Log.e(TAG,"setHighestScore"+highestScore);
    }

    public void setmLine(int mLine) {
        editor.putInt("Line",mLine);
        this.mLine = mLine;
        editor.commit();
    }


    public void setmTarget(int mTarget) {
        editor.putInt("Target",mTarget);
        this.mTarget = mTarget;
        editor.commit();
    }



}
