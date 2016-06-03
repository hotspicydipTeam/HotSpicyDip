package com.scnu.hotspicydip.model;

/**
 * Created by lxxz on 16/6/2.
 */

import android.content.Context;
import android.graphics.Color;

import com.scnu.hotspicydip.constants.Constants;

import java.util.Random;

/**
 * 鱼丸信息
 */
public class Pellet {

    private Context context;

    public PelletType pelletType;   //鱼丸类型

    private int width;              //鱼丸视图宽度

    public float x;                 //鱼丸当前位置的x坐标

    public float y;                 //鱼丸当前位置的y坐标


    public Pellet(Context context) {

        this.context = context;

        this.pelletType = randomPelletType();
        this.width = Constants.getPelletWidth(context);
        this.x = randomOriginX();
        this.y = -this.width;

    }

    private PelletType randomPelletType() {

        int type = new Random().nextInt(3);
        switch (type) {
            case 0:
                return PelletType.PELLET_TYPE_RED;
            case 1:
                return PelletType.PELLET_TYPE_GREEN;
            default:
                return PelletType.PELLET_TYPE_BLUE;
        }
    }

    private float randomOriginX() {

        int screenWidth = Constants.getScreenWidth(context);
        return new Random().nextInt(screenWidth - this.width);
    }

    /***********************getters*************************/

    public PelletType getPelletType() {
        return pelletType;
    }

    public int getWidth() {
        return width;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getBackgroundColor() {

        if (this.pelletType == PelletType.PELLET_TYPE_RED) {
            return Color.RED;
        }
        if (this.pelletType == PelletType.PELLET_TYPE_GREEN) {
            return Color.GREEN;
        }
        return Color.BLUE;
    }

    /***********************setters*************************/

    public void setPelletType(PelletType pelletType) {
        this.pelletType = pelletType;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

}
