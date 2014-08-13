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
        x = x;
        y = y;
    }

    public void update(Paint paint, float top, float left, float bottom, float right) {
        // Nothing to do
    }

    public void draw() {
        Paint vPaint = new Paint();
        vPaint.setAntiAlias(true);
        vPaint.setDither(true);
        vPaint.setARGB(0xFF, Color.red(mPaint.getColor()), Color.green(mPaint.getColor()), Color.blue(mPaint.getColor()));
        vPaint.setStyle(Paint.Style.FILL);
        vPaint.setStrokeJoin(Paint.Join.ROUND);
        vPaint.setStrokeCap(Paint.Cap.ROUND);
        vPaint.setStrokeWidth(1);

        mCanvas.drawCircle(x, y, mPaint.getStrokeWidth() / 2, vPaint);
    }

}
