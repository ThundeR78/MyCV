package fr.wetstein.mycv.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;

import fr.wetstein.mycv.R;

public class AppRater {
    private static final String TAG = "AppRater";

    public static boolean ENABLED = true; // FIXME set to "true" in PRODUCTION

//    private final static int DAYS_UNTIL_PROMPT = 3;
    private final static int LAUNCHES_UNTIL_PROMPT = 3;
    
    private static boolean needToShow = false;
    
    public static void checkAppRated(Context mContext) {
        SharedPreferences prefs = PrefsManager.getPreferences(mContext);
        
        //User asked to never show alert again
        if (prefs.getBoolean(PrefsManager.PREF_DONT_SHOW_AGAIN, false))
        	return;
        
        SharedPreferences.Editor editor = prefs.edit();
        
        //Increment launch counter
        long launch_count = prefs.getLong(PrefsManager.PREF_LAUNCH_COUNT, 0) + 1;

        //Get date of first launch
//        Long date_firstLaunch = prefs.getLong(PrefsManager.PREF_DATE_FIRST_LAUNCH_KEY, 0);
//        if (date_firstLaunch == 0) {
//            date_firstLaunch = System.currentTimeMillis();
//            editor.putLong(PrefsManager.PREF_DATE_FIRST_LAUNCH_KEY, date_firstLaunch);
//        }
        
        //Wait at least n days before opening
        if (launch_count >= LAUNCHES_UNTIL_PROMPT) {
//            if (System.currentTimeMillis() >= date_firstLaunch + (DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000))
        	if (ENABLED) {
                needToShow = true;
        	}
        	launch_count = 0;
        }
        editor.putLong(PrefsManager.PREF_LAUNCH_COUNT, launch_count);

        editor.commit();
    }
    
    public static void checkToShowDialog(Context context) {    	
    	if (needToShow) {
    		showRateDialog(context);
    		needToShow = false;
    	}
    }
    
    private static void showRateDialog(final Context mContext) {
    	if (mContext != null) {
	    	SharedPreferences prefs = PrefsManager.getPreferences(mContext);
	    	final SharedPreferences.Editor editor = prefs.edit();
	    	
	    	String appName = mContext.getString(R.string.app_name);
	    	
	    	AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
	
	    	builder.setTitle(R.string.rate_app)
	    		.setMessage(mContext.getString(R.string.dialog_rate_message, appName))
				.setCancelable(false)
				.setPositiveButton(R.string.rate, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Actions.rateApp(mContext);
						if (editor != null) {
		                    editor.putBoolean(PrefsManager.PREF_DONT_SHOW_AGAIN, true);
		                    editor.commit();
		                }
						dialog.dismiss();
					}
				})
				.setNeutralButton(R.string.later, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						 dialog.dismiss();
					}
				})
				.setNegativeButton(R.string.never, new OnClickListener() {
				    @Override
					public void onClick(DialogInterface dialog,int id) {
				    	if (editor != null) {
		                    editor.putBoolean(PrefsManager.PREF_DONT_SHOW_AGAIN, true);
		                    editor.commit();
		                }
		                dialog.dismiss();
				    }
				});
	    	    	
	    	Dialog dialog = builder.create();
			try {
				if (!((Activity) mContext).isFinishing())
					dialog.show();
			}
			catch (ClassCastException e) {
			}
    	}
    }
}