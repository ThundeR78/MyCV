package fr.wetstein.mycv.draw;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by jwetstein on 13/08/2014.
 */
public abstract class Draw implements IDraw {
    protected Paint mPaint;
    protected Canvas mCanvas;

    public Draw(Canvas canvas, Paint paint) {
        mCanvas = canvas;
        mPaint = new Paint(paint);
    }
}
