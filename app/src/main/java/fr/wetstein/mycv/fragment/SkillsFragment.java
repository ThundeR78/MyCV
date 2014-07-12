package fr.wetstein.mycv.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.wetstein.mycv.R;
import fr.wetstein.mycv.adapter.ExpandableListSkillAdapter;
import fr.wetstein.mycv.business.GroupSkill;
import fr.wetstein.mycv.business.Skill;
import fr.wetstein.mycv.util.ParserAssets;
import fr.wetstein.mycv.view.TextProgressBar;

/**
 * Created by ThundeR on 05/07/2014.
 */
public class SkillsFragment extends Fragment {
    public static final String TAG = "SkillsFragment";

    public static final int PROGRESSBAR_ANIMATION_TIME = 1200;

    private HashMap<GroupSkill, List<Skill>> mapSkills;

    private LinearLayout rootLinear;
    private ExpandableListView expListView;
    private ExpandableListSkillAdapter expListAdapter;


    public SkillsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Load Skills
        mapSkills =  ParserAssets.loadSkills(getActivity());
        Log.v(TAG, mapSkills.toString());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_skills, container, false);

        expListView = (ExpandableListView) rootView.findViewById(R.id.explv_skills);

        ProgressBar progbar = (ProgressBar) rootView.findViewById(R.id.progressbar);
        progbar.getProgressDrawable().setLevel(60*100);
        progbar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.violet_dark), PorterDuff.Mode.SRC_IN);
        progbar.setProgress(60);

        ProgressBar progbar3 = (ProgressBar) rootView.findViewById(R.id.progressbar3);
        Rect bounds = progbar3.getProgressDrawable().getBounds();
        progbar3.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.blue_dark), PorterDuff.Mode.SRC_IN);
        progbar3.getProgressDrawable().setColorFilter(getResources().getColor(R.color.blue_light), PorterDuff.Mode.SRC_IN);
        progbar3.setProgress(70);
        progbar3.getProgressDrawable().setBounds(bounds);
        progbar3.setProgress(90);

        TextProgressBar progbar2 = (TextProgressBar) rootView.findViewById(R.id.progressbar2);
        progbar2.getProgressDrawable().setColorFilter(getResources().getColor(R.color.red_dark), PorterDuff.Mode.SRC_IN);
        progbar2.setText("Android");

        ProgressBar progressBar = new ProgressBar(getActivity(), null, android.R.attr.progressBarStyleHorizontal);

        // Define a shape with rounded corners
        final float[] roundedCorners = new float[] { 5, 5, 5, 5, 5, 5, 5, 5 };
        ShapeDrawable pgDrawable = new ShapeDrawable(new RoundRectShape(roundedCorners,     null, null));

        // Sets the progressBar color
        pgDrawable.getPaint().setColor(getResources().getColor(R.color.violet_dark));

        // Adds the drawable to your progressBar
        ClipDrawable progress = new ClipDrawable(pgDrawable, Gravity.LEFT, ClipDrawable.HORIZONTAL);
        progressBar.setProgressDrawable(progress);

        // Sets a background to have the 3D effect
        progressBar.setBackgroundDrawable(getActivity().getResources()
                .getDrawable(android.R.drawable.progress_horizontal));

        // Adds your progressBar to your layout
        ((LinearLayout)rootView.findViewById(R.id.rootLinearLayout)).addView(progressBar);
        rootLinear = (LinearLayout) rootView.findViewById(R.id.rootLinearLayout);

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

        //loadSkills();
    }

    private void loadSkills() {
        for (Map.Entry<GroupSkill, List<Skill>> entry : mapSkills.entrySet()) {
            GroupSkill key = entry.getKey();
            List<Skill> values = entry.getValue();

            TextView txtSection = new TextView(getActivity());
            txtSection.setText(key.label);
            rootLinear.addView(txtSection);

            for (Skill s : values) {
                //Layout
                LinearLayout rl = new LinearLayout(getActivity());
                rl.setGravity(Gravity.CENTER);
                rl.setOrientation(LinearLayout.HORIZONTAL);

                //Label
                TextView txt = new TextView(getActivity());
                txt.setText(s.label);
                LinearLayout.LayoutParams paramsLabel = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                //paramsLabel.addRule(RelativeLayout.CENTER_IN_PARENT);
                rl.addView(txt, paramsLabel);

                //ProgressBar
                ProgressBar pgb = new ProgressBar(getActivity(), null, android.R.attr.progressBarStyleHorizontal);
                if (s.color != null) {
                    int colorId = getResources().getIdentifier(s.color, "color", getActivity().getPackageName());
                    pgb.getProgressDrawable().setColorFilter(getResources().getColor(colorId), PorterDuff.Mode.SRC_IN);
                } else
                    pgb.getProgressDrawable().setColorFilter(getResources().getColor(R.color.violet_dark), PorterDuff.Mode.SRC_IN);
                pgb.setProgress(s.rate);
                LinearLayout.LayoutParams paramsProgress = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                //paramsProgress.addRule(RelativeLayout.CENTER_IN_PARENT);
                rl.addView(pgb, paramsProgress);

                rootLinear.addView(rl);
            }

            //Add section margin
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) rootLinear.getLayoutParams();
            lp.setMargins(0, 0, 0, 10);
            rootLinear.setLayoutParams(lp);
        }
    }
}
