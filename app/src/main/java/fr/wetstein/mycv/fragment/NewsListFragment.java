package fr.wetstein.mycv.fragment;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import fr.wetstein.mycv.R;
import fr.wetstein.mycv.activity.DetailSliderActivity;
import fr.wetstein.mycv.adapter.ListStudyAdapter;
import fr.wetstein.mycv.model.News;
import fr.wetstein.mycv.model.Study;
import fr.wetstein.mycv.parser.StudyParser;
import fr.wetstein.mycv.request.NewsRequest;

/**
 * Created by ThundeR on 05/07/2014.
 */
public class NewsListFragment extends ListFragment {
    public static final String TAG = "NewsListFragment";

    private List<Study> listNews;
    private ListStudyAdapter listAdapter;

    public NewsListFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Load items
        listNews =  StudyParser.loadStudies(getActivity(), true);
        Log.v(TAG, listNews.toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (listNews != null) {
            //Fill ExpandableListView with Skills
            listAdapter = new ListStudyAdapter(getActivity(), R.layout.list_study_item, listNews);
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
        launchRequest();
    }

    private void launchRequest() {
        //refreshLayout.setRefreshing(true);

        NewsRequest request = new NewsRequest(getActivity().getApplicationContext());

        Response.Listener<List<News>> successListener = new Response.Listener<List<News>>() {
            @Override
            public void onResponse(List<News> listItem) {
                Log.v(TAG, "SUCCESS REQUEST : "+ (listItem != null ? listItem.size() : "null"));
                Toast.makeText(getActivity(), "Success", Toast.LENGTH_LONG).show();
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v(TAG, "ERROR REQUEST : "+ error);
                /*String messageError = getString(R.string.error_request);
                if (error != null && error.networkResponse != null)
                    messageError = getString(R.string.error_request_status, error.networkResponse.statusCode);
                Crouton.makeText(getActivity(), messageError, Style.ALERT).show();*/
                Toast.makeText(getActivity(), "Error : "+error, Toast.LENGTH_LONG).show();
            }
        };
        request.getListNews(successListener, errorListener);
    }

    @Override
    public void onListItemClick(ListView lv, View view, int position, long id) {
        super.onListItemClick(lv, view, position, id);
        ListStudyAdapter lc_adapter = (ListStudyAdapter) lv.getAdapter();

        //Go to Detail with parameters
        Intent intent = new Intent(getActivity(), DetailSliderActivity.class);
        intent.putExtra(DetailSliderActivity.FRAGMENT_NAME_KEY, StudyDetailFragment.class.getName());
        intent.putParcelableArrayListExtra(DetailSliderActivity.ITEM_LIST_KEY, (ArrayList<Study>) listNews);
        intent.putExtra(DetailSliderActivity.POSITION_KEY, position);
        Bundle extras = new Bundle();
        intent.putExtra(DetailSliderActivity.EXTRAS_BUNDLE_KEY, extras);

        startActivity(intent);
    }
}
