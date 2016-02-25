package com.learninghorizon.skibuddy.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.learninghorizon.skibuddy.R;
import com.learninghorizon.skibuddy.utility.DataUtil;
import com.learninghorizon.skibuddy.utility.RestUtil;

/**
 * Created by ramnivasindani on 11/30/15.
 */
public class SaveLocation extends AsyncTask<String, Void, Void> {

    Context activity;

    public SaveLocation(Context activity){
        this.activity = activity;
    }

    @Override
    protected Void doInBackground(String... params){
            RestUtil.saveLocation(activity.getResources().getString(R.string.baseURL).concat("/savelocation/").concat(DataUtil.getInstance().getUser().getFacebookId())
                    .concat("/").concat(params[0])
                    .concat("/").concat(params[1])
                    .concat("/").concat(params[2])
                    .concat("/").concat(params[3])
            );

        return null;
    }
}
