package fr.wetstein.mycv.notification;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import fr.sophiacom.ynp.androidlib.notification.YNPMessageHandler;

/**
 * Created by ThundeR on 01/08/2014.
 */
public class NotifHandler implements YNPMessageHandler {

    public static final String TAG = "NotifHandler";

    public boolean onMessage(Context context, Intent intent) {
        //This is running in background thread and in a CPU wave lock.
        //Be gentle on the traitement here and watchout with shared resources.
        //Launch your own service for long tasks.

        boolean shouldBehaveAsDefault = true;

        //Display all key in extras
        for (String set : intent.getExtras().keySet()) {
            Log.v(TAG, "EXTRA = " + set + " VALUE = " + intent.getExtras().get(set));
        }

        return shouldBehaveAsDefault;
    }

}