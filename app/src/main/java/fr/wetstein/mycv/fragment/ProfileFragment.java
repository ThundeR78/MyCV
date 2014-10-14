package fr.wetstein.mycv.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

import fr.wetstein.mycv.MyCVApp;
import fr.wetstein.mycv.R;

/**
 * Created by ThundeR on 05/07/2014.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = "ProfileFragment";

    public static final String EXTRA_QUALITIES = "QUALITIES";
    public static final String EXTRA_SHORTCOMINGS = "SHOTCOMINGS";

    private static View view;
    private TextView txtAge;
    private Button btnMemoire;
    private ImageButton btnEmail, btnCall;
    private TableLayout tblPersonnality;

    //Google Maps
    private GoogleMap mMap;
    private int mLevelZoom = 10;

    //StreetView
    private StreetViewPanorama mStreetview;

    private Calendar calBirthday;
    private String[] arrayQuality;
    private String[] arrayShortcoming;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
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
            /* Map is already there, just return view as it is */
        }
        //View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        ImageView image = (ImageView) view.findViewById(R.id.image);
        txtAge = (TextView) view.findViewById(R.id.profile_age);
        btnEmail = (ImageButton) view.findViewById(R.id.button_email);
        btnCall = (ImageButton) view.findViewById(R.id.button_phone);
        btnMemoire = (Button) view.findViewById(R.id.button_read_memoire);
        tblPersonnality = (TableLayout) view.findViewById(R.id.profile_table);

        btnEmail.setOnClickListener(this);
        btnCall.setOnClickListener(this);
        btnMemoire.setOnClickListener(this);
        //image.setImageBitmap(getHexagonShape(((BitmapDrawable)image.getDrawable()).getBitmap()));

        txtAge.setText(getString(R.string.value_age, calculateAge()));

        if (savedInstanceState != null) {
            arrayQuality = savedInstanceState.getStringArray(EXTRA_QUALITIES);
            arrayShortcoming = savedInstanceState.getStringArray(EXTRA_SHORTCOMINGS);
        }

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

        displayTable();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putStringArray(EXTRA_QUALITIES, arrayQuality);
        outState.putStringArray(EXTRA_SHORTCOMINGS, arrayShortcoming);
    }

    private void initMap() {
        if (MyCVApp.isGooglePlayServicesAvailable(getActivity())) {
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
        if (MyCVApp.isGooglePlayServicesAvailable(getActivity())) {
            if (mStreetview == null) {
                mStreetview = ((StreetViewPanoramaFragment) getFragmentManager().findFragmentById(R.id.streetview_fragment)).getStreetViewPanorama();
                if (mStreetview != null) {
                    setupStreetView();
                } else
                    Toast.makeText(getActivity(), "Sorry! unable to create StreetView Panorama", Toast.LENGTH_SHORT).show();
            }
        } else {
            getView().findViewById(R.id.streetview_fragment).setVisibility(View.GONE);
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
        mMap.getUiSettings().setAllGesturesEnabled(true);
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

    private void displayTable() {
        if (tblPersonnality.getChildCount() <= 1) {
            arrayQuality = getResources().getStringArray(R.array.array_qualities);
            arrayShortcoming = getResources().getStringArray(R.array.array_shortcomings);
            int maxSize = (arrayQuality.length >= arrayShortcoming.length) ? arrayQuality.length : arrayShortcoming.length;

            for (int i=0; i < maxSize ;i++) {
                String quality = (arrayQuality.length > 0 && arrayQuality.length > i) ? arrayQuality[i] : "";
                String shortcoming = (arrayShortcoming.length > 0 && arrayShortcoming.length > i) ? arrayShortcoming[i] : "";

                TableRow row = new TableRow(getActivity());
                TextView txtQuality = new TextView(getActivity());
                TextView txtShortcoming = new TextView(getActivity());

                txtQuality.setText(quality);
                txtShortcoming.setText(shortcoming);

                row.addView(txtQuality);
                row.addView(txtShortcoming);

                adjustTableColumn(txtQuality, true);
                adjustTableColumn(txtShortcoming, false);

                tblPersonnality.addView(row);
            }
        }
    }

    private TextView adjustTableColumn(TextView text, boolean paddingLeft){
        int borderSize = (int) getResources().getDimension(R.dimen.table_border_size);
        int paddingSize = (int) getResources().getDimension(R.dimen.table_padding_size);

        TableRow.LayoutParams params = (TableRow.LayoutParams) text.getLayoutParams();
        params.setMargins(paddingLeft ? borderSize : 0, 0, borderSize, borderSize);
        params.width = 0;
        params.weight = 1;
        params.height = TableRow.LayoutParams.MATCH_PARENT;
        text.setLayoutParams(params);
        text.setPadding(paddingSize, paddingSize, paddingSize, paddingSize);
        text.setBackgroundColor(getResources().getColor(R.color.table_row_background));

        return text;
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
            case R.id.button_read_memoire:
                readPDFAction("memoire.pdf");
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

    private void readPDFAction(String fileName) {
        //TODO : copy file into device to read it
        File filePdf = new File("/sdcard/"+fileName);
        if (!filePdf.exists())
            CopyAssetsFile(fileName);

        File file = new File("/sdcard/"+fileName);

        //File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +"/"+ fileName);
        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setDataAndType(Uri.fromFile(file), "application/pdf");
        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        Intent intent = Intent.createChooser(target, "Open File");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // Instruct the user to install a PDF reader here, or something
            Toast.makeText(getActivity(), "NO Pdf Viewer", Toast.LENGTH_SHORT).show();
        }
    }

    private void CopyAssetsFile(String fileName) {
        AssetManager assetManager = getActivity().getAssets();
        String[] files = null;
        try {
            files = assetManager.list("");
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }

        for (int i=0; i<files.length; i++) {
            String fStr = files[i];
            if (fStr.equalsIgnoreCase(fileName)) {
                InputStream in = null;
                OutputStream out = null;
                try {
                    in = assetManager.open(files[i]);
                    out = new FileOutputStream("/sdcard/" + files[i]);
                    copyFile(in, out);
                    in.close();
                    in = null;
                    out.flush();
                    out.close();
                    out = null;
                    break;
                } catch(Exception e) {
                    Log.e("tag", e.getMessage());
                }
            }
        }
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }
}
