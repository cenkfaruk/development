package com.example.cenk.flickralbum.classes.java.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

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
    private int _mExtraCount;// to make pager circular it keeps 2 more slot
    private PhotoItem[] mExtraList;// extra list for circular slider.

    public PhotoDetailAdapter(FragmentManager _fm,Context _context,ArrayList<PhotoItem>_photoList,int _index,final ViewPager _viewPager) {
        super(_fm);
        this.mContext=_context;//Context
        this.mPhotoList=_photoList;//General Photo list consist of PhotoItem
        this.mIndex=_index;// Position of clicked PhotoItem in list
        mExtraList = new PhotoItem[mPhotoList.size()+2];
        //asign an extra list with two for layer to make view pager infinite and circular
        for (int i = 0; i < mPhotoList.size(); i++) {
            mExtraList[i + 1] = mPhotoList.get(i);
        }
        mExtraList[0]=mPhotoList.get(mPhotoList.size()-1);
        mExtraList[mExtraList.length-1]=mPhotoList.get(0);


        // prepares current item according to infinite circular slide
        _viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            public void onPageSelected(int position) {
                int totalPage = getCount();
                //0 is blank dumm fragment that we put. If index is 0 then move to last indexed photo
                if (position == 0){
                    _viewPager.setCurrentItem(totalPage-2,false);
                } else if (position == totalPage-1){
                    _viewPager.setCurrentItem(1,false);
                }
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // TODO Auto-generated method stub
            }

            public void onPageScrollStateChanged(int state) {
                // TODO Auto-generated method stub
            }
        });
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