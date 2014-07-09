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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.wetstein.mycv.R;
import fr.wetstein.mycv.business.Skill;
import fr.wetstein.mycv.util.ParserAssets;
import fr.wetstein.mycv.view.TextProgressBar;

/**
 * Created by ThundeR on 05/07/2014.
 */
public class SkillsFragment extends Fragment {
    public static final String TAG = "SkillsFragment";

    private HashMap<String, List<Skill>> mapSkills;

    private LinearLayout rootLinear;

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
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //((HomeActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
    }

    @Override
    public void onStart() {
        super.onStart();

        for (Map.Entry<String, List<Skill>> entry : mapSkills.entrySet()) {
            String key = entry.getKey();
            List<Skill> values = entry.getValue();

            TextView txtSection = new TextView(getActivity());
            txtSection.setText(key);
            rootLinear.addView(txtSection);

            for (Skill s : values) {
                //Layout
                RelativeLayout rl = new RelativeLayout(getActivity());
                rl.setGravity(Gravity.CENTER);

                RelativeLayout.LayoutParams paramsProgress = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                paramsProgress.addRule(RelativeLayout.CENTER_IN_PARENT);

                //ProgressBar
                ProgressBar pgb = new ProgressBar(getActivity(), null, android.R.attr.progressBarStyleHorizontal);
                if (s.color != null) {
                    int colorId = getResources().getIdentifier(s.color, "color", getActivity().getPackageName());
                    pgb.getProgressDrawable().setColorFilter(getResources().getColor(colorId), PorterDuff.Mode.SRC_IN);
                } else
                    pgb.getProgressDrawable().setColorFilter(getResources().getColor(R.color.violet_dark), PorterDuff.Mode.SRC_IN);
                pgb.setProgress(s.rate);
                rl.addView(pgb, paramsProgress);

                RelativeLayout.LayoutParams paramsLabel = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                paramsLabel.addRule(RelativeLayout.CENTER_IN_PARENT);

                //Label
                TextView txt = new TextView(getActivity());
                txt.setText(s.label);
                rl.addView(txt, paramsLabel);

                rootLinear.addView(rl);
            }
        }
    }
}
