package com.example.android.opengltemple;

import android.app.Application;
import android.content.Context;
import android.util.Log;

/**
 * Created by nixu on 2017/9/19.
 */

public class ShaderApplication extends Application {
    private static final String TAG = "ShaderApplication";
    private static Context applicationContext;

    public ShaderApplication(){
        applicationContext = this;
    }

    public static Context getAppContext(){
        Log.d(TAG, "getAppContext: ");
        return applicationContext;
    }
}
