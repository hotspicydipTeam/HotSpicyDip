package com.scnu.hotspicydip.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.scnu.hotspicydip.constants.Constants;
import com.scnu.hotspicydip.model.Pellet;

import java.util.ArrayList;

/**
 * Created by lxxz on 16/6/2.
 */

/**
 * 游戏主界面的View
 */
public class GameView extends View {

    private int screenWidth;

    private int screenHeight;

    public ArrayList<Pellet> pellets = new ArrayList<Pellet>();   //当前GameView上的所有鱼丸视图

    public GameView(Context context, int screenWidth, int screenHeight) {
        super(context);

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.setBackgroundColor(Constants.MainBackgroundColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        for (Pellet pellet : pellets) {
            paint.setColor(pellet.getBackgroundColor());
            canvas.drawRect(
                    pellet.getX(),
                    pellet.getY(),
                    pellet.getX() + pellet.getWidth(),
                    pellet.getY() + pellet.getWidth(),
                    paint);
        }
    }

    public void moveOn(int speed) {

        ArrayList<Pellet> removePellets = new ArrayList<Pellet>();

        for (Pellet pellet : pellets) {
            if (pellet.moveWithSpeed(speed) > this.screenHeight) {
                removePellets.add(pellet);
            }
        }
        for (Pellet pellet : removePellets) {
            this.pellets.remove(pellet);
        }
        removePellets.clear();
    }

    public void newPellet() {
        this.pellets.add(new Pellet(this.screenWidth));
    }

}
