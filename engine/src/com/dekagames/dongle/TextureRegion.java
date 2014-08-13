package com.dekagames.dongle;

import com.dekagames.slon.Slon;
import com.dekagames.slon.SlonNode;

import java.io.IOException;

/**
 * Класс области текстуры, нужен в том случае, если в атлас вклеивается другой атлас - например атлас частей модели
 *  */
public class TextureRegion {
    public Texture texture;               // полный атлас
    public int x,y, w, h;        // координаты региона на атласе


    public TextureRegion(Texture tex, int left, int top, int width, int height){
        texture = tex;
        x = left;
        y = top;
        w = width;
        h = height;
    }

    // загрузка региона из атласа, как кадр спрайта
    public TextureRegion(Texture tex, Slon slon, String name, int frame) throws  IOException{
        SlonNode nodeFrame;
        SlonNode spritesNode = slon.getRoot().getChildWithKeyValue("name", "sprites");
        if (spritesNode == null){
//            Log.e("SPRITES", "Could not find sprites section while loading " + name + "!");
            throw new IOException();
        }

        texture = tex;

        // найдем спрайт с именем name
        SlonNode nodeSprite = spritesNode.getChildWithKeyValue("name", name);
        // если спрайт не был найден - кинем исключение
        if (nodeSprite == null) {
            throw (new IOException("No sprite with name "+name+ " in atlas!"));
        }

        // количество кадров
        int n_frames = nodeSprite.getChildCount();
        if (frame >= n_frames){
//            Log.e("TEXTURE REGION", "No frame with number "+frame);
            throw (new IOException("Wrong frame number while loading texture region!"));
        }

        // Прочитаем кадр без лишней информации
        nodeFrame = nodeSprite.getChildAt(frame);
        x = nodeFrame.getKeyAsInt("x");
        y = nodeFrame.getKeyAsInt("y");
        w = nodeFrame.getKeyAsInt("w");
        h = nodeFrame.getKeyAsInt("h");
    }
}
