package fr.wetstein.mycv.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import fr.wetstein.mycv.R;

/**
 * Created by ThundeR on 12/07/2014.
 */
public class ParkourFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = "ParkourFragment";

    private static final String urlBook = "http://www.amazon.fr/Parkour-David-Belle/dp/2357560258";
    private static final String urlCPK = "https://www.facebook.com/cultureparkour";
    private static final String urlGS = "http://www.gravitystyleofficiel.com/fr/content/4-a-propos";

    private ImageView imgCPK;
    private ImageView imgGS;
    private ImageView imgBookPK;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_parkour, container, false);

        imgCPK = (ImageView) rootView.findViewById(R.id.image_cpk);
        imgGS = (ImageView) rootView.findViewById(R.id.image_gs);
        imgBookPK = (ImageView) rootView.findViewById(R.id.image_pk_book);

        imgCPK.setOnClickListener(this);
        imgGS.setOnClickListener(this);
        imgBookPK.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        String url;
        url = (v.getId() == R.id.image_cpk) ? urlCPK : (v.getId() == R.id.image_gs) ? urlGS : (v.getId() == R.id.image_pk_book) ? urlBook : null;

        if (url != null) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        }
    }
}
