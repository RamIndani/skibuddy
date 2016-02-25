package com.learninghorizon.skibuddy.AsyncTasks;

import android.app.Activity;
import android.os.AsyncTask;

import com.learninghorizon.skibuddy.R;
import com.learninghorizon.skibuddy.utility.DataUtil;
import com.learninghorizon.skibuddy.utility.RestUtil;

/**
 * Created by ramnivasindani on 11/30/15.
 */
public class ShareLocation extends AsyncTask<Boolean, Void, Void> {

    Activity activity;
    public ShareLocation(Activity activity){
        this.activity = activity;
    }

    @Override
    protected Void doInBackground(Boolean... params){
        try{
            RestUtil.shareLocation(activity.getResources().getString(R.string.baseURL)+"/sharelocation/"
            + DataUtil.getInstance().getUser().getFacebookId());
        }catch(Exception exception){
            exception.printStackTrace();
        }
        return null;
    }
}
