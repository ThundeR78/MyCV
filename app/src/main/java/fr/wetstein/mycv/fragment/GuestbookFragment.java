package fr.wetstein.mycv.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fr.wetstein.mycv.R;
import fr.wetstein.mycv.model.Travel;
import fr.wetstein.mycv.parser.xml.TravelParser;
import fr.wetstein.mycv.view.GuestbookView;

/**
 * Created by ThundeR on 12/07/2014.
 */
public class GuestbookFragment extends Fragment {
    public static final String TAG = "GuestbookFragment";

    private GuestbookView mDrawView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_guestbook, container, false);

        mDrawView = (GuestbookView) rootView.findViewById(R.id.surfaceview);
        mDrawView.init(this);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setCurrentLineStyle(0);
        setCurrentLineColor(0);
        setCurrentLineThickness(3);
    }

    private void setCurrentLineStyle(int lineStyle) {
        mDrawView.setPaintStyle(lineStyle);
    }

    private void setCurrentLineColor(int lineColor) {
        int r=0, g=0, b=0;
        r=41; g=171; b=226;

        mDrawView.setPaintColor(r, g, b);
    }

    private void setCurrentLineThickness(int lineThickness) {
        int thickness = 3;
        mDrawView.setPaintThickness(thickness);
    }
}
