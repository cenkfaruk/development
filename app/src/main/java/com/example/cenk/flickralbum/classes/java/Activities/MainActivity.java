package com.example.cenk.flickralbum.classes.java.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.cenk.flickralbum.R;
import com.example.cenk.flickralbum.classes.java.Helpers.NameValuePair;
import com.example.cenk.flickralbum.classes.java.Helpers.ServiceConnection;
import com.example.cenk.flickralbum.classes.java.Helpers.Tools;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final static int SPLASH_DURATION=1750;
    private static final String SEARCH = "flickr.photos.search" ;// Search method link from Flickr.
    private static final String BASE_URL="https://api.flickr.com/services/rest/"; // Base url of rest api.
    private List<NameValuePair> propList;// List of NameValuePair that contains specific features that will add on url.
    private static String API_KEY="dd5a1757d9bddb6eeaf1ae88485b6086";// Api key of the app taken from Flickr
    private static String SECRET_KEY="07ae2971d28058b0";//Secret key of the app taken fromFlickr

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        propList=new ArrayList<>();
        propList.add(new NameValuePair("method", SEARCH));
        propList.add(new NameValuePair("api_key",API_KEY));
        propList.add(new NameValuePair("format","json"));
        propList.add(new NameValuePair("nojsoncallback","1"));
        propList.add(new NameValuePair("tags","moda"));

        /*
        1750 milliseconds later runnable stops sleeping and changes
        the Activity. (Splash screen will be visible for 1.75 seconds)
         */
        Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                changeActivityWithResponse(MainActivity.this);
            }
        },SPLASH_DURATION);
    }





    private void showConnectionAlert(final Context _context){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(_context);
        builder1.setTitle("Connection Error!");
        builder1.setMessage("Please check your internet connection.");
        builder1.setCancelable(false);

        builder1.setPositiveButton(
                R.string.connection_retry,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        changeActivityWithResponse(_context);
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void changeActivityWithResponse(Context _context){
        if(Tools.isConnectted(_context))
            // connects api and get response asynchronously main thread can give crash for this operation.
            new FlickrSearch().execute();
        else{
            showConnectionAlert(_context);
            }
    }



    class FlickrSearch extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                //Creates service connection object and run function for service respose
                ServiceConnection connection= new ServiceConnection(BASE_URL,propList);
                return connection.getServiceResponse();
            } catch (Exception ex) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String _response) {
            if(_response!=null) {
                Intent intent= new Intent(MainActivity.this,  ImageListActivity.class);
                intent.putExtra("response",_response);
                startActivity(intent);
            }


        }
    }

}
