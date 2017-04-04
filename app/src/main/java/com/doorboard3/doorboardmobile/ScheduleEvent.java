package com.doorboard3.doorboardmobile;

/**
 * Created by danielchen on 4/4/17.
 */

public class ScheduleEvent {
    String ID;
    String name;
    String startTime;
    String endTime;

    public ScheduleEvent(String ID, String name, String startTime, String endTime) {
        this.ID = ID;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
