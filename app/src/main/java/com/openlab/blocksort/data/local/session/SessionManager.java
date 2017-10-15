package com.openlab.blocksort.data.local.session;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Bryam Soto on 13/10/2017.
 */

public class SessionManager {

    private static final String PREFERENCE_NAME = "blocksort_preference";
    private int PRIVATE_MODE = 0;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context context;
    private static SessionManager sessionManager;

    public static final String LOAD_DATA = "loadData";      //Verifica si la informacion ha sido cargada
    public static final String TIME_TO_SORT = "timeToSort"; //Verifica si la funcionalidad de ordeaniemto ha sido activada
    public static final String LAST_USE = "lastUse";        //Valor que indica el ultimo elemento usado

    public static SessionManager getInstance(Context context) {
        if (sessionManager == null) {
            sessionManager = new SessionManager(context);
        }
        return sessionManager;
    }

    private SessionManager(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREFERENCE_NAME, PRIVATE_MODE);
        editor = preferences.edit();
    }

    public void setTimeToSort(boolean timeToSort) {
        editor.putBoolean(TIME_TO_SORT, timeToSort);
        editor.commit();
    }

    public boolean isTimeToSort() {
        return preferences.getBoolean(TIME_TO_SORT, false);
    }

    public void setLoadData(boolean loadData) {
        editor.putBoolean(LOAD_DATA, loadData);
        editor.commit();
    }

    public boolean isLoadData() {
        return preferences.getBoolean(LOAD_DATA, false);
    }

    public void setLastUse(int lastUse) {
        editor.putInt(LAST_USE, lastUse);
        editor.commit();
    }

    public int getLastUse() {
        return preferences.getInt(LAST_USE, 0);
    }
}
