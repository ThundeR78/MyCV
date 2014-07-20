package fr.wetstein.mycv.parser;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import fr.wetstein.mycv.business.Experience;

/**
 * Created by ThundeR on 18/07/2014.
 */
public class ExperienceParser extends ParserAssets {
    public static final String TAG = "ExperienceParser";

    private static final String file = "career.json";

    //Load Experience list
    public static List<Experience> loadCareer(Context context) {
        List<Experience> listItem = new ArrayList<Experience>();

        try {
            JSONObject jsonObjectRoot = new JSONObject(loadJSONFromAsset(context, pathData+file));
            JSONArray jsonArrayRoot = jsonObjectRoot.getJSONArray("career");

            //Loop each node
            for (int i = 0; i < jsonArrayRoot.length(); i++) {
                Experience item = new Experience();
                JSONObject jsonObjectItem = jsonArrayRoot.getJSONObject(i);

                if (jsonObjectItem != null) {
                    item.name = jsonObjectItem.getString("name");
                    item.link = jsonObjectItem.getString("link");
                    item.address = jsonObjectItem.getString("address");
                    item.latitude = jsonObjectItem.getDouble("latitude");
                    item.longitude = jsonObjectItem.getDouble("longitude");
                    item.logo = jsonObjectItem.getString("logo");

                    //Loop each Task in node
                    JSONArray jsonArray = jsonObjectItem.getJSONArray("tasks");
                    for (int j = 0; j < jsonArray.length(); j++) {
                        item.listTask.add(jsonArray.getString(j));
                    }

                    listItem.add(item);
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, e.toString());
        }

        return listItem;
    }

}
