package com.dekagames.dongle;

/**
 * Простой класс текстуры. Необходим только для хранения Id текстуры и ее размеров.
 * Конструктор НЕ ДОЛЖЕН использоваться напрямую.
 * Вся загрузка - выгрузка текстур производится фабричными методами класса {@link com.dekagames.dongle.Graphics Graphics}.
 * Сами текстуры должны находится в папке assets модуля, запускающего android-версию игры.
 * Эта папка обычно создается автоматически AndroidSDK при создании Android-проекта.
 *
 */
public class Texture {
    /**
     * Путь к файлу текстуры относительно папки assets
     */
    public String filename;


    /**
     * Идентификатор текстуры, используемый OpenGL. Можно использовать его во всех функциях OpenGL,
     * требующих идентификатор текстуры.
     */
    public int textureId;


    /**
     * Ширина изображения текстуры в пикселях.
     */
    public int width;


    /**
     * Высота изображения текстуры в пикселях
     */
    public int height;


    /**
     * Примитивный конструктор текстуры используется только для задания пути к файлу текстуры.
     * Напрямую не используется - вызывается фабричными методами класса {@link com.dekagames.dongle.Graphics Graphics},
     * например таким, как {@link com.dekagames.dongle.Graphics#createTexture(String)}.
     * @param filename - путь к файлу текстуры. Должен быть относительным путем к файлу, относительно
     *                 папки assets.
     */
    public Texture(String filename){
        this.filename = filename;
    }

}
