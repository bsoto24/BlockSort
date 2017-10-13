package com.openlab.blocksort.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.openlab.blocksort.model.Block;
import com.openlab.blocksort.session.SessionManager;

import java.util.ArrayList;

/**
 * Created by Bryam Soto on 13/10/2017.
 */

public class SQLiteManager {

    private SQLiteDatabase db;
    private SQLiteHelper helper;
    private Context context;

    public SQLiteManager(Context context) {
        this.context = context;
        helper = new SQLiteHelper(context, "Blocksort", null, 1);
        db = helper.getWritableDatabase();
    }

    /**
     * Inserta un nuevo bloque en la db
     * @param block
     */
    public void insertBlock(Block block) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", block.getId());
        contentValues.put("COLOR", block.getColor());
        contentValues.put("CLICKS", block.getClicks());
        contentValues.put("POSITION", block.getPosition());
        db.insert("BLOCK", null, contentValues);
    }

    /**
     * Funcion que retorna la lista de bloques registrados en la db
     * @return
     */
    public ArrayList<Block> getBlock() {
        ArrayList<Block> preguntas = new ArrayList<>();
        Cursor c;

        /*Verifica si la funcion de ordenar esta activada para ejecutarla*/
        if (SessionManager.getInstance(context).isTimeToSort()) {
            c = db.rawQuery("SELECT * FROM BLOCK ORDER BY CLICKS DESC, POSITION DESC", null);
        } else {
            c = db.rawQuery("SELECT * FROM BLOCK", null);
        }

        if (c.moveToFirst()) {
            do {
                Block preguntaTO = new Block(c.getInt(0), c.getInt(1), c.getInt(2), c.getInt(3));
                preguntas.add(preguntaTO);
            } while (c.moveToNext());
        }

        resetAllPositions();
        resetAllClicks();

        return preguntas;
    }

    /**
     * Actualiza la información de los bloques
     * @param block
     */
    public void updateBlock(Block block) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("CLICKS", block.getClicks());
        contentValues.put("POSITION", block.getPosition());
        db.update("BLOCK", contentValues, "ID=" + block.getId(), null);
    }

    /**
     * Resetea a 0 el valor de la ultima posicion de los bloques
     */
    public void resetAllPositions() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("POSITION", 0);
        db.update("BLOCK", contentValues, null, null);
    }

    /**
     * Resetea a 0 el valor del numero de toques de los bloques
     */
    public void resetAllClicks() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("CLICKS", 0);
        db.update("BLOCK", contentValues, null, null);
    }
}