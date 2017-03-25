package com.example.cenk.flickralbum.classes.java.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.example.cenk.flickralbum.classes.java.Fragments.PhotoDetailFragment;
import com.example.cenk.flickralbum.classes.java.Helpers.PhotoItem;
import java.util.ArrayList;

/**
 * Adapter for ViewPager that shows photo detail
 * Created by Cenk on 25.03.2017.
 */

public class PhotoDetailAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private ArrayList<PhotoItem>mPhotoList;
    private int mIndex;

    public PhotoDetailAdapter(FragmentManager _fm,Context _context,ArrayList<PhotoItem>_photoList,int _index) {
        super(_fm);
        this.mContext=_context;//Context
        this.mPhotoList=_photoList;//General Photo list consist of PhotoItem
        this.mIndex=_index;// Position of clicked PhotoItem in list
    }



    //Gives total count of photo list size
    @Override
    public int getCount() {
        return mPhotoList.size();
    }

    /*
    *Creates Fragment of the PhotoItem on specific position
    *and passes related data.
    */
    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        PhotoDetailFragment fragment=new PhotoDetailFragment();
        Bundle bundle= new Bundle();
        bundle.putParcelable("PhotoItem",mPhotoList.get(mIndex));
        fragment.setArguments(bundle);
        mIndex=mIndex+1;
        return fragment;
    }
}