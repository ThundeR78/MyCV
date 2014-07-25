package fr.wetstein.mycv.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import fr.wetstein.mycv.R;
import fr.wetstein.mycv.model.Place;
import fr.wetstein.mycv.util.FormatValue;

public class MapActivity extends Activity {
	private static final String TAG = "MapActivity";

	public static final String EXTRA_ITEM_LIST_KEY = "ITEM_LIST";

	protected List<Place> listItem;
	protected Place currentItem;

    private GoogleMap mMap;
    private int mLevelZoom = 10;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map);

		final Intent intent = getIntent();
		if (intent != null) {
            listItem = intent.getParcelableArrayListExtra(EXTRA_ITEM_LIST_KEY);
            Log.v(TAG, listItem.toString());
        }

		getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        initMap();
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

    private void initMap() {
        //Check if Google Play Services is available on device to display Google Maps
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        if (status == ConnectionResult.SUCCESS) {
            if (mMap == null) {
                mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map_fragment)).getMap();

                //Check if map is created successfully or not
                if (mMap != null) {
                    setupMap();
                } else
                    Toast.makeText(this, "Sorry! unable to create Google Maps", Toast.LENGTH_SHORT).show();
            }
        } else {
            findViewById(R.id.map_fragment).setVisibility(View.GONE);
        }
    }

    private void setupMap() {
        //Change settings Google Maps
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        //Display Home Marker
        displayItems(listItem);
    }

    private void displayItems(List<Place> items) {
        if (mMap != null && items != null) {
            for (Place item : items) {
                if (item != null && item.latitude != null && item.longitude != null) {
                    String expDuration = "";
                    if (item.dateBegin != null && item.dateEnd != null) {
                        String strDateBegin = FormatValue.monthDateFormat.format(item.dateBegin);
                        String strDateEnd = FormatValue.monthDateFormat.format(item.dateEnd);
                        expDuration = getString(R.string.value_dates, strDateBegin, strDateEnd)+"\n";
                    }

                    //Add Marker
                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(new LatLng(item.latitude, item.longitude))
                            .title(item.name)
                            .snippet(expDuration + item.address)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
                    Marker marker = mMap.addMarker(markerOptions);
                    //marker.showInfoWindow();

                    //Move Camera
                    //CameraPosition cameraPosition = new CameraPosition.Builder().target(MyCVApp.HOME_LATLNG).zoom(mLevelZoom).build();
                    //mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }
            }
        }
    }
}