package com.jike.project1_2048.myGame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jike.project1_2048.R;
import com.jike.project1_2048.view.GameView;

public class Home extends ActionBarActivity implements View.OnClickListener{

    private static final String TAG = "Home";

    private MyApplication app;
    private static Home mActivity;
    private GameView mGameView;

    private Button bt_home_restart;
    private Button bt_home_revert ;
    private Button bt_home_option ;
    private TextView tv_home_target;
    private TextView tv_home_score;
    private TextView tv_home_highestscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.e(TAG, "onCreate");

        //获取自己的应用和应用的引用,要放在setContentView的前面
        mActivity = this;
        app= (MyApplication)getApplication();



        //布局相关
        RelativeLayout rl_home_content = (RelativeLayout) findViewById(R.id.rl_home_content);
        rl_home_content.setPadding(5, 5, 5, 5);

        mGameView = new GameView(this);
        rl_home_content.addView(mGameView);



        //找到各种控件
        bt_home_restart = (Button) findViewById(R.id.bt_home_restart);
        bt_home_revert = (Button) findViewById(R.id.bt_home_revert);
        bt_home_option = (Button) findViewById(R.id.bt_home_option);
        bt_home_restart.setOnClickListener(this);
        bt_home_revert .setOnClickListener(this);
        bt_home_option .setOnClickListener(this);

        tv_home_target = (TextView) findViewById(R.id.tv_home_target);
        tv_home_score = (TextView) findViewById(R.id.tv_home_score);
        tv_home_highestscore = (TextView) findViewById(R.id.tv_home_highestscore);

        tv_home_target.setText(app.getmTarget() + "");
        tv_home_score.setText("0");
        tv_home_highestscore.setText(app.getHighestScore() + "");

        Log.e(TAG, "onCreate");

    }

    public static Home getActivity(){
        return mActivity;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_home_restart:
                mGameView.reStart();
                break;
            case R.id.bt_home_revert:
                mGameView.revert();
                break;
            case R.id.bt_home_option:
                Intent intent = new Intent(this, OptionActivity.class);
                startActivityForResult(intent,200);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==200){
            //if (resultCode==2){}
            if (resultCode==1){
                updateTarget(app.getmTarget());
                mGameView.reStart();
            }
        }
    }

    private void updateTarget(int target) {
        tv_home_target.setText(target + "");
    }

    public  void updateScore(int score) {
        Log.e(TAG,"score "+score);
        tv_home_score.setText(score+"");
    }

    public void updateHighestScore(int highestscore) {
        Log.e(TAG,"highestscore"+highestscore);
        tv_home_highestscore.setText(highestscore+"");
    }


}
