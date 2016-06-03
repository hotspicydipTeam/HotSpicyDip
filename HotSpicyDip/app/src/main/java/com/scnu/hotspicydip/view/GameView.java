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

    private Context context;

    public ArrayList<Pellet> pellets = new ArrayList<Pellet>();   //当前GameView上的所有鱼丸视图

    public GameView(Context context) {
        super(context);

        this.context = context;
        this.setBackgroundColor(Constants.MainBackgroundColor);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        pellets.add(new Pellet(context));
        pellets.add(new Pellet(context));
        pellets.add(new Pellet(context));
        Paint paint = new Paint();
        for (Pellet pellet : pellets) {
            paint.setColor(pellet.getBackgroundColor());
            canvas.drawCircle(pellet.getX(), pellet.getY() + 500, pellet.getWidth() / 2, paint);
        }
    }
}
