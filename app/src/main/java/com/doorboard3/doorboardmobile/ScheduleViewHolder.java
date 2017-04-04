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

    ScheduleViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.name);
        startTime = (TextView) itemView.findViewById(R.id.date_time);
        endTime = (TextView) itemView.findViewById(R.id.status);
    }
}
