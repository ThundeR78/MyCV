package fr.wetstein.mycv.draw;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by jwetstein on 13/08/2014.
 */
public class LineDraw extends Draw {
    protected float top, left, bottom, right;
    protected Paint mPaint;

    public LineDraw(Canvas canvas, Paint paint, float top, float left, float bottom, float right) {
        super(canvas, paint);
        update(paint, top, left, bottom, right);
    }

    public void update(Paint paint, float top, float left, float bottom, float right) {
        this.top = top;
        this.left = left;
        this.bottom = bottom;
        this.right = right;

        mPaint = new Paint(paint);
    }

    public void draw() {
        mCanvas.drawLine(left, top, right, bottom, mPaint);
    }
}
