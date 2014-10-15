package fr.wetstein.mycv.parser.json;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import fr.wetstein.mycv.model.Company;
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
    public static List<Experience> loadCareer(Context context, boolean knowCompany) {
        List<Experience> listItem = new ArrayList<Experience>();
        List<Company> listCompany = null;
        SparseArray<Company> map = new SparseArray<Company>();

        if (knowCompany) {
            listCompany = loadCompanies(context, false);
            for (Company o : listCompany) map.put(o.id, o);
        }

        try {
            JSONObject jsonObjectRoot = new JSONObject(loadStringFromAsset(context, pathData + file));
            JSONArray jsonArrayRoot = jsonObjectRoot.getJSONArray("career");

            //Loop each node
            for (int i = 0; i < jsonArrayRoot.length(); i++) {
                Experience item = new Experience();
                JSONObject jsonObjectItem = jsonArrayRoot.getJSONObject(i);

                if (jsonObjectItem != null) {
                    item.function = jsonObjectItem.getString("function");
                    item.address = jsonObjectItem.getString("address");
                    item.latitude = jsonObjectItem.getDouble("latitude");
                    item.longitude = jsonObjectItem.getDouble("longitude");

                    //Dates
                    String dateBegin = jsonObjectItem.getString("dateBegin");
                    item.dateBegin = FormatValue.dateFormat.parse(dateBegin);
                    String dateEnd = jsonObjectItem.getString("dateEnd");
                    item.dateEnd = (!dateEnd.equalsIgnoreCase("now")) ? FormatValue.dateFormat.parse(dateEnd) : null;

                    //Type
                    String resTypeName = jsonObjectItem.getString("type");
                    if (resTypeName != null) {
                        int resTypeId = context.getResources().getIdentifier(resTypeName, "string", context.getPackageName());
                        if (resTypeId != 0) {
                            item.type = context.getString(resTypeId);
                            item.name = item.type;
                        }
                    }

                    //Loop each Task in node
                    JSONArray jsonArray = jsonObjectItem.getJSONArray("tasks");
                    for (int j = 0; j < jsonArray.length(); j++) {
                        item.listTask.add(jsonArray.getString(j));
                    }

                    if (knowCompany) {
                        //Link Experience -> Company
                        int companyId = jsonObjectItem.getInt("company");
                        Company o = map.get(companyId);
                        if (o != null)
                            item.company = o;
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

    //Load Company list
    public static List<Company> loadCompanies(Context context, boolean knowExperiences) {
        List<Company> listItem = new ArrayList<Company>();
        List<Experience> listExperience = null;
        SparseArray<Experience> map = new SparseArray<Experience>();

        if (knowExperiences) {
            listExperience = loadCareer(context, false);
            for (Experience o : listExperience) map.put(o.id, o);
        }

        try {
            JSONObject jsonObjectRoot = new JSONObject(loadStringFromAsset(context, pathData + file));
            JSONArray jsonArrayRoot = jsonObjectRoot.getJSONArray("companies");

            //Loop each node
            for (int i = 0; i < jsonArrayRoot.length(); i++) {
                Company item = new Company();
                JSONObject jsonObjectItem = jsonArrayRoot.getJSONObject(i);

                if (jsonObjectItem != null) {
                    item.id = jsonObjectItem.getInt("id");
                    item.name = jsonObjectItem.getString("name");
                    item.website = jsonObjectItem.getString("website");
                    item.phone = jsonObjectItem.getString("phone");
                    item.email = jsonObjectItem.getString("email");
                    item.twitter = jsonObjectItem.getString("twitter");
                    item.address = jsonObjectItem.getString("headOffice");
                    item.desc = jsonObjectItem.getString("desc");
                    item.ceo = jsonObjectItem.getString("ceo");
                    item.nbEmployees = jsonObjectItem.getString("employees");
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

                    //Activity
                    String resActivityName = jsonObjectItem.getString("activity");
                    if (resActivityName != null) {
                        int resActivityId = context.getResources().getIdentifier(resActivityName, "string", context.getPackageName());
                        if (resActivityId != 0)
                            item.activity = context.getString(resActivityId);
                    }

                    //Date
                    String dateBegin = jsonObjectItem.getString("dateBegin");
                    item.dateBegin = FormatValue.dateYearFormat.parse(dateBegin);
                    String dateEnd = jsonObjectItem.getString("dateEnd");
                    item.dateEnd = !dateEnd.equals("now") ? FormatValue.dateYearFormat.parse(dateEnd) : null;

                    //Experiences
                    if (knowExperiences) {
                        JSONArray jsonArray = jsonObjectItem.getJSONArray("experiencesId");

                        for (int j = 0; j < jsonArray.length(); j++) {
                            int experienceId = jsonArray.getInt(j);
                            //Link Company -> Experiences
                            Experience o = map.get(experienceId);
                            if (o != null)
                                item.listExperience.add(o);
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
