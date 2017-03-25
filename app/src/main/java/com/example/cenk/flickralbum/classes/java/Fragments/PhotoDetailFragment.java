package com.example.cenk.flickralbum.classes.java.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.cenk.flickralbum.R;
import com.example.cenk.flickralbum.classes.java.Helpers.PhotoItem;
import com.squareup.picasso.Picasso;

/**
 * Custom fragment for updating visual design in fragment's view
 * Created by Cenk on 25.03.2017.
 */

public class PhotoDetailFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Selected PhotoItem
        PhotoItem photoItem = getArguments().getParcelable("PhotoItem");
        View view=inflater.inflate(R.layout.detail_fragment_layout, container, false);
        ImageView detailImage=(ImageView) view.findViewById(R.id.fragment_image);
        Picasso.with(getContext()).load(photoItem.getFlickrImageLink()).placeholder(R.mipmap.placeholder).into(detailImage);
        return view;
    }
}
