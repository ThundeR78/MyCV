package fr.wetstein.mycv.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.wetstein.mycv.R;

public abstract class ListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
	public static final String TAG = "ListFragment";

    protected RecyclerView mList;
    protected SwipeRefreshLayout mRefreshLayout;
    protected AdFragment mAdFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        mList = (RecyclerView) rootView.findViewById(R.id.list);
        mList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mList.setHasFixedSize(true);
        mList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int topRowVerticalPosition = (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                mRefreshLayout.setEnabled(topRowVerticalPosition >= 0);
            }
        });

        //RefreshLayout
        mRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.refresh_layout);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setColorSchemeResources(R.color.blue, R.color.green, R.color.red, R.color.violet);

        //We shouldn't inflate fragments inside other fragments
        //mAdFragment = (AdFragment) getFragmentManager().findFragmentById(R.id.adFragment);
        mAdFragment = new AdFragment();
        getFragmentManager().beginTransaction().replace(R.id.adFragment, mAdFragment).commit();

        return rootView;
    }

	@Override
	public void onStart() {
		super.onStart();

	}
	
	@Override
	public void onResume() {
		super.onResume();

	}

	@Override
	public void onPause() {
		super.onPause();
		
	}

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (mAdFragment != null)
            getFragmentManager().beginTransaction().remove(mAdFragment).commit();
    }

    @Override
    public void onRefresh() {

    }
}
