package com.doorboard3.doorboardmobile;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import static com.doorboard3.doorboardmobile.DoorboardContract.MessageEntry.COLUMN_NAME_NAME;

/**
 * Created by danielchen on 3/27/17.
 */

public class DoorboardDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Doorboard.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DoorboardContract.MessageEntry.TABLE_NAME + " (" +
                    DoorboardContract.MessageEntry._ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_NAME + " TEXT," +
                    DoorboardContract.MessageEntry.COLUMN_NAME_PROFILE_PIC + " TEXT," +
                    DoorboardContract.MessageEntry.COLUMN_NAME_DATE_TIME + " TEXT," +
                    DoorboardContract.MessageEntry.COLUMN_NAME_STATUS + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DoorboardContract.MessageEntry.TABLE_NAME;

    public DoorboardDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public static boolean containsMessageFromUser(SQLiteDatabase db, String name) {
        String query = "SELECT * FROM " + DoorboardContract.MessageEntry.TABLE_NAME + " WHERE " +
                COLUMN_NAME_NAME + " = '" + name + "'";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public static ArrayList<Message> getMessages(SQLiteDatabase db) {
        String query = "SELECT * FROM " + DoorboardContract.MessageEntry.TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
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

        return messages;
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

