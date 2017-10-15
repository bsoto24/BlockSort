package com.openlab.blocksort.data.local.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.openlab.blocksort.data.entities.Block;
import com.openlab.blocksort.data.local.session.SessionManager;

import java.util.ArrayList;

/**
 * Created by Bryam Soto on 13/10/2017.
 */

public class SQLiteManager {

    private final String TAG = getClass().getSimpleName();
    private SQLiteDatabase db;
    private SQLiteHelper helper;
    private Context context;
    private boolean sort = false;

    public SQLiteManager(Context context) {
        this.context = context;
        helper = new SQLiteHelper(context, "Blocksort", null, 1);
        db = helper.getWritableDatabase();
    }

    /**
     * Inserta un nuevo bloque en la db
     *
     * @param block
     */
    public void insertBlock(Block block) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(PersistenceContract.BlockEntry.COLUMN_NAME_BLOCK_ID, block.getId());
        contentValues.put(PersistenceContract.BlockEntry.COLUMN_NAME_BLOCK_COLOR, block.getColor());
        contentValues.put(PersistenceContract.BlockEntry.COLUMN_NAME_BLOCK_CLICKS, block.getClicks());
        contentValues.put(PersistenceContract.BlockEntry.COLUMN_NAME_BLOCK_LAST_USE, block.getLastUse());
        contentValues.put(PersistenceContract.BlockEntry.COLUMN_NAME_BLOCK_POSITION, block.getPosition());
        db.insert("BLOCK", null, contentValues);
    }

    /**
     * Funcion que retorna la lista de bloques registrados en la db
     * Los retorna ordenados de acuerdo a la lógica implementada
     *
     * @return
     */
    public ArrayList<Block> getBlocks() {
        ArrayList<Block> blocks = new ArrayList<>();
        Cursor c;
        int position = 0;

        /*Verifica si la funcion de ordenar esta activada para ejecutarla*/
        if (SessionManager.getInstance(context).isTimeToSort()) {
            c = db.rawQuery("SELECT * FROM " + PersistenceContract.BlockEntry.TABLE_NAME + " ORDER BY " + PersistenceContract.BlockEntry.COLUMN_NAME_BLOCK_CLICKS + " DESC, " + PersistenceContract.BlockEntry.COLUMN_NAME_BLOCK_LAST_USE + " DESC, " + PersistenceContract.BlockEntry.COLUMN_NAME_BLOCK_POSITION + " ASC", null);
            sort = true;
        } else {
            c = db.rawQuery("SELECT * FROM " + PersistenceContract.BlockEntry.TABLE_NAME + " ORDER BY " + PersistenceContract.BlockEntry.COLUMN_NAME_BLOCK_POSITION +" ASC", null);
        }

        if (c.moveToFirst()) {
            do {
                Block block;
                if (sort) {
                    block = new Block(c.getInt(0), c.getInt(1), 0, 0, ++position);
                } else {
                    block = new Block(c.getInt(0), c.getInt(1), c.getInt(2), c.getInt(3), ++position);
                }
                blocks.add(block);
                updatePosition(block);
            } while (c.moveToNext());
        }

        /*Si ya ordeno los elementos, resetea los contadores y desactiva la funcion de ordenamiento*/
        if (sort) {
            resetValues();
            SessionManager.getInstance(context).setTimeToSort(false);
            SessionManager.getInstance(context).setLastUse(0);
            sort = false;
        }

        return blocks;
    }

    /**
     * Actualiza la información de los bloques
     *
     * @param block
     */
    public void updateBlock(Block block) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(PersistenceContract.BlockEntry.COLUMN_NAME_BLOCK_CLICKS, block.getClicks());
        contentValues.put(PersistenceContract.BlockEntry.COLUMN_NAME_BLOCK_LAST_USE, block.getLastUse());
        db.update(PersistenceContract.BlockEntry.TABLE_NAME, contentValues, PersistenceContract.BlockEntry.COLUMN_NAME_BLOCK_ID + "=" + block.getId(), null);
    }

    /**
     * Actualiza el valor de la posición del elemento
     */
    public void updatePosition(Block block) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(PersistenceContract.BlockEntry.COLUMN_NAME_BLOCK_POSITION, block.getPosition());
        db.update(PersistenceContract.BlockEntry.TABLE_NAME, contentValues, PersistenceContract.BlockEntry.COLUMN_NAME_BLOCK_ID + "=" + block.getId(), null);
    }

    /**
     * Resetea a 0 el valor de ultimo uso del bloque
     * Resetea a 0 el valor del numero de toques del bloque
     */
    public void resetValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(PersistenceContract.BlockEntry.COLUMN_NAME_BLOCK_CLICKS, 0);
        contentValues.put(PersistenceContract.BlockEntry.COLUMN_NAME_BLOCK_LAST_USE, 0);
        db.update(PersistenceContract.BlockEntry.TABLE_NAME, contentValues, null, null);
    }

}