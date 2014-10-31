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

import java.util.Arrays;

import fr.wetstein.mycv.MyCVApp;
import fr.wetstein.mycv.R;

/**
 * Created by ThundeR on 12/07/2014.
 */
public class ParkourFragment extends Fragment implements View.OnClickListener, YouTubePlayer.OnInitializedListener {
    public static final String TAG = "ParkourFragment";

    private static final String urlBook1 = "http://www.amazon.fr/Parkour-David-Belle/dp/2357560258";
    private static final String urlBook2 = "http://book.parkour.center/";
    private static final String urlCPK = "http://www.cultureparkour.com/";
    private static final String urlGS = "http://www.gravitystyleofficiel.com/fr/content/4-a-propos";

    private static final String videoCP2013 = "-veYBCsLRqg";  //"https://www.youtube.com/watch?v=-veYBCsLRqg&list=UUJc-2qElSkLms6655TxEXgg";
    private static final String videoCP2014 = "thLptgEZmT4";  //"https://www.youtube.com/watch?v=thLptgEZmT4&list=UUJc-2qElSkLms6655TxEXgg";

    private static final LatLng CP_LATLNG = new LatLng(48.8028152, 2.3516721);

    private YouTubePlayerFragment ytbPlayerFragment;
    private YouTubePlayer ytbPlayer;
    private ImageView imgCPK, imgGS;
    private ImageView imgBookPK1, imgBookPK2;

    private boolean isFullScreenPlaying = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_parkour, container, false);

        ytbPlayerFragment = (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtubeplayerfragment);
        ytbPlayerFragment.initialize(getString(MyCVApp.DEV_MODE ? R.string.app_dev_key : R.string.app_prod_key), this);

        imgCPK = (ImageView) rootView.findViewById(R.id.image_cpk);
        imgGS = (ImageView) rootView.findViewById(R.id.image_gs);
        imgBookPK1 = (ImageView) rootView.findViewById(R.id.image_pk_book1);
        imgBookPK2 = (ImageView) rootView.findViewById(R.id.image_pk_book2);

        imgCPK.setOnClickListener(this);
        imgGS.setOnClickListener(this);
        imgBookPK1.setOnClickListener(this);
        imgBookPK2.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (ytbPlayerFragment != null) {
            getFragmentManager().beginTransaction().remove(ytbPlayerFragment).commit();
            //ytbPlayerFragment.onDestroyView();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (ytbPlayer != null)
            ytbPlayer.release();
        //if (ytbPlayerFragment != null)
            //ytbPlayerFragment.onDestroy();
    }

    @Override
    public void onClick(View v) {
        String url;
        int id = v.getId();
        url = (id == R.id.image_cpk) ? urlCPK :
                (id == R.id.image_gs) ? urlGS :
                (id == R.id.image_pk_book1) ? urlBook1 :
                (id == R.id.image_pk_book2) ? urlBook2 : null;

        if (url != null) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        ytbPlayer = player;

        //Toast.makeText(getActivity(), "YouTubePlayer.onInitializationSuccess()", Toast.LENGTH_LONG).show();

        //youTubePlayer.setPlayerStateChangeListener(myPlayerStateChangeListener);
        ytbPlayer.setPlaybackEventListener(new YouTubePlayer.PlaybackEventListener() {
            @Override
            public void onPlaying() {
            }
            @Override
            public void onPaused() {
            }
            @Override
            public void onStopped() {
            }
            @Override
            public void onBuffering(boolean isBuffering) {
            }
            @Override
            public void onSeekTo(int newPositionMillis) {
            }
        });

        //TODO : Allow fullScreen
        ytbPlayer.setShowFullscreenButton(false);
        ytbPlayer.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
            @Override
            public void onFullscreen(boolean isFullScreen) {
                isFullScreenPlaying = isFullScreen;
            }
        });

        if (!wasRestored) {
            player.cueVideos(Arrays.asList(videoCP2014, videoCP2013));
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

    /*@Override
    public void onBackPressed() {
        if (isFullScreenPlaying){
            ytbPlayer.pause();
            ytbPlayer.setFullscreen(false);
        }
        super.onBackPressed();
    }*/
}
