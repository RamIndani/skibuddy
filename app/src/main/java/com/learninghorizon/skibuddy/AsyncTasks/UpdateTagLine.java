package com.learninghorizon.skibuddy.AsyncTasks;

import android.app.Activity;
import android.os.AsyncTask;

import com.learninghorizon.skibuddy.R;
import com.learninghorizon.skibuddy.model.User;
import com.learninghorizon.skibuddy.utility.RestUtil;

/**
 * Created by ramnivasindani on 11/27/15.
 */
public class UpdateTagLine extends AsyncTask<User, Void, Void> {

    Activity activity;

    public UpdateTagLine(Activity activity){
        this.activity = activity;
    }
    @Override
    protected Void doInBackground(User... params){
        User user = params[0];
        String userId = user.getFacebookId();
        String tagLine = user.getTagLine();
        RestUtil.updateTagLine(tagLine, activity.getResources().getString(R.string.baseURL).concat("/savetagline/").concat(userId));
        return null;
    }
}
