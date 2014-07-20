package fr.wetstein.mycv.fragment;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fr.wetstein.mycv.R;
import fr.wetstein.mycv.adapter.ListExperienceAdapter;
import fr.wetstein.mycv.business.Experience;
import fr.wetstein.mycv.parser.ExperienceParser;

/**
 * Created by ThundeR on 05/07/2014.
 */
public class CareerFragment extends ListFragment {
    public static final String TAG = "CareerFragment";

    private List<Experience> listExperience;
    private ListExperienceAdapter listAdapter;

    public CareerFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Load items
        listExperience =  ExperienceParser.loadCareer(getActivity());
        Log.v(TAG, listExperience.toString());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_experiences, container, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (listExperience != null) {
            //Fill ExpandableListView with Skills
            listAdapter = new ListExperienceAdapter(getActivity(), R.layout.list_experience_item, listExperience);
            setListAdapter(listAdapter);
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
