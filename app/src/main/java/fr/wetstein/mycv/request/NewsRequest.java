package fr.wetstein.mycv.request;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.sophiacom.ynp.androidlib.YNPClient;
import fr.wetstein.mycv.model.News;
import fr.wetstein.mycv.util.PrefsManager;

/**
 * Created by ThundeR on 04/08/2014.
 */
public class NewsRequest extends RequestManager {
    public static final String TAG = "NewsRequest";

    private static final String BASE_URL_YNPNEWS = "https://sandbox-mobile.youandpush.com/";
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

        if (context != null)
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

    public void getListNews(Response.Listener<List<News>> successListener, Response.ErrorListener errorListener) {
        String url = getUrl();

        GsonBuilder gsonBuilder = new GsonBuilder();
        //gsonBuilder.setDateFormat("M/d/yy hh:mm a"); //Format of our JSON dates
        Gson gson = gsonBuilder.create();

        Type listType = new TypeToken<ArrayList<News>>(){}.getType();
        Map<String, String> headers = getHeaders();

        //Create request
        GsonRequest<List<News>> request = new GsonRequest<List<News>>(
            Request.Method.GET,
            url,
            listType,
            headers,
            successListener,
            errorListener);
        request.setGson(gson);

        //Launch request
        addRequest(request, errorListener);
    }

    protected Map<String, String> getHeaders() {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "ApiKey " + YNPClient.apiKey);

        return headers;
    }
}
