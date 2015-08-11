package fr.wetstein.mycv.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import fr.wetstein.mycv.R;
import fr.wetstein.mycv.activity.DetailSliderActivity;
import fr.wetstein.mycv.adapter.ListNewsAdapter;
import fr.wetstein.mycv.database.DatabaseManager;
import fr.wetstein.mycv.model.News;
import fr.wetstein.mycv.request.NewsRequest;

/**
 * Created by ThundeR on 05/07/2014.
 */
public class NewsListFragment extends ListFragment {
    public static final String TAG = "NewsListFragment";

    private DatabaseManager db;

    private List<News> listNews;
    private ListNewsAdapter listAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new DatabaseManager(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_list_news);

        mList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int topRowVerticalPosition = (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                mRefreshLayout.setEnabled(topRowVerticalPosition >= 0);
            }
        });

        //Load News in DB
        listNews = db.getAllNews();
        updateListView(listNews);
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
        mRefreshLayout.setRefreshing(true);

        NewsRequest request = new NewsRequest(getActivity().getApplicationContext());

        Response.Listener<List<News>> successListener = new Response.Listener<List<News>>() {
            @Override
            public void onResponse(List<News> listItem) {
                Log.v(TAG, "SUCCESS REQUEST : "+ (listItem != null ? listItem.size() : "null"));

                if (listItem != null) {
                    db.insertOrUpdateListNews(listItem);

                    listNews = db.getAllNews();
                    int nbNewNews = listNews.size() - mList.getChildCount();
                    updateListView(listNews);

                    if (nbNewNews > 0) {
                        Snackbar.make(getActivity().findViewById(R.id.fragment_container),
                                getString(R.string.alert_new_news, nbNewNews),
                                Snackbar.LENGTH_SHORT)
                                .show();
                    }
                }

                mRefreshLayout.setRefreshing(false);
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

                if (error != null && error.networkResponse != null)
                    Toast.makeText(getActivity(), "Error "+(error.networkResponse!=null?error.networkResponse.statusCode:"")+" : "+error.getMessage(), Toast.LENGTH_LONG).show();

                mRefreshLayout.setRefreshing(false);
            }
        };
        request.getListNews(successListener, errorListener);
    }

    private void updateListView(List<News> listItem) {
        if (listItem != null) {
            //Fill ListView with News
            listAdapter = new ListNewsAdapter(listItem);
            mList.setAdapter(listAdapter);

            listAdapter.setOnItemClickListener(new ListNewsAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    //Go to Detail with parameters
                    Intent intent = new Intent(getActivity(), DetailSliderActivity.class);
                    intent.putExtra(DetailSliderActivity.FRAGMENT_NAME_KEY, NewsDetailFragment.class.getName());
                    intent.putParcelableArrayListExtra(DetailSliderActivity.ITEM_LIST_KEY, (ArrayList<News>) listNews);
                    intent.putExtra(DetailSliderActivity.POSITION_KEY, position);
                    Bundle extras = new Bundle();
                    intent.putExtra(DetailSliderActivity.EXTRAS_BUNDLE_KEY, extras);

                    startActivity(intent);
                }
            });

            listAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRefresh() {
        launchRequest();
    }
}
