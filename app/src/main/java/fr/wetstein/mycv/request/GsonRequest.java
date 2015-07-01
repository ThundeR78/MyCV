package fr.wetstein.mycv.request;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by ThundeR on 05/08/2014.
 */
public class GsonRequest<T> extends Request<T> {
    public static final String TAG = "GsonRequest";

    protected Gson gson = new Gson();
    protected Class<T> clazz;
    protected Type type;
    protected Map<String, String> headers;
    protected String body = null;
    protected final Response.Listener<T> listener;

    protected final Charset UTF8_CHARSET = Charset.forName("UTF-8");

    //For One
    public GsonRequest(int method, String url, Class<T> classType, Map<String, String> headers, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.clazz = classType;
        this.type = null;
        this.headers = headers;
        this.listener = listener;
    }
    //For One with params
    public GsonRequest(int method, String url, Class<T> classType, Map<String, String> headers, Response.Listener<T> listener, Response.ErrorListener errorListener, String body) {
        this(method, url, classType, headers, listener, errorListener);
        this.body = body;
    }

    //For List
    public GsonRequest(int method, String url, Type type, Map<String, String> headers, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.clazz = null;
        this.type = type;
        this.headers = headers;
        this.listener = listener;
    }
    //For List with params
    public GsonRequest(int method, String url, Type type, Map<String, String> headers, Response.Listener<T> listener, Response.ErrorListener errorListener, String body) {
        this(method, url, type, headers, listener, errorListener);
        this.body = body;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        try {
            return body.getBytes(getParamsEncoding());
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, e.toString());
        }
        return null;
    }

//    @Override
//    public String getBodyContentType() {
//        return postString != null ? "application/json; charset=utf-8" : super.getBodyContentType();
//    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            //Parse Response into Object
            String json = new String(response.data, UTF8_CHARSET);  //Replace HttpHeaderParser.parseCharset(response.headers) to fix encoding in UTF-8
            T parsedObject = null;

            if (gson == null)
                gson = new Gson();

            if (clazz != null) {
                //Item
                parsedObject = gson.fromJson(json, clazz);
            } else if (type != null) {
                //List Item
                parsedObject = gson.fromJson(json, type);
            }

            return Response.success(parsedObject, HttpHeaderParser.parseCacheHeaders(response));
//        } catch (UnsupportedEncodingException e) {
//            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }
}

