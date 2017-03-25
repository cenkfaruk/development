package com.example.cenk.flickralbum.classes.java.Helpers;

import android.net.Uri;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a general class that contains parameters and functions
 * to make HTTPURL request and helps to get string formatted response.
 *
 * Created by Cenk on 25.03.2017.
 */
public class ServiceConnection {
    private String mBaseUrl; //Basic part of url
    private List<NameValuePair> mProps= new ArrayList<>(); //List of properties that will be added on url


    //Basic constructur
    public ServiceConnection(String _baseUrl, List<NameValuePair> _props) {
        this.mBaseUrl = _baseUrl;
        this.mProps = _props;
    }


    /*
        getUrl function takes mBaseUrl and mProps
        Adds mBaseUrl and all items of mProps into a URL and returns.
     */
    private URL getUrl(){
        URL url=null;
        Uri.Builder builtUri = Uri.parse(mBaseUrl).buildUpon();

        for(NameValuePair pair :mProps){
            builtUri.appendQueryParameter(pair.getName(),pair.getValue());
        }
        Uri uri =builtUri.build();


        try{
          url=new URL(uri.toString());
        }
        catch(MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }

    /*
        getServiceResponse takes a URL with specific features and
        provides a string response from service
     */
    public String getServiceResponse() {
        String res = null;
        try {
            URL url = getUrl();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            InputStream instream = con.getInputStream();
            res = convertStreamToString(instream);
            instream.close();
            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }


    /*
        convertStreamToString consumes an input stream
        and produces a string by reading stream line by line
     */
    private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
