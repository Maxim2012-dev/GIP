package com.example.fitflex;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAAM = "MijnDatabank";
    public static final String TB_NAAM = "leden";
    public static final String naam = "naam";
    public static final String email = "naam";
    public static final String pass = "naam";

    public DBHelper(Context context) {

        super(context, DB_NAAM, null, 1);

    }

    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "create table leden " +
                        "(id integer primary key, naam text,telefoonnummer text,email text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
