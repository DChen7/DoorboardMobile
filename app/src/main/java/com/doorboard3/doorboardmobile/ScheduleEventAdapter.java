package com.doorboard3.doorboardmobile;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by danielchen on 4/6/17.
 */

public class ScheduleEventAdapter extends ArrayAdapter<ScheduleEvent> {

    private Context context;
    private ScheduleEvent event;

    public ScheduleEventAdapter(Context context, ArrayList<ScheduleEvent> values) {
        super(context, 0, values);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.schedule_entry, parent, false);
        }
        event = getItem(position);
        TextView name = (TextView) convertView.findViewById(R.id.event_name);
        TextView room = (TextView) convertView.findViewById(R.id.event_room);
        TextView startTime = (TextView) convertView.findViewById(R.id.start_time);
        TextView endTime = (TextView) convertView.findViewById(R.id.end_time);
        name.setText(event.name);
        room.setText(event.room);

        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            final Date dateObj = sdf.parse(event.startTime);
            final SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm a");
            startTime.setText(sdf2.format(dateObj));

            final Date dateObj2 = sdf.parse(event.endTime);
            endTime.setText(sdf2.format(dateObj2));
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get selected items
                Intent intent = new Intent(v.getContext(), EditScheduleActivity.class);
                intent.putExtra("ID", event.ID);
                intent.putExtra("name", event.name);
                intent.putExtra("date", event.date);
                intent.putExtra("startTime", event.startTime);
                intent.putExtra("endTime", event.endTime);
                intent.putExtra("room", event.room);
                intent.putExtra("description", event.description);
                context.startActivity(intent);
            }
        });
        return convertView;
    }
}
