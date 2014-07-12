package fr.wetstein.mycv;

import android.app.Application;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by ThundeR on 12/07/2014.
 */
public class MyCVApp extends Application {

    public static final LatLng HOME_LATLNG = new LatLng(48.8494030, 2.2945998);

    @Override
    public void onCreate() {
        super.onCreate();
    }

}
