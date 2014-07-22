package fr.wetstein.mycv.util;

import android.text.format.DateFormat;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class FormatValue {
	
	//DATE FORMAT
	public static SimpleDateFormat datetimeSQLiteFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
	public static SimpleDateFormat dateSQLiteFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
	public static SimpleDateFormat dateWebServiceFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
	public static SimpleDateFormat datetimeShortFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.getDefault());
	public static SimpleDateFormat dateSpaceFormat = new SimpleDateFormat("dd MMM yyyy");
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
	public static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
	public static SimpleDateFormat datetimeLabelFormat = new SimpleDateFormat("HH:mm 'le' dd/MM/yyyy", Locale.getDefault());
	public static SimpleDateFormat datetimeLabelFormat2 = new SimpleDateFormat("'le 'dd/MM/yyyy' à 'HH'h'mm", Locale.getDefault());
	public static SimpleDateFormat datetimeLabelFormat3 = new SimpleDateFormat("dd/MM/yyyy' à 'HH'h'mm", Locale.getDefault());
	public static SimpleDateFormat timedateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy", Locale.getDefault());
	public static SimpleDateFormat dayDateFormat = new SimpleDateFormat("EEEE F MMMM yyyy' à 'HH'h'mm", Locale.getDefault());
    public static SimpleDateFormat monthDateFormat = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
	public static NumberFormat numberFormat = NumberFormat.getInstance();
	
	public static String millisecondFormat(long time) {
	    if (time > 60000)	//1min
	    	return (String) DateFormat.format("m'min' ss'sec'", new Date(time));
	    else 
	    	return (String) DateFormat.format("s'sec'", new Date(time));
	}
	
	public static String formatBigNumber(int number) {
		return NumberFormat.getNumberInstance(Locale.getDefault()).format(number);
	}
	
	public static String formatTimeSinceLastUpdate(Date date) {
		String lastConnection = "??";
		long timeUp = (new Date()).getTime() - date.getTime();
		
		if (TimeUnit.MILLISECONDS.toDays(timeUp) >= 1) {
			lastConnection = TimeUnit.MILLISECONDS.toDays(timeUp) + (TimeUnit.MILLISECONDS.toDays(timeUp) >1 ? "jours" : "jour");
		} else if (TimeUnit.MILLISECONDS.toHours(timeUp) >= 1) {
			lastConnection = TimeUnit.MILLISECONDS.toHours(timeUp) + (TimeUnit.MILLISECONDS.toHours(timeUp) >1 ? "heures" : "heure");
		} else if (TimeUnit.MILLISECONDS.toMinutes(timeUp) >= 1) {
			lastConnection = TimeUnit.MILLISECONDS.toMinutes(timeUp) + (TimeUnit.MILLISECONDS.toMinutes(timeUp) >1 ? "minutes" : "minute");
		} else if (TimeUnit.MILLISECONDS.toSeconds(timeUp) >= 1) {
			lastConnection = TimeUnit.MILLISECONDS.toMinutes(timeUp) + (TimeUnit.MILLISECONDS.toMinutes(timeUp) >1 ? "secondes" : "seconde");
		}
		
		return lastConnection;
	}
}
