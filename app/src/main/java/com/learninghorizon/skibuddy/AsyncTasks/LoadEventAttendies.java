package com.learninghorizon.skibuddy.AsyncTasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.learninghorizon.skibuddy.R;
import com.learninghorizon.skibuddy.adpaters.AttendiesListAdapter;
import com.learninghorizon.skibuddy.model.User;
import com.learninghorizon.skibuddy.utility.DataUtil;
import com.learninghorizon.skibuddy.utility.RestUtil;

import java.util.List;


/**
 * Created by ramnivasindani on 11/29/15.
 */
public class LoadEventAttendies extends AsyncTask<String,  Void, Void> {

    Activity activity;
    List<User> usersList;
    List<String> eventInvites;
    AttendiesListAdapter mAdapter;
    public LoadEventAttendies(Activity activity, List<String> eventInvites, AttendiesListAdapter mAdapter){
        this.activity = activity;
        this.eventInvites = eventInvites;
        this.mAdapter = mAdapter;
    }

    private ProgressDialog pdia;
    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        DataUtil.getInstance().clearEventAttendies();
        pdia = new ProgressDialog(activity);
        pdia.setMessage("Loading...");
        pdia.show();
    }
    @Override
    protected Void doInBackground(String... params){
        usersList = RestUtil.loadUsers(eventInvites,activity.getResources().getString(R.string.baseURL) + "/loadusers/" + params[0]);
       // DataUtil.getInstance().clearEventAttendies();
        DataUtil.getInstance().setEventAttendies(usersList);
        //return usersList;
        return null;
    }

    @Override
    protected void onPostExecute(Void result){
        pdia.dismiss();
        mAdapter.notifyDataSetChanged();
    }
}
