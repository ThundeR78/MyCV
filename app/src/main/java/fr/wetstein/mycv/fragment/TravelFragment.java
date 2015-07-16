package fr.wetstein.mycv.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import fr.wetstein.mycv.MyCVApp;
import fr.wetstein.mycv.R;
import fr.wetstein.mycv.model.Place;
import fr.wetstein.mycv.model.Travel;
import fr.wetstein.mycv.parser.xml.TravelParser;
import fr.wetstein.mycv.util.FormatValue;

/**
 * Created by ThundeR on 12/07/2014.
 */
public class TravelFragment extends Fragment implements GoogleMap.OnInfoWindowClickListener {
    public static final String TAG = "TravelFragment";

    //Google Maps
    private GoogleMap mMap;
    private int mLevelZoom = 10;
    private static View view;
    private HashMap<Marker, Travel> mapMarker;

    private List<Travel> listTravel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null)
            return null;

        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.fragment_travel, container, false);
        } catch (InflateException e) {
            /* Map is already there, just return view as it is */
        }


        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listTravel = TravelParser.loadTravels(getActivity());

        initMap();
    }

    //Init Map
    private void initMap() {
        if (MyCVApp.isGooglePlayServicesAvailable(getActivity())) {
            if (mMap == null) {
                SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_travel_fragment);
                if (mapFragment != null)
                    mMap = mapFragment.getMap();

                //Check if map is created successfully or not
                if (mMap != null) {
                    setupMap();
                } else
                    Toast.makeText(getActivity(), "Sorry! unable to create Google Maps", Toast.LENGTH_SHORT).show();
            }
        } else {
            getView().findViewById(R.id.map_travel_fragment).setVisibility(View.GONE);
        }
    }

    //Setup Map
    private void setupMap() {
        //Change settings Google Maps
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setAllGesturesEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        //Position camera
        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(48.8534100, 2.3488000)).zoom(mMap.getMinZoomLevel()).build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        //Display Travels
        displayItems(listTravel);
    }

    private void displayItems(List<Travel> items) {
        if (mMap != null && items != null) {
            //mapMarker = new HashMap<Marker, Travel>();

            //Loop Travels
            for (int i=0; i<items.size() ;i++) {
                Travel travel = items.get(i);

                if (travel != null && travel.listPlace != null) {
                    //Loop Places
                    for (int j = 0; j < travel.listPlace.size(); j++) {
                        Place item = travel.listPlace.get(j);
                        if (item != null && item.latitude != null && item.longitude != null) {
                            String expDuration = null;
                            Date dateBegin = null, dateEnd = null;

                            if (item.dateBegin != null && item.dateEnd != null) {
                                dateBegin = item.dateBegin;
                                dateEnd = item.dateEnd;
                            } else if (travel.dateBegin != null && travel.dateEnd != null) {
                                dateBegin = travel.dateBegin;
                                dateEnd = travel.dateEnd;
                            }

                            if (dateBegin != null && dateEnd != null) {
                                String strDateBegin = FormatValue.monthDateFormat.format(dateBegin);
                                String strDateEnd = FormatValue.monthDateFormat.format(dateEnd);
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

                            //mapMarker.put(marker, item);

                            //Add Line
                            if (j > 0) {
                                PolylineOptions lineOptions = new PolylineOptions();
                                lineOptions.add(travel.listPlace.get(j - 1).getLatLng()).add(travel.listPlace.get(j).getLatLng()).geodesic(true);
                                lineOptions.color(getResources().getColor(item.color > 0 ? item.color : R.color.red));

                                Polyline polyline = mMap.addPolyline(lineOptions);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        if (mapMarker.containsKey(marker)) {
            //Place item = mapMarker.get(marker);
        }
    }
}
