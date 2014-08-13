package fr.wetstein.mycv.draw;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by jwetstein on 13/08/2014.
 */
public class OvalDraw extends RectDraw {
    public OvalDraw(Canvas canvas, Paint paint, float top, float left, float bottom, float right) {
        super(canvas, paint, top, left, bottom, right);
    }

    public void draw() {
        mCanvas.drawOval(new RectF(left, top, right, bottom), mPaint);
    }

}
