package fr.wetstein.mycv.draw;

import android.graphics.Paint;

/**
 * Created by jwetstein on 13/08/2014.
 */
public interface IDraw {

    public void update(Paint paint, float top, float left, float bottom, float right);

    public void draw();

}
