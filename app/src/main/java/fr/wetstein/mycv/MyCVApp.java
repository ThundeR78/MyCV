package fr.wetstein.mycv;

import android.app.Application;

import com.google.android.gms.maps.model.LatLng;

import fr.sophiacom.ynp.androidlib.YNPClient;
import fr.wetstein.mycv.notification.MyMessageHandler;

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

        registerYNP();
    }

    private void registerYNP() {
        YNPClient.apiKey = "qd5nthbqui934o2e";
        YNPClient.sharedSecret = "mvejejv70c";
        YNPClient.senderId = "448487069157";
        YNPClient.mode = DEV_MODE ? YNPClient.MODE_SANDBOX : YNPClient.MODE_PRODUCTION;
        YNPClient.notificationIconId = R.drawable.me_manga;
        YNPClient.messageHandler = new MyMessageHandler();
        if (DEV_MODE) YNPClient.checkManifest(this);
        YNPClient.initialize(this);
    }

}
