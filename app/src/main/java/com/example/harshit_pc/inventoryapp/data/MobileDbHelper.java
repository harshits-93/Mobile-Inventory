package com.example.harshit_pc.inventoryapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MobileDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "mobileStorage.db";

    public MobileDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_MOBILES_TABLE = "CREATE TABLE " +
                MobileContract.MobileEntry.TABLE_NAME + "( " +
                MobileContract.MobileEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MobileContract.MobileEntry.COLUMN_MOBILE_NAME + " TEXT NOT NULL, " +
                MobileContract.MobileEntry.COLUMN_MOBILE_BRAND + " TEXT, " +
                MobileContract.MobileEntry.COLUMN_MOBILE_QUANTITY + " INTEGER NOT NULL , " +
                MobileContract.MobileEntry.COLUMN_MOBILE_PRICE + " INTEGER NOT NULL DEFAULT 0 );";
        sqLiteDatabase.execSQL(SQL_CREATE_MOBILES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String SQL_DROP_MOBILES_TABLE = "DROP TABLE IF EXISTS " +
                MobileContract.MobileEntry.TABLE_NAME;
        sqLiteDatabase.execSQL(SQL_DROP_MOBILES_TABLE);

        onCreate(sqLiteDatabase);
    }
}
