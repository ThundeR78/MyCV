package fr.wetstein.mycv.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import fr.wetstein.mycv.R;
import fr.wetstein.mycv.activity.HomeActivity;

/**
 * Created by ThundeR on 05/07/2014.
 */
public class HomeFragment extends Fragment {

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        TextView label = (TextView) rootView.findViewById(R.id.section_label);
        ImageView image = (ImageView) rootView.findViewById(R.id.image);

        image.setImageBitmap(getHexagonShape(((BitmapDrawable)image.getDrawable()).getBitmap()));

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((HomeActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
    }

    //TEST
    public Bitmap getHexagonShape(Bitmap scaleBitmapImage)
        int targetWidth = scaleBitmapImage.getWidth();
        int targetHeight = scaleBitmapImage.getHeight();
        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
                targetHeight,Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(targetBitmap);

        Path path = new Path();
        float stdW = targetWidth/2;
        float stdH = targetHeight/2;
        float w3 =stdW / 2;
        float h2 = stdH / 2;
        path.moveTo(0, (float) (h2*Math.sqrt(3)/2));
        path.rLineTo(w3/2, -(float) (h2*Math.sqrt(3)/2)); path.rLineTo(w3, 0);   path.rLineTo(w3/2, (float) (h2*Math.sqrt(3)/2));
        path.rLineTo(-w3/2, (float) (h2*Math.sqrt(3)/2)); path.rLineTo(-w3, 0); path.rLineTo(-w3/2, -(float) (h2*Math.sqrt(3)/2));


        canvas.clipPath(path);
        Bitmap sourceBitmap = scaleBitmapImage;
        canvas.drawBitmap(sourceBitmap,
                new Rect(0, 0, sourceBitmap.getWidth(),
                        sourceBitmap.getHeight()),
                new Rect(0, 0, targetWidth,
                        targetHeight), null);
        return targetBitmap;
    }
}
