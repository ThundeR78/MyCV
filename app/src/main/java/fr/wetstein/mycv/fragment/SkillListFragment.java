package fr.wetstein.mycv.fragment;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import fr.wetstein.mycv.R;
import fr.wetstein.mycv.adapter.ExpandableListSkillAdapter;
import fr.wetstein.mycv.model.GroupSkill;
import fr.wetstein.mycv.model.Skill;
import fr.wetstein.mycv.parser.json.SkillParser;

/**
 * Created by ThundeR on 05/07/2014.
 */
public class SkillListFragment extends Fragment {
    public static final String TAG = "SkillListFragment";

    public static final int PROGRESSBAR_ANIMATION_TIME = 1200;

    private HashMap<GroupSkill, List<Skill>> mapSkills;

    private ExpandableListView expListView;
    private ExpandableListSkillAdapter expListAdapter;
    private Configuration configuration;

    public SkillListFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Load Skills
        mapSkills =  SkillParser.loadSkills(getActivity());
        Log.v(TAG, mapSkills.toString());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_skills, container, false);

        expListView = (ExpandableListView) rootView.findViewById(R.id.explv_skills);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (mapSkills != null) {
            //Get all keys
            List<GroupSkill> listGroup = new ArrayList<GroupSkill>(Arrays.asList(mapSkills.keySet().toArray(new GroupSkill[mapSkills.size()])));

            //Sort List by orderId on group
            Collections.sort(listGroup, new Comparator() {
                @Override
                public int compare(Object obj1, Object obj2) {
                    GroupSkill v1 = (GroupSkill) obj1;
                    GroupSkill v2 = (GroupSkill) obj2;

                    // ascending order
                    return v1.orderId - v2.orderId;

                    // descending order
                    //return v2.orderId - v1.orderId;
                }
            });

            //Fill ExpandableListView with Skills
            expListAdapter = new ExpandableListSkillAdapter(getActivity(), listGroup, mapSkills);
            expListView.setAdapter(expListAdapter);

            //Open first group
            if (expListAdapter.getGroupCount() > 0)
                expListView.expandGroup(0, true);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

}
