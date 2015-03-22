package com.dekagames.dongle.android;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
//import android.opengl.Matrix;
import android.os.Build;
import android.util.Log;

import com.dekagames.dongle.Game;
import com.dekagames.dongle.Graphics;
import com.dekagames.dongle.Shader;
import com.dekagames.dongle.Matrix;
import com.dekagames.dongle.Texture;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by deka on 16.06.14.
 */
@TargetApi(Build.VERSION_CODES.FROYO)
public class AndroidGraphics  extends Graphics {


    public AndroidGraphics(Game game) {
        super(game);
        gl = new AndroidGL();

    }


    @Override
    public Texture createTexture(String filepath) {
        Texture texture = new Texture(filepath);

        int textureIds[] = new int[1];
        GLES20.glGenTextures(1, textureIds, 0);
        texture.textureId = textureIds[0];

        reload_texture_from_file(texture);
        // добавим в список текстур, чтобы можно было перезагрузить все текстуры при потере контекста
        game.getManagedTextures().add(texture);

        return texture;
    }


    @Override
    public void deleteTexture(Texture texture){
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture.textureId);
        int[] texureIds = {texture.textureId};
        GLES20.glDeleteTextures(1,texureIds,0);
        game.getManagedTextures().remove(texture);
    }





    // перечитывает текстуру из файла, записывает ее в готовый уже Id
    // используется при повторной перезагрузке ранее загруженных текстур после потери контекста
    @Override
    protected void reload_texture_from_file(Texture texture){
        InputStream is = game.fileIO.readAsset(texture.filename);
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        texture.width = bitmap.getWidth();
        texture.height = bitmap.getHeight();

        // загрузим текстуру
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture.textureId);
        // установим картинку
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
        // установим режим фильтрации
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        // удалим уже ненужный битмэп
        bitmap.recycle();
        // закроем входной поток
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
