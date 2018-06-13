package com.example.harshit_pc.inventoryapp.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class MobileProvider extends ContentProvider {
    /**
     * Tag for the log messages
     */
    public static final String LOG_TAG = MobileProvider.class.getSimpleName();

    /*Global variable for MobileDbHelper class*/
    private MobileDbHelper mDbHelper;

    /**
     * URI matcher code for the content URI for the mobiles table
     */
    private static final int MOBILES = 100;

    /**
     * URI matcher code for the content URI for the single mobile in mobiles table
     */
    private static final int MOBILE_ID = 101;

    /**
     * UriMatcher object to match a content URI to a corresponding code.
     * The input passed into the constructor represents the code to return for the root URI.
     * It's common to use NO_MATCH as the input for this case.
     */
    private static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Static initializer. This is run the first time anything is called from this class.
    static {
        // The calls to addURI() go here, for all of the content URI patterns that the provider
        // should recognize. All paths added to the UriMatcher have a corresponding code to return
        // when a match is found.
        matcher.addURI(MobileContract.CONTENT_AUTHORITY, MobileContract.PATH_MOBILES, MOBILES);
        matcher.addURI(MobileContract.CONTENT_AUTHORITY, MobileContract.PATH_MOBILES + "/#", MOBILE_ID);
    }

    @Override
    public boolean onCreate() {
        // To initialize your content provider on startup.
        mDbHelper = new MobileDbHelper(getContext());
        return true;
    }

    private Uri insertPet(Uri uri, ContentValues values) {
        //Sanity check for mobile name
        String name = values.getAsString(MobileContract.MobileEntry.COLUMN_MOBILE_NAME);
        if (name == null) {
            throw new IllegalArgumentException("Mobile requires a name");
        }

        //Sanity check for mobile price
        Double price = values.getAsDouble(MobileContract.MobileEntry.COLUMN_MOBILE_PRICE);
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }

        //Sanity check for supplier name
        String supplierName = values.getAsString(MobileContract.MobileEntry.COLUMN_SUPPLIER_NAME);
        if (supplierName == null) {
            throw new IllegalArgumentException("Must require a supplier name");
        }

        //Sanity check for supplier email
        String supplierEmail = values.getAsString(MobileContract.MobileEntry.COLUMN_SUPPLIER_EMAIL);
        if (supplierEmail == null) {
            throw new IllegalArgumentException("Must requires a supplier email");
        }

        // No need to check the brand, any value is valid (including null).

        // Get writable database
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        // Insert the new mobile with the given values
        long rowId = db.insert(MobileContract.MobileEntry.TABLE_NAME, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (rowId == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);
        // Once we know the ID of the new row in the table,
        // return the new URI with the ID appended to the end of it
        return ContentUris.withAppendedId(uri, rowId);

    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final int match = matcher.match(uri);
        switch (match) {
            case MOBILES:
                return insertPet(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor;
        int match = matcher.match(uri);
        switch (match) {
            case MOBILES:
                cursor = db.query(MobileContract.MobileEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case MOBILE_ID:
                selection = MobileContract.MobileEntry._ID + " =? ";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(MobileContract.MobileEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Cannot query unknown URI " + uri);
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        final int match = matcher.match(uri);
        switch (match) {
            case MOBILES:
                return updatePet(uri, values, selection, selectionArgs);
            case MOBILE_ID:
                // For the PET_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = MobileContract.MobileEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updatePet(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    /**
     * Update mobiles in the database with the given content values. Apply the changes to the rows
     * specified in the selection and selection arguments (which could be 0 or 1 or more mobiles).
     * Return the number of rows that were successfully updated.
     */
    private int updatePet(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }

        //Sanity check for mobile name
        if (values.containsKey(MobileContract.MobileEntry.COLUMN_MOBILE_NAME)) {
            String name = values.getAsString(MobileContract.MobileEntry.COLUMN_MOBILE_NAME);
            if (name == null) {
                throw new IllegalArgumentException("Mobile requires a name");
            }
        }

        //Sanity check for mobile price
        if (values.containsKey(MobileContract.MobileEntry.COLUMN_MOBILE_PRICE)) {
            Double price = values.getAsDouble(MobileContract.MobileEntry.COLUMN_MOBILE_PRICE);
            if (price < 0) {
                throw new IllegalArgumentException("Price cannot be negative");
            }
        }

        //Sanity check for supplier name
        if (values.containsKey(MobileContract.MobileEntry.COLUMN_SUPPLIER_NAME)) {
            String supplierName = values.getAsString(MobileContract.MobileEntry.COLUMN_SUPPLIER_NAME);
            if (supplierName == null) {
                throw new IllegalArgumentException("Must require a supplier name");
            }
        }

        //Sanity check for supplier email
        if (values.containsKey(MobileContract.MobileEntry.COLUMN_SUPPLIER_NAME)) {
            String supplierEmail = values.getAsString(MobileContract.MobileEntry.COLUMN_SUPPLIER_EMAIL);
            if (supplierEmail == null) {
                throw new IllegalArgumentException("Must requires a supplier email");
            }
        }

        // No need to check the brand, any value is valid (including null).

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        //returning the no of rows updated
        return db.update(MobileContract.MobileEntry.TABLE_NAME, values, selection, selectionArgs);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Get writable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        final int match = matcher.match(uri);
        switch (match) {
            case MOBILES:
                // Delete all rows that match the selection and selection args
                return database.delete(MobileContract.MobileEntry.TABLE_NAME, selection, selectionArgs);
            case MOBILE_ID:
                // Delete a single row given by the ID in the URI
                selection = MobileContract.MobileEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return database.delete(MobileContract.MobileEntry.TABLE_NAME, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
    }

    @Override
    public String getType(Uri uri) {
        // It handle requests for the MIME type of the data
        // at the given URI.
        final int match = matcher.match(uri);
        switch (match) {
            case MOBILES:
                return MobileContract.MobileEntry.CONTENT_LIST_TYPE;
            case MOBILE_ID:
                return MobileContract.MobileEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

}
