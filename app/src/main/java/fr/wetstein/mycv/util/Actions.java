package fr.wetstein.mycv.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.Html;

import java.util.List;

import fr.wetstein.mycv.R;

public class Actions {

    public static final String URL_MARKET = "market://details?id=";
    public static final String URL_STORE = "http://play.google.com/store/apps/details?id=";

	//Go to Store to rate the app
	public static void rateApp(Context context) {
		if (context != null) {
			String packageName = context.getPackageName();
			Uri uri = Uri.parse(URL_MARKET + packageName);
			Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
			
			try {
				context.startActivity(goToMarket);
			} catch (ActivityNotFoundException e) {
				uri = Uri.parse(URL_STORE + packageName);
				context.startActivity(new Intent(Intent.ACTION_VIEW, uri));
			}
		}
	}
	
	//Send message with an app selected
	public static void shareApp(Context context) {
		if (context != null) {
			String packageName = context.getPackageName();
			String appName = context.getString(R.string.app_name);
			String shareTitle = context.getString(R.string.share_app_title, appName);
			String shareMessage = context.getString(R.string.share_app_message, appName, URL_STORE + packageName);
			
			if (shareMessage != null && !shareMessage.isEmpty()) {
				Intent sendIntent = new Intent();
				sendIntent.setAction(Intent.ACTION_SEND);
				sendIntent.putExtra(Intent.EXTRA_SUBJECT, shareTitle);
				sendIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(shareMessage));
				sendIntent.setType("text/html");
				
				context.startActivity(sendIntent);
			}
		}
	}

    public static void sendEmail(Context context, String senderTitle,
            String[] emailTO, String[] emailCC, String[] emailBC,
            String subject, String text, boolean isHTMLFormat) {
        // Create a new Intent to send messages
        Intent sendIntent = new Intent(Intent.ACTION_SEND);

        // Add attributes to the intent
        if (emailTO != null && emailTO.length > 0)
            sendIntent.putExtra(Intent.EXTRA_EMAIL, emailTO);
        if (emailCC != null && emailCC.length > 0)
            sendIntent.putExtra(Intent.EXTRA_CC, emailCC);
        if (emailBC != null && emailBC.length > 0)
            sendIntent.putExtra(Intent.EXTRA_BCC, emailBC);

        sendIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        sendIntent.putExtra(Intent.EXTRA_TEXT, isHTMLFormat ? Html.fromHtml(text) : text);

        sendIntent.setType("text/plain");

        context.startActivity(Intent.createChooser(sendIntent, senderTitle));
    }
		
	/**
	 * @param context
	 * @param type("text/plain","mail","application/mail"), senderTitle, emailTO, emailCC, emailBC, subject, text, isHTMLFormat
	 */
	public static void initShareIntent(Context context, String type, String senderTitle, String[] emailTO, String[] emailCC, String[] emailBC, String subject, String text, boolean isHTMLFormat) {
		if (context == null)
			return;
		
		boolean found = false;
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.setType(type);
		shareIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail"); // GMAIL
			
		// gets the list of intents that can be loaded.
		List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(shareIntent, 0);
		if (!resInfo.isEmpty()) {
			for (ResolveInfo info : resInfo) {
//				if (info.activityInfo.packageName.toLowerCase().contains(type) || info.activityInfo.name.toLowerCase().contains(type)) {
			        if (emailTO != null && emailTO.length > 0)
			        	shareIntent.putExtra(Intent.EXTRA_EMAIL, emailTO);
			        if (emailCC != null && emailCC.length > 0)
			        	shareIntent.putExtra(Intent.EXTRA_CC, emailCC);
			        if (emailBC != null && emailBC.length > 0)
			        	shareIntent.putExtra(Intent.EXTRA_BCC, emailBC);
			        
			        shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
			        if (isHTMLFormat)
			        	shareIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(text));
			        else
			        	shareIntent.putExtra(Intent.EXTRA_TEXT, text);
			               
//					share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(myPath))); // Optional, just if you wanna share an image.
			        shareIntent.setPackage(info.activityInfo.packageName);
					found = true;
					break;
//				}
			}
			if (!found)
				return;
	        
	        context.startActivity(Intent.createChooser(shareIntent, senderTitle));
		} else {
			sendEmail(context, senderTitle, emailTO, emailCC, emailBC, subject, text, isHTMLFormat);
		}
	}
}
