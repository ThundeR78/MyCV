package fr.wetstein.mycv.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PrefsManager {

	public static final String PREFS_MYCV = "fr.wetstein.mycv_prefs";

	public static final String PREF_DONT_SHOW_AGAIN = "dont_show_again";
	public static final String PREF_LAUNCH_COUNT = "launch_count";
	
	//YNP
	public static final String PREF_INSTALL_ID = "install_id";

	private static SharedPreferences prefs;
	
	public static SharedPreferences getPreferences(Context context) {
		if (prefs == null && context != null)
			prefs = context.getSharedPreferences(PREFS_MYCV, context.MODE_PRIVATE);//PreferenceManager.getDefaultSharedPreferences(context);
		
		return prefs; 
	}
	
	public static Editor getPreferencesEditor(Context context) {		
		return getPreferences(context).edit();
	}
	
}

