package fr.wetstein.mycv.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import fr.wetstein.mycv.R;

/**
 * Created by ThundeR on 12/07/2014.
 */
public class HobbiesFragment extends Fragment implements TabLayout.OnTabSelectedListener {
    public static final String TAG = "HobbiesFragment";

    private TabLayout mTabLayout;
    private SectionsPagerAdapter mSectionsPagerAdapter;  //Provide mFragments for each of the sections
    private ViewPager mViewPager;    //Host the section contents

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_hobbies, container, false);

        // Set up the ViewPager with the sections adapter
        mViewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        initTabsAdapter();

        mTabLayout = (TabLayout) rootView.findViewById(R.id.sliding_tabs);
        //mTabLayout.setupWithViewPager(mViewPager);
        //mTabLayout.setTabsFromPagerAdapter(mSectionsPagerAdapter);
        mTabLayout.setOnTabSelectedListener(this);

        addTabsWithAdapter();

        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mTabLayout.getTabAt(position).select();
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_list_hobby);
    }

    //Create the adapter with fragments
    private void initTabsAdapter() {
        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());

        mSectionsPagerAdapter.addItem(new MangaFragment(), getString(R.string.title_manga), R.drawable.ic_book_black);
        mSectionsPagerAdapter.addItem(new ParkourFragment(), getString(R.string.title_parkour), R.drawable.ic_directions_run_black);
        mSectionsPagerAdapter.addItem(new GamesFragment(), getString(R.string.title_games), R.drawable.ic_games_black);
        mSectionsPagerAdapter.addItem(new TravelFragment(), getString(R.string.title_travel), R.drawable.ic_public_black);

        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    private void addTabsWithAdapter() {
        if (mTabLayout != null && mSectionsPagerAdapter != null) {
            for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
                //Create Tab with text corresponding to the page title defined by the adapter
                TabLayout.Tab tab = mTabLayout.newTab()
                        .setText(mSectionsPagerAdapter.getPageTitle(i))
                        .setContentDescription(mSectionsPagerAdapter.getPageTitle(i))
                        .setIcon(mSectionsPagerAdapter.getPageIcon(i));

                //Add Tab
                mTabLayout.addTab(tab);
            }
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragments;
        private List<String> mTitles;
        private List<Integer> mIcons;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            mFragments = new ArrayList<Fragment>();
            mTitles = new ArrayList<String>();
            mIcons = new ArrayList<>();
        }

        public void addItem(Fragment myFragment, String title, int icon) {
            mFragments.add(myFragment);
            mTitles.add(title);
            mIcons.add(icon);
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

        public int getPageIcon(int position) {
            return mIcons.get(position);
        }
    }
}
