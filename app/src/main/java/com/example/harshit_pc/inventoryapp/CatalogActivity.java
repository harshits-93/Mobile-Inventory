package com.example.harshit_pc.inventoryapp;

import android.content.ContentValues;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.harshit_pc.inventoryapp.data.MobileContract.MobileEntry;

public class CatalogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    private void insertDummyData() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MobileEntry.COLUMN_MOBILE_NAME, "Iphone 6");
        contentValues.put(MobileEntry.COLUMN_MOBILE_BRAND, "Apple");
        contentValues.put(MobileEntry.COLUMN_MOBILE_QUANTITY, 5);
        contentValues.put(MobileEntry.COLUMN_MOBILE_PRICE, 24800);
        contentValues.put(MobileEntry.COLUMN_SUPPLIER_NAME, "Vineet");
        contentValues.put(MobileEntry.COLUMN_SUPPLIER_EMAIL, "vineet.jain1@gmail.com");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                // Do nothing for now
                //insertData();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                //deleteAllPets();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
