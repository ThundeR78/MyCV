package fr.wetstein.mycv;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

public class TimeChangedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v("TimeChangedReceiver", "Time Changed");

        if (!isConnected(context))
            trimCache(context);
    }

    public static void trimCache(Context context) {
        try {
            File dir = context.getCacheDir();
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return dir.delete();
    }

    //Check if connection possible
    public static boolean isConnected(Context context) {
        if (context != null) {
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            // Need permission : android.permission.ACCESS_NETWORK_STATE
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                return true;
            } else {
                // display error
	        	/*if (context instanceof Activity)
	        		Crouton.makeText((Activity) context, "Network Error", Style.ALERT).show();
	        	else*/
                Toast.makeText(context, "Network Error", Toast.LENGTH_LONG).show();
                return false;
            }
        } else
            return false;
    }
}
