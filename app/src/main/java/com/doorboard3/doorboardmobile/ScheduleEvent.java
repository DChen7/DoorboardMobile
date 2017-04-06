package com.doorboard3.doorboardmobile;

/**
 * Created by danielchen on 4/4/17.
 */

public class ScheduleEvent {
    String ID;
    String name;
    String date;
    String startTime;
    String endTime;
    String room;
    String description;

    public ScheduleEvent(String ID, String name, String date, String startTime, String endTime, String room, String description) {
        this.ID = ID;
        this.name = name;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
        this.description = description;
    }
}
