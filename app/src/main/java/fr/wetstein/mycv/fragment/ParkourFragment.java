package fr.wetstein.mycv.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

import fr.wetstein.mycv.MyCVApp;
import fr.wetstein.mycv.R;

/**
 * Created by ThundeR on 12/07/2014.
 */
public class ParkourFragment extends Fragment implements View.OnClickListener, YouTubePlayer.OnInitializedListener {
    public static final String TAG = "ParkourFragment";

    private static final String urlBook = "http://www.amazon.fr/Parkour-David-Belle/dp/2357560258";
    private static final String urlCPK = "http://www.cultureparkour.com/";
    private static final String urlGS = "http://www.gravitystyleofficiel.com/fr/content/4-a-propos";

    private static final String videoCP2013 = "-veYBCsLRqg";  //"https://www.youtube.com/watch?v=-veYBCsLRqg&list=UUJc-2qElSkLms6655TxEXgg";
    private static final String videoCP2014 = "thLptgEZmT4";  //"https://www.youtube.com/watch?v=thLptgEZmT4&list=UUJc-2qElSkLms6655TxEXgg";

    private static final LatLng CP_LATLNG = new LatLng(48.8028152, 2.3516721);

    private YouTubePlayerFragment ytbPlayerFragment;
    private YouTubePlayer ytbPlayer;
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

        ytbPlayerFragment = (YouTubePlayerFragment)getFragmentManager().findFragmentById(R.id.youtubeplayerfragment);
        ytbPlayerFragment.initialize(getString(MyCVApp.DEV_MODE ? R.string.android_dev_key : R.string.android_prod_key), this);

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

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        ytbPlayer = player;

        Toast.makeText(getActivity(), "YouTubePlayer.onInitializationSuccess()", Toast.LENGTH_LONG).show();

        //youTubePlayer.setPlayerStateChangeListener(myPlayerStateChangeListener);
        //youTubePlayer.setPlaybackEventListener(myPlaybackEventListener);

        if (!wasRestored) {
            player.cueVideo(videoCP2013);
            player.cueVideo(videoCP2014);
        }

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult result) {
        if (result.isUserRecoverableError()) {
            result.getErrorDialog(getActivity(), 1).show();
        } else {
            Toast.makeText(getActivity(), "YouTubePlayer.onInitializationFailure(): " + result.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
