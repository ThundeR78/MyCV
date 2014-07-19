package fr.wetstein.mycv.parser;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

public class ParserAssets {

	public static final String TAG = "ParserAssets";
	
	protected static final String pathData = "data/";


    //Load File in JSON
	public static String loadJSONFromAsset(Context context, String filename) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
