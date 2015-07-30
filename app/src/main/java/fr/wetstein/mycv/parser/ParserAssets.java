package fr.wetstein.mycv.parser;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

public class ParserAssets {

	public static final String TAG = "ParserAssets";
	
	protected static final String pathData = "data/";


    //Load File in String
	public static String loadStringFromAsset(Context context, String filename) {
        String string = null;
        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            string = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return string;
    }

    public static InputStream loadInputStreamFromAsset(Context context, String filename) {
        InputStream is = null;
        try {
            is = context.getAssets().open(filename);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return is;
    }

    public static String getStringRessourceFromName(Context context, String resourceName) {
        if (resourceName != null) {
            int resTypeId = context.getResources().getIdentifier(resourceName, "string", context.getPackageName());
            if (resTypeId != 0) {
                return context.getString(resTypeId);
            }
        }
        return "";
    }
}
