package fr.wetstein.mycv.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import fr.wetstein.mycv.R;
import fr.wetstein.mycv.fragment.ExperienceListFragment;
import fr.wetstein.mycv.fragment.HobbiesFragment;
import fr.wetstein.mycv.fragment.NavDrawerFragment;
import fr.wetstein.mycv.fragment.ProfileFragment;
import fr.wetstein.mycv.fragment.SkillsFragment;


public class HomeActivity extends Activity implements NavDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavDrawerFragment mNavigationDrawerFragment;

    private String[] mArrayTitle;
    /** Used to store the last screen title. For use in {@link #restoreActionBar()}. */
    private CharSequence mTitle;
    private int mCurrentPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mArrayTitle = getResources().getStringArray(R.array.menu_titles);

        setContentView(R.layout.activity_home);

        mNavigationDrawerFragment = (NavDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);

        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        if (mCurrentPosition == position)
            return;

        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new ProfileFragment();
                break;
            case 1:
                break;
            case 2:
                fragment = new ExperienceListFragment();
                break;
            case 3:
                fragment = new SkillsFragment();
                break;
            case 4:
                fragment = new HobbiesFragment();
                break;
            case 5:
                break;
        }

        if (fragment != null) {
            //Update the main content by replacing fragments
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();

            mTitle = mArrayTitle[position];
        } else
            Toast.makeText(this, "En travaux, sera bientôt disponible", Toast.LENGTH_SHORT).show();
    }

//    public void onSectionAttached(int number) {
//        switch (number) {
//            case 1:
//                mTitle = getString(R.string.title_section1);
//                break;
//            case 2:
//                mTitle = getString(R.string.title_section2);
//                break;
//            case 3:
//                mTitle = getString(R.string.title_section3);
//                break;
//        }
//    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen if the drawer is not showing. Otherwise, let the drawer decide what to show in the action bar.
            //getMenuInflater().inflate(R.menu.home, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
