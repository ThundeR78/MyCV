package fr.wetstein.mycv.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Calendar;

import fr.wetstein.mycv.MyCVApp;
import fr.wetstein.mycv.R;

/**
 * Created by ThundeR on 05/07/2014.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = "ProfileFragment";

    private static View view;
    private TextView txtAge;
    private ImageButton btnEmail, btnCall;

    //Google Maps
    private GoogleMap mMap;
    private int mLevelZoom = 10;

    //StreetView
    private StreetViewPanorama mStreetview;

    private Calendar calBirthday;

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
            view = inflater.inflate(R.layout.fragment_profile, container, false);
        } catch (InflateException e) {
        /* map is already there, just return view as it is */
        }
        //View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        ImageView image = (ImageView) view.findViewById(R.id.image);
        txtAge = (TextView) view.findViewById(R.id.profile_age);
        btnEmail = (ImageButton) view.findViewById(R.id.button_email);
        btnCall = (ImageButton) view.findViewById(R.id.button_phone);

        btnEmail.setOnClickListener(this);
        btnCall.setOnClickListener(this);
        //image.setImageBitmap(getHexagonShape(((BitmapDrawable)image.getDrawable()).getBitmap()));

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initMap();
        initStreetView();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        txtAge.setText(getString(R.string.value_age, calculateAge()));
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    private void initMap() {
        //Check if Google Play Services is available on device to display Google Maps
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity().getApplicationContext());
        if (status == ConnectionResult.SUCCESS) {
            if (mMap == null) {
                mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map_fragment)).getMap();

                //Check if map is created successfully or not
                if (mMap != null) {
                    setupMap();
                } else
                    Toast.makeText(getActivity(), "Sorry! unable to create Google Maps", Toast.LENGTH_SHORT).show();
            }
        } else {
            getView().findViewById(R.id.map_fragment).setVisibility(View.GONE);
        }
    }

    private void initStreetView() {
        if (mStreetview == null) {
            mStreetview = ((StreetViewPanoramaFragment) getFragmentManager().findFragmentById(R.id.streetview_fragment)).getStreetViewPanorama();
            if (mStreetview != null) {
                setupStreetView();
            } else
                Toast.makeText(getActivity(), "Sorry! unable to create StreetView Panorama", Toast.LENGTH_SHORT).show();
        }
    }

    /**** The mapfragment's id must be removed from the FragmentManager or else if the same it is passed on the next time then app will crash ****/
    @Override
    public void onDestroyView() {
        super.onDestroyView();

        /*if (mMap != null) {
            Log.v(TAG, "REMOVE GMAPS");
            getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.map_fragment)).commit();
            mMap = null;
        }*/
    }

    private void setupMap() {
        //Change settings Google Maps
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        //Display Home Marker
        displayHome();
    }

    private void setupStreetView() {
        mStreetview.setPanningGesturesEnabled(true);
        mStreetview.setUserNavigationEnabled(true);
        mStreetview.setZoomGesturesEnabled(true);
        mStreetview.setStreetNamesEnabled(true);

        //Display Home Panorama
        mStreetview.setPosition(MyCVApp.HOME_LATLNG);
    }


    //Calculate my age with Date
    private int calculateAge() {
        calBirthday = Calendar.getInstance();
        calBirthday.set(1988, Calendar.DECEMBER, 13);
        Calendar calNow = Calendar.getInstance();

        int age = calNow.get(Calendar.YEAR) - calBirthday.get(Calendar.YEAR);

        if (calNow.get(Calendar.DAY_OF_YEAR) < calBirthday.get(Calendar.DAY_OF_YEAR))
            age--;

        return age;
    }

    private void displayHome() {
        if (mMap != null) {
            //Add Marker
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(MyCVApp.HOME_LATLNG)
                    .title("Julien Wetstein")
                    .snippet("6 rue violet, 75015 Paris")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
            Marker marker = mMap.addMarker(markerOptions);
            marker.showInfoWindow();

            //Move Camera
            CameraPosition cameraPosition = new CameraPosition.Builder().target(MyCVApp.HOME_LATLNG).zoom(mLevelZoom).build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }

    //TEST
    public Bitmap getHexagonShape(Bitmap scaleBitmapImage) {
        int targetWidth = scaleBitmapImage.getWidth();
        int targetHeight = scaleBitmapImage.getHeight();
        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
                targetHeight,Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(targetBitmap);

        Path path = new Path();
        float stdW = targetWidth/2;
        float stdH = targetHeight/2;
        float w3 =stdW / 2;
        float h2 = stdH / 2;
        path.moveTo(0, (float) (h2*Math.sqrt(3)/2));
        path.rLineTo(w3/2, -(float) (h2*Math.sqrt(3)/2)); path.rLineTo(w3, 0);   path.rLineTo(w3/2, (float) (h2*Math.sqrt(3)/2));
        path.rLineTo(-w3/2, (float) (h2*Math.sqrt(3)/2)); path.rLineTo(-w3, 0); path.rLineTo(-w3/2, -(float) (h2*Math.sqrt(3)/2));


        canvas.clipPath(path);
        Bitmap sourceBitmap = scaleBitmapImage;
        canvas.drawBitmap(sourceBitmap,
                new Rect(0, 0, sourceBitmap.getWidth(),
                        sourceBitmap.getHeight()),
                new Rect(0, 0, targetWidth,
                        targetHeight), null);
        return targetBitmap;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_email:
                emailAction(getString(R.string.value_email));
                break;
            case R.id.button_phone:
                callAction(getString(R.string.value_phone));
                break;
        }
    }

    //Send email to the Company
    private void emailAction(String email) {
        if (email != null && !email.isEmpty()) {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("message/rfc822");
            sharingIntent.putExtra(Intent.EXTRA_EMAIL,  new String[]{email});

            startActivity(Intent.createChooser(sharingIntent, getString(R.string.label_send_email)));

        }
    }

    //Make a call with Company
    private void callAction(String phone) {
        if (phone != null && !phone.isEmpty()) {
            phone.trim();
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + phone));
            startActivity(callIntent);
        }
    }
}
