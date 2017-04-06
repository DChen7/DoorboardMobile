package com.doorboard3.doorboardmobile;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by danielchen on 4/4/17.
 */

public class ScheduleViewHolder extends RecyclerView.ViewHolder {
    TextView name;
    TextView startTime;
    TextView endTime;
    TextView room;

    ScheduleViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.event_name);
        startTime = (TextView) itemView.findViewById(R.id.start_time);
        endTime = (TextView) itemView.findViewById(R.id.end_time);
        room = (TextView) itemView.findViewById(R.id.event_room);
    }
}
