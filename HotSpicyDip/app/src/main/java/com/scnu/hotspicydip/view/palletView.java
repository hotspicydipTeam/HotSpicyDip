package com.scnu.hotspicydip.view;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.scnu.hotspicydip.R;
import com.scnu.hotspicydip.activity.GameViewActivity;
import com.scnu.hotspicydip.model.PelletType;

import java.util.ArrayList;


/**
 * Created by HaloRain on 2016/6/20.
 */
public class palletView extends View {

    private int originX = 80;
    private int originY = 30;
    private int Width = 60;


    public palletView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    protected void onDraw(Canvas canvas) {


        Paint paint = new Paint();

        paint.setShadowLayer(5, 10, 10, Color.rgb(180, 180, 180));

        canvas.drawRect(0, originY + (Width - 10) / 2, originX, originY + (Width - 10) / 2 + 16, paint);


        int i;
        for (i = 0; i < GameViewActivity.it.pellets.size(); i++) {
            int x = originX + 65 * i;
            int y = originY;

            if (GameViewActivity.it.pellets.get(i).pelletType == PelletType.PELLET_TYPE_RED)
                //paint.setColor(Color.RED);
                canvas.drawBitmap(
                        BitmapFactory.decodeResource(getResources(), R.mipmap.icon_shrimp_1),
                        x, y, paint);
            else if (GameViewActivity.it.pellets.get(i).pelletType == PelletType.PELLET_TYPE_GREEN)
                //paint.setColor(Color.GREEN);
                canvas.drawBitmap(
                        BitmapFactory.decodeResource(getResources(), R.mipmap.icon_vegetable_1),
                        x, y, paint);
            else if (GameViewActivity.it.pellets.get(i).pelletType == PelletType.PELLET_TYPE_BLUE)
                //paint.setColor(Color.BLUE);
                canvas.drawBitmap(
                        BitmapFactory.decodeResource(getResources(), R.mipmap.icon_egg_1),
                        x, y, paint);

            //canvas.drawRect(x, y, x + Width, y + Width, paint);
        }
        paint.setColor(Color.BLACK);
        canvas.drawRect(originX + 65 * i - 5, originY + (Width - 10) / 2, 1000, originY + (Width - 10) / 2 + 16, paint);
        super.onDraw(canvas);
    }
}


