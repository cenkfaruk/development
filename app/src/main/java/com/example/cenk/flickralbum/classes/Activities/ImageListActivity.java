package com.example.cenk.flickralbum.classes.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.cenk.flickralbum.R;


public class ImageListActivity extends AppCompatActivity {
    private static final String SEARCH = "flickr.photos.search" ;
    private static final String BASE_URL="https://api.flickr.com/services/rest/" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);

    }
}
