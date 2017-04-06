package com.doorboard3.doorboardmobile;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_entry, parent, false);
        ScheduleViewHolder holder = new ScheduleViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ScheduleViewHolder holder, int position) {
        ScheduleEvent event = events.get(position);
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            final Date dateObj = sdf.parse(event.startTime);
            final SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm a");
            holder.startTime.setText(sdf2.format(dateObj));

            final Date dateObj2 = sdf.parse(event.startTime);
            holder.endTime.setText(sdf2.format(dateObj2));
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        holder.name.setText(event.name);
        holder.room.setText(event.room);
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
