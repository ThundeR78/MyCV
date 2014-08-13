package fr.wetstein.mycv.draw;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by jwetstein on 13/08/2014.
 */
public class RectDraw extends LineDraw {
    public RectDraw(Canvas canvas, Paint paint, float top, float left, float bottom, float right) {
        super(canvas, paint, top, left, bottom, right);
    }

    public void update(Paint paint, float top, float left, float bottom, float right) {
        super.update(paint, top, left, bottom, right);

        if (right < left) {
            this.left = right;
            this.right = left;
        }
        if (bottom < top) {
            this.top = bottom;
            this.bottom = top;
        }
    }

    public void draw() {
        mCanvas.drawRect(left, top, right, bottom, mPaint);
    }

}
