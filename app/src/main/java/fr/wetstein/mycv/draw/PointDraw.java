package fr.wetstein.mycv.draw;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by jwetstein on 13/08/2014.
 */
public class PointDraw extends Draw {
    protected float x, y;

    public PointDraw(Canvas canvas, Paint paint, float x, float y) {
        super(canvas, paint);
        this.x = x;
        this.y = y;
    }

    public void update(Paint paint, float top, float left, float bottom, float right) {
        // Nothing to do
    }

    public void draw() {
        mPaint.setStyle(Paint.Style.FILL);

        mCanvas.drawCircle(x, y, mPaint.getStrokeWidth() / 2, mPaint);
    }

}
