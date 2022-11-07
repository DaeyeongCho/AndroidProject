package com.example.membermanagementapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class FeedReaderDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TableInfo.TABLE_NAME +" ("+
                    TableInfo.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    TableInfo.COLUMN_NAME_NAME + "TEXT, " +
                    TableInfo.COLUMN_NAME_PHONE + "INTEGER," +
                    TableInfo.COLUMN_NAME_GENDER + "TEXT, " +
                    TableInfo.COLUMN_NAME_BIRTHDAY_YEAR + "INTEGER," +
                    TableInfo.COLUMN_NAME_BIRTHDAY_MONTH + "INTEGER," +
                    TableInfo.COLUMN_NAME_BIRTHDAY_DAY + "INTEGER," +
                    TableInfo.COLUMN_NAME_INFO + "TEXT)";

    private static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + TableInfo.TABLE_NAME;


    public FeedReaderDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DELETE_TABLE);
        onCreate(db);
    }
}
