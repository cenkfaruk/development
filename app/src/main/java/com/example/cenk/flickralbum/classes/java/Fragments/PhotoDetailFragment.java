package com.example.cenk.flickralbum.classes.java.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
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
        final View view=inflater.inflate(R.layout.detail_fragment_layout, container, false);
        ImageView detailImage=(ImageView) view.findViewById(R.id.fragment_image);
        // Load photo from url and add place holder photo as default image
        Picasso.with(getContext()).load(photoItem.getFlickrImageLink()).placeholder(R.mipmap.placeholder).into(detailImage);
        detailImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeInFadeOut(view);
            }
        });
        return view;
    }

    /*
        Shows left and right arrow with fade-in, fade-out animation to emphises that
         images are slidable and it takes totaly 1 second.
     */
    public void fadeInFadeOut(final View _view){
        final ImageView leftArrow=(ImageView) _view.findViewById(R.id.left_arrow);
        final ImageView rightArrow=(ImageView) _view.findViewById(R.id.right_arrow);
        leftArrow.animate()
                .alpha(1)
                .withEndAction(new Runnable() {
            @Override
            public void run() {
                // fade out animation start after faide in ends
                leftArrow.animate()
                        .alpha(0)
                        .setDuration(500)
                        .start();
            }
                })
                .setDuration(500)
                .start();

        rightArrow.animate()
                .alpha(1)
                .setDuration(500)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        // fade out animation start after faide in ends
                        rightArrow.animate()
                                .alpha(0)
                                .setDuration(500)
                                .start();
                    }
                })
                .start();
    }
}
