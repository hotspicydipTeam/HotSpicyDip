package com.scnu.hotspicydip.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.scnu.hotspicydip.R;
import com.scnu.hotspicydip.view.GameView;

public class GameViewActivity extends AppCompatActivity {

    private GameView gameView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupSubViews();
    }

    private void setupSubViews() {

        this.gameView = new GameView(this);

        setContentView(this.gameView);
    }

}
