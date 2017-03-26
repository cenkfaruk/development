package com.example.cenk.flickralbum.classes.java.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cenk.flickralbum.R;
import com.example.cenk.flickralbum.classes.java.Adapters.PhotoDetailAdapter;
import com.example.cenk.flickralbum.classes.java.Helpers.PhotoItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void onSharePhoto(View v) {
        ImageView fragmentImage = (ImageView)detailPager.findViewById(R.id.fragment_image);
        // Get Uri and check null control for operation
        Uri mUri = getUriOfPhoto(fragmentImage);
        if (mUri != null) {
            Intent shareInt = new Intent();
            shareInt.setAction(Intent.ACTION_SEND);
            shareInt.putExtra(Intent.EXTRA_STREAM, mUri);
            shareInt.setType("image/*");

            //start Activity to start share operation
            startActivity(Intent.createChooser(shareInt, "Share Photo"));
        } else {
            // Sharing error toast message
            Toast.makeText(PhotoDetailActivity.this,"Sharing Error!",Toast.LENGTH_SHORT).show();
        }
    }

    // Helps to get uri data of the photo
    public Uri getUriOfPhoto(ImageView _image) {
        // get the drawable of image
        Drawable drw = _image.getDrawable();
        Bitmap bitmap = null;
        if (drw instanceof BitmapDrawable){
            bitmap = ((BitmapDrawable) _image.getDrawable()).getBitmap();
        } else {
            return null;
        }
        // Creates uri of the image
        Uri uri = null;
        try {
            //gives a unique name to photo with system currentTimeMillis
            File file =  new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "PHOTO_" + System.currentTimeMillis() + ".png");
            FileOutputStream outStream = new FileOutputStream(file);
            // Compress bitmap with Stream data to get Uri.
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.close();
            uri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uri;
    }

}
