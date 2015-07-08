package fr.wetstein.mycv.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import fr.wetstein.mycv.R;
import fr.wetstein.mycv.view.RemoteImageView;

/**
 * Created by ThundeR on 12/07/2014.
 */
public class MangaFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = "MangaFragment";

    private static final String urlMangaNews = "http://www.manga-news.com/index.php/collection-manga/ThundeR";

    private RemoteImageView imgBanner;
    private Button btnSeeCollection;
    private WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_manga, container, false);

        imgBanner = (RemoteImageView) rootView.findViewById(R.id.image_banner);
        //imgBanner.setImageUrl(urlBanner);

        btnSeeCollection = (Button) rootView.findViewById(R.id.button_see_collection);
        btnSeeCollection.setOnClickListener(this);

        webView = (WebView) rootView.findViewById(R.id.webview);
        setupWebview();

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadWebview();
    }

    //Config WebView
    private void setupWebview() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setBuiltInZoomControls(false);
    }

    private void loadWebview() {
        String html = "<!DOCTYPE html>"+
                "<html lang=\"fr\">"+
                "<head><meta charset=\"utf-8\">" +
                "<style>" +
                "html, body {padding:0; margin:0;}" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<a href=\"http://www.manga-news.com/index.php/membre/ThundeR\" title=\"Ma Collection Manga-News\">" +
                "<img src=\"http://www.manga-news.com/public/sign/ThundeR.png\" alt=\"Ma Collection Manga-News\" width=\"350px\" max-width=\"100%\" height=\"34px\"/>" +
                "</a>" +
                "</body>" +
                "</html>";
        //webView.loadDataWithBaseURL("", html, "text/html", "UTF-8", "");
        webView.loadUrl(urlMangaNews);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_see_collection) {
            webView.setVisibility(View.VISIBLE);
        }
    }
}
