package com.openlab.blocksort.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Bryam Soto on 13/10/2017.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    /*Script para la creacion de la tabla de bloques*/
    private String sqlCreateBlock =
            "CREATE TABLE BLOCK (" +
                    "ID INTEGER PRIMARY KEY, " +
                    "COLOR INTEGER, " +
                    "CLICKS INTEGER, " +
                    "POSITION INTEGER)";

    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreateBlock);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS PREGUNTAS");
        db.execSQL(sqlCreateBlock);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
        db.execSQL("DROP TABLE IF EXISTS PREGUNTAS");
        db.execSQL(sqlCreateBlock);
    }
}
