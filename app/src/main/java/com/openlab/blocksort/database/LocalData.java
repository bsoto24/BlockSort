package com.openlab.blocksort.database;

import android.content.Context;

import com.openlab.blocksort.R;
import com.openlab.blocksort.model.Block;

/**
 * Created by Bryam Soto on 13/10/2017.
 */

public class LocalData {

    private static SQLiteManager db;

    /**
     * Inicializa los datos
     * @param context
     */
    public static void load(Context context) {

        db = new SQLiteManager(context);

        db.insertBlock(new Block(1, R.color.red, 0, 0));
        db.insertBlock(new Block(2, R.color.blue, 0, 0));
        db.insertBlock(new Block(3, R.color.green, 0, 0));

    }
}
