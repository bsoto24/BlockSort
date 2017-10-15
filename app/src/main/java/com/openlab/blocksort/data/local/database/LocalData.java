package com.openlab.blocksort.data.local.database;

import android.content.Context;

import com.openlab.blocksort.R;
import com.openlab.blocksort.data.entities.Block;

/**
 * Created by Bryam Soto on 13/10/2017.
 */

public class LocalData {

    private static SQLiteManager db;

    /**
     * Inicializa los datos
     * @param context
     */
    public static void loadBlocks(Context context) {

        db = new SQLiteManager(context);

        db.insertBlock(new Block(1, R.color.red, 0, 0, 1));
        db.insertBlock(new Block(2, R.color.blue, 0, 0, 2));
        db.insertBlock(new Block(3, R.color.green, 0, 0, 3));

    }
}
