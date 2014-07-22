package fr.wetstein.mycv.adapter;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.List;

public class SlidePagerAdapter extends FragmentPagerAdapter {

	private static final String TAG = "SlidePagerAdapter";
	
	private final List<Fragment> fragments;
	
	public SlidePagerAdapter(FragmentManager fm, List<Fragment> fragments) {
		super(fm);
		
		this.fragments = fragments;
	}
	
	@Override
	public Fragment getItem(int position) {
		return this.fragments.get(position);
	}
	
	@Override
	public int getCount() {
		return this.fragments.size();
	}
	
	public List<Fragment> getFragments() {
		return fragments;
	}
}