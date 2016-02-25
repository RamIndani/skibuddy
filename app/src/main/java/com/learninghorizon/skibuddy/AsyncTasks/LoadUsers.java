package com.learninghorizon.skibuddy.AsyncTasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.learninghorizon.skibuddy.R;
import com.learninghorizon.skibuddy.adpaters.UsersListAdapter;
import com.learninghorizon.skibuddy.model.User;
import com.learninghorizon.skibuddy.utility.DataUtil;
import com.learninghorizon.skibuddy.utility.RestUtil;

import java.util.List;

/**
 * Created by ramnivasindani on 11/29/15.
 */
public class LoadUsers extends AsyncTask<String,  Void, Void> {

    Activity activity;
    UsersListAdapter mAdapter;
    public LoadUsers(Activity activity, UsersListAdapter mAdapter){
        this.mAdapter = mAdapter;
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
    protected Void doInBackground(String... params){
        List<User> usersList = RestUtil.listUsers(activity.getResources().getString(R.string.baseURL) + "/listusers/" + params[0]);
        DataUtil.getInstance().clearUsers();
        DataUtil.getInstance().setUsersList(usersList);
        //return usersList;
        return null;
    }

    @Override
    protected void onPostExecute(Void result){
        mAdapter.notifyDataSetChanged();
        pdia.dismiss();
    }
}
