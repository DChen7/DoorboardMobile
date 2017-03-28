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
}
