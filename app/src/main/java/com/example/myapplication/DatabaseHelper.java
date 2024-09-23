package com.example.myapplication;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "EventCalendar.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USER = "users";
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USER_NAME = "username";
    private static final String COLUMN_USER_PASSWORD = "password";

    private static final String TABLE_EVENT = "events";
    private static final String COLUMN_EVENT_ID = "event_id";
    private static final String COLUMN_EVENT_USER_ID = "user_id";
    private static final String COLUMN_EVENT_TITLE = "title";
    private static final String COLUMN_EVENT_DATE = "date";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USER_NAME + " TEXT,"
                + COLUMN_USER_PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_USER_TABLE);

        String CREATE_EVENT_TABLE = "CREATE TABLE " + TABLE_EVENT + "("
                + COLUMN_EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_EVENT_USER_ID + " INTEGER,"
                + COLUMN_EVENT_TITLE + " TEXT,"
                + COLUMN_EVENT_DATE + " TEXT" + ")";
        db.execSQL(CREATE_EVENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENT);
        onCreate(db);
    }

    public boolean insertUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, username);
        values.put(COLUMN_USER_PASSWORD, password);
        long result = db.insert(TABLE_USER, null, values);
        return result != -1;
    }

    public Cursor getUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_USER, new String[]{COLUMN_USER_ID}, COLUMN_USER_NAME + "=? AND " + COLUMN_USER_PASSWORD + "=?",
                new String[]{username, password}, null, null, null);
    }

    public boolean insertEvent(int userId, String title, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EVENT_USER_ID, userId);
        values.put(COLUMN_EVENT_TITLE, title);
        values.put(COLUMN_EVENT_DATE, date);
        long result = db.insert(TABLE_EVENT, null, values);
        return result != -1;
    }

    public Cursor getUserEvents(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_EVENT, null, COLUMN_EVENT_USER_ID + "=?",
                new String[]{String.valueOf(userId)}, null, null, COLUMN_EVENT_DATE + " ASC");
    }

    public String getColumnEventTitle() {
        return COLUMN_EVENT_TITLE;
    }

    public String getColumnEventDate() {
        return COLUMN_EVENT_DATE;
    }
}
