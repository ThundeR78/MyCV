package fr.wetstein.mycv.parser.json;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import fr.wetstein.mycv.model.Experience;
import fr.wetstein.mycv.parser.ParserAssets;
import fr.wetstein.mycv.util.FormatValue;

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
            JSONObject jsonObjectRoot = new JSONObject(loadStringFromAsset(context, pathData + file));
            JSONArray jsonArrayRoot = jsonObjectRoot.getJSONArray("career");

            //Loop each node
            for (int i = 0; i < jsonArrayRoot.length(); i++) {
                Experience item = new Experience();
                JSONObject jsonObjectItem = jsonArrayRoot.getJSONObject(i);

                if (jsonObjectItem != null) {
                    item.name = jsonObjectItem.getString("name");
                    item.link = jsonObjectItem.getString("link");
                    item.function = jsonObjectItem.getString("function");
                    item.address = jsonObjectItem.getString("address");
                    item.latitude = jsonObjectItem.getDouble("latitude");
                    item.longitude = jsonObjectItem.getDouble("longitude");
                    item.pin = Float.parseFloat(jsonObjectItem.optString("pin").toString());

                    //Color
                    if (jsonObjectItem.has("color")) {
                        String resColorName = jsonObjectItem.getString("color");
                        if (resColorName != null)
                            item.color = context.getResources().getIdentifier(resColorName, "color", context.getPackageName());
                    }

                    //Logo
                    String resLogoName = jsonObjectItem.getString("logo");
                    if (resLogoName != null)
                        item.logo = context.getResources().getIdentifier(resLogoName, "drawable", context.getPackageName());

                    //Dates
                    String dateBegin = jsonObjectItem.getString("dateBegin");
                    item.dateBegin = FormatValue.dateFormat.parse(dateBegin);
                    String dateEnd = jsonObjectItem.getString("dateEnd");
                    item.dateEnd = FormatValue.dateFormat.parse(dateEnd);

                    String resTypeName = jsonObjectItem.getString("type");
                    int resTypeId = context.getResources().getIdentifier(resTypeName, "string", context.getPackageName());
                    if (resTypeId != 0)
                        item.type = context.getString(resTypeId);

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
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return listItem;
    }

}