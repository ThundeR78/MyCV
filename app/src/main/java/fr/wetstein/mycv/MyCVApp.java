package fr.wetstein.mycv;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.model.LatLng;

import fr.sophiacom.ynp.androidlib.YNPClient;
import fr.wetstein.mycv.util.AppRater;
import fr.wetstein.mycv.util.NukeSSLCerts;

/**
 * Created by ThundeR on 12/07/2014.
 */
public class MyCVApp extends Application {
    public static final String TAG = "MyCVApp";

    public static final boolean DEV_MODE = false;

    public static final LatLng HOME_LATLNG = new LatLng(48.8494030, 2.2945998);

    @Override
    public void onCreate() {
        super.onCreate();

        NukeSSLCerts.nuke();

        registerYNP();

        AppRater.checkAppRated(this);
    }

    private void registerYNP() {
        YNPClient.apiKey = "qd5nthbqui934o2e";
        YNPClient.sharedSecret = "mvejejv70c";
        YNPClient.senderId = "448487069157";
        YNPClient.mode = DEV_MODE ? YNPClient.MODE_SANDBOX : YNPClient.MODE_PRODUCTION;
        //YNPClient.serverBaseURL = YNPClient.mode == YNPClient.MODE_PRODUCTION ? "https://mobile.youandpush.com/" : "https://sandbox-mobile.youandpush.com/";
        YNPClient.notificationIconId = R.drawable.me_manga;
        //YNPClient.messageHandler = new NotifHandler();
        if (DEV_MODE) YNPClient.checkManifest(this);
        YNPClient.initialize(this);
    }

    //Check if Google Play Services is available on device to display Google Maps
    public static boolean isGooglePlayServicesAvailable(Activity context) {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);

        //Display error dialog
        if (status != ConnectionResult.SUCCESS) {
            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, context, requestCode);
            dialog.show();
        }

        return status == ConnectionResult.SUCCESS;
    }

    public static String getAppVersion(Context context) {
        String appVersion = "?";
        if (context != null) {
            try {
                String pkg = context.getPackageName();
                appVersion = context.getPackageManager().getPackageInfo(pkg, 0).versionName;
            } catch (PackageManager.NameNotFoundException e) {
            }
        }
        return appVersion;
    }
}
