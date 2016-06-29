package com.scnu.hotspicydip.view;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.scnu.hotspicydip.R;
import com.scnu.hotspicydip.constants.Constants;
import com.scnu.hotspicydip.model.Pellet;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by lxxz on 16/6/2.
 */

/**
 * 游戏主界面的View
 */
public class GameView extends View {

    private int screenWidth;

    private int screenHeight;

    private float bottom_constrain;

    public ArrayList<Pellet> pellets = new ArrayList<Pellet>();   //当前GameView上的所有鱼丸视图

    public GameView(Context context, int screenWidth, int screenHeight) {
        super(context);

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        this.bottom_constrain = (float)1540 / (float)1920 * screenHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();

        for (Pellet pellet : pellets) {
            paint.setColor(pellet.getBackgroundColor());
            switch (pellet.getPelletType()) {
                case PELLET_TYPE_GREEN: {
                    switch (pellet.getRotate()) {
                        case 0:
                            canvas.drawBitmap(
                                    BitmapFactory.decodeResource(getResources(), R.mipmap.icon_vegetable_1),
                                    pellet.getX(),
                                    pellet.getY(),
                                    paint);
                            break;
                        case 1:
                            canvas.drawBitmap(
                                    BitmapFactory.decodeResource(getResources(), R.mipmap.icon_vegetable_2),
                                    pellet.getX(),
                                    pellet.getY(),
                                    paint);
                            break;
                        case 2:
                            canvas.drawBitmap(
                                    BitmapFactory.decodeResource(getResources(), R.mipmap.icon_vegetable_3),
                                    pellet.getX(),
                                    pellet.getY(),
                                    paint);
                            break;
                        case 3:
                            canvas.drawBitmap(
                                    BitmapFactory.decodeResource(getResources(), R.mipmap.icon_vegetable_4),
                                    pellet.getX(),
                                    pellet.getY(),
                                    paint);
                            break;
                    }
                    break;
                }
                case PELLET_TYPE_BLUE: {
                    switch (pellet.getRotate()) {
                        case 0:
                            canvas.drawBitmap(
                                    BitmapFactory.decodeResource(getResources(), R.mipmap.icon_egg_1),
                                    pellet.getX(),
                                    pellet.getY(),
                                    paint);
                            break;
                        case 1:
                            canvas.drawBitmap(
                                    BitmapFactory.decodeResource(getResources(), R.mipmap.icon_egg_2),
                                    pellet.getX(),
                                    pellet.getY(),
                                    paint);
                            break;
                        case 2:
                            canvas.drawBitmap(
                                    BitmapFactory.decodeResource(getResources(), R.mipmap.icon_egg_3),
                                    pellet.getX(),
                                    pellet.getY(),
                                    paint);
                            break;
                        case 3:
                            canvas.drawBitmap(
                                    BitmapFactory.decodeResource(getResources(), R.mipmap.icon_egg_4),
                                    pellet.getX(),
                                    pellet.getY(),
                                    paint);
                            break;
                    }
                    break;
                }
                case PELLET_TYPE_RED: {
                    switch (pellet.getRotate()) {
                        case 0:
                            canvas.drawBitmap(
                                    BitmapFactory.decodeResource(getResources(), R.mipmap.icon_shrimp_1),
                                    pellet.getX(),
                                    pellet.getY(),
                                    paint);
                            break;
                        case 1:
                            canvas.drawBitmap(
                                    BitmapFactory.decodeResource(getResources(), R.mipmap.icon_shrimp_2),
                                    pellet.getX(),
                                    pellet.getY(),
                                    paint);
                            break;
                        case 2:
                            canvas.drawBitmap(
                                    BitmapFactory.decodeResource(getResources(), R.mipmap.icon_shrimp_3),
                                    pellet.getX(),
                                    pellet.getY(),
                                    paint);
                            break;
                        case 3:
                            canvas.drawBitmap(
                                    BitmapFactory.decodeResource(getResources(), R.mipmap.icon_shrimp_4),
                                    pellet.getX(),
                                    pellet.getY(),
                                    paint);
                            break;
                    }
                    break;
                }
            }
        }
    }

    public void moveOn(int speed) {

        ArrayList<Pellet> removePellets = new ArrayList<Pellet>();

        for (Pellet pellet : pellets) {
            if (pellet.moveWithSpeed(speed) > this.bottom_constrain) {
                removePellets.add(pellet);
            }
        }
        for (Pellet pellet : removePellets) {
            this.pellets.remove(pellet);
        }
        removePellets.clear();
    }

    public void newPellet() {

        for (int i = 0; i < new Random().nextInt(2) + 1; i ++) {
            this.pellets.add(new Pellet(this.screenWidth));
        }
    }

}
