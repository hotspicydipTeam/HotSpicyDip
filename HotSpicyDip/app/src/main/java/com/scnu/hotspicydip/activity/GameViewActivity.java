package com.scnu.hotspicydip.activity;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import com.scnu.hotspicydip.view.GameView;

public class GameViewActivity extends AppCompatActivity implements Runnable {

    private GameView gameView = null;

    private static final int refreshGap = 100;

    private static final int createNewPelletGap = 5000;

    private long beginTimeMillis;

    private long lastCreateTimeMillis = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupSubViews();
        new Thread(this).start();
    }

    private void setupSubViews() {

        this.gameView = new GameView(this, getScreenWidth(), getScreenHeight());
        setContentView(this.gameView);
    }

    @Override
    public void run() {

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
    }

    private boolean isCreateNewPellet(long cur, long last) {

        if (cur - last > createNewPelletGap) {
            this.lastCreateTimeMillis = cur;
            return true;
        }
        return false;
    }

    private int getSpeedByPassedTimeMillis(long passedTimeMillis) {

        if (passedTimeMillis < 10000) {
            return 5;
        }
        if (passedTimeMillis >= 10000 && passedTimeMillis < 20000) {
            return 8;
        }
        if (passedTimeMillis >= 20000 && passedTimeMillis < 40000){
            return 11;
        }
        if (passedTimeMillis >= 40000 && passedTimeMillis < 60000){
            return 14;
        }
        if (passedTimeMillis >= 60000 && passedTimeMillis < 80000) {
            return 17;
        }
        return 20;
    }

    private int getScreenWidth() {
        return getResources().getDisplayMetrics().widthPixels;
    }

    private int getScreenHeight() {
        return getResources().getDisplayMetrics().heightPixels;
    }
}
