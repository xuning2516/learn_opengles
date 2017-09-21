package com.example.android.opengltemple.util;

import android.opengl.GLES10;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.util.*;
import android.util.Log;

/**
 * Created by nixu on 2017/9/21.
 */

public class GLESUtils {


    public static int getMaxVertexAttribs(){
        int[] maxVertexAttribute = new int[1];

        int error;
        GLES20.glGetIntegerv(GLES20.GL_MAX_TEXTURE_SIZE,maxVertexAttribute,0);
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            android.util.Log.e("MyGLSurfaceView", ": glError " + error);

        }
        Log.d("MyGLSurfaceView", "getMaxVertexAttribs: maxVertexAttribute"+ maxVertexAttribute[0]);
        return maxVertexAttribute[0];
    }
}
