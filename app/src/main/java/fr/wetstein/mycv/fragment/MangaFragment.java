package fr.wetstein.mycv.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import fr.wetstein.mycv.R;

/**
 * Created by ThundeR on 12/07/2014.
 */
public class MangaFragment extends Fragment {
    public static final String TAG = "MangaFragment";

    private WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_manga, container, false);

        webView = (WebView) rootView.findViewById(R.id.webview);
        setupWebview();

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadWebview();
    }

    private void setupWebview() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setBuiltInZoomControls(false);
    }

    private void loadWebview() {
        String html = "<a href=\"http://www.manga-news.com/index.php/membre/ThundeR\" title=\"Ma Collection Manga-News\">" +
            "<img src=\"http://www.manga-news.com/public/sign/ThundeR.png\" alt=\"Ma Collection Manga-News\" width=\"350px\" height=\"34px\"/>" +
             "</a>";
        webView.loadDataWithBaseURL("", html, "text/html", "UTF-8", "");
    }
}
