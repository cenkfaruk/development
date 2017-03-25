package com.example.cenk.flickralbum.classes.java.Helpers;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Custom object to map every paratameter of api response
 *Class is parcelable for easy store and easy transfer
 * Created by Cenk on 25.03.2017.
 */

public class PhotoItem implements Parcelable {
    private int mFarmId;
    private String mServerId;// Id of the server that photo comes from
    private String mPhotoId;// If of the photo
    private String mSecretId;//Secret Id from reponse
    private String mSize; // Size of the photo can be s, m, l

    public PhotoItem(int _farmId, String _serverId, String _photoId, String _secretId, String _size) {
        this.mFarmId = _farmId;
        this.mServerId = _serverId;
        this.mPhotoId = _photoId;
        this.mSecretId = _secretId;
        this.mSize = _size;
    }

    public int getFarmId() {
        return mFarmId;
    }

    public void setFarmId(int _farmId) {
        this.mFarmId = _farmId;
    }

    public String getServerId() {
        return mServerId;
    }

    public void setServerId(String _serverId) {
        this.mServerId = _serverId;
    }

    public String getPhotoId() {
        return mPhotoId;
    }

    public void setPhotoId(String _photoId) {
        this.mPhotoId = _photoId;
    }

    public String getSecretId() {
        return mSecretId;
    }

    public void setSecretId(String _secretId) {
        this.mSecretId = _secretId;
    }

    public String getSize() {
        return mSize;
    }

    public void setSize(String _size) {
        this.mSize = _size;
    }


    /*
    Combines all paramters to generate url of specific image
     */
    public String getFlickrImageLink(){
       return "https://farm"+mFarmId+".staticflickr.com/"+ mServerId +"/"+mPhotoId+"_"+ mSecretId +"_"+ mSize +".jpg";
    }

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    public PhotoItem(Parcel in){
        mFarmId=in.readInt();
        mServerId=in.readString();
        mPhotoId=in.readString();
        mSecretId=in.readString();
        mSize=in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mFarmId);
        dest.writeString(mServerId);
        dest.writeString(mPhotoId);
        dest.writeString(mSecretId);
        dest.writeString(mSize);
    }

    public static final Parcelable.Creator<PhotoItem> CREATOR = new Parcelable.Creator<PhotoItem>() {
                @Override
                public PhotoItem createFromParcel(Parcel source) {
                    return new PhotoItem(source);
                }
                @Override
                public PhotoItem[] newArray(int size) {
                    return new PhotoItem[size];
                }
            };
}
