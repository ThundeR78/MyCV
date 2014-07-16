package fr.wetstein.mycv.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import fr.wetstein.mycv.R;

public class RemoteImageView extends ImageView {
    public static final String TAG = "RemoteImageView";

    private static final String ATTR_IMAGE_URL = "imageUrl";

    private String mUrl = null;
    private Target mTarget;

    public RemoteImageView(Context context) {
        this(context, null, 0);
    }

    public RemoteImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RemoteImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mTarget = new Target() {
            @Override
            public void onPrepareLoad(Drawable drawable) {

            }
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                setImageBitmap(bitmap);
            }
            @Override
            public void onBitmapFailed(Drawable drawable) {

            }
        };

        setAttributes(attrs);
    }

    private void setAttributes(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.RemoteImageView);
            //setImageUrl(attrs.getAttributeValue(null, ATTR_IMAGE_URL));

            setImageUrl(a.getString(R.styleable.RemoteImageView_imageUrl));
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mTarget != null) {
            Picasso.with(getContext()).cancelRequest(mTarget);
        }
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public void setImageUrl(String url) {
        if (url != null) {
            setUrl(url);

            Picasso.with(getContext()).load(url).into(mTarget);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        Drawable d = getDrawable();

        if (d != null) {
            // ceil not round - avoid thin vertical gaps along the left/right edges
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = (int) Math.ceil((float) width * (float) d.getIntrinsicHeight() / (float) d.getIntrinsicWidth());
            setMeasuredDimension(width, height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
