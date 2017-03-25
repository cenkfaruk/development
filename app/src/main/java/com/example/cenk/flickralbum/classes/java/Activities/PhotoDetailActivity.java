package com.example.cenk.flickralbum.classes.java.Activities;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.cenk.flickralbum.R;
import com.example.cenk.flickralbum.classes.java.Adapters.PhotoDetailAdapter;
import com.example.cenk.flickralbum.classes.java.Helpers.PhotoItem;

import java.util.ArrayList;

public class PhotoDetailActivity extends AppCompatActivity {
    private ViewPager detailPager;//ViewPager that contains fragments for image detail.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
        detailPager=(ViewPager)findViewById(R.id.detail_pager);
        //Get data from previous activity
        Bundle extras= getIntent().getExtras();
        ArrayList<PhotoItem>photoItems=extras.getParcelableArrayList("PhotoList");
        //Set adapter to ViewPager.
        detailPager.setAdapter(new PhotoDetailAdapter(getSupportFragmentManager(),PhotoDetailActivity.this,photoItems,extras.getInt("Position")));
    }
}
