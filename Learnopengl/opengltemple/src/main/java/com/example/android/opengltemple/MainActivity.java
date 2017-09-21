package com.example.android.opengltemple;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    private static final int CONTEXT_CLIENT_VERSION = 2;
    private MyGLSurfaceView myGLSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        myGLSurfaceView = new MyGLSurfaceView(this);
        if(detectOpenGLES20()){
            myGLSurfaceView.setEGLContextClientVersion(CONTEXT_CLIENT_VERSION);
            myGLSurfaceView.setRenderer(new MySurfaceRenderer());
            // optional
            //myGLSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        }else{
            // can use opengl 1.0 here just make a toast
            Toast.makeText(this,"This device does not support OpenGL ES 2.0",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        setContentView(myGLSurfaceView);

    }

    private boolean detectOpenGLES20()
    {
        ActivityManager am =
                ( ActivityManager ) getSystemService ( Context.ACTIVITY_SERVICE );
        ConfigurationInfo info = am.getDeviceConfigurationInfo();
        return ( info.reqGlEsVersion >= 0x20000 );
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(myGLSurfaceView != null){
            myGLSurfaceView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(myGLSurfaceView != null){
            myGLSurfaceView.onPause();
        }
    }
}
