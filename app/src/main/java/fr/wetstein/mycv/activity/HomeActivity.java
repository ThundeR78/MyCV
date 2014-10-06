package fr.wetstein.mycv.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import fr.sophiacom.ynp.androidlib.YNPClient;
import fr.wetstein.mycv.MyCVApp;
import fr.wetstein.mycv.R;
import fr.wetstein.mycv.fragment.ExperienceListFragment;
import fr.wetstein.mycv.fragment.GuestbookFragment;
import fr.wetstein.mycv.fragment.HobbiesFragment;
import fr.wetstein.mycv.fragment.NavDrawerFragment;
import fr.wetstein.mycv.fragment.NewsListFragment;
import fr.wetstein.mycv.fragment.ProfileFragment;
import fr.wetstein.mycv.fragment.SkillsFragment;
import fr.wetstein.mycv.fragment.StudyListFragment;
import fr.wetstein.mycv.util.Actions;
import fr.wetstein.mycv.util.PrefsManager;


public class HomeActivity extends Activity implements NavDrawerFragment.NavigationDrawerCallbacks {
    public static final String TAG = "HomeActivity";

    public static final String EXTRA_CONTENT = "CONTENT";

    //private Bundle mFragmentExtras;

    /** Fragment managing the behaviors, interactions and presentation of the navigation drawer */
    private NavDrawerFragment mNavigationDrawerFragment;

    private String[] mArrayTitle;
    /** Used to store the last screen title. For use in {@link #restoreActionBar()}. */
    private CharSequence mTitle;
    private int mCurrentPosition = 0;
    private long mCurrentId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*if (savedInstanceState != null) {
            //Restore the fragment's instance
            mContent = getFragmentManager().getFragment(savedInstanceState, EXTRA_CONTENT);
        }*/

        mArrayTitle = getResources().getStringArray(R.array.menu_titles);

        setContentView(R.layout.activity_home);

        mNavigationDrawerFragment = (NavDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);

        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Register app on YNP services
        if (YNPClient.isThisDeviceSupported(this)) {
            YNPClient.registerApp(this);
            String installId = YNPClient.getServerRegistrationId(this);

            if (installId != null) {
                SharedPreferences.Editor editor = PrefsManager.getPreferencesEditor(this);
                editor.putString(PrefsManager.PREF_INSTALL_ID, installId);
                editor.commit();
                Log.v(TAG, "YNP INSTALL ID = " + installId);
            } else
                Log.e(TAG, "YNP INSTALL ID = NO ID");

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Save the fragment's instance
        //getFragmentManager().putFragment(outState, EXTRA_CONTENT, mContent);
    }

    @Override
    public void onNavigationDrawerItemSelected(int id, int position) {
        Fragment fragment = null;
        switch (id) {
            case R.id.item_profile:
                fragment = new ProfileFragment();
                break;
            case R.id.item_news:
                fragment = new NewsListFragment();
                break;
            case R.id.item_studies:
                fragment = new StudyListFragment();
                break;
            case R.id.item_career:
                fragment = new ExperienceListFragment();
                break;
            case R.id.item_skills:
                fragment = new SkillsFragment();
                break;
            case R.id.item_hobbies:
                fragment = new HobbiesFragment();
                break;
            case R.id.item_guestbook:
                fragment = new GuestbookFragment();
                break;
            default:
                fragment = new ProfileFragment();
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
            getMenuInflater().inflate(R.menu.home, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int itemId = item.getItemId();
        if (itemId == R.id.action_contact) {
            String senderTitle = getString(R.string.contact_email_title);
            String[] emailTO = new String[] { getString(R.string.contact_email_to) };
            String[] emailCC = null, emailBC = null;
            String subject = getString(R.string.contact_email_subject);
            String text = getString(R.string.contact_email_body, MyCVApp.getAppVersion(getApplicationContext()), Build.BRAND+" "+Build.MODEL, Build.VERSION.RELEASE);
            boolean isHTMLFormat = true;
            //Send an email
            Actions.initShareIntent(this, "plain/text", senderTitle, emailTO, emailCC, emailBC, subject, text, isHTMLFormat);
            return true;
        } else
        if (itemId == R.id.action_rate_app) {
            //Go to Store to rate the app
            Actions.rateApp(this);
            return true;
        } else
        if (itemId == R.id.action_share_app) {
            //Share message
            Actions.shareApp(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
