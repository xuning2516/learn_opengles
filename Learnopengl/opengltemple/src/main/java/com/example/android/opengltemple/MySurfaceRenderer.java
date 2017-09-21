package com.example.android.opengltemple;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static com.example.android.opengltemple.ShaderApplication.getAppContext;

/**
 * Created by nixu on 2017/9/19.
 */

public class MySurfaceRenderer implements GLSurfaceView.Renderer {
    private static final String TAG = "MySurfaceRenderer";

    // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private final float[] mRotationMatrix = new float[16];
    private float mAngle;

    private Triangle mTriangle;

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        Log.d(TAG, "onSurfaceCreated: ");
        GLES20.glClearColor(1.0f,1.0f,1.0f,0.0f);
        mTriangle = new Triangle(getAppContext());
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        Log.d(TAG, "onSurfaceChanged: ");
        GLES20.glViewport(0,0,width,height);

        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        //Log.d(TAG, "onDrawFrame: ");

        int[] maxVertexAttribute = new int[1];

        int error;
        GLES20.glGetIntegerv(GLES20.GL_MAX_TEXTURE_SIZE,maxVertexAttribute,0);
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            android.util.Log.e("MyGLSurfaceView", ": glError " + error);

        }
        Log.d("MyGLSurfaceView", "getMaxVertexAttribs maxVertexAttribute: "+ maxVertexAttribute[0]);


        float[] scratch = new float[16];

        // Draw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        // Set the camera position (View matrix)
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        // Create a rotation for the triangle

        // Use the following code to generate constant rotation.
        // Leave this code out when using TouchEvents.
        long time = SystemClock.uptimeMillis() % 4000L;
        float angle = 0.090f * ((int) time);

        //Matrix.setRotateM(mRotationMatrix, 0, mAngle, 0, 0, 1.0f);
        Matrix.setRotateM(mRotationMatrix, 0, angle, 0, 0, 1.0f);

        // Combine the rotation matrix with the projection and camera view
        // Note that the mMVPMatrix factor *must be first* in order
        // for the matrix multiplication product to be correct.
        Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);

        // Draw triangle
        mTriangle.draw(scratch);
    }
}
