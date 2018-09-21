package info.androidhive.sqlite.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.sqlite.database.model.Event;

public class EventDatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "events_db";


    public EventDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create events table
        db.execSQL(Event.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Event.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insertEvent(Event event) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Event.COLUMN_TITLE, event.getTitle());
        values.put(Event.COLUMN_TIMESTAMP, event.getTimestamp());
        values.put(Event.COLUMN_FROM, event.getFrom());
        values.put(Event.COLUMN_TO, event.getTo());

        // insert row
        long id = db.insert(Event.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public Event getEvent(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Event.TABLE_NAME,
                new String[]{Event.COLUMN_ID, Event.COLUMN_TITLE, Event.COLUMN_TIMESTAMP,
                        Event.COLUMN_FROM, Event.COLUMN_TO},
                Event.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare event object
        Event event = new Event(
                cursor.getInt(cursor.getColumnIndex(Event.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Event.COLUMN_TITLE)),
                cursor.getString(cursor.getColumnIndex(Event.COLUMN_TIMESTAMP)),
                cursor.getString(cursor.getColumnIndex(Event.COLUMN_FROM)),
                cursor.getString(cursor.getColumnIndex(Event.COLUMN_TO)));

        // close the db connection
        cursor.close();

        return event;
    }

    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Event.TABLE_NAME + " ORDER BY " +
                Event.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Event event = new Event();
                event.setId(cursor.getInt(cursor.getColumnIndex(Event.COLUMN_ID)));
                event.setTitle(cursor.getString(cursor.getColumnIndex(Event.COLUMN_TITLE)));
                event.setTimestamp(cursor.getString(cursor.getColumnIndex(Event.COLUMN_TIMESTAMP)));
                event.setFrom(cursor.getString(cursor.getColumnIndex(Event.COLUMN_FROM)));
                event.setTo(cursor.getString(cursor.getColumnIndex(Event.COLUMN_TO)));

                events.add(event);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return events list
        return events;
    }

    public int getEventsCount() {
        String countQuery = "SELECT  * FROM " + Event.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public int updateEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Event.COLUMN_TITLE, event.getTitle());

        // updating row
        return db.update(Event.TABLE_NAME, values, Event.COLUMN_ID + " = ?",
                new String[]{String.valueOf(event.getId())});
    }

    public void deleteEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Event.TABLE_NAME, Event.COLUMN_ID + " = ?",
                new String[]{String.valueOf(event.getId())});
        db.close();
    }
}
