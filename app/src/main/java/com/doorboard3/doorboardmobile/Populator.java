package com.doorboard3.doorboardmobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by danielchen on 3/27/17.
 */

public class Populator {

    private DoorboardDbHelper dbHelper;
    private Context ctx;

    public Populator(DoorboardDbHelper dbHelper, Context ctx) {
        this.dbHelper = dbHelper;
        this.ctx = ctx;
    }

    public void populate() {
        // Gets the data repository in read mode
        SQLiteDatabase rdb = dbHelper.getReadableDatabase();

        // Gets the data repository in write mode
        SQLiteDatabase wdb = dbHelper.getWritableDatabase();

        // Check to see if entries have not already been added
        if (!DoorboardDbHelper.roomContainsMessages(rdb, "Iribe 203")) {
            // Create dummy user 1
            ContentValues values = new ContentValues();
            values.put(DoorboardContract.MessageEntry.COLUMN_NAME_ROOM, "Iribe 203");
            values.put(DoorboardContract.MessageEntry.COLUMN_NAME_NAME, "Daniel Chen");
            values.put(DoorboardContract.MessageEntry.COLUMN_NAME_STATUS, "Will be 5 min late");
            Bitmap b1 = BitmapFactory.decodeResource(ctx.getResources(), R.mipmap.ic_profile_1);
            values.put(DoorboardContract.MessageEntry.COLUMN_NAME_PROFILE_PIC, DoorboardDbHelper.bitmapToBase64(b1));
            values.put(DoorboardContract.MessageEntry.COLUMN_NAME_DATE_TIME, "03-28-2017 8:15 AM");

            // Insert the new row, returning the primary key value of the new row
            wdb.insert(DoorboardContract.MessageEntry.TABLE_NAME, null, values);

            // Create dummy user 2
            values = new ContentValues();
            values.put(DoorboardContract.MessageEntry.COLUMN_NAME_ROOM, "Iribe 203");
            values.put(DoorboardContract.MessageEntry.COLUMN_NAME_NAME, "Testudo");
            values.put(DoorboardContract.MessageEntry.COLUMN_NAME_STATUS, "Out to lunch. Be back at 1");
            Bitmap b2 = BitmapFactory.decodeResource(ctx.getResources(), R.mipmap.ic_profile_2);
            values.put(DoorboardContract.MessageEntry.COLUMN_NAME_PROFILE_PIC, DoorboardDbHelper.bitmapToBase64(b2));
            values.put(DoorboardContract.MessageEntry.COLUMN_NAME_DATE_TIME, "03-28-2017 12:15 PM");

            // Insert the new row, returning the primary key value of the new row
            wdb.insert(DoorboardContract.MessageEntry.TABLE_NAME, null, values);
        }

        // Check to see if entries have not already been added
        if (!DoorboardDbHelper.roomContainsMessages(rdb, "Iribe 304")) {
            // Create dummy user 3
            ContentValues values = new ContentValues();
            values.put(DoorboardContract.MessageEntry.COLUMN_NAME_ROOM, "Iribe 304");
            values.put(DoorboardContract.MessageEntry.COLUMN_NAME_NAME, "Eric Cartman");
            values.put(DoorboardContract.MessageEntry.COLUMN_NAME_STATUS, "At a meeting until 2");
            Bitmap b1 = BitmapFactory.decodeResource(ctx.getResources(), R.mipmap.ic_profile_3);
            values.put(DoorboardContract.MessageEntry.COLUMN_NAME_PROFILE_PIC, DoorboardDbHelper.bitmapToBase64(b1));
            values.put(DoorboardContract.MessageEntry.COLUMN_NAME_DATE_TIME, "03-28-2017 10:00 AM");

            // Insert the new row, returning the primary key value of the new row
            wdb.insert(DoorboardContract.MessageEntry.TABLE_NAME, null, values);

            // Create dummy user 4
            values = new ContentValues();
            values.put(DoorboardContract.MessageEntry.COLUMN_NAME_ROOM, "Iribe 304");
            values.put(DoorboardContract.MessageEntry.COLUMN_NAME_NAME, "John Doe");
            values.put(DoorboardContract.MessageEntry.COLUMN_NAME_STATUS, "Out solving mysteries. Be back in an hour");
            Bitmap b2 = BitmapFactory.decodeResource(ctx.getResources(), R.mipmap.ic_profile_4);
            values.put(DoorboardContract.MessageEntry.COLUMN_NAME_PROFILE_PIC, DoorboardDbHelper.bitmapToBase64(b2));
            values.put(DoorboardContract.MessageEntry.COLUMN_NAME_DATE_TIME, "03-28-2017 1:00 PM");

            // Insert the new row, returning the primary key value of the new row
            wdb.insert(DoorboardContract.MessageEntry.TABLE_NAME, null, values);
        }
    }

}
