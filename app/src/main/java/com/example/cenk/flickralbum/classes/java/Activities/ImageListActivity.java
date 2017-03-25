package com.example.cenk.flickralbum.classes.java.Activities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.cenk.flickralbum.R;
import com.example.cenk.flickralbum.classes.java.Adapters.PhotoAdapter;
import com.example.cenk.flickralbum.classes.java.Helpers.NameValuePair;
import com.example.cenk.flickralbum.classes.java.Helpers.PhotoItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class ImageListActivity extends AppCompatActivity {
    private List<PhotoItem> photoItems;// List of PhotoItem
    private PhotoAdapter    photoAdapter;// Custom adapter for photoRecyclerView.
    private RecyclerView  photoRecyclerView;//photo container.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);
        photoItems= new ArrayList<>();
        String apiResponse=getIntent().getStringExtra("response");
        parseJsonIntoList(apiResponse);
        photoAdapter = new PhotoAdapter(ImageListActivity.this,photoItems);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        photoRecyclerView = (RecyclerView) findViewById(R.id.photo_recyle_list);
        photoRecyclerView.setLayoutManager(mLayoutManager);
        photoRecyclerView.setItemAnimator(new DefaultItemAnimator());
        photoRecyclerView.setAdapter(photoAdapter);


    }





    /*
        Consumes a Json formated string and parse all
        photo information into list
     */
    private void parseJsonIntoList(String _jsonResponse){
        String image_size="m";// Medium size
        try {
            JSONObject photoJson = new JSONObject(_jsonResponse);
            JSONObject photo=photoJson.getJSONObject("photos");//Accessing the jason object that contains JasonArray of photo info.
            JSONArray photos=photo.getJSONArray("photo");//JsonArray that contains list of photo information.
            for(int i=0;i<photos.length();i++){
                JSONObject currPhoto=(JSONObject)photos.get(i);//Specific JasonObject that contains photo info.
                photoItems.add(new PhotoItem(currPhoto.getInt("farm"),
                                              currPhoto.getString("server"),
                                              currPhoto.getString("id"),
                                              currPhoto.getString("secret"),
                                              image_size));
            }
        }
        catch(JSONException e){
            e.printStackTrace();
        }
    }
}
