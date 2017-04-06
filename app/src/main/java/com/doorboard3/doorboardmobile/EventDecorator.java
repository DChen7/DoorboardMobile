package com.doorboard3.doorboardmobile;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

/**
 * Created by danielchen on 4/5/17.
 */

public class EventDecorator implements DayViewDecorator {

    private final int color;
    private DoorboardDbHelper dbHelper;

    public EventDecorator(int color, DoorboardDbHelper dbHelper) {
        this.color = color;
        this.dbHelper = dbHelper;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dbHelper.containsEvent(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new DotSpan(10, color));
    }
}
