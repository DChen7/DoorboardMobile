package com.doorboard3.doorboardmobile;

import android.provider.BaseColumns;

/**
 * Created by danielchen on 3/27/17.
 */

public class DoorboardContract {

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private DoorboardContract() {}

    /* Inner class that defines the table contents */
    public static class MessageEntry implements BaseColumns {
        public static final String TABLE_NAME = "messages";
        public static final String COLUMN_NAME_ROOM = "room";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_PROFILE_PIC = "profilePic";
        public static final String COLUMN_NAME_DATE_TIME = "dateTime";
        public static final String COLUMN_NAME_STATUS = "status";
    }

    public static class ScheduleEntry implements BaseColumns {
        public static final String TABLE_NAME = "scheduleEntries";
        public static final String COLUMN_NAME_ROOM = "room";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_START_TIME = "startTime";
        public static final String COLUMN_NAME_END_TIME = "endTime";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
//        public static final String COLUMN_NAME_DAY_OF_WEEK = "dayOfWeek";
//        // This is either never, daily, weekly, or monthly
//        public static final String COLUMN_NAME_REPEAT = "repeat";
//        // This is the date where the repeat ends, inclusive. If never repeat, this is 0.
//        // If there is no end date, this is 13.
//        public static final String COLUMN_NAME_END_REPEAT = "endRepeat";
    }
}
