package com.doorboard3.doorboardmobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import static com.doorboard3.doorboardmobile.DoorboardContract.MessageEntry.COLUMN_NAME_NAME;

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
        if (!DoorboardDbHelper.containsMessageFromUser(rdb, "Daniel Chen")) {
            // Create dummy user 1
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME_NAME, "Daniel Chen");
            values.put(DoorboardContract.MessageEntry.COLUMN_NAME_STATUS, "Will be 5 min late");
            Bitmap b1 = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.ic_profile_1);
            values.put(DoorboardContract.MessageEntry.COLUMN_NAME_PROFILE_PIC, DoorboardDbHelper.bitmapToBase64(b1));
            values.put(DoorboardContract.MessageEntry.COLUMN_NAME_DATE_TIME, "03-28-2017 08:15 AM");

            // Insert the new row, returning the primary key value of the new row
            wdb.insert(DoorboardContract.MessageEntry.TABLE_NAME, null, values);
        }

        // Check to see if entries have not already been added
        if (!DoorboardDbHelper.containsMessageFromUser(rdb, "John Doe")) {
            // Create dummy user 2
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME_NAME, "John Doe");
            values.put(DoorboardContract.MessageEntry.COLUMN_NAME_STATUS, "Out to lunch. Be back at 1");
            Bitmap b2 = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.ic_profile_2);
            values.put(DoorboardContract.MessageEntry.COLUMN_NAME_PROFILE_PIC, DoorboardDbHelper.bitmapToBase64(b2));
            values.put(DoorboardContract.MessageEntry.COLUMN_NAME_DATE_TIME, "03-28-2017 12:15 PM");

            // Insert the new row, returning the primary key value of the new row
            wdb.insert(DoorboardContract.MessageEntry.TABLE_NAME, null, values);
        }
    }

}
