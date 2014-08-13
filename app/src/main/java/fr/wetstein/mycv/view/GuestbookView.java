package fr.wetstein.mycv.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.wetstein.mycv.draw.Draw;
import fr.wetstein.mycv.draw.LineDraw;
import fr.wetstein.mycv.draw.PointDraw;
import fr.wetstein.mycv.fragment.GuestbookFragment;

/**
 * Created by jwetstein on 13/08/2014.
 */
public class GuestbookView extends SurfaceView implements SurfaceHolder.Callback {
    public static final String TAG = "GuestbookView";

    private GuestbookFragment mParent;
    private SurfaceHolder mHolder;      //Holder to draw
    private DrawingThread mThread;      //Thread to draw in view
    private Bitmap mBitmap;             //Graphic object to display draw (Canvas)
    private Canvas mCanvas;             //Interface to draw (Drawer)
    private Paint mPaint;               //Tool to draw (Pencil, Palette)
    private int mPaintStyle;
    private float oldX = -1, oldY = -1;
    private float firstX = -1, firstY = -1;
    private Object sync = new Object();

    //DEFAULT
    public final static int DEFAULT_THICKNESS = 3;
    public final static int[] DEFAULT_COLOR = {0xFF, 0xFF, 0xFF};
    public final static int DEFAULT_BGCOLOR = 0xffffff;

    private List<Draw> mDraws;
    private List<Draw> mDrawsRemoved;
    private Draw mCurrentDraw;

    public GuestbookView(Context context) {
        this(context, null, 0);
    }
    public GuestbookView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public GuestbookView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void init(GuestbookFragment fragmentParent) {
       // setFocusable(true);
        //setZOrderOnTop(true);
        mHolder = getHolder();
        mHolder.addCallback(this);
        //mHolder.setFormat(PixelFormat.TRANSPARENT);
        mHolder.setFormat(PixelFormat.OPAQUE);
        mThread = new DrawingThread();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setARGB(0xFF, DEFAULT_COLOR[0], DEFAULT_COLOR[1], DEFAULT_COLOR[2]);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(DEFAULT_THICKNESS);

        mPaintStyle = 0;//EditDraw.LINE_STYLE_LIBRE;

        mDraws = new ArrayList<Draw>();
        mDrawsRemoved = new ArrayList<Draw>();
        mParent = fragmentParent;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mBitmap != null) {
//			Log.i("MyDrawView", "onDraw mDraws:" + mDraws.size());

            //if (_bgBitmap != null)
              //  mCanvas.drawBitmap(_bgBitmap, 0, 0, mPaint);
            //else
            mCanvas.drawColor(DEFAULT_BGCOLOR, PorterDuff.Mode.CLEAR);

            synchronized (sync) {
                if (mDraws != null) {
                    List<Draw> drawsCopy = new ArrayList<Draw>(mDraws);
                    for (Iterator<Draw> iterator = drawsCopy.iterator(); iterator.hasNext();) {
                        Draw myDraw = (Draw) iterator.next();
                //    for (int i = 0; i < mDraws.size(); i++) {
                        //Draw myDraw = mDraws.get(i);
                        myDraw.draw();
                    }
                }
            }
            canvas.drawBitmap(mBitmap, 0, 0, null);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mThread.keepDrawing = true;
        mThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mThread.keepDrawing = false;

        boolean joined = false;
        while (!joined) {
            try {
                mThread.join();
                joined = true;
            } catch (InterruptedException e) {}
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.i("onSizeChanged", "w:" + w + " - h:" + h + " - oldw:" + oldw + " - oldh:" + oldh);
        int curW = mBitmap != null ? mBitmap.getWidth() : 0;
        int curH = mBitmap != null ? mBitmap.getHeight() : 0;
        if (curW >= w && curH >= h) {
            return;
        }

        if (curW < w)
            curW = w;
        if (curH < h)
            curH = h;

        Bitmap newBitmap = Bitmap.createBitmap(curW, curH, Bitmap.Config.ARGB_8888);
        Canvas newCanvas = new Canvas();
        newCanvas.setBitmap(newBitmap);
        if (mBitmap != null) {
            newCanvas.drawBitmap(mBitmap, 0, 0, null);
        }
        mBitmap = newBitmap;
        mCanvas = newCanvas;
    }

    public void setPaintColor(int r, int g, int b) {
        mPaint.setARGB(0xFF, r, g, b);
    }

    public void setPaintThickness(int thickness) {
        mPaint.setStrokeWidth(thickness);
    }

    public void setPaintStyle(int style) {
        mPaintStyle = style;
    }

    public void clear() {
        mDraws.clear();
        mDrawsRemoved.clear();
        invalidate();

        //mParent.setUndoButtonEnabled(false);
//		mParentWindow.setRedoButtonEnabled(false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();
        // Log.i("onTouchEvent", "x:"+x+" - y:"+y);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                oldX = oldY = -1;
                firstX = x;
                firstY = y;


                mCurrentDraw = new PointDraw(mCanvas, mPaint, x, y);
                //mCurrentDraw.draw();

                synchronized (sync) {
                    mDraws.add(mCurrentDraw);
                }
                //mParent.setUndoButtonEnabled(true);
                break;

            case MotionEvent.ACTION_MOVE:
                mCurrentDraw = new LineDraw(mCanvas, mPaint, oldY, oldX, y, x);
                //mCurrentDraw = new PointDraw(mCanvas, mPaint, x, y);
                //mCurrentDraw = new OvalDraw(mCanvas, mPaint, oldY, oldX, y, x);
                synchronized (sync) {
                    mDraws.add(mCurrentDraw);
                }
                break;

            case MotionEvent.ACTION_UP:
                // Do Nothing
                break;
        }

        oldX = x;
        oldY = y;
        invalidate();

        return true;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private class DrawingThread extends Thread {
        boolean keepDrawing = true;      //Use to stop draw when necessary

        @Override
        public void run() {
            while (keepDrawing) {
                Canvas canvas = null;

                try {
                    canvas = mHolder.lockCanvas();  //Get Canvas to draw on it
                    //Ensure no other thread get the Holder
                    synchronized (mHolder) {
                        //draw(canvas);
                        postInvalidate();
                    }
                } finally {
                    //Draw finish, we free Canvas to display draw on view
                    if (canvas != null)
                        mHolder.unlockCanvasAndPost(canvas);
                }

                //Draw at 50fps
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {}
            }
        }
    }
}
