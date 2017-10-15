package com.openlab.blocksort.data.local.database;

import android.provider.BaseColumns;

/**
 * Created by Bryam Soto on 15/10/2017.
 */

public class PersistenceContract {

    public static abstract class BlockEntry implements BaseColumns {

        public static final String TABLE_NAME = "BLOCK";
        public static final String COLUMN_NAME_BLOCK_ID = "ID";
        public static final String COLUMN_NAME_BLOCK_COLOR = "COLOR";
        public static final String COLUMN_NAME_BLOCK_CLICKS = "CLICKS";
        public static final String COLUMN_NAME_BLOCK_LAST_USE = "LAST_USE";
        public static final String COLUMN_NAME_BLOCK_POSITION = "POSITION";

    }
}
