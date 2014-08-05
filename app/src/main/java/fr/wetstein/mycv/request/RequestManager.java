package fr.wetstein.mycv.request;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class RequestManager {
	public static final String TAG = "RequestManager";

	protected Context mContext;
	protected RequestQueue mRequestQueue;
	protected ImageLoader mImageLoader;

	public RequestManager(Context context) {
		mContext = context;
		initPoolRequest(context);
	}
	
	//Stop all request
	public void stop() {
		mRequestQueue.cancelAll(mContext);
	}
	
	public RequestQueue getRequestQueue() {
		return mRequestQueue;
	}
	
	//Init Volley pool request
	public void initPoolRequest(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
//        mImageLoader = new ImageLoader(mRequestQueue, new BitmapLruCache());
        mRequestQueue.start();
	}
	
	//Send Request
	public boolean addRequest(Request request) {
		if (isConnected(mContext)) {
			Log.v(TAG, "URL = "+ request.getUrl());
		    request.setTag(mContext);
		    
			mRequestQueue.add(request);
			return true;
		} else
			return false;
	}
	
	//Check if connection possible
	public static boolean isConnected(Context context) {
		if (context != null) {
	        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	        // Need permission : android.permission.ACCESS_NETWORK_STATE
	        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
	
	        if (networkInfo != null && networkInfo.isConnected()) {
	            return true;
	        } else {
	            // display error
	        	/*if (context instanceof Activity)
	        		Crouton.makeText((Activity) context, "Network Error", Style.ALERT).show();
	        	else*/
	        		Toast.makeText(context, "Network Error", Toast.LENGTH_LONG).show();
	            return false;
	        }
		} else 
			return false;
    } 
}
