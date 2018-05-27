package com.tamoghnodas.android.tododemo;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Work {
    private String mName;
//    private String mUid;
    private Date mTimestamp;
    public Work() { } // Needed for Firebase

    public Work(String name) {
        mName = name;
    }

    public String getName() { return mName; }

    public void setName(String name) { mName = name; }

//    public String getUid() { return mUid; }
//
//    public void setUid(String uid) { mUid = uid; }
//
    @ServerTimestamp
    public Date getTimestamp() { return mTimestamp; }
//
//    public void setTimestamp(Date timestamp) { mTimestamp = timestamp; }
}


