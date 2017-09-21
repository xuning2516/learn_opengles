package com.example.android.opengltemple.util;

import android.content.Context;
import android.content.res.Resources;
import android.opengl.GLES20;
import android.opengl.GLES30;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by nixu on 2017/9/19.
 */

public class ShaderHelper {
    private static final String TAG = "ShaderHelper";
    //
    ///
    /// \brief Read a shader source into a String
    /// \param context Application context
    /// \param fileName Name of shader file
    /// \return A String object containing shader source, otherwise null
    //
    public static String readShaderFromAssets (Context context, String fileName ) {
        String shaderSource = null;

        if ( fileName == null ) {
            return shaderSource;
        }

        // Read the shader file from assets
        InputStream is = null;
        byte [] buffer;

        try {
            is =  context.getAssets().open(fileName);

            // Create a buffer that has the same size as the InputStream
            buffer = new byte[is.available()];

            // Read the text file as a stream, into the buffer
            is.read ( buffer );

            ByteArrayOutputStream os = new ByteArrayOutputStream();

            // Write this buffer to the output stream
            os.write ( buffer );

            // Close input and output streams
            os.close();
            is.close();

            shaderSource = os.toString();
        }catch (IOException ioe) {
            //android.util.Log.d(TAG, "Triangle readShaderFromAssets: "+ ioe.getStackTrace());
            ioe.printStackTrace();

            is = null;
        }

        if (is == null){
            return shaderSource;
        }

        return shaderSource;
    }

    public static String readShaderFromResource(Context context, int resourceId){
        StringBuilder stringBuilder = new StringBuilder();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;

        try {
            inputStream = context.getResources().openRawResource(resourceId);
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line);
                stringBuilder.append('\n');
            }
        }catch (IOException ex){
            ex.printStackTrace();
        }catch (Resources.NotFoundException ex){
            ex.printStackTrace();
        } finally{
            if(inputStream != null){
                try {
                    inputStream.close();
                }catch (IOException ex){
                    ex.printStackTrace();
                }
            }
            if(inputStreamReader != null){
                try {
                    inputStreamReader.close();
                }catch (IOException ex){
                    ex.printStackTrace();
                }
            }
        }


        return stringBuilder.toString();
    }


    public static int loadVertextShader(String shaderSource){
        return loadShader(GLES20.GL_VERTEX_SHADER,shaderSource);
    }

    public static int loadFragmentShader(String shaderSource){
        return loadShader(GLES20.GL_FRAGMENT_SHADER,shaderSource);
    }
    public static int loadShader(int type,String shaderSource){
        final int shaderObjectId = GLES20.glCreateShader(type);
        if(shaderObjectId == 0){
            Log.logd(TAG,"Could not create new shader");
            return 0;
        }

        GLES20.glShaderSource(shaderObjectId,shaderSource);
        GLES20.glCompileShader(shaderObjectId);

        final int[] compileStatus = new int[1];
        GLES20.glGetShaderiv(shaderObjectId, GLES20.GL_COMPILE_STATUS, compileStatus,0);
        if(compileStatus[0] == 0){
            GLES20.glDeleteShader(shaderObjectId);
            Log.logd(TAG,"Compilation of shader failed");
            Log.logw(TAG,"Results of compiling source:\n"
                    + shaderSource+ "\n"
                    + GLES20.glGetShaderInfoLog(shaderObjectId));
            return 0;
        }

        return shaderObjectId;
    }


    public static int linkProgram(final int vertexShaderId, final int fragmentShaderId){
        final int programObejctId = GLES20.glCreateProgram();

        if(programObejctId == 0){
            Log.logw(TAG,"Could not create new program");
            return  0;
        }

        GLES20.glAttachShader(programObejctId, vertexShaderId);
        GLES20.glAttachShader(programObejctId,fragmentShaderId);

        GLES20.glLinkProgram(programObejctId);

        int[] linkStatus = new int[1];
        GLES20.glGetProgramiv(programObejctId,GLES20.GL_LINK_STATUS,linkStatus,0);
        if(linkStatus[0] == 0){
            Log.logw(TAG,"Linking of program failed");
            GLES20.glDeleteProgram(programObejctId);
            Log.logw(TAG,"Result of link program:\n"
                    + GLES20.glGetProgramInfoLog(programObejctId));
        }
        // Free up no longer needed shader resources
        GLES20.glDeleteShader ( vertexShaderId );
        GLES20.glDeleteShader ( fragmentShaderId );

        return programObejctId;
    }

    /**
     * Utility method for debugging OpenGL calls. Provide the name of the call
     * just after making it:
     *
     * <pre>
     * mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
     * MyGLRenderer.checkGlError("glGetUniformLocation");</pre>
     *
     * If the operation is not successful, the check throws an error.
     *
     * @param glOperation - Name of the OpenGL call to check.
     */
    public static void checkGlError(String glOperation) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            android.util.Log.e(TAG, glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }
}
