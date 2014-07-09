package fr.wetstein.mycv.util;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.wetstein.mycv.business.Skill;

public class ParserAssets {

	public static final String TAG = "ParserAssets";
	
	private static final String pathData = "data/";
	private static final String fileSkills = "skills.json";

    public static final String NODE_DEV = "dev";
    public static final String NODE_SOFTWARE = "software";
    public static final String NODE_LANGUAGE = "language";
    public static final String NODE_OS = "os";
    public static final String NODE_VERSIONING = "versioning";

    private enum NodesSkill {
        DEV("dev"), SOFTWARE("software"), LANGUAGE("language"), OS("os"), VERSIONING("versioning");
        private String label;
        private NodesSkill(String l) { this.label = l; }
        @Override
        public String toString(){ return label; }
    }

    //Load Skill Map
    public static HashMap<String, List<Skill>> loadSkills(Context context) {
        HashMap<String, List<Skill>> mapSkill = new HashMap<String, List<Skill>>();
		
		try {
			JSONObject jsonObjectRoot = new JSONObject(loadJSONFromAsset(context, pathData+fileSkills));

            //Loop each node
            for (NodesSkill ns : NodesSkill.values()) {
                List<Skill> items = new ArrayList<Skill>();
                JSONArray jsonArrayRoot = jsonObjectRoot.getJSONArray(ns.toString());

                //Loop each skill in node
                for (int i = 0; i < jsonArrayRoot.length(); i++) {
                    JSONObject jsonObjectItem = jsonArrayRoot.getJSONObject(i);
                    Skill item = new Skill();

                    item.label = jsonObjectItem.getString("label");
                    item.rate = jsonObjectItem.getInt("rate");

                    items.add(item);
                }

                mapSkill.put(ns.name(), items);
            }
		} catch (JSONException e) {
			Log.e(TAG, e.toString());
		}
		
		return mapSkill;
	}

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
