package fr.wetstein.mycv.fragment;

import android.app.Activity;
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
import fr.wetstein.mycv.adapter.ListStudyAdapter;
import fr.wetstein.mycv.model.School;
import fr.wetstein.mycv.model.Study;
import fr.wetstein.mycv.parser.json.StudyParser;

/**
 * Created by ThundeR on 05/07/2014.
 */
public class StudyListFragment extends ListFragment {
    public static final String TAG = "StudyListFragment";

    private List<Study> listStudy;
    private ListStudyAdapter listAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Load items
        listStudy =  StudyParser.loadStudies(getActivity(), true);
        Log.v(TAG, listStudy.toString());

        sortList(listStudy);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        ((SwipeRefreshLayout) rootView.findViewById(R.id.refresh_layout)).setOnRefreshListener(null);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (listStudy != null) {
            //Fill ExpandableListView with Skills
            listAdapter = new ListStudyAdapter(getActivity(), R.layout.list_study_item, listStudy);
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
            List<School> listSchool = StudyParser.loadSchools(getActivity(), true);
            intent.putParcelableArrayListExtra(MapActivity.EXTRA_ITEM_LIST_KEY, (ArrayList<? extends Parcelable>) listSchool);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClick(ListView lv, View view, int position, long id) {
        super.onListItemClick(lv, view, position, id);
        ListStudyAdapter lc_adapter = (ListStudyAdapter) lv.getAdapter();

        //Go to Detail with parameters
        Intent intent = new Intent(getActivity(), DetailSliderActivity.class);
        intent.putExtra(DetailSliderActivity.FRAGMENT_NAME_KEY, StudyDetailFragment.class.getName());
        intent.putParcelableArrayListExtra(DetailSliderActivity.ITEM_LIST_KEY, (ArrayList<Study>) listStudy);
        intent.putExtra(DetailSliderActivity.POSITION_KEY, position);
        Bundle extras = new Bundle();
        intent.putExtra(DetailSliderActivity.EXTRAS_BUNDLE_KEY, extras);

        startActivity(intent);
    }

    public void sortList(List<Study> list) {
        Collections.sort(list, new Comparator<Study>() {
            public int compare(Study o1, Study o2) {
                return o2.id - o1.id;
            }
        });
    }
}
