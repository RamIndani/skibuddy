package com.learninghorizon.skibuddy.adpaters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.learninghorizon.skibuddy.R;
import com.learninghorizon.skibuddy.model.SessionInfo;
import com.learninghorizon.skibuddy.model.SkiSession;
import com.learninghorizon.skibuddy.utility.DataUtil;
import com.learninghorizon.skibuddy.viewholders.SessionViewHolder;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by ramnivasindani on 11/24/15.
 */
public class SessionAdapter extends RecyclerView.Adapter<SessionViewHolder> {

    private List<SkiSession> sessionList;
    private Activity activity;
    public SessionAdapter(List<SkiSession> sessionList, Activity activity) {
        this.sessionList = sessionList;
        this.activity = activity;
        DataUtil.getInstance().setSkiSession(sessionList);
    }

    @Override
    public int getItemCount() {
        return sessionList.size();
    }

    @Override
    public void onBindViewHolder(SessionViewHolder sessionViewHolder, int i) {
        SkiSession si = sessionList.get(i);
        sessionViewHolder.setsName(si.getSessionName());

            sessionViewHolder.setsDistance(si.getDistance() + " m");

        SimpleDateFormat formatter = new SimpleDateFormat("MMM d, yyyy");
            sessionViewHolder.setsSessionDate(""+formatter.format(si.getDate()));
        sessionViewHolder.setsSessionAverageSpeed(si.getAverageSpeed()+" m/s");
        sessionViewHolder.setsSessionMaxSpeed(si.getMaxSpeed()+" m/s");
        sessionViewHolder.setsSessionDuration(si.getDuration());
        //sessionViewHolder.sSessionId.setText(i);

    }

    @Override
    public SessionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.session_card, viewGroup, false);

        return new SessionViewHolder(itemView);
    }

    public void notifyChange(){
        notifyDataSetChanged();
    }

}
