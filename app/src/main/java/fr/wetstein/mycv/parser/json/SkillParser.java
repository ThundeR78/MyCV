package fr.wetstein.mycv.parser.json;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.wetstein.mycv.model.GroupSkill;
import fr.wetstein.mycv.model.Skill;
import fr.wetstein.mycv.parser.ParserAssets;

/**
 * Created by ThundeR on 18/07/2014.
 */
public class SkillParser extends ParserAssets {
    public static final String TAG = "SkillParser";

    private static final String fileSkills = "skills.json";

    private enum NodesSkill {
        DEV("dev"), SOFTWARE("software"), LANGUAGE("language"), OS("os"), VERSIONING("versioning");
        private String label;
        private NodesSkill(String l) { this.label = l; }
        @Override
        public String toString(){ return label; }
    }

    //Load Skill Map
    public static HashMap<GroupSkill, List<Skill>> loadSkills(Context context) {
        HashMap<GroupSkill, List<Skill>> mapSkill = new HashMap<GroupSkill, List<Skill>>();

        try {
            JSONObject jsonObjectRoot = new JSONObject(loadStringFromAsset(context, pathData + fileSkills));

            //Loop each node
            for (NodesSkill ns : NodesSkill.values()) {
                List<Skill> items = new ArrayList<Skill>();
                GroupSkill groupItem = new GroupSkill();
                JSONObject jsonGroup = jsonObjectRoot.getJSONObject(ns.toString());

                if (jsonGroup != null) {
                    //Group Item
                    groupItem.orderId = jsonGroup.getInt("orderId");
                    if (jsonGroup.has("color"))
                        groupItem.color = jsonGroup.getString("color");
                    groupItem.label = ns.name();

                    //Loop each skill in node
                    JSONArray jsonArrayRoot = jsonGroup.getJSONArray("values");
                    for (int i = 0; i < jsonArrayRoot.length(); i++) {
                        JSONObject jsonObjectItem = jsonArrayRoot.getJSONObject(i);
                        Skill item = new Skill();

                        item.label = jsonObjectItem.getString("label");
                        item.rate = jsonObjectItem.getInt("rate");
                        if (jsonObjectItem.has("color"))
                            item.color = jsonObjectItem.getString("color");

                        items.add(item);
                    }

                    mapSkill.put(groupItem, items);
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, e.toString());
        }

        return mapSkill;
    }

}
