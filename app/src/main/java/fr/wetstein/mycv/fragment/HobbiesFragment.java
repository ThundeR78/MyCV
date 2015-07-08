package fr.wetstein.mycv.fragment;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import fr.wetstein.mycv.R;

/**
 * Created by ThundeR on 12/07/2014.
 */
public class HobbiesFragment extends Fragment implements ActionBar.TabListener {
    public static final String TAG = "HobbiesFragment";

    public SectionsPagerAdapter mSectionsPagerAdapter;  //Provide mFragments for each of the sections
    public ViewPager mViewPager;    //Host the section contents
    public List<ActionBar.Tab> mTabs;  //Contain tabs

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Create Tabs
        initTabs();
        addTabs();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_hobbies, container, false);

        // Set up the ViewPager with the sections adapter
        mViewPager = (ViewPager) rootView.findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                //actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
                //actionBar.setSelectedNavigationItem(position);
                mTabs.get(position).select();
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public void initTabs() {
        //Create the adapter with fragments
        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());

        mSectionsPagerAdapter.addItem(new MangaFragment(), getString(R.string.title_manga));
        mSectionsPagerAdapter.addItem(new ParkourFragment(), getString(R.string.title_parkour));
        mSectionsPagerAdapter.addItem(new GamesFragment(), getString(R.string.title_games));
        mSectionsPagerAdapter.addItem(new TravelFragment(), getString(R.string.title_travel));
    }

    public void addTabs() {
        ActionBar actionBar = getActivity().getActionBar();
        mTabs = new ArrayList<ActionBar.Tab>();

        //For each of the sections in the app, add a tab to the action bar
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            //Create Tab with text corresponding to the page title defined by the adapter
            ActionBar.Tab tab = actionBar.newTab()
                .setText(mSectionsPagerAdapter.getPageTitle(i))
                .setTabListener(this);

            //Add Tab
            mTabs.add(tab);
            actionBar.addTab(tab);
        }
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragments;
        private List<String> mTitles;


        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            mFragments = new ArrayList<Fragment>();
            mTitles = new ArrayList<String>();

        }

        public void addItem(Fragment myFragment, String title) {
            mFragments.add(myFragment);
            mTitles.add(title);
        }


        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }
    }
}
