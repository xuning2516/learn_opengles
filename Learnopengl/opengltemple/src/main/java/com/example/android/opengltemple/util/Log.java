package com.example.android.opengltemple.util;

/**
 * Created by nixu on 2017/9/19.
 */

public class Log {
    private static final boolean FORCE_LOG = false;
    private static final boolean DEBD = true;//android.util.Log.isLoggable("", android.util.Log.DEBUG);
    private static final boolean DEGW = true;

    
    public static void logd(String tag, String msg){
        if(FORCE_LOG || DEBD) {
            android.util.Log.d(tag, msg);
        }
    }

    public static void logw(String tag, String msg){
        if(FORCE_LOG || DEGW){
            android.util.Log.w(tag, msg);
        }
    }

    public static void logw(String tag, String msg, Throwable tr){
        if(FORCE_LOG || DEGW){
            android.util.Log.w(tag, msg, tr);
        }
    }
    
}
