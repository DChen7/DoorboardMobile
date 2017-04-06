package com.doorboard3.doorboardmobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

/**
 * Created by danielchen on 3/27/17.
 */

public class DoorboardDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Doorboard.db";

    private static final String SQL_CREATE_MESSAGES =
            "CREATE TABLE " + DoorboardContract.MessageEntry.TABLE_NAME + " (" +
                    DoorboardContract.MessageEntry._ID + " INTEGER PRIMARY KEY," +
                    DoorboardContract.MessageEntry.COLUMN_NAME_ROOM + " TEXT," +
                    DoorboardContract.MessageEntry.COLUMN_NAME_NAME + " TEXT," +
                    DoorboardContract.MessageEntry.COLUMN_NAME_PROFILE_PIC + " TEXT," +
                    DoorboardContract.MessageEntry.COLUMN_NAME_DATE_TIME + " TEXT," +
                    DoorboardContract.MessageEntry.COLUMN_NAME_STATUS + " TEXT)";

    private static final String SQL_DELETE_MESSAGES =
            "DROP TABLE IF EXISTS " + DoorboardContract.MessageEntry.TABLE_NAME;

    private static final String SQL_CREATE_SCHEDULES =
            "CREATE TABLE " + DoorboardContract.ScheduleEntry.TABLE_NAME + " (" +
                    DoorboardContract.ScheduleEntry._ID + " INTEGER PRIMARY KEY," +
                    DoorboardContract.ScheduleEntry.COLUMN_NAME_ROOM + " TEXT," +
                    DoorboardContract.ScheduleEntry.COLUMN_NAME_NAME + " TEXT," +
                    DoorboardContract.ScheduleEntry.COLUMN_NAME_DATE + " TEXT," +
                    DoorboardContract.ScheduleEntry.COLUMN_NAME_DESCRIPTION + " TEXT," +
                    DoorboardContract.ScheduleEntry.COLUMN_NAME_START_TIME + " TEXT," +
                    DoorboardContract.ScheduleEntry.COLUMN_NAME_END_TIME + " TEXT)";

    private static final String SQL_DELETE_SCHEDULES =
            "DROP TABLE IF EXISTS " + DoorboardContract.ScheduleEntry.TABLE_NAME;

    public DoorboardDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_MESSAGES);
        db.execSQL(SQL_CREATE_SCHEDULES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_MESSAGES);
        db.execSQL(SQL_DELETE_SCHEDULES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public boolean roomContainsMessages(String room) {
        String query = "SELECT * FROM " + DoorboardContract.MessageEntry.TABLE_NAME + " WHERE " +
                DoorboardContract.MessageEntry.COLUMN_NAME_ROOM + " = '" + room + "'";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    // Get messages in reverse order, so that more recent messages go to the top
    public ArrayList<Message> getMessagesForRoom(String room) {
        String query = "SELECT * FROM " + DoorboardContract.MessageEntry.TABLE_NAME + " WHERE " +
                DoorboardContract.MessageEntry.COLUMN_NAME_ROOM + " = '" + room + "'";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        ArrayList messages = new ArrayList<Message>();
        while(cursor.moveToNext()) {
            String pic = cursor.getString(cursor.getColumnIndexOrThrow(
                    DoorboardContract.MessageEntry.COLUMN_NAME_PROFILE_PIC));
            Bitmap profilePic = base64ToBitmap(pic);
            String name = cursor.getString(cursor.getColumnIndexOrThrow(
                    DoorboardContract.MessageEntry.COLUMN_NAME_NAME));
            String status = cursor.getString(cursor.getColumnIndexOrThrow(
                    DoorboardContract.MessageEntry.COLUMN_NAME_STATUS));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(
                    DoorboardContract.MessageEntry.COLUMN_NAME_DATE_TIME));
            messages.add(new Message(profilePic, name, status, date));
        }
        cursor.close();

        // Reverse message order
        ArrayList reverseOrderMessages = new ArrayList<Message>();
        for (int i = messages.size() - 1; i >= 0; i--) {
            reverseOrderMessages.add(messages.get(i));
        }

        return reverseOrderMessages;
    }

    public void clearDB() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(SQL_DELETE_MESSAGES);
        db.execSQL(SQL_DELETE_SCHEDULES);
        onCreate(db);
    }

    public Message getMessageForNameAndRoom(String name, String room) {
        String query = "SELECT * FROM " + DoorboardContract.MessageEntry.TABLE_NAME + " WHERE " +
                DoorboardContract.MessageEntry.COLUMN_NAME_ROOM + " = '" + room + "' AND " +
                DoorboardContract.MessageEntry.COLUMN_NAME_NAME + " = '" + name + "'";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return null;
        }
        cursor.moveToNext();
        String pic = cursor.getString(cursor.getColumnIndexOrThrow(
                DoorboardContract.MessageEntry.COLUMN_NAME_PROFILE_PIC));
        Bitmap profilePic = base64ToBitmap(pic);
        String status = cursor.getString(cursor.getColumnIndexOrThrow(
                DoorboardContract.MessageEntry.COLUMN_NAME_STATUS));
        String date = cursor.getString(cursor.getColumnIndexOrThrow(
                DoorboardContract.MessageEntry.COLUMN_NAME_DATE_TIME));
        cursor.close();
        return new Message(profilePic, name, status, date);
    }

    public void deleteMessage(String room) {
        String query = "DELETE FROM " + DoorboardContract.MessageEntry.TABLE_NAME + " WHERE " +
                DoorboardContract.MessageEntry.COLUMN_NAME_NAME + " = 'Daniel Chen' AND " +
                DoorboardContract.MessageEntry.COLUMN_NAME_ROOM + " = '" + room + "'";
        this.getWritableDatabase().execSQL(query);
    }

    public void insertMessage(Context ctx, String room, String message) {
        ContentValues values = new ContentValues();
        values.put(DoorboardContract.MessageEntry.COLUMN_NAME_ROOM, room);
        values.put(DoorboardContract.MessageEntry.COLUMN_NAME_NAME, "Daniel Chen");
        values.put(DoorboardContract.MessageEntry.COLUMN_NAME_STATUS, message);
        Bitmap b1 = BitmapFactory.decodeResource(ctx.getResources(), R.mipmap.ic_profile_1);
        values.put(DoorboardContract.MessageEntry.COLUMN_NAME_PROFILE_PIC, DoorboardDbHelper.bitmapToBase64(b1));
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm a", Locale.US);
        values.put(DoorboardContract.MessageEntry.COLUMN_NAME_DATE_TIME, sdf.format(Calendar.getInstance().getTime()));

        // Insert the new row, returning the primary key value of the new row
        this.getWritableDatabase().insert(DoorboardContract.MessageEntry.TABLE_NAME, null, values);
    }

    public void saveEvent(String eventName, String date, String startTime, String endTime, String room, String description) {
        ContentValues values = new ContentValues();
        values.put(DoorboardContract.ScheduleEntry.COLUMN_NAME_NAME, eventName);
        values.put(DoorboardContract.ScheduleEntry.COLUMN_NAME_DATE, date);
        values.put(DoorboardContract.ScheduleEntry.COLUMN_NAME_START_TIME, startTime);
        values.put(DoorboardContract.ScheduleEntry.COLUMN_NAME_END_TIME, endTime);
        values.put(DoorboardContract.ScheduleEntry.COLUMN_NAME_ROOM, room);
        values.put(DoorboardContract.ScheduleEntry.COLUMN_NAME_DESCRIPTION, description);

        this.getWritableDatabase().insert(DoorboardContract.ScheduleEntry.TABLE_NAME, null, values);
    }

    public boolean containsEvent(CalendarDay date) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMMM dd, yyyy", Locale.US);
        Calendar c = Calendar.getInstance();
        c.set(date.getYear(), date.getMonth(), date.getDay());
        String dateStr = sdf.format(c.getTime());
        String query = "SELECT * FROM " + DoorboardContract.ScheduleEntry.TABLE_NAME + " WHERE " +
                DoorboardContract.ScheduleEntry.COLUMN_NAME_DATE + " = '" + dateStr + "'";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    public ArrayList<ScheduleEvent> getEventsForDate(int year, int month, int dayOfMonth) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMMM dd, yyyy", Locale.US);
        Calendar c = Calendar.getInstance();
        c.set(year, month, dayOfMonth);
        String dateStr = sdf.format(c.getTime());
        String query = "SELECT * FROM " + DoorboardContract.ScheduleEntry.TABLE_NAME + " WHERE " +
                DoorboardContract.ScheduleEntry.COLUMN_NAME_DATE + " = '" + dateStr + "'";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        ArrayList<ScheduleEvent> events = new ArrayList<ScheduleEvent>();
        while(cursor.moveToNext()) {
            String ID = cursor.getString(cursor.getColumnIndexOrThrow(
                    DoorboardContract.ScheduleEntry._ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(
                    DoorboardContract.ScheduleEntry.COLUMN_NAME_NAME));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(
                    DoorboardContract.ScheduleEntry.COLUMN_NAME_DATE));
            String startTime = cursor.getString(cursor.getColumnIndexOrThrow(
                    DoorboardContract.ScheduleEntry.COLUMN_NAME_START_TIME));
            String endTime = cursor.getString(cursor.getColumnIndexOrThrow(
                    DoorboardContract.ScheduleEntry.COLUMN_NAME_END_TIME));
            String room = cursor.getString(cursor.getColumnIndexOrThrow(
                    DoorboardContract.ScheduleEntry.COLUMN_NAME_ROOM));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(
                    DoorboardContract.ScheduleEntry.COLUMN_NAME_DESCRIPTION));
            events.add(new ScheduleEvent(ID, name, date, startTime, endTime, room, description));
        }
        cursor.close();
        Collections.sort(events, new Comparator<ScheduleEvent>() {
            @Override
            public int compare(ScheduleEvent o1, ScheduleEvent o2) {
                return o1.startTime.compareTo(o2.startTime);
            }
        });
        return events;
    }

//    public ArrayList<ScheduleEvent> getEventsForDate(CalendarDay date) {
//        int month = date.getMonth() + 1;
//        int day = date.getDay();
//        String monthDay = month + "/" + day;
//        SimpleDateFormat simpleDateformat = new SimpleDateFormat("E"); // the day of the week abbreviated
//        String dayOfWeek = simpleDateformat.format(date);
//
//        String query = "SELECT * FROM " + DoorboardContract.ScheduleEntry.TABLE_NAME;
//        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
//        if(cursor.getCount() <= 0){
//            cursor.close();
//            return null;
//        }
//
//        ArrayList events = new ArrayList<ScheduleEvent>();
//        while(cursor.moveToNext()) {
//            String entryDate = cursor.getString(cursor.getColumnIndexOrThrow(
//                    DoorboardContract.ScheduleEntry.COLUMN_NAME_DATE));
//            String repeat = cursor.getString(cursor.getColumnIndexOrThrow(
//                    DoorboardContract.ScheduleEntry.COLUMN_NAME_END_REPEAT));
//            String entryDayOfWeek = cursor.getString(cursor.getColumnIndexOrThrow(
//                    DoorboardContract.ScheduleEntry.COLUMN_NAME_DAY_OF_WEEK));
//            String endRepeatDate = cursor.getString(cursor.getColumnIndexOrThrow(
//                    DoorboardContract.ScheduleEntry.COLUMN_NAME_END_REPEAT));
//            if (entryDate.equals(monthDay) || repeated(entryDate, entryDayOfWeek, repeat,
//                    endRepeatDate, month, day, dayOfWeek)) {
//                String name = cursor.getString(cursor.getColumnIndexOrThrow(
//                        DoorboardContract.ScheduleEntry.COLUMN_NAME_NAME));
//                String ID = cursor.getString(cursor.getColumnIndexOrThrow(
//                        DoorboardContract.ScheduleEntry._ID));
//                String startTime = cursor.getString(cursor.getColumnIndexOrThrow(
//                        DoorboardContract.ScheduleEntry.COLUMN_NAME_START_TIME));
//                String endTime = cursor.getString(cursor.getColumnIndexOrThrow(
//                        DoorboardContract.ScheduleEntry.COLUMN_NAME_END_REPEAT));
//                events.add(new ScheduleEvent(ID, name, startTime, endTime));
//            }
//        }
//        cursor.close();
//
//        return events.sort(new Comparator() {
//            @Override
//            public int compare(Object o1, Object o2) {
//                return ((ScheduleEvent) o1).startTime.
//            }
//        })
//    }

    private boolean repeated(String entryDate, String entryDayOfWeek, String repeat,
                             String endRepeatDate, int month, int day, String dayOfWeek) {
        // if the end repeat date is smaller than the date, return false
        if (endRepeatDate.compareTo(month + "/" + day) < 0) {
            return false;
        }
        int entryDay = Integer.parseInt(entryDate.substring(entryDate.indexOf("/") + 1));
        switch (repeat) {
            case "never":
                return false;
            case "daily":
                return true;
            case "weekly":
                return entryDayOfWeek.equals(dayOfWeek);
            case "monthly":
                return day == entryDay;
            default:
                return false;
        }
    }

    public static String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public static Bitmap base64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }
}

