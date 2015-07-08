package fr.wetstein.mycv.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.wetstein.mycv.R;
import fr.wetstein.mycv.view.DrawSettingsView;
import fr.wetstein.mycv.view.GuestbookView;

/**
 * Created by ThundeR on 12/07/2014.
 */
public class GuestbookFragment extends Fragment implements DrawSettingsView.IDrawSettings {
    public static final String TAG = "GuestbookFragment";

    private GuestbookView mDrawView;
    private DrawSettingsView mDrawSettings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_guestbook, container, false);

        mDrawView = (GuestbookView) rootView.findViewById(R.id.surfaceview);
        mDrawView.init(this);

        mDrawSettings = (DrawSettingsView) rootView.findViewById(R.id.draw_settings);
        mDrawSettings.setCallback(this);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setCurrentLineStyle(0);
        setCurrentLineColor(0);
        setCurrentLineSize(3);
    }

    public void setCurrentLineStyle(int lineStyle) {
        mDrawView.setPaintStyle(lineStyle);
    }

    public void setCurrentLineColor(int lineColor) {
        int r=0, g=0, b=0;
       // r=41; g=171; b=226;

        mDrawView.setPaintColor(r, g, b);
    }

    public void setCurrentLineSize(int lineThickness) {
        int thickness = 3;
        mDrawView.setPaintThickness(thickness);
    }


}
