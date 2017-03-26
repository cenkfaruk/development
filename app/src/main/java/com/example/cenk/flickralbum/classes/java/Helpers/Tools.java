package com.example.cenk.flickralbum.classes.java.Helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Tools is a container class that contains static
 * helper functions.
 * Created by Cenk on 25.03.2017.
 */

public class Tools {
    public static String API_KEY="dd5a1757d9bddb6eeaf1ae88485b6086";// Api key of the app taken from Flickr
    public static String SECRET_KEY="07ae2971d28058b0";//Secret key of the app taken fromFlickr
    public static final String SEARCH = "flickr.photos.search" ;// Search method link from Flickr.
    public static final String BASE_URL="https://api.flickr.com/services/rest/"; // Base url of rest api.
    /*
      Controls if device has internet connection and
      produces a boolean result.
   */
    public static boolean isConnectted(Context _context){
        ConnectivityManager connectivityManager =
                (ConnectivityManager)_context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        //boolean isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
        return isConnected;
    }
}
