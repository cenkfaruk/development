package com.example.cenk.flickralbum.classes.java.Adapters;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.cenk.flickralbum.R;
import com.example.cenk.flickralbum.classes.java.Activities.PhotoDetailActivity;
import com.example.cenk.flickralbum.classes.java.Helpers.PhotoItem;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/**
 * A RecylerView adapter consumes a list of PhotoItem
 * and shows  them in a RecylerView
 *
 * Created by Cenk on 25.03.2017.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {
    private ArrayList<PhotoItem> mPhotoList; //List of photo item contains informaton about the photo
    private Context mContext;// Contexts

    public PhotoAdapter(Context _context, ArrayList<PhotoItem> _photoList) {
        this.mPhotoList = _photoList;
        this.mContext=_context;
    }


    //PhotoViewHolder class contains a ImageView and resues it.
    public class PhotoViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImage;

        public PhotoViewHolder(View _view) {
            super(_view);
            mImage = (ImageView) _view.findViewById(R.id.gallery_row_photo);
        }
    }



    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup _parent, int _viewType) {
        View rowView = LayoutInflater.from(_parent.getContext())
                .inflate(R.layout.image_row, _parent, false);
        return new PhotoViewHolder(rowView);
    }


    //Binds the item in specific order into mImage inside _holder
    @Override
    public void onBindViewHolder(PhotoViewHolder _holder, final int _position) {
        PhotoItem photoItem = mPhotoList.get(_position);
        String imageLink=photoItem.getFlickrImageLink();
        Picasso.with(mContext).load(imageLink).placeholder(R.mipmap.placeholder).into(_holder.mImage);
        _holder.mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get action when image clicked. Change activity for ImageDetail
                Intent detailIntent= new Intent(mContext, PhotoDetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putParcelableArrayList("PhotoList",mPhotoList);
                bundle.putInt("Position",_position);
                detailIntent.putExtras(bundle);
                ((Activity)mContext).startActivity(detailIntent);
            }
        });
    }

    // Gives the total number of element in the list
    @Override
    public int getItemCount() {
        return mPhotoList.size();
    }
}
