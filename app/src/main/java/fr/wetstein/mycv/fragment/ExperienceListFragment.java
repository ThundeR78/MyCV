package fr.wetstein.mycv.fragment;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import fr.wetstein.mycv.R;
import fr.wetstein.mycv.activity.DetailSliderActivity;
import fr.wetstein.mycv.activity.MapActivity;
import fr.wetstein.mycv.adapter.ListExperienceAdapter;
import fr.wetstein.mycv.model.Experience;
import fr.wetstein.mycv.parser.json.ExperienceParser;

/**
 * Created by ThundeR on 05/07/2014.
 */
public class ExperienceListFragment extends ListFragment {
    public static final String TAG = "ExperienceListFragment";

    private List<Experience> listExperience;
    private ListExperienceAdapter listAdapter;

    public ExperienceListFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Load items
        //listExperience =  ExperienceParser.loadCareer(getActivity());
        Log.v(TAG, listExperience.toString());

        sortList(listExperience);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        ((SwipeRefreshLayout) rootView.findViewById(R.id.refresh_layout)).setOnRefreshListener(null);

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_list_experience, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_map) {
            Intent intent = new Intent(getActivity(), MapActivity.class);
            intent.putParcelableArrayListExtra(MapActivity.EXTRA_ITEM_LIST_KEY, (ArrayList<? extends Parcelable>) listExperience);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClick(ListView lv, View view, int position, long id) {
        super.onListItemClick(lv, view, position, id);
        ListExperienceAdapter lc_adapter = (ListExperienceAdapter) lv.getAdapter();

        //Go to Detail with parameters
        Intent intent = new Intent(getActivity(), DetailSliderActivity.class);
        intent.putExtra(DetailSliderActivity.FRAGMENT_NAME_KEY, ExperienceDetailFragment.class.getName());
        intent.putParcelableArrayListExtra(DetailSliderActivity.ITEM_LIST_KEY, (ArrayList<Experience>) listExperience);
        intent.putExtra(DetailSliderActivity.POSITION_KEY, position);
        Bundle extras = new Bundle();
        intent.putExtra(DetailSliderActivity.EXTRAS_BUNDLE_KEY, extras);

        startActivity(intent);
    }

    public void sortList(List<Experience> list) {
        Collections.sort(list, new Comparator<Experience>() {
            public int compare(Experience o1, Experience o2) {
                return o2.dateBegin.compareTo(o1.dateBegin);
            }
        });
    }
}
