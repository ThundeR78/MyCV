package fr.wetstein.mycv.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fr.wetstein.mycv.R;
import fr.wetstein.mycv.model.Travel;
import fr.wetstein.mycv.parser.xml.TravelParser;

/**
 * Created by ThundeR on 12/07/2014.
 */
public class TravelFragment extends Fragment {
    public static final String TAG = "TravelFragment";

    private List<Travel> listTravel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_travel, container, false);


        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listTravel = TravelParser.loadTravels(getActivity());
        Log.v(TAG, listTravel.size()+"");
    }
}
