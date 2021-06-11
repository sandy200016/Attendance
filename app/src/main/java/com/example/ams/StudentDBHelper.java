package com.example.ams;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.ViewParent;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class StudentDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="Attendence";
    public static final int DATABASE_VERSION=1;


    public StudentDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public Cursor Details() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select SUBJECT,SEMESTER from DETAILS",null);
        return res;
    }
    public void Delete(){
        Cursor c= Details();
        if (c.getCount() > 10){
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("delete from DETAILS");

        }


    }




}
