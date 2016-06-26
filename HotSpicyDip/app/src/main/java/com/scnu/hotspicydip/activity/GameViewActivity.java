package com.scnu.hotspicydip.activity;

import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.scnu.hotspicydip.R;
import com.scnu.hotspicydip.model.Pellet;
import com.scnu.hotspicydip.view.GameView;

import java.util.Iterator;
import java.util.Random;

import android.os.Handler;

import java.util.logging.LogRecord;

public class GameViewActivity extends AppCompatActivity {

    private GameView gameView = null;

    private static final int refreshGap = 40;

    private static int createNewPelletGap = 2000;

    private static int createNewPelletGapSeed = 20;

    private long beginTimeMillis;

    private long lastCreateTimeMillis = 0;
    /**
     * 获取部件
     */
    private FrameLayout flGameView;

    private RelativeLayout rlControl;

    private RelativeLayout rlShield;

    private FrameLayout flCharge;

    private ImageView ivSkewer;

    private RelativeLayout.LayoutParams lpSkewer;

    private RelativeLayout.LayoutParams lpCharge;

    private FrameLayout.LayoutParams lpShield;

    private FrameLayout.LayoutParams lpControl;
    /**
     * 记录竹签位置的变量
     */
    //private float firstX = 0;
    private float firstY = 0;
    private int top;
    private int bottom;
    private int power = 0;
    private int temp = 1;
    private int temp1 = 1;
    /**
     * 记录屏幕高度和宽度
     */
    private int screenHeight;
    private int screenWidth;
    //记录蓄力条原始高度
    private int Sheight;
    //设置flag让竹签插鱼蛋时不移动
    private boolean ifBunch = false;
    /**
     * 多线程创建
     */
    //传递给主线程的信息
    private int sendMessage;
    //主线程
    private Handler mainHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //掉落鱼蛋动画操作
                case 0:
                    long cur = System.currentTimeMillis();

                    gameView.moveOn(getSpeedByPassedTimeMillis(cur - beginTimeMillis));
                    gameView.postInvalidate();

                    if (isCreateNewPellet(cur, lastCreateTimeMillis)) {
                        gameView.newPellet();
                    }
                    break;
                //插鱼蛋动作操作
                case 1:
                    //伸长竹签
                    if (lpSkewer.width >= (screenWidth / 10 + screenWidth * 9 * (int) (msg.obj) / 1000)) {
                        temp1 = -1;
                        // Log.i("action","1");
                    } else if (lpSkewer.width <= screenWidth / 10 && temp1 == -1) {
                        // Log.i("action", 2 + "");
                        temp1 = 1;
                        Bunch.interrupt();
                    } else
                        ifBunch = true;
                    if (!Bunch.isAlive()) {
                        //Log.i("action", "1");
                        ifBunch = false;
                        power = 0;
                    }
                    lpSkewer.width = lpSkewer.width + temp1;
                    //Log.i("action", ifBunch + "");
                    ivSkewer.setLayoutParams(lpSkewer);
                    //在插过去过程中检测碰撞
                    if(temp1==1) {
                        Iterator<Pellet> iterator = gameView.pellets.iterator();
                        while (iterator.hasNext()) {
                            Pellet pellet = iterator.next();
                            if (ivSkewer.getLeft() >= (pellet.getX() + pellet.getWidth() - 5) &&
                                    ivSkewer.getLeft() <= (pellet.getX() + pellet.getWidth() + 5) &&
                                    (rlControl.getTop() + ivSkewer.getTop()) >= pellet.getY() &&
                                    (rlControl.getTop() + ivSkewer.getTop()) <= (pellet.getY() + pellet.getWidth())) {
                                Log.i("action", pellet.getPelletType() + "");
                                iterator.remove();
                            }
                        }
                    }
                   /* for (Pellet pellet : gameView.pellets) {
                        if (ivSkewer.getLeft() >= (pellet.getX() + pellet.getWidth() - 5) &&
                                ivSkewer.getLeft() <= (pellet.getX() + pellet.getWidth() + 5) &&
                                (rlControl.getTop() + ivSkewer.getTop()) >= pellet.getY() &&
                                (rlControl.getTop() + ivSkewer.getTop()) <= (pellet.getY() + pellet.getWidth())) {
                            Log.i("action",pellet.getPelletType()+"");
                            gameView.pellets.remove(pellet);
                        }
                    }*/
                    break;
            }
            super.handleMessage(msg);
        }
    };
    //掉落鱼蛋线程
    private Thread hsdDown = new Thread(new Runnable() {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                mainHandler.obtainMessage(0).sendToTarget();
                try {
                    Thread.sleep(refreshGap);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    });
    //插鱼蛋线程
    private Thread Bunch;
    private Runnable bunchRun = new Runnable() {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                //Log.i("action",ifBunch+"");
                mainHandler.obtainMessage(1, power).sendToTarget();
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    //    Log.i("action","2");
                    Thread.currentThread().interrupt();
                }
            }
            /*if(Thread.currentThread().isInterrupted()){
              //  Log.i("action","1");
                ifBunch=false;
            }*/
        }
    };


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupSubViews();
        skewerMove();
        hsdDown.start();
    }

    /**
     * 竹签移动的方法
     */
    private void skewerMove() {

        rlControl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                //如果不释放竹签：
                if (!ifBunch) {
                    switch (action) {
                        //按下所处坐标
                        case MotionEvent.ACTION_DOWN:
                            // Log.i("action",1+"");
                            //firstX=event.getRawX();
                            firstY = event.getRawY();
                            //Log.i("action",v.getTop()+"");
                            break;
                        //移动
                        case MotionEvent.ACTION_MOVE:
                            // Log.i("action", 2 + "");
                            //蓄力
                            if (power == 0) temp = 1;
                            else if (power == 100) temp = -1;
                            power = power + temp;
                            //lpShield.width = (screenWidth / 10 - 4) * (100 - power) / 100;
                            lpShield.height=Sheight * (100 - power) / 100;
                            //Log.i("action",lpShield.width+"");
                            rlShield.setLayoutParams(lpShield);
                            //移动的距离
                            float distanceY = event.getRawY() - firstY;
                            //Log.i("action",distanceY+"y");
                            //移动后的坐标
                            top = (int) (ivSkewer.getTop() + distanceY);
                            bottom = (int) (screenHeight - ivSkewer.getBottom() - distanceY);

                            //处理移动后超出屏幕的情况
                            if (top < 0) {
                                top = 0;
                                bottom = ivSkewer.getBottom();
                            }
                            if (bottom > screenHeight) {
                                bottom = screenHeight;
                                top = ivSkewer.getTop();
                            }
                            //显示图片
                            //Log.i("action",bottom+"");
                            lpSkewer.bottomMargin = bottom;
                            //Log.i("action",distanceY+"");
                            ivSkewer.setLayoutParams(lpSkewer);
                            firstY = event.getRawY();
                            break;
                        case MotionEvent.ACTION_UP:
                            Bunch = new Thread(bunchRun);
                            Bunch.start();

                            break;
                        default:
                            break;
                    }
                }
                //这里必须返回true才能相应MotionEvent.ACTION_MOVE
                return true;
            }

        });
       /* rlSkewer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.i("action", 1 + "");
                flag=1;
                Thread skewer = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 1000; i++) {
                           // Log.i("action",i+"");
                        }
                    }
                });
                skewer.start();
                return false;
            }
        });*/
    }


    private void setupSubViews() {
        screenHeight = getScreenHeight();
        screenWidth = getScreenWidth();
        this.gameView = new GameView(this, screenWidth, screenHeight);
        setContentView(R.layout.activity_game_view);
        flGameView = (FrameLayout) findViewById(R.id.fl_game_view);
        rlControl = (RelativeLayout) findViewById(R.id.rl_control);
        rlShield = (RelativeLayout) findViewById(R.id.rl_shield);
        ivSkewer = (ImageView) findViewById(R.id.iv_skewer);
        flCharge = (FrameLayout) findViewById(R.id.fl_charge);
        lpSkewer = (RelativeLayout.LayoutParams) ivSkewer.getLayoutParams();
        lpCharge = (RelativeLayout.LayoutParams) flCharge.getLayoutParams();
        lpShield = (FrameLayout.LayoutParams) rlShield.getLayoutParams();
        lpControl = (FrameLayout.LayoutParams) rlControl.getLayoutParams();
        lpCharge.width=screenWidth/8;
        lpCharge.height= lpCharge.width*707/138;
        lpSkewer.height = screenHeight / 30;
        lpSkewer.width = screenWidth / 10;
        Sheight=lpShield.height=lpCharge.height-lpCharge.height*125/707-lpCharge.height*27/707;
        lpShield.width=lpCharge.width*59/138;
        lpShield.topMargin=lpCharge.height*27/707;
        rlShield.setLayoutParams(lpShield);
        //lpControl.height = lpCharge.height + lpSkewer.height;
        //rlControl.setLayoutParams(lpControl);
        ivSkewer.setLayoutParams(lpSkewer);
        flCharge.setLayoutParams(lpCharge);
        flGameView.addView(gameView);
        this.beginTimeMillis = System.currentTimeMillis();
    }

/*    public void run() {

        this.beginTimeMillis = System.currentTimeMillis();
        while (!Thread.currentThread().isInterrupted()) {

            long cur = System.currentTimeMillis();

            this.gameView.moveOn(getSpeedByPassedTimeMillis(cur - beginTimeMillis));
            this.gameView.postInvalidate();

            if (isCreateNewPellet(cur, this.lastCreateTimeMillis)) {
                this.gameView.newPellet();
            }

            try {
                Thread.sleep(refreshGap);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }*/

    private boolean isCreateNewPellet(long cur, long last) {

        if (cur - last > createNewPelletGap) {
            this.lastCreateTimeMillis = cur;
            this.createNewPelletGap = new Random().nextInt(createNewPelletGapSeed) * 200 + 500;
            return true;
        }
        return false;
    }

    private int getSpeedByPassedTimeMillis(long passedTimeMillis) {

        if (passedTimeMillis < 10000) {
            this.createNewPelletGapSeed = 20;
            return 2;
        }
        if (passedTimeMillis >= 10000 && passedTimeMillis < 20000) {
            this.createNewPelletGapSeed = 17;
            return 4;
        }
        if (passedTimeMillis >= 20000 && passedTimeMillis < 40000) {
            this.createNewPelletGapSeed = 14;
            return 6;
        }
        if (passedTimeMillis >= 40000 && passedTimeMillis < 60000) {
            this.createNewPelletGapSeed = 11;
            return 8;
        }
        if (passedTimeMillis >= 60000 && passedTimeMillis < 80000) {
            this.createNewPelletGapSeed = 8;
            return 10;
        }
        this.createNewPelletGapSeed = 5;
        return 12;
    }

    private int getScreenWidth() {
        return getResources().getDisplayMetrics().widthPixels;
    }

    private int getScreenHeight() {
        return getResources().getDisplayMetrics().heightPixels;
    }

/*    class RefreshThread  implements Runnable{
        @Override
        public void run() {
          while (!Thread.currentThread().isInterrupted()) {

                try {
                    Thread.sleep(refreshGap);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }*/
}
