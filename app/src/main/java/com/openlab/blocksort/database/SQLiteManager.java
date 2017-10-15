package com.openlab.blocksort.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.openlab.blocksort.model.Block;
import com.openlab.blocksort.session.SessionManager;

import java.util.ArrayList;

/**
 * Created by Bryam Soto on 13/10/2017.
 */

public class SQLiteManager {

    private final String TAG = getClass().getSimpleName();
    private SQLiteDatabase db;
    private SQLiteHelper helper;
    private Context context;
    private boolean reset = false;

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
        contentValues.put("LAST_USE", block.getLastUse());
        contentValues.put("POSITION", block.getPosition());
        db.insert("BLOCK", null, contentValues);
    }

    /**
     * Funcion que retorna la lista de bloques registrados en la db
     * Los retorna ordenados de acuerdo a la lógica implementada
     * @return
     */
    public ArrayList<Block> getBlocks() {
        ArrayList<Block> blocks = new ArrayList<>();
        Cursor c;
        int position = 0;

        /*Verifica si la funcion de ordenar esta activada para ejecutarla*/
        if (SessionManager.getInstance(context).isTimeToSort()) {
            c = db.rawQuery("SELECT * FROM BLOCK ORDER BY CLICKS DESC, LAST_USE DESC", null);
            SessionManager.getInstance(context).setTimeToSort(false);
            Log.e(TAG, "Ordenamiento desactivado");
            reset = true;
        } else {
            c = db.rawQuery("SELECT * FROM BLOCK ORDER BY POSITION ASC", null);
        }

        Log.e(TAG, "Listando bloques");

        if (c.moveToFirst()) {
            do {
                Block block = new Block(c.getInt(0), c.getInt(1), c.getInt(2), c.getInt(3), c.getInt(4));
                block.setPosition(++position);
                blocks.add(block);
                updatePosition(block);
                Log.e("SQLiteManager", block.toString());
            } while (c.moveToNext());
        }

        if(reset){
            resetValues();
            reset = false;
        }

        return blocks;
    }

    /**
     * Actualiza la información de los bloques
     * @param block
     */
    public void updateBlock(Block block) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("CLICKS", block.getClicks());
        contentValues.put("LAST_USE", block.getLastUse());
        db.update("BLOCK", contentValues, "ID=" + block.getId(), null);
    }

    /**
     * Actualiza el valor de la posición del elemento
     */
    public void updatePosition(Block block) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("POSITION", block.getPosition());
        db.update("BLOCK", contentValues, "ID=" + block.getId(), null);
    }

    /**
     * Resetea a 0 el valor de ultimo uso del bloque
     * Resetea a 0 el valor del numero de toques del bloque
     */
    public void resetValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("CLICKS", 0);
        contentValues.put("LAST_USE", 0);
        db.update("BLOCK", contentValues, null, null);
    }

}