package com1032.cw1.vs00162.vs00162_todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * This class contains everything to do with the SQLiteDatabase.
 */
public class SQLiteDB {

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TEXT = "txt";
    public static final String COLUMN_DES = "des";
    public static final String COLUMN_DATE_CREATED = "date_created";
    public static final String COLUMN_DATE_DUE = "date_due";
    private static final String DB_NAME = "listDb";
    private static final int DB_VERSION = 1;
    private static final String DB_TABLE_TASKS = "listTasks";
    private static final String DB_CREATE =
            "create table " + DB_TABLE_TASKS + "(" +
                    COLUMN_ID + " integer primary key, " +
                    COLUMN_TEXT + " text, " + COLUMN_DES + " text, " + COLUMN_DATE_CREATED + " text, " +
                    COLUMN_DATE_DUE + " text" + ");";
    private final Context ctx;
    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;

    public SQLiteDB(Context context) {
        this.ctx = context;
    }

    /**
     * Open connection to the database.
     * Create and/or open a database that will be used for reading and writing
     */
    public void open() {
        dbHelper = new DBHelper(ctx, DB_NAME, null, DB_VERSION);
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }

    /**
     * Close connection to the database.
     */
    public void close() {
        if (!dbHelper.equals(null)) {
            dbHelper.close();
        }
    }

    /**
     * Get all data from the table DB_TABLE_TASKS using Cursor.
     */
    public Cursor getAllData() {
        return sqLiteDatabase.query(DB_TABLE_TASKS, null, null, null, null, null, null);
    }

    /**
     * Add a value to the table DB_TABLE_TASKS.
     */
    public void addText(String txt, String txt2, String txt3, String txt4) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TEXT, txt);
        cv.put(COLUMN_DES, txt2);
        cv.put(COLUMN_DATE_CREATED, txt3);
        cv.put(COLUMN_DATE_DUE, txt4);
        sqLiteDatabase.insert(DB_TABLE_TASKS, null, cv);
        Toast toast = Toast.makeText(ctx.getApplicationContext(), "Item Added", Toast.LENGTH_LONG);
        toast.show();
    }

    /**
     * Delete a value from the table DB_TABLE_TASKS.
     */
    public void delRec(long id) {
        sqLiteDatabase.delete(DB_TABLE_TASKS, COLUMN_ID + " = " + id, null);
        Toast toast = Toast.makeText(ctx.getApplicationContext(), "Item Deleted", Toast.LENGTH_LONG);
        toast.show();
    }

    /**
     * Completely clear the database.
     */
    public void clearDatabase(String TABLE_NAME) {
        String clearDBQuery = "DELETE FROM " + TABLE_NAME;
        sqLiteDatabase.execSQL(clearDBQuery);
        Toast toast = Toast.makeText(ctx.getApplicationContext(), "Database Cleared", Toast.LENGTH_LONG);
        toast.show();
    }

    /**
     * Private SQLiteOpenHelper class to create and control the database.
     */
    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, CursorFactory factory,
                        int version) {
            super(context, name, factory, version);
        }

        /**
         * Creating and filling up the database.
         */
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);
        }

        /**
         * This method has not been implemented.
         */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}