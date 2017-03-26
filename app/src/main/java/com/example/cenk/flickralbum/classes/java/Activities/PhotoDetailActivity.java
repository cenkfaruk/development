package com.example.cenk.flickralbum.classes.java.Activities;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
        SetActionBar();
        detailPager=(ViewPager)findViewById(R.id.detail_pager);
        //Get data from previous activity
        Bundle extras= getIntent().getExtras();
        ArrayList<PhotoItem>photoItems=extras.getParcelableArrayList("PhotoList");
        //Set adapter to ViewPager.
        detailPager.setAdapter(new PhotoDetailAdapter(getSupportFragmentManager(),PhotoDetailActivity.this,photoItems,extras.getInt("Position"),detailPager));
    }

    private void SetActionBar() {
        try {
            android.support.v7.app.ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setHomeButtonEnabled(false);
            actionBar.setCustomView(R.layout.action_bar);
            ImageView search=(ImageView)actionBar.getCustomView().findViewById(R.id.search_image);
            ImageView columnEdit=(ImageView)actionBar.getCustomView().findViewById(R.id.num_of_column);
            TextView title= (TextView)actionBar.getCustomView().findViewById(R.id.action_title);
            ImageView share=(ImageView)actionBar.getCustomView().findViewById(R.id.share);
            share.setVisibility(View.VISIBLE);
            title.setText("Photo Detail");
            search.setVisibility(View.GONE);
            columnEdit.setVisibility(View.GONE);
            View.OnClickListener actionBarClick=new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            };
            share.setOnClickListener(actionBarClick);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
