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
