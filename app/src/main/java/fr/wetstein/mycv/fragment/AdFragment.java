package fr.wetstein.mycv.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import fr.wetstein.mycv.R;

public class AdFragment extends Fragment {

    private AdView mAdView;

    public AdFragment() {
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdOpened() {
                // Enregistrement de l'état de l'application avant d'accéder à l'information affichée en superposition sur une annonce.
            }
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }
            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);
            }
            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ad, container, false);

        mAdView = (AdView) rootView.findViewById(R.id.adView);

        // Create an ad request. Check logcat output for the hashed device ID to get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                //.addTestDevice("7F0F0A263A5D79B8F6F55D7CC62C4230")
                .setGender(AdRequest.GENDER_MALE)
                //.addKeyword("manga")
                .build();

        // Start loading the ad in the background.
        mAdView.loadAd(adRequest);

        return rootView;
    }

    /** Called when leaving the activity */
    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    /** Called when returning to the activity */
    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    /** Called before the activity is destroyed */
    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

}
