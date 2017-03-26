package com.example.cenk.flickralbum.classes.java.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

public class SplashActivity extends AppCompatActivity {
    private final static int SPLASH_DURATION=1750;
    public static List<NameValuePair> propList;// List of NameValuePair that contains specific features that will add on url.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        propList=new ArrayList<>();
        propList.add(new NameValuePair("method", Tools.SEARCH));
        propList.add(new NameValuePair("api_key",Tools.API_KEY));
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
                changeActivityWithResponse(SplashActivity.this);
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
        if(Tools.isConnectted(_context)) {
            // connects api and get response asynchronously main thread can give crash for this operation.
            new AsyncTask<String, String, String>() {

                @Override
                protected String doInBackground(String... params) {
                    try {
                        //Creates service connection object and run function for service respose
                        ServiceConnection connection = new ServiceConnection(Tools.BASE_URL, propList);
                        return connection.getServiceResponse();
                    } catch (Exception ex) {
                        return null;
                    }
                }
                    @Override
                    protected void onPostExecute(String _response) {
                        if(_response!=null) {
                            Intent intent = new Intent(SplashActivity.this, ImageListActivity.class);
                            intent.putExtra("response", _response);
                            startActivity(intent);
                        }
                    }

            }.execute();
        }
        else{
            showConnectionAlert(_context);
            }
    }

}
