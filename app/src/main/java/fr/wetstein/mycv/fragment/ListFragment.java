package fr.wetstein.mycv.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.wetstein.mycv.R;

public abstract class ListFragment extends android.app.ListFragment implements SwipeRefreshLayout.OnRefreshListener {
	public static final String TAG = "ListFragment";

    protected SwipeRefreshLayout refreshLayout;
    protected AdFragment mAdFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        //RefreshLayout
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(R.color.blue, R.color.green, R.color.red, R.color.violet);

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
