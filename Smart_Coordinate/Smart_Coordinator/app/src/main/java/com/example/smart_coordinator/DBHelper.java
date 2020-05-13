package com.example.smart_coordinator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DBHelper {
    //Database Version
    private static final int DATABASE_VERSION = 1;
    private final Context mContext;

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    //Database Name
    private static final String DATABASE_NAME = "database_name";

    // Table Names
    private static final String DB_TABLE = "table_image";

    // column names
    private static final String KEY_ID = "image_id";
    private static final String KEY_IMAGE = "image_data";

    //Table create statement
    private static final String CREATE_TABLE_IMAGE = "CREATE TABLE " + DB_TABLE + "("+
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_IMAGE + " BLOB NOT NULL );";

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //creating table
            db.execSQL(CREATE_TABLE_IMAGE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //on upgrade drop older tables
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);

            //create new table
            onCreate(db);
        }
    }

    public void Reset() {
        dbHelper.onUpgrade(this.db, 1,1);
    }

    public DBHelper(Context ctx) {
        mContext = ctx;
        dbHelper = new DatabaseHelper(mContext);
    }

    public DBHelper open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public DBHelper openRead() throws SQLException {
        db = dbHelper.getReadableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }


    public void onCreateItem(byte[] image) {

        ContentValues cv = new ContentValues();
        cv.put(KEY_IMAGE, image);

        db.insert(DB_TABLE, null, cv);
    }

    public byte[] onReadItem() {
        Cursor cursor = db.query(false, DB_TABLE, new String[]{KEY_ID, KEY_IMAGE},
                null, null, null, null,
                KEY_ID + " DESC", "1");


        if(cursor.moveToFirst())
        {
            byte [] image = cursor.getBlob(cursor.getColumnIndex(KEY_IMAGE));
            cursor.close();
            return image;
        }
        cursor.close();
        return null;

    }

}
