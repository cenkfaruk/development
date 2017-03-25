package com.example.cenk.flickralbum.classes.java.Helpers;

/**
 * NameValuePair is a basic structure
 * that every name refers to a value.
 *
 * Created by Cenk on 25.03.2017.
 */

public class NameValuePair {
    private String mName;//Name tag of the value
    private String mValue;//Value refered by specific name

    public NameValuePair(String _name, String _value) {
        this.mName = _name;
        this.mValue = _value;
    }

    public String getName() {
        return mName;
    }

    public void setName(String _name) {
        this.mName = _name;
    }

    public String getValue() {
        return mValue;
    }

    public void setValue(String _value) {
        this.mValue = _value;
    }
}
