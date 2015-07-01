package fr.wetstein.mycv.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.vending.billing.IabHelper;
import com.android.vending.billing.IabResult;
import com.android.vending.billing.Inventory;
import com.android.vending.billing.Purchase;
import com.android.vending.billing.SkuDetails;

import java.util.ArrayList;
import java.util.List;

import fr.sophiacom.ynp.androidlib.YNPClient;
import fr.wetstein.mycv.MyCVApp;
import fr.wetstein.mycv.R;
import fr.wetstein.mycv.fragment.ExperienceListFragment;
import fr.wetstein.mycv.fragment.GuestbookFragment;
import fr.wetstein.mycv.fragment.HobbiesFragment;
import fr.wetstein.mycv.fragment.NavDrawerFragment;
import fr.wetstein.mycv.fragment.NewsListFragment;
import fr.wetstein.mycv.fragment.ProfileFragment;
import fr.wetstein.mycv.fragment.SkillListFragment;
import fr.wetstein.mycv.fragment.StudyListFragment;
import fr.wetstein.mycv.util.Actions;
import fr.wetstein.mycv.util.PrefsManager;


public class HomeActivity extends Activity implements NavDrawerFragment.NavigationDrawerCallbacks {
    public static final String TAG = "HomeActivity";

    public static final String EXTRA_CONTENT = "CONTENT";

    //private Bundle mFragmentExtras;

    //IAP
    private IabHelper mHelper;
    private static final int RC_REQUEST = 10001;

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

        initIAP();
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
    public void onDestroy() {
        super.onDestroy();

        if (mHelper != null) {
            mHelper.dispose();
            mHelper = null;
        }
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
                fragment = new SkillListFragment();
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
            getMenuInflater().inflate(R.menu.menu_home, menu);
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
        } else
        if (itemId == R.id.action_donate) {
            displayDonationList();
        }

        return super.onOptionsItemSelected(item);
    }

    private void initIAP() {
        String base64EncodedPublicKey = getString(R.string.app_iap_key);
        Log.d(TAG, "Creating IAB helper.");
        mHelper = new IabHelper(this, base64EncodedPublicKey);

        //Enable debug logging (false for production app)
        mHelper.enableDebugLogging(MyCVApp.DEV_MODE);

        Log.d(TAG, "Starting setup.");
        //Start setup. This is asynchronous and the specified listener will be called once setup completes.
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                Log.d(TAG, "Setup finished.");

                if (!result.isSuccess()) {
                    Toast.makeText(getApplicationContext(), getString(R.string.in_app_bill_error) + result, Toast.LENGTH_LONG).show();
                    return;
                }

                //Have we been disposed of in the meantime? If so, quit.
                if (mHelper == null)
                    return;

                //IAB is fully set up. Now, let's get an inventory of stuff we own.
                // --commented out here as we didn't need it for donation purposes.
                Log.d(TAG, "Setup successful. Querying inventory.");
//                mHelper.queryInventoryAsync(mGotInventoryListener);

                List<String> moreSkus = new ArrayList<String>();
                moreSkus.add("donate_tiny");
                moreSkus.add("donate_medium");
                moreSkus.add("donate_xlarge");
                mHelper.queryInventoryAsync(true, moreSkus, mGotInventoryListener);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);
        if (mHelper == null)
            return;

        //Pass on the activity result to the helper for handling
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            //Not handled, so handle it ourselves (here's where you'd perform any handling of activity results not related to in-app billing...
            super.onActivityResult(requestCode, resultCode, data);
        } else {
            Log.d(TAG, "onActivityResult handled by IABUtil.");
        }
    }

    private void displayDonationList() {
        final Activity activity = this;
        int selectedItem = 0;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.iap_choose_donation)
            .setIcon(R.drawable.ic_action_good)
            .setCancelable(true)
            .setSingleChoiceItems(getResources().getStringArray(R.array.iap_donation_array), selectedItem, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            })
            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String payload = "";
                    int selectedIndex = ((AlertDialog)dialog).getListView().getCheckedItemPosition();
                    String iapId = getString((selectedIndex == 0) ? R.string.iap_donate_tiny_id :
                            (selectedIndex == 1) ? R.string.iap_donate_small_id :
                                    (selectedIndex == 2) ? R.string.iap_donate_medium_id :
                                            (selectedIndex == 3) ? R.string.iap_donate_large_id :
                                                    (selectedIndex == 4) ? R.string.iap_donate_xlarge_id : 0);
                    //Toast.makeText(getApplicationContext(), selectedIndex+" "+iapId, Toast.LENGTH_LONG).show();
                    mHelper.launchPurchaseFlow(activity, iapId, RC_REQUEST, mPurchaseFinishedListener, payload);
                    dialog.dismiss();
                }
            })
            .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

        Dialog dialog = builder.create();
        dialog.show();
    }

    /** Verifies the developer payload of a purchase. */
    boolean verifyDeveloperPayload(Purchase p) {
        String payload = p.getDeveloperPayload();

        /**Follow google guidelines to create your own payload string here, in case it is needed.
         *Remember it is recommended to store the keys on your own server for added protection
         USE as necessary*/

        return true;
    }

    //Callback when Setup is finished
    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            // Is it a failure?
            if (result.isFailure()) {
                Log.e(TAG, "Failed to query inventory: " + result);
                return;
            }

            Log.d(TAG, "Query inventory was successful.");

            /* Check for items we own. Notice that for each purchase, we check
             * the developer payload to see if it's correct! See verifyDeveloperPayload(). */

            List<SkuDetails> listSku = inventory.getAllSku();
            Toast.makeText(getApplicationContext(), "NB SKU AVAILABLE = "+(listSku != null ? listSku.size() : 0)+"/3", Toast.LENGTH_LONG).show();
            Log.v("NB SKU", "SKUS AVAILABLE = "+ (listSku != null ? listSku.size() : 0));

            if (listSku != null && listSku.size() > 0) {
                String text = "";
                for (SkuDetails sku : listSku) {
                    Purchase purchase = inventory.getPurchase(sku.getSku());
                    if (purchase != null)
                        text += "Purchase :\nSignature="+purchase.getSignature()+" - Payload="+purchase.getDeveloperPayload()+"\n";
                    text += "Type="+sku.getType()+"\n";
                    text += sku.toString() + " \n\n";
                }
                String senderTitle = "IAP Test";
                String[] emailTO = new String[] { "jwetstein@sophiacom.fr" };
                String[] emailCC = null, emailBC = null;
                String subject = "IAP SkuDetails";
                boolean isHTMLFormat = false;
                Actions.initShareIntent(HomeActivity.this, "plain/text", senderTitle, emailTO, emailCC, emailBC, subject, text, isHTMLFormat);

                /*SkuDetails sku = listSku.get(0);
                if (sku != null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                    builder.setTitle("SkuDetails")
                            .setCancelable(false)
                            .setMessage("Title=" + sku.getTitle() + " - " +
                                    "Sku=" + sku.getSku() + " - " +
                                    "Type=" + sku.getType() + " - " +
                                    "Price=" + sku.getPrice() + " - " +
                                    "Desc=" + sku.getDescription() + " - \n" +
                                    "Json=" + sku.toString());

                    Dialog dialog = builder.create();
                    dialog.show();
                }*/
            }

            /* // Check Purchase Premium
            Purchase premiumPurchase = inventory.getPurchase(SKU_PREMIUM);
            mIsPremium = (premiumPurchase != null && verifyDeveloperPayload(premiumPurchase));
            Log.d(TAG, "User is " + (mIsPremium ? "PREMIUM" : "NOT PREMIUM"));

            // Check Subscription
            Purchase subscribePurchase = inventory.getPurchase(SKU_SUBSCRIPTION);
            mIsSubscribed = (subscribePurchase != null && verifyDeveloperPayload(subscribePurchase));
            Log.d(TAG, "User " + (mIsSubscribed ? "HAS" : "DOES NOT HAVE") + " a subscription.");

            // Check Consume Purchase
            Purchase consumePurchase = inventory.getPurchase(SKU_CONSUME);
            if (consumePurchase != null && verifyDeveloperPayload(consumePurchase)) {
                Log.d(TAG, "Consuming it.");
                mHelper.consumeAsync(inventory.getPurchase(SKU_CONSUME), mConsumeFinishedListener);
                return;
            }*/

            Log.d(TAG, "Initial inventory query finished; enabling main UI.");
        }
    };

    // Callback for when a purchase is finished
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            Log.d(TAG, "Purchase finished: " + result + ", purchase: " + purchase);

            if (mHelper == null)
                return;

            if (result.isFailure()) {
                Toast.makeText(getApplicationContext(), getString(R.string.purchase_error) + result, Toast.LENGTH_LONG).show();
                // setWaitScreen(false);
                return;
            }
            if (!verifyDeveloperPayload(purchase)) {
                Toast.makeText(getApplicationContext(), getString(R.string.error_verification), Toast.LENGTH_LONG).show();
                // setWaitScreen(false);
                return;
            }

            Log.d(TAG, "Purchase successful.");

            if (purchase != null) {
                String senderTitle = "IAP Test";
                String[] emailTO = new String[] { "jwetstein@sophiacom.fr" };
                String[] emailCC = null, emailBC = null;
                String subject = "IAP Purchase";
                String text = purchase.toString();
                text += "\nSku="+purchase.getSku()+"\nOrderId="+purchase.getOrderId()+"\nType="+purchase.getItemType()+"\nSignature="+purchase.getSignature()+"\nPayload="+purchase.getDeveloperPayload();
                boolean isHTMLFormat = false;
                Actions.initShareIntent(HomeActivity.this, "plain/text", senderTitle, emailTO, emailCC, emailBC, subject, text, isHTMLFormat);

                /*AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setTitle("Purchase")
                        .setCancelable(false)
                        .setMessage("Package=" + purchase.getPackageName() + " - " +
                                "Sku=" + purchase.getSku() + " - " +
                                "ItemType=" + purchase.getItemType() + " - " +
                                "OrderId=" + purchase.getOrderId() + " - " +
                                "PurchaseState=" + purchase.getPurchaseState() + " - " +
                                "PurchaseTime=" + purchase.getPurchaseTime() + " - " +
                                "Signature=" + purchase.getSignature() + " - " +
                                "Token=" + purchase.getToken() + " - " +
                                "Payload=" + purchase.getDeveloperPayload()+ " \n" +
                                "Json=" + purchase.toString());

                Dialog dialog = builder.create();
                dialog.show();*/
            }

            if (purchase.getSku().equals(getString(R.string.iap_donate_tiny_id))
                    || purchase.getSku().equals(getString(R.string.iap_donate_small_id))
                    || purchase.getSku().equals(getString(R.string.iap_donate_medium_id))
                    || purchase.getSku().equals(getString(R.string.iap_donate_large_id))
                    || purchase.getSku().equals(getString(R.string.iap_donate_xlarge_id))) {
                Log.d(TAG, "Purchase donation Ok");
                mHelper.consumeAsync(purchase, mConsumeFinishedListener);
            }
        }
    };

    // Called when consumption is complete
    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
        public void onConsumeFinished(Purchase purchase, IabResult result) {
            Log.d(TAG, "Consumption finished. Purchase: " + purchase + ", result: " + result);

            if (mHelper == null)
                return;

            //Check which SKU is consumed here and then proceed
            if (result.isSuccess()) {
                Log.d(TAG, "Consumption successful. Provisioning.");
                Toast.makeText(getApplicationContext(), getString(R.string.thank_you), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.error_consume) + result, Toast.LENGTH_LONG).show();
            }

            Log.d(TAG, "End consumption flow.");
        }
    };
}
