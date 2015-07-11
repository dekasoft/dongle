package com.dekagames.dongle.desktop;

import com.dekagames.dongle.*;

import javax.imageio.ImageIO;
import javax.media.opengl.GL;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.logging.Level;

import static com.dekagames.dongle.GLCommon.*;

/**
 * Created by deka on 26.06.14.
 */
public class JoglGraphics extends Graphics{
    private GL glInst;

    public JoglGraphics(Game game, GL glinst){
        super(game);
        gl = new JoglGL(glinst);
        glInst = glinst;
        Log.info("GL Version: "+glinst.glGetString(glinst.GL_VERSION));
    }

    @Override
    public Texture createTexture(String filepath) {
        Texture texture = new Texture(filepath);

        int textureIds[] = new int[1];
        gl.glGenTextures(1, textureIds, 0);
        texture.textureId = textureIds[0];

        reload_texture_from_file(texture);
        // добавим в список текстур, чтобы можно было перезагрузить все текстуры при потере контекста
        game.getManagedTextures().add(texture);

        return texture;
    }

//    @Override
//    public void deleteTexture(Texture texture) {
//        gl.glBindTexture(GLCommon.GL_TEXTURE_2D, texture.textureId);
//        int[] texureIds = {texture.textureId};
//        gl.glDeleteTextures(1,texureIds,0);
//        game.getManagedTextures().remove(texture);
//    }


    @Override
    public void init(int physical_width, int physical_height){
        super.init(physical_width, physical_height);
        gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA); // !!!!!!       // for desktop
    }


    @Override
    protected void reload_texture_from_file(Texture texture) {
        BufferedImage img = null;

        InputStream is = game.fileIO.readAsset(texture.filename);
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        texture.width = img.getWidth();
        texture.height = img.getHeight();

        ByteBuffer tempBuffer = null;
        tempBuffer = ByteBuffer.allocateDirect(img.getHeight()*img.getWidth()*4);
        tempBuffer.limit(img.getHeight()*img.getWidth()*4);

        for(int y=0;y<img.getHeight();y++) {
            for(int x=0;x<img.getWidth();x++) {
                Object pixelIm = img.getRaster().getDataElements(x, y, null);

                tempBuffer.put((byte)img.getColorModel().getRed(pixelIm));
                tempBuffer.put((byte)img.getColorModel().getGreen(pixelIm));
                tempBuffer.put((byte)img.getColorModel().getBlue(pixelIm));
                tempBuffer.put((byte)img.getColorModel().getAlpha(pixelIm));
            }
        }

        tempBuffer.rewind();

        // загрузим текстуру
        gl.glBindTexture(GLCommon.GL_TEXTURE_2D, texture.textureId);
        // установим картинку
        gl.glTexImage2D(GLCommon.GL_TEXTURE_2D, 0, GLCommon.GL_RGBA, texture.width, texture.height,
                0, GLCommon.GL_RGBA, GLCommon.GL_UNSIGNED_BYTE, tempBuffer);

        // установим режим фильтрации
        gl.glTexParameterf(GLCommon.GL_TEXTURE_2D, GLCommon.GL_TEXTURE_MIN_FILTER, GLCommon.GL_LINEAR);
        gl.glTexParameterf(GLCommon.GL_TEXTURE_2D, GLCommon.GL_TEXTURE_MAG_FILTER, GLCommon.GL_LINEAR);

        tempBuffer.clear();

        // закроем входной поток
        try {
            is.close();
        } catch (IOException e) {  }
    }
}
