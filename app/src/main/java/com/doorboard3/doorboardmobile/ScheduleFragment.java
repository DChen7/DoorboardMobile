package com.doorboard3.doorboardmobile;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;

public class ScheduleFragment extends Fragment implements View.OnClickListener{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private DoorboardDbHelper dbHelper;
    private EventDecorator decorator;
    private ArrayList<ScheduleEvent> events;
    private int fragID;

    public static ScheduleFragment newInstance() {
        ScheduleFragment fragment = new ScheduleFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_schedule, container, false);

        dbHelper = new DoorboardDbHelper(this.getActivity());
        fragID = this.getId();

        MaterialCalendarView calendarView = (MaterialCalendarView) v.findViewById(R.id.calendar_view);
        calendarView.setDateSelected(Calendar.getInstance(), true);
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int year = date.getYear();
                int month = date.getMonth();
                int dayOfMonth = date.getDay();
                events =  dbHelper.getEventsForDate(year, month, dayOfMonth);
                mAdapter = new ScheduleAdapter(events, (ScheduleFragment) getActivity().getSupportFragmentManager().findFragmentById(fragID));
                mRecyclerView.setAdapter(mAdapter);
            }
        });

        decorator = new EventDecorator(Color.GREEN, dbHelper);
        calendarView.addDecorator(decorator);
//        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
//                ArrayList<ScheduleEvent> events =  dbHelper.getEventsForDate(year, month, dayOfMonth);
//                mAdapter = new ScheduleAdapter(events);
//                mRecyclerView.setAdapter(mAdapter);
//            }
//        });
//
//        CalendarView calendarView = (CalendarView) v.findViewById(R.id.calendar_view);
//        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
//                ArrayList<ScheduleEvent> events =  dbHelper.getEventsForDate(year, month, dayOfMonth);
//                mAdapter = new ScheduleAdapter(events);
//                mRecyclerView.setAdapter(mAdapter);
//            }
//        });

        Calendar c = Calendar.getInstance();
        // Get messages
        mRecyclerView = (RecyclerView) v.findViewById(R.id.event_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemViewCacheSize(20);
        mRecyclerView.setDrawingCacheEnabled(true);
        mRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        events = dbHelper.getEventsForDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        mAdapter = new ScheduleAdapter(events, this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        FloatingActionButton addEntry = (FloatingActionButton) v.findViewById(R.id.add_event_fab);
        addEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddScheduleActivity.class);
                startActivity(intent);
            }
        });
        return v;
    }

    @Override
    public void onClick(View v) {
        int itemPosition = mRecyclerView.getChildLayoutPosition(v);
        ScheduleEvent item = events.get(itemPosition);
        Intent intent = new Intent(v.getContext(), EditScheduleActivity.class);
        intent.putExtra("ID", item.ID);
        intent.putExtra("name", item.name);
        intent.putExtra("date", item.date);
        intent.putExtra("startTime", item.startTime);
        intent.putExtra("endTime", item.endTime);
        intent.putExtra("room", item.room);
        intent.putExtra("description", item.description);
        startActivity(intent);
    }
}
