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
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import static com.dekagames.dongle.GLCommon.*;

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


    // перечитывает текстуру из файла, записывает ее в готовый уже Id
    // используется при повторной перезагрузке ранее загруженных текстур после потери контекста
    @Override
    protected void reload_texture_from_file(Texture texture){
        System.out.println("Reloading texture "+texture.filename);
        InputStream is = game.fileIO.readAsset(texture.filename);

        Bitmap bitmap = BitmapFactory.decodeStream(is);
        int w = bitmap.getWidth();
        int h =bitmap.getHeight();

        texture.width = w;
        texture.height = h;

        ByteBuffer tempBuffer;
        tempBuffer = ByteBuffer.allocateDirect(h * w * 4);
        tempBuffer.limit(h * w * 4);

        int[] pixels = new int[w*h];
        bitmap.getPixels(pixels, 0, w, 0, 0, w, h);

        for(int color:pixels){
            tempBuffer.put((byte)((color>>16) & 0xFF)); // red
            tempBuffer.put((byte)((color>>8)&0xFF));    // green
            tempBuffer.put((byte)((color)&0xFF));       // blue
            tempBuffer.put((byte)((color>>24)&0xFF));   // alpha
        }

        tempBuffer.rewind();

        // загрузим текстуру
        gl.glBindTexture(GL_TEXTURE_2D, texture.textureId);
        // установим картинку
        gl.glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, texture.width, texture.height,
                0, GL_RGBA, GL_UNSIGNED_BYTE, tempBuffer);

        // GLUtils хреново загружает файлы - производит пре-мультиплирование альфа канала
//        GLUtils.texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);
        // установим режим фильтрации
        gl.glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        gl.glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
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
