package com.openlab.blocksort.data.local.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Bryam Soto on 13/10/2017.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String INT_TYPE = " INTEGER";

    /*Script para la creacion de la tabla de bloques*/
    private String sqlCreateTableBlock =
            "CREATE TABLE " + PersistenceContract.BlockEntry.TABLE_NAME + " (" +
                    PersistenceContract.BlockEntry.COLUMN_NAME_BLOCK_ID + INT_TYPE + " PRIMARY KEY, " +
                    PersistenceContract.BlockEntry.COLUMN_NAME_BLOCK_COLOR + INT_TYPE +" , " +
                    PersistenceContract.BlockEntry.COLUMN_NAME_BLOCK_CLICKS + INT_TYPE +" , " +
                    PersistenceContract.BlockEntry.COLUMN_NAME_BLOCK_LAST_USE + INT_TYPE +" , " +
                    PersistenceContract.BlockEntry.COLUMN_NAME_BLOCK_POSITION + INT_TYPE +" )";

    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreateTableBlock);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PersistenceContract.BlockEntry.TABLE_NAME);
        db.execSQL(sqlCreateTableBlock);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + PersistenceContract.BlockEntry.TABLE_NAME);
        db.execSQL(sqlCreateTableBlock);
    }
}
