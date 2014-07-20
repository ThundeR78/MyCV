package fr.wetstein.mycv.parser;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import fr.wetstein.mycv.business.Console;

/**
 * Created by ThundeR on 18/07/2014.
 */
public class GameParser extends ParserAssets {
    public static final String TAG = "GameParser";

    private static final String file = "games.json";

    //Load Consoles Map
    public static List<Console> loadConsoles(Context context) {
        List<Console> listItem = new ArrayList<Console>();

        try {
            JSONObject jsonObjectRoot = new JSONObject(loadJSONFromAsset(context, pathData+file));
            JSONArray jsonArrayRoot = jsonObjectRoot.getJSONArray("consoles");

            //Loop each node
            for (int i = 0; i < jsonArrayRoot.length(); i++) {
                Console item = new Console();
                JSONObject jsonObjectItem = jsonArrayRoot.getJSONObject(i);

                if (jsonObjectItem != null) {
                    item.name = jsonObjectItem.getString("name");
                    item.shortName = jsonObjectItem.getString("shortname");
                    if (jsonObjectItem.has("logo"))
                        item.logo = jsonObjectItem.getString("logo");
                    if (jsonObjectItem.has("icon"))
                        item.icon = jsonObjectItem.getString("icon");

                    //Loop each Game in node
                    JSONArray jsonArray = jsonObjectItem.getJSONArray("games");
                    for (int j = 0; j < jsonArray.length(); j++) {
                        item.listGame.add(jsonArray.getString(j));
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
