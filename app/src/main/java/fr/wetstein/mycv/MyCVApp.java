package fr.wetstein.mycv;

import android.app.Application;
import android.content.Context;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.model.LatLng;

import fr.sophiacom.ynp.androidlib.YNPClient;
import fr.wetstein.mycv.util.NukeSSLCerts;

/**
 * Created by ThundeR on 12/07/2014.
 */
public class MyCVApp extends Application {
    public static final String TAG = "MyCVApp";

    public static boolean DEV_MODE = true;

    public static final LatLng HOME_LATLNG = new LatLng(48.8494030, 2.2945998);

    @Override
    public void onCreate() {
        super.onCreate();

        NukeSSLCerts.nuke();

        registerYNP();
    }

    private void registerYNP() {
        YNPClient.apiKey = "qd5nthbqui934o2e";
        YNPClient.sharedSecret = "mvejejv70c";
        YNPClient.senderId = "448487069157";
        YNPClient.mode = DEV_MODE ? YNPClient.MODE_SANDBOX : YNPClient.MODE_PRODUCTION;
        YNPClient.notificationIconId = R.drawable.me_manga;
        //YNPClient.messageHandler = new NotifHandler();
        if (DEV_MODE) YNPClient.checkManifest(this);
        YNPClient.initialize(this);
    }

    //Check if Google Play Services is available on device to display Google Maps
    public static boolean isGooglePlayServicesAvailable(Context context) {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        return status == ConnectionResult.SUCCESS;
    }
}
