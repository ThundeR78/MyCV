package fr.wetstein.mycv.parser.json;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import fr.wetstein.mycv.model.School;
import fr.wetstein.mycv.model.Study;
import fr.wetstein.mycv.parser.ParserAssets;
import fr.wetstein.mycv.util.FormatValue;

/**
 * Created by ThundeR on 18/07/2014.
 */
public class StudyParser extends ParserAssets {
    public static final String TAG = "StudyParser";

    private static final String file = "studies.json";

    //Load Study list
    public static List<Study> loadStudies(Context context, boolean knowSchool) {
        List<Study> listItem = new ArrayList<Study>();

        try {
            JSONObject jsonObjectRoot = new JSONObject(loadStringFromAsset(context, pathData + file));
            JSONArray jsonArrayRoot = jsonObjectRoot.getJSONArray("studies");

            //Loop each node
            for (int i = 0; i < jsonArrayRoot.length(); i++) {
                Study item = new Study();
                JSONObject jsonObjectItem = jsonArrayRoot.getJSONObject(i);

                if (jsonObjectItem != null) {
                    item.id = jsonObjectItem.getInt("id");
                    item.name = jsonObjectItem.getString("name");
                    item.option = jsonObjectItem.getString("option");
                    item.date = jsonObjectItem.getString("date");

                    //Loop each certif in node
                    JSONArray jsonArray = jsonObjectItem.getJSONArray("certifications");
                    for (int j = 0; j < jsonArray.length(); j++) {
                        item.listCertif.add(jsonArray.getString(j));
                    }

                    if (knowSchool) {
                        List<School> listSchool = loadSchools(context, false);
                        //Link Study -> School
                        int schoolId = jsonObjectItem.getInt("school");
                        for (School school : listSchool) {
                            if (school.id == schoolId) {
                                item.school = school;
                            }
                        }
                    }

                    listItem.add(item);
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, e.toString());
        }

        return listItem;
    }

    //Load School list
    public static List<School> loadSchools(Context context, boolean knowStudies) {
        List<School> listItem = new ArrayList<School>();

        try {
            JSONObject jsonObjectRoot = new JSONObject(loadStringFromAsset(context, pathData + file));
            JSONArray jsonArrayRoot = jsonObjectRoot.getJSONArray("schools");

            //Loop each node
            for (int i = 0; i < jsonArrayRoot.length(); i++) {
                School item = new School();
                JSONObject jsonObjectItem = jsonArrayRoot.getJSONObject(i);

                if (jsonObjectItem != null) {
                    item.id = jsonObjectItem.getInt("id");
                    item.name = jsonObjectItem.getString("name");
                    item.link = jsonObjectItem.getString("link");
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

                    //Date
                    String dateBegin = jsonObjectItem.getString("dateBegin");
                    item.dateBegin = FormatValue.dateMonthFormat.parse(dateBegin);
                    String dateEnd = jsonObjectItem.getString("dateEnd");
                    item.dateEnd = FormatValue.dateMonthFormat.parse(dateEnd);

                    //Studies
                    if (knowStudies) {
                        List<Study> listStudy = loadStudies(context, false);
                        JSONArray jsonArray = jsonObjectItem.getJSONArray("studiesId");

                        for (int j = 0; j < jsonArray.length(); j++) {
                            int studyId = jsonArray.getInt(j);
                            //Link School -> Studies
                            for (Study study : listStudy) {
                                if (study.id == studyId) {
                                    item.listStudy.add(study);
                                }
                            }
                        }
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
