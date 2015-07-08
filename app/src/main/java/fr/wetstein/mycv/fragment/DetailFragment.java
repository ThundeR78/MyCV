package fr.wetstein.mycv.fragment;

import android.app.Activity;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

public abstract class DetailFragment<Item extends Parcelable> extends Fragment {

	private static final String TAG = "DetailFragment";

	private Activity mActivity;

	protected Item currentItem;

	protected abstract void displayItem(Item inItem);
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		mActivity = activity;
	}
	
	public void setCurrentItem(Item inCurrentItem) {
        currentItem = inCurrentItem;
	}

	@Override
	public void onStart() {
		super.onStart();

		if (currentItem != null) {
			displayItem(currentItem);
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();

	}

	public void refreshUI() {
		displayItem(currentItem);		
	}


	@Override
	public void onPause() {
		super.onPause();
		
	}
}
