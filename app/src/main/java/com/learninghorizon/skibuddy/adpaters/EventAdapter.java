package com.learninghorizon.skibuddy.adpaters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.learninghorizon.skibuddy.R;
import com.learninghorizon.skibuddy.model.SkiEvent;
import com.learninghorizon.skibuddy.viewholders.EventViewHolder;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by ramnivasindani on 11/24/15.
 */
public class EventAdapter extends RecyclerView.Adapter<EventViewHolder> {

    private List<SkiEvent> eventsList;
    private Activity activity;
    public EventAdapter(List<SkiEvent> eventsList, Activity activity) {
        this.eventsList = eventsList;
        this.activity = activity;
    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }

    @Override
    public void onBindViewHolder(EventViewHolder eventViewHolder, int i) {
        SkiEvent se = eventsList.get(i);
        eventViewHolder.seteName(se.getEventName());
        eventViewHolder.seteDescription(se.getEventDescription());
        SimpleDateFormat formatter = new SimpleDateFormat("MMM d, yyyy");
        eventViewHolder.setsEventDate(""+formatter.format(se.getDate()));
        eventViewHolder.setEventStartTime(se.getStartTime());
        eventViewHolder.setEventEndTime(se.getEndTime());
        //sessionViewHolder.sSessionAverageSpeed.setText(se.getAverageSpeed());
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.event_card, viewGroup, false);

        return new EventViewHolder(itemView);
    }

    @Override
    public long getItemId(int position){
        return  position;
    }

}
