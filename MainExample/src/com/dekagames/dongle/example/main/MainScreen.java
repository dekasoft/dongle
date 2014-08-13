package com.dekagames.dongle.example.main;

import com.dekagames.dongle.*;
import com.dekagames.slon.Slon;
import com.dekagames.slon.SlonException;

import java.io.IOException;
import java.util.Random;

/**
 * Created by deka on 23.06.14.
 */
public class MainScreen extends Screen {
    // слон файл
    private Slon slon;

    // текстура с полоской кадров - 1 способ
    private Texture texAngelStrip;

    // текстура с атласом - 2 способ
    private Texture textureAtlas;

    // Текстура для модели сделанной в скелетном редакторе
    private Texture textureModel;

   // Скелетная модель
    private SkeletonModel model;

    // разные спрайты для разных способов загрузки
    private Sprite  sprAngelStrip, spriteFromAtlas;



    private Font font;

    private  Sound sound;

    private Music music;

    private Random rand;

    private float fangle;
    private boolean nowTouched;

    public MainScreen(MainGame mainGame){
        super(mainGame);
        rand = new Random(System.currentTimeMillis());
    }

    @Override
    public boolean initialize(){
        // ----------  1 способ - загрузка текстуры с полоской кадров -----------------------
        texAngelStrip = game.graphics.createTexture("angel.png");
        // создание спрайта из полоски кадров
        sprAngelStrip = new Sprite(texAngelStrip,16,10,0,0,64,64);
        sprAngelStrip.play(true);       // запуск анимации


        // ----------  2 способ - загрузка текстуры с атласом кадров -----------------------
        textureAtlas = game.graphics.createTexture("atlas.png");          // спрайт с кадрами упакованными в атлас
        // слон файл с параметрами атласа
        slon = new Slon();
        try {
            // слон файл для всего атласа - загружается один раз для всего атласа целиком и потом из него
            // дергаются отдельные спрайты
            slon.load(getGame().fileIO.readAsset("atlas.png.atlas"));
        } catch (SlonException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        // непосредственно загрузка самого спрайта из слона по имени
        try {
            spriteFromAtlas = new Sprite(textureAtlas, slon,"sprMan",12);
            spriteFromAtlas.play(true);
        } catch (IOException e) {
            e.printStackTrace();
        }


        // ----------  2 способ - загрузка не спрайта, а скелетной модели -----------------------
        textureModel = game.graphics.createTexture("hero.model.png");
        // скелетная модель содержит мини атлас всех своих костей, который, обычно, склеивается с  общим
        // атласом в качестве кадра спрайта, поэтому для загрузки модель использует не текстуру
        // а только область текстуры. В нашем тестовом примере мини-атлас модели хранится сам по себе, а не
        // в общем атласе, поэтому просто создадим TextureRegion из всей текстуры
        TextureRegion region = new TextureRegion(textureModel,0,0,textureModel.width, textureModel.height);
        model = new SkeletonModel(game, region, "hero.model", 10);
        model.setFlip(true, false);     // отражение модели - просто для примера
        model.play(true);               // запуск анимации


        // ------------------------  загрузка шрифта ----------------------------------------------------
        font = new Font(textureAtlas, slon, "fntComic");

        // ------------------------ загрузка звуов и музыки ------------------------------------
        sound = game.audio.newSound("bird.wav", true);
        music = game.audio.newMusic("polka.ogg", true);

        music.play();


        rand = new Random(System.currentTimeMillis());
        return super.initialize();
    }

    @Override
    public void update(float deltaTime) {
        sprAngelStrip.update(deltaTime);
        spriteFromAtlas.update(deltaTime);

        model.update(deltaTime);
        fangle += deltaTime*10;         // чтобы модель крутилась
    }

    @Override
    public void draw(Graphics gr) {
        gr.clearScreen(0,0,0);               // очищает весь экран (полосы по бокам)
        gr.clearViewport(0.1f, 0.7f, 0.1f);  // очищаем саму область вывода

        // весь вывод графики должен предворяться begin
        gr.begin();

        //выводим спрайт созданный из полоски
        sprAngelStrip.draw(gr, 100, 100);

        // выводим спрайт загруженный из атласа
        spriteFromAtlas.draw(gr, 200,100);

        for (int i = 0; i < 5000; i++) {
            spriteFromAtlas.draw(gr,rand.nextInt(game.getVirtualWidth()), rand.nextInt(game.getVirtualHeight()));
        }


        // рисуем скелетную модель
        model.draw(gr, 400,200, fangle);

        // выведем FPS загруженным шрифтом
        font.drawString(gr,"Hello world! FPS: "+game.getFPS(), 200,100);

        // обработка ввода
        if (game.input.touched[0] && !nowTouched){ // произошла касание
            nowTouched = true;
            sound.play();
        }

        if (!game.input.touched[0]){
            nowTouched = false;
        }


        for (int i=0; i<3; i++){
            if (game.input.touched[i]) {
                float x = game.input.touchX[i];
                float y = game.input.touchY[i];
                font.drawString(game.graphics,"Pointer "+i, x, y );
            }
        }

        // вывод графики должен заканчиваться end
        gr.end();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
