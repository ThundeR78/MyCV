package fr.wetstein.mycv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import fr.wetstein.mycv.R;
import fr.wetstein.mycv.adapter.SlidePagerAdapter;
import fr.wetstein.mycv.fragment.DetailFragment;

public class DetailSliderActivity<Item extends Parcelable> extends AppCompatActivity {
	
	private static final String TAG = "DetailSliderActivity";

	public final static String FRAGMENT_NAME_KEY = "FRAGMENT_NAME";
	public static final String ITEM_LIST_KEY = "ITEM_LIST";
	public static final String POSITION_KEY = "POSITION";
	public final static String EXTRAS_BUNDLE_KEY = "EXTRA_BUNDLE";
	public final static String EXTRAS_RESULT_KEY = "EXTRA_RESULT";

	protected SlidePagerAdapter myPagerAdapter;
	protected String fragmentName;
	protected ArrayList<Item> items;
	protected Item currentItem;
	protected int currentIndex;
	protected Bundle extras, results;	
	List<Fragment> fragments;

	protected ViewPager pager;
	
//	protected abstract void updateTitle();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        extras = new Bundle();
        final Intent intent = getIntent();
        if (intent != null) {
            fragmentName = intent.getStringExtra(FRAGMENT_NAME_KEY);
            items = intent.getParcelableArrayListExtra(ITEM_LIST_KEY);
            currentIndex = intent.getIntExtra(POSITION_KEY, 0);
            if (items != null)
                currentItem = items.get(currentIndex);
            if (intent.hasExtra(EXTRAS_BUNDLE_KEY))
                extras = intent.getBundleExtra(EXTRAS_BUNDLE_KEY);
        }

        setContentView(R.layout.activity_slide_detail);

        if (extras != null && results != null)
            extras.putAll(results);

        fragments = new ArrayList<Fragment>();
        if (fragmentName != null) {
            for (int i = 0; i < items.size(); i++) {
                DetailFragment<Item> detailFragment = (DetailFragment<Item>) Fragment.instantiate(this, fragmentName);
                if (extras != null)
                    detailFragment.setArguments(extras);
                if (items != null && items.get(i) != null)
                    detailFragment.setCurrentItem(items.get(i));

                fragments.add(detailFragment);
            }
        } else
            finish();

		this.myPagerAdapter = new SlidePagerAdapter(super.getSupportFragmentManager(), fragments);

		pager = (ViewPager) super.findViewById(R.id.viewpager);
		pager.setAdapter(this.myPagerAdapter);
		pager.setCurrentItem(currentIndex);
		pager.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);		//Stop problem auto scroll when swipe
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
	}
	
	public ViewPager getPager() {
		return pager;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
	    case android.R.id.home:
	    	finish();
	        break;
		}
		return false;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//	    if (requestCode == 1) {
	        if (resultCode == RESULT_OK) {
	        	results = data.getExtras();
	            Item item = data.getParcelableExtra(EXTRAS_RESULT_KEY);
	            if (item != null) {
	            	currentItem = item;
		            DetailFragment<Parcelable> fragment = (DetailFragment<Parcelable>) fragments.get(currentIndex);
		            fragment.setCurrentItem(item);
	            }
	        }
	        if (resultCode == RESULT_CANCELED) {

	        }
//	    }
	}
}