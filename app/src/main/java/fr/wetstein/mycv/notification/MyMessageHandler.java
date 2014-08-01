package fr.wetstein.mycv.notification;

import android.content.Context;
import android.content.Intent;

import fr.sophiacom.ynp.androidlib.notification.YNPMessageHandler;

/**
 * Created by ThundeR on 01/08/2014.
 */
public class MyMessageHandler implements YNPMessageHandler {

    public boolean onMessage(Context context, Intent intent) {
        //This is running in background thread and in a CPU wave lock.
        //Be gentle on the traitement here and watchout with shared resources.
        //Launch your own service for long tasks.

        boolean shouldBehaveAsDefault = true;



        return shouldBehaveAsDefault;
    }

}