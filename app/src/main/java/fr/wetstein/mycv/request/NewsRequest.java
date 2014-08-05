package fr.wetstein.mycv.request;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import fr.wetstein.mycv.util.PrefsManager;

/**
 * Created by ThundeR on 04/08/2014.
 */
public class NewsRequest extends RequestManager {
    public static final String TAG = "NewsRequest";

    private static final String BASE_URL_YNPNEWS = "https://mobile.youandpush.com/";
    private static final String PREFIX_URL = "v1/";
    private static final String URL_APP = "application/";
    private static final String URL_INSTALL = "/installation/";
    private static final String URL_STREAM = "/stream/";
    private static final String URL_ITEMS = "/items";

    private static String appId = "uut9ogl8j1ofvhc6";
    private static String installId = "";
    private static String streamId = "bpv2tn6293sru8oj";

    public NewsRequest(Context context) {
        super(context);

        installId = PrefsManager.getPreferences(context).getString(PrefsManager.PREF_INSTALL_ID, "");
    }

    public static String getBaseUrl() {
        StringBuilder urlBuilder = new StringBuilder(BASE_URL_YNPNEWS);
        urlBuilder.append(PREFIX_URL);

        urlBuilder.append(URL_APP).append(appId);
        urlBuilder.append(URL_INSTALL).append(installId);

        return urlBuilder.toString();
    }

    public static String getUrl() {
        StringBuilder urlBuilder = new StringBuilder(getBaseUrl());

        urlBuilder.append(URL_STREAM).append(streamId);
        urlBuilder.append(URL_ITEMS);

        return urlBuilder.toString();
    }

    public void getListNews(Response.Listener<JSONArray> successListener, Response.ErrorListener errorListener) {
        String url = getUrl();

        JsonArrayRequest request = new JsonArrayRequest(url, successListener, errorListener);

        boolean ok = addRequest(request);
        if (!ok)
            errorListener.onErrorResponse(null);
    }
}
