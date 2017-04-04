package com.doorboard3.doorboardmobile;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by danielchen on 4/4/17.
 */

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleViewHolder> {
    private ArrayList<ScheduleEvent> events;

    public ScheduleAdapter(ArrayList<ScheduleEvent> events) {
        this.events = events;
    }

    @Override
    public ScheduleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_entry, parent, false);
        ScheduleViewHolder holder = new ScheduleViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ScheduleViewHolder holder, int position) {
        ScheduleEvent event = events.get(position);
        holder.name.setText(event.name);
        holder.startTime.setText(event.startTime);
        holder.endTime.setText(event.endTime);
    }

    @Override
    public int getItemCount() {
        //returns the number of elements the RecyclerView will display
        return events.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
