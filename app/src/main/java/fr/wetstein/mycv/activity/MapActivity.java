package fr.wetstein.mycv.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.wetstein.mycv.MyCVApp;
import fr.wetstein.mycv.R;
import fr.wetstein.mycv.fragment.ExperienceDetailFragment;
import fr.wetstein.mycv.model.Experience;
import fr.wetstein.mycv.model.Place;
import fr.wetstein.mycv.model.School;
import fr.wetstein.mycv.util.FormatValue;

public class MapActivity extends Activity implements GoogleMap.OnInfoWindowClickListener {
	private static final String TAG = "MapActivity";

	public static final String EXTRA_ITEM_LIST_KEY = "ITEM_LIST";

	protected List<Place> listItem;
	protected Place currentItem;

    private GoogleMap mMap;
    private int mLevelZoom = 9;
    private HashMap<Marker, Integer> mapMarker;

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
        if (MyCVApp.isGooglePlayServicesAvailable(this)) {
            if (mMap == null) {
                mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map_fragment)).getMap();

                //Check if map is created successfully or not
                if (mMap != null) {
                    setupMap();
                } else
                    Toast.makeText(this, "Sorry! Unable to create Google Maps", Toast.LENGTH_SHORT).show();
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
        mMap.setOnInfoWindowClickListener(this);

        //Display Markers
        displayItems(listItem);
    }

    private void displayItems(List<Place> items) {
        if (mMap != null && items != null) {
            mapMarker = new HashMap<Marker, Integer>();

            for (int i=0; i<items.size() ;i++) {
                Place item = items.get(i);

                if (item != null && item.latitude != null && item.longitude != null) {
                    String expDuration = null;
                    if (item.dateBegin != null && item.dateEnd != null) {
                        String strDateBegin = FormatValue.monthDateFormat.format(item.dateBegin);
                        String strDateEnd = FormatValue.monthDateFormat.format(item.dateEnd);
                        expDuration = getString(R.string.value_dates, strDateBegin, strDateEnd);
                    }

                    //Add Marker
                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(new LatLng(item.latitude, item.longitude))
                            .title(item.name)
                            .icon(BitmapDescriptorFactory.defaultMarker(item.pin)); //BitmapDescriptorFactory.HUE_
                    if (expDuration != null && !expDuration.isEmpty())
                        markerOptions.snippet(expDuration);

                    Marker marker = mMap.addMarker(markerOptions);
                    //marker.showInfoWindow();

                    mapMarker.put(marker, i);

                    //Add Line
                    if (i > 0) {
                        PolylineOptions lineOptions = new PolylineOptions();
                        lineOptions.add(items.get(i-1).getLatLng()).add(items.get(i).getLatLng()).geodesic(true);
                        lineOptions.color(getResources().getColor(item.color > 0 ? item.color : R.color.red));

                        Polyline polyline = mMap.addPolyline(lineOptions);
                    }

                    //Move Camera
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(48.8534100, 2.3488000)).zoom(mLevelZoom).build();
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }
            }
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        if (mapMarker.containsKey(marker)) {
            int position = mapMarker.get(marker);
            Place item = listItem.get(position);

            if (item != null) {
                //Go to Detail with parameters
                Intent intent = new Intent(this, DetailSliderActivity.class);

                //Check list item class
                if (item instanceof Experience) {
                    intent.putExtra(DetailSliderActivity.FRAGMENT_NAME_KEY, ExperienceDetailFragment.class.getName());
                    List<Experience> listExperience = (List<Experience>)(List<?>) listItem;
                    intent.putParcelableArrayListExtra(DetailSliderActivity.ITEM_LIST_KEY,  (ArrayList<Experience>)listExperience);
                    intent.putExtra(DetailSliderActivity.POSITION_KEY, position);
                } else if (item instanceof School) {

                }

                Bundle extras = new Bundle();
                intent.putExtra(DetailSliderActivity.EXTRAS_BUNDLE_KEY, extras);

                startActivity(intent);
            }
        }
    }
}