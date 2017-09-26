package com.example.android.opengltemple.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLUtils;

/**
 * Created by nixu on 2017/9/22.
 */

public class TextureHelper {
    private static final String TAG = "TextureHelper";

    /**
     * Loads a texture from a resource ID, returning the OpenGL ID for that
     * texture. Returns 0 if the load failed.
     *
     * @param context
     * @param resourceId
     * @return
     */
    public static int loadTexture(Context context, int resourceId){
        final int[] textureObjectId = new int[1];
        GLES20.glGenTextures(1,textureObjectId, 0);

        if(textureObjectId[0] == 0){
            Log.logw(TAG,"Could not generate a new OpenGL texture Object");
            return 0;
        }

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;

        final Bitmap bitmap = BitmapFactory.decodeResource(
                context.getResources(),resourceId,options);
        if(bitmap == null){
            Log.logw(TAG,"Resource ID" + resourceId+ "could not be decoded");
            GLES20.glDeleteTextures(1, textureObjectId,0);
            return 0;
        }

        //绑定纹理对象，指定当前的纹理对象，后续操作(glTextImage2D)将影响绑定的纹理对象
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureObjectId[0]);
        // 指定缩小的情况，三线性过滤
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MIN_FILTER,GLES20.GL_LINEAR_MIPMAP_LINEAR);
        // 指定放大的情况，双线性过滤
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MAG_FILTER,GLES20.GL_LINEAR);

        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D,0,bitmap,0);
        ////  Load the texture
        //GLES20.glTexImage2D ( GLES30.GL_TEXTURE_2D, 0, GLES30.GL_RGB, 2, 2, 0, GLES30.GL_RGB, GLES30.GL_UNSIGNED_BYTE, pixelBuffer );

        bitmap.recycle();
        // 生成bitmap贴图
        GLES20.glGenerateMipmap(GLES20.GL_TEXTURE_2D);
        // 解除与当前纹理的绑定
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);

        return textureObjectId[0];
    }
}
