package com.example.myrestaurantapp.sqLite;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqLite extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "user_details.db";
    private static final int DATABASE_VERSION = 1;

    public SqLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS user_db (email TEXT, access_token TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Example upgrade code
        if (oldVersion < 2) {
            // Add a new column in version 2
            db.execSQL("ALTER TABLE user_db ADD COLUMN new_column TEXT");
        }
        // Handle other version upgrades as needed
    }
    public void insertUserDetails(String email, String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("email", email);
        values.put("id", id);
        db.insert("user_db", null, values);
        db.close();
    }
}
