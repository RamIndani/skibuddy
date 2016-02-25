package com.learninghorizon.skibuddy.AsyncTasks;

import android.app.Activity;
import android.os.AsyncTask;

import com.learninghorizon.skibuddy.R;
import com.learninghorizon.skibuddy.model.SkiSession;
import com.learninghorizon.skibuddy.utility.RestUtil;

/**
 * Created by ramnivasindani on 11/29/15.
 */
public class SaveSession extends AsyncTask<SkiSession, Void, Void> {

    Activity activity;
    String userId;
    public SaveSession(Activity activity, final String userId){
        this.activity = activity;
        this.userId = userId;
    }
    @Override
    protected Void doInBackground(SkiSession... params){

        SkiSession skiSession = RestUtil.saveSession(params[0], activity.getResources().getString(R.string.baseURL) + "/createsession/"+userId);

        return null;
    }
}
