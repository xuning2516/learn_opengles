package com.example.android.opengltemple;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.example.android.opengltemple.util.GLESUtils;

/**
 * Created by nixu on 2017/9/19.
 */

public class MyGLSurfaceView extends GLSurfaceView {
    private static final String TAG = "MyGLSurfaceView";
    private Context mContext;

   public MyGLSurfaceView(Context context){
       super(context);
       mContext = context;
   }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //Log.d(TAG, "onTouchEvent: " + event.getAction());
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                int maxAttribute = GLESUtils.getMaxVertexAttribs();
                Toast.makeText(mContext,"maxAttribute num: " + maxAttribute,Toast.LENGTH_SHORT)
                        .show();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return super.onTouchEvent(event);
    }
}
