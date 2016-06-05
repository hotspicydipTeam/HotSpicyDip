package com.scnu.hotspicydip.activity;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import com.scnu.hotspicydip.view.GameView;

import java.util.Random;

public class GameViewActivity extends AppCompatActivity implements Runnable {

    private GameView gameView = null;

    private static final int refreshGap = 40;

    private static int createNewPelletGap = 2000;

    private static int createNewPelletGapSeed = 20;

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
        if (passedTimeMillis >= 20000 && passedTimeMillis < 40000){
            this.createNewPelletGapSeed = 14;
            return 6;
        }
        if (passedTimeMillis >= 40000 && passedTimeMillis < 60000){
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
}
