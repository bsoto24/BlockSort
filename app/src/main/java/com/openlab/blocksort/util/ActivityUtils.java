package com.openlab.blocksort.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Bryam Soto on 15/10/2017.
 */

public class ActivityUtils {

    /**
     * Método para iniciar una transición entre activities
     * Se puede pasar parámetros
     * Se puede especificar si la actividad origen es destruida
     * @param activityOrigin
     * @param bundle
     * @param activityDestiny
     * @param destroy
     */
    public static void nextActivity(Activity activityOrigin, Bundle bundle, Class<?> activityDestiny, boolean destroy) {
        Intent intent = new Intent(activityOrigin, activityDestiny);
        if (bundle != null) intent.putExtras(bundle);
        activityOrigin.startActivity(intent);
        if (destroy) activityOrigin.finish();
    }

}
