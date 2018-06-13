package com.example.harshit_pc.inventoryapp.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class MobileContract {
    private MobileContract() {
    }

    /**
     * The "Content authority" is a name for the entire content provider, similar to the
     * relationship between a domain name and its website. A convenient string to use for the
     * content authority is the package name for the app, which is guaranteed to be unique on the
     * device.
     */
    public static final String CONTENT_AUTHORITY = "com.example.harshit_pc.inventoryapp.data";

    /**
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Possible path (appended to base content URI for possible URI's)
     * For instance, content://com.example.android.pets/pets/ is a valid path for
     * looking at pet data. content://com.example.android.pets/staff/ will fail,
     * as the ContentProvider hasn't been given any information on what to do with "staff".
     */
    public static final String PATH_MOBILES = "mobiles";

    /**
     * Inner class that defines constant values for the mobiles database table.
     * Each entry in the table represents a single pet.
     */
    public static final class MobileEntry implements BaseColumns {

        /** The content URI to access the mobile data in the provider */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,PATH_MOBILES);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of mobiles.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOBILES;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single mobile.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOBILES;

        /*String Constant for table name mobiles*/
        public static final String TABLE_NAME = "mobiles";

        /*String Constants for mobiles table columns*/
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_MOBILE_NAME = "name";
        public static final String COLUMN_MOBILE_BRAND = "brand";
        public static final String COLUMN_MOBILE_QUANTITY = "quantity";
        public static final String COLUMN_MOBILE_PRICE = "price";
        public static final String COLUMN_SUPPLIER_NAME = "supplier name";
        public static final String COLUMN_SUPPLIER_EMAIL = "supplier email";

    }
}
