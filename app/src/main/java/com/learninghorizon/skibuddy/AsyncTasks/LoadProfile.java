package com.learninghorizon.skibuddy.AsyncTasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;

import com.learninghorizon.skibuddy.MainActivity;
import com.learninghorizon.skibuddy.R;
import com.learninghorizon.skibuddy.model.User;
import com.learninghorizon.skibuddy.utility.DataUtil;
import com.learninghorizon.skibuddy.utility.RestUtil;

/**
 * Created by ramnivasindani on 11/27/15.
 */
public class LoadProfile extends AsyncTask<User, Void, Void> {

    Activity activity;
    public LoadProfile(Activity activity){
        this.activity = activity;
    }

    private ProgressDialog pdia;

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        pdia = new ProgressDialog(activity);
        pdia.setMessage("Loading...");
        pdia.show();
    }
    @Override
    protected Void doInBackground(User... params){
        User user = RestUtil.loadUserProfile(activity.getResources().getString(R.string.baseURL)+"/getuser/".concat(params[0].getFacebookId()));
        if(null != user){
            DataUtil.getInstance().setUser(user);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result){
        Intent intent = new Intent(activity.getApplicationContext(), MainActivity.class);
        activity.startActivity(intent);
        pdia.dismiss();
        activity.finish();

    }
}
