package com.jike.project1_2048.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.GridLayout;

import com.jike.project1_2048.R;
import com.jike.project1_2048.myGame.Home;
import com.jike.project1_2048.myGame.MyApplication;
import com.jike.project1_2048.myGame.OptionActivity;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by wancc on 2016/3/21.
 */
public class GameView extends GridLayout {
    private static final String TAG ="GameView";

    private Home mHome;
    private MyApplication app;
    private NumberItem[][] numberIteMatrix;

    static int COLUMN_COUNT;
    static int ROW_COUNT;
    private int TargetNum;
    private int highestScore;

    private ArrayList<Point> list=new ArrayList();
    private float startx;
    private float starty;
    private float endx;
    private float endy;
    private static int width;
    private static int height;
    private int score=0;
    private int addscore;
    private int[][] intMatrix;
    private boolean flag;
    private boolean moveflag;


    //用于程序员在代码中动态new view
    public GameView(Context context) {
        super(context);
        init(context);
    }

    //写在xml文件中的view，系统会调用这个构造方法区创建该view的对象
    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        Log.e(TAG,"init");

        mHome = Home.getActivity();
        app= (MyApplication) mHome.getApplication();
        COLUMN_COUNT=app.getmLine();
        ROW_COUNT=app.getmLine();
        TargetNum=app.getmTarget();
        highestScore=app.getHighestScore();
        Log.e(TAG,"init"+COLUMN_COUNT+","+ROW_COUNT+","+TargetNum+","+highestScore);


        //设置xml属性
        setBackgroundResource(R.drawable.bg_gameview);
        setPadding(5, 5, 5, 5);

        setColumnCount(COLUMN_COUNT);
        setRowCount(ROW_COUNT);

        //获取屏幕宽高
        getSize();

        //往gridLayout中加入指定行数列数的元素
        numberIteMatrix=new NumberItem[COLUMN_COUNT][ROW_COUNT];
        intMatrix=new int[COLUMN_COUNT][ROW_COUNT];

        for (int i=0;i<ROW_COUNT;i++){
            for (int j=0;j<COLUMN_COUNT;j++){
                NumberItem numberItem=new NumberItem(context,0);
                numberItem.setTextNumber(0);

                //TODO 屏幕适配？？？
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams((width-40)/COLUMN_COUNT, (width-40)/ROW_COUNT);
//                params.setMargins(5,5,5,5); //不起作用

                //用矩阵去保存numberItem的引用
                numberIteMatrix[i][j]=numberItem;

                //初始化没有数字的textview的list,从中随机取元素，对应到矩阵里
                list.add(new Point(i,j));
                addView(numberItem, params);
            }
        }

        //第一次进入主页，在两个随机的textView上加数字
        addRandomNumber();
        addRandomNumber();
        Log.e(TAG, "init");

    }

    private void addRandomNumber() {
        updateList();

        //判断list是否为空
        if (list.size()==0){
            handleResult(3);
            return;
        }
        int random = new Random().nextInt(list.size());
        Point point = list.get(random);
        numberIteMatrix[point.x][point.y].setTextNumber(new Random().nextFloat()>0.2f?2:4);
    }

    private void updateList() {
        list.clear();
        //遍历表盘，找出所有number字段的值为0的
        for (int i=0;i<ROW_COUNT;i++){
            for (int j=0;j<COLUMN_COUNT;j++){
                if (numberIteMatrix[i][j].getNumber()==0) list.add(new Point(i,j));
            }
        }
    }

    public void getSize() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);

        width = displayMetrics.widthPixels;
        height =displayMetrics.heightPixels;

        Log.e(TAG, "getSize,width=" + width);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                startx = event.getX();
                starty = event.getY();
                Log.e(TAG,"startx="+ startx +",starty"+ starty);
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                endx = event.getX();
                endy = event.getY();
                Log.e(TAG, "endx=" + endx + ",endy" + endy);

                saveNumberItem();

                judgeDerection();



                //让主页去更新实时分数
                score+=addscore;
                mHome.updateScore(score);
                Log.e(TAG, "onTouchEvent highestScore,score" + highestScore + "," + score);

                //让主页去更新最高历史记录
                if (score>highestScore){
                    highestScore=score;
                    mHome.updateHighestScore(highestScore);
                    Log.e(TAG,"onTouchEvent highestScore,score"+highestScore+","+score);
                }
                handleResult(isOver());

                break;
        }

        return true;//表示控件是否会接受事件
    }

    private void saveNumberItem() {
        for (int i=0;i<ROW_COUNT;i++){
            for (int j=0;j<COLUMN_COUNT;j++){
                int number = numberIteMatrix[i][j].getNumber();
                intMatrix[i][j]=number;
            }
        }
        flag=true;

    }

    private void handleResult(int over) {
        Log.e(TAG,"handleResult moveflag"+moveflag);
        if (moveflag){
            switch (over){
                case 1:
                    new AlertDialog.Builder(getContext())
                            .setTitle("hi")
                            .setMessage("success reach target score! do you want to restart ?")
                            .setPositiveButton("restart", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    reStart();
                                }
                            })
                            .setNegativeButton("try harder one", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(mHome, OptionActivity.class);
                                    mHome.startActivityForResult(intent, 200);
                                }
                            })
                            .show();
                    break;
                case 2:
                    addRandomNumber();
                    break;
                case 3:
                    new AlertDialog.Builder(getContext())
                            .setTitle("hi")
                            .setMessage("game over! do you want to restart ?")
                            .setPositiveButton("restart", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    reStart();
                                }
                            })
                            .setNegativeButton("exit", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //退出游戏
                                    exit();
                                }
                            })
                            .show();
                    break;
                case 4:
                    addRandomNumber();
                    break;
            }

        }

    }

    private void exit() {
        app.setHighestScore(highestScore);
        mHome.finish();
    }

    public void reStart() {

        app.setHighestScore(highestScore);

        score=0;
        mHome.updateScore(score);
        mHome.updateHighestScore(highestScore);
        Log.e(TAG, "HighestScore " + highestScore);

        removeAllViews();
        init(getContext());
        Log.e(TAG, "HighestScore " + highestScore);
    }

    public void revert(){
        if (flag){
            for (int i=0;i<ROW_COUNT;i++)
                for (int j=0;j<COLUMN_COUNT;j++){
                    numberIteMatrix[i][j].setTextNumber(intMatrix[i][j]);
                }
            mHome.updateScore(score-addscore);

        }

    }

    //判断游戏状态：1完成target 2表格满了但还有相邻相同元素 3表格满了且没有相邻相同元素，游戏结束 4格子没有满
    private int isOver() {

        for (int i=0;i<ROW_COUNT;i++){
            for (int j=0;j<COLUMN_COUNT;j++){
                int number = numberIteMatrix[i][j].getNumber();
                if (number==TargetNum){
                    return 1;
                }
            }
        }
        updateList();
        if (list.size()==0){
            for (int i=0;i<ROW_COUNT;i++){
                for (int p=0;p<COLUMN_COUNT-1;p++){
                    if (numberIteMatrix[i][p].getNumber()==numberIteMatrix[i][p+1].getNumber())
                        return 2;
                    if (numberIteMatrix[p][i].getNumber()==numberIteMatrix[p+1][i].getNumber())
                        return 2;
                }
            }
            return 3;
        }
        return 4;

    }

    private void judgeDerection() {
        float absX = Math.abs(startx - endx);
        float absY= Math.abs(starty - endy);
        addscore=0;
        //moveflag=false;
        moveflag=absX>width/10||absY>width/10;
        Log.e(TAG,"judgeDerection moveflag"+moveflag);

        if (moveflag){
            boolean derectflag=absX>absY?true:false;

            if (derectflag){
                if (startx>endx) {
                    slideLeft();
                    Log.e(TAG,"judgeDerection:LEFT ");
                }
                else {
                    slideRight();
                    Log.e(TAG,"judgeDerection:RIGHT");
                }
            }else {
                if (starty>endy) {
                    slideUp();
                    Log.e(TAG,"judgeDerection:UP ");
                }
                else {
                    slideDown();
                    Log.e(TAG,"judgeDerection:DOWN");
                }
            }

        }
    }

    private void slideDown() {
        for (int i=0;i<ROW_COUNT;i++){
            int temp=-1;
            ArrayList<Integer> resultList=new ArrayList();
            for (int j=COLUMN_COUNT-1;j>=0;j--){
                int number = numberIteMatrix[j][i].getNumber();
                if (number!=0){
                    if (number!=temp&&temp!=-1){
                        resultList.add(temp);

                    }else if(temp!=-1){
                        resultList.add(number*2);
                        addscore+=number*2;
                        //currentScore+=number*2;
                        temp=-1;
                        continue;
                    }
                    temp=number;
                }
            }

            if (temp!=-1&&temp!=0){
                resultList.add(temp);
            }
            for (int p=0;p<resultList.size();p++){
                numberIteMatrix[COLUMN_COUNT-1-p][i].setTextNumber(resultList.get(p));
            }
            for (int q=resultList.size();q<COLUMN_COUNT;q++){
                numberIteMatrix[COLUMN_COUNT-1-q][i].setTextNumber(0);
            }
            resultList.clear();
        }

    }

    private void slideUp() {
        for (int i=0;i<ROW_COUNT;i++){
            int temp=-1;
            ArrayList<Integer> resultList=new ArrayList();
            for (int j=0;j<COLUMN_COUNT;j++){
                int number = numberIteMatrix[j][i].getNumber();
                if (number!=0){
                    if (number!=temp&&temp!=-1){
                        resultList.add(temp);

                    }else if(temp!=-1){
                        resultList.add(number*2);
                        addscore=number*2;
                        //currentScore+=number*2;
                        temp=-1;
                        continue;
                    }
                    temp=number;
                }
            }
            if (temp!=-1&&temp!=0){
                resultList.add(temp);
            }

            for (int p=0;p<resultList.size();p++){
                numberIteMatrix[p][i].setTextNumber(resultList.get(p));
            }
            for (int q=resultList.size();q<COLUMN_COUNT;q++){
                numberIteMatrix[q][i].setTextNumber(0);
            }
            resultList.clear();
        }

    }

    private void slideRight() {
        for (int i=0;i<ROW_COUNT;i++){
            int temp=-1;
            ArrayList<Integer> resultList=new ArrayList();
            for (int j=COLUMN_COUNT-1;j>=0;j--){
                int number = numberIteMatrix[i][j].getNumber();
                if (number!=0){
                    if (number!=temp&&temp!=-1){
                        resultList.add(temp);

                    }else if(temp!=-1){
                        resultList.add(number*2);
                        addscore=number*2;
                        //currentScore+=number*2;
                        temp=-1;
                        continue;
                    }
                    temp=number;
                }

            }
            if (temp!=-1&&temp!=0){
                resultList.add(temp);
            }
            Log.e(TAG,"resultList.size()"+resultList.size());
            for (int p=0;p<resultList.size();p++){
                numberIteMatrix[i][COLUMN_COUNT-1-p].setTextNumber(resultList.get(p));
            }
            for (int q=resultList.size();q<COLUMN_COUNT;q++){
                numberIteMatrix[i][COLUMN_COUNT-1-q].setTextNumber(0);
            }
            resultList.clear();
        }
    }

    private void slideLeft() {
        for (int i=0;i<ROW_COUNT;i++){
            int temp=-1;
            ArrayList<Integer> resultList=new ArrayList();

            for (int j=0;j<COLUMN_COUNT;j++){
                int number = numberIteMatrix[i][j].getNumber();

                if (number!=0){
                    if (number!=temp&&temp!=-1){
                        resultList.add(temp);

                    }else if(temp!=-1){
                        resultList.add(number*2);
                        addscore=number*2;
                        //currentScore+=number*2;
                        temp=-1;
                        continue;
                    }
                    temp=number;
                }
            }
            //把最后一个prenumber加入到集合中
            if (temp!=0&&temp!=-1)
                resultList.add(temp);

            Log.e(TAG,"resultList.size()"+resultList.size());
            for (int p=0;p<resultList.size();p++){
                numberIteMatrix[i][p].setTextNumber(resultList.get(p));
            }
            for (int q=resultList.size();q<COLUMN_COUNT;q++){
                numberIteMatrix[i][q].setTextNumber(0);
            }
            resultList.clear();
        }
    }
}
