package com.learninghorizon.skibuddy.AsyncTasks;


import android.content.Context;
import android.os.AsyncTask;

import com.learninghorizon.skibuddy.R;
import com.learninghorizon.skibuddy.fragments.EventsFragment;
import com.learninghorizon.skibuddy.model.SkiEvent;
import com.learninghorizon.skibuddy.utility.DataUtil;
import com.learninghorizon.skibuddy.utility.RestUtil;

/**
 * Created by ramnivasindani on 11/29/15.
 */
public class CreateSkiEvent extends AsyncTask<SkiEvent, Void, Void> {
    Context activity;
    public CreateSkiEvent(Context activity){
        this.activity = activity;
    }



    @Override
    protected void onPreExecute(){
        super.onPreExecute();

    }
    @Override
    protected Void doInBackground(SkiEvent... params){
        RestUtil.createEvent(params[0], activity.getResources().getString(R.string.baseURL)+"/createevent/"+
                DataUtil.getInstance().getUser().getFacebookId());
        DataUtil.getInstance().setTotalEvent(params[0]);
        return null;
    }

    @Override
    protected void onPostExecute(Void result){
        EventsFragment.eventAdapter.notifyDataSetChanged();
    }
}
