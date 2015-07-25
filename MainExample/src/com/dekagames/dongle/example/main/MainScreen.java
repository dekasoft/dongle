package com.dekagames.dongle.example.main;

import com.dekagames.dongle.*;
import com.dekagames.dongle.gui.Control;
import com.dekagames.dongle.gui.controls.Button;
import com.dekagames.slon.Slon;
import com.dekagames.slon.SlonException;
import com.dekagames.slon.SlonNode;

import java.io.IOException;
import java.util.Random;

/**
 * Created by deka on 23.06.14.
 */
public class MainScreen extends Screen {
    // слон файл
    public Slon slon;

    // текстура с одиночной картинкой
    private Texture texLogo;

    // текстура с полоской кадров - 1 способ
    private Texture texAngelStrip;

    // текстура с атласом - 2 способ
    public Texture textureAtlas;

    // Текстура для модели сделанной в скелетном редакторе
    private Texture textureModel;

   // Скелетная модель
    private SkeletonModel model;

    // разные спрайты для разных способов загрузки
    private Sprite  sprAngelStrip, spriteFromAtlas, spriteLogo;


    // GUI window - needed for gui elements
    private MainWindow mainWindow;
    // GUI element - simple button
//    private Button button;



    private Font font;

    public  Sound sound;

    private Music music;

    private Random rand;

    private float fangle;
//    private boolean nowTouched;

    private int lastKeyUp;


    public MainScreen(MainGame mainGame){
        super(mainGame);

//        game.input.catchBackKey = true;

        rand = new Random(System.currentTimeMillis());

        // create window for controls and add it to screen
        mainWindow = new MainWindow(getGame().getVirtualWidth(), getGame().getVirtualHeight());
        addWindow(mainWindow);
    }

    @Override
    public boolean initialize(){
        // вся работа с input должна делаться в потоке GL
        getGame().input.catchBackKey = true;

        // текстура с одиночной картинкой
        texLogo = getGame().graphics.createTexture("logo.png");
        spriteLogo = new Sprite(texLogo, 1, 1, 0, 0, 256, 128);
        spriteLogo.fadeInOut(0.5f, 2.5f, 0.5f, 0);

        // ----------  1 способ - загрузка текстуры с полоской кадров -----------------------
        texAngelStrip = getGame().graphics.createTexture("angel.png");
        // создание спрайта из полоски кадров
        sprAngelStrip = new Sprite(texAngelStrip,16,10,0,0,64,64);
        sprAngelStrip.play(true);       // запуск анимации


        // ----------  2 способ - загрузка текстуры с атласом кадров -----------------------
        textureAtlas = getGame().graphics.createTexture("atlas.png");          // спрайт с кадрами упакованными в атлас
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
        textureModel = getGame().graphics.createTexture("hero.model.png");
        // скелетная модель содержит мини атлас всех своих костей, который, обычно, склеивается с  общим
        // атласом в качестве кадра спрайта, поэтому для загрузки модель использует не текстуру
        // а только область текстуры. В нашем тестовом примере мини-атлас модели хранится сам по себе, а не
        // в общем атласе, поэтому просто создадим TextureRegion из всей текстуры
        TextureRegion region = new TextureRegion(textureModel,0,0,textureModel.width, textureModel.height);
        model = new SkeletonModel(getGame(), region, "hero.model", 10);
        model.setFlip(true, false);     // отражение модели - просто для примера
        model.play(true);               // запуск анимации


        // ------------------------  загрузка шрифта ----------------------------------------------------
        font = new Font(textureAtlas, slon, "fntComic");

        // --------------------------- create controls by call initControls method from GL thread --------
        mainWindow.initControls();

        // ------------------------ загрузка звуов и музыки ------------------------------------
        sound = getGame().audio.newSound("bird.wav", true);
        music = getGame().audio.newMusic("polka.ogg", true);

        music.play();


        rand = new Random(System.currentTimeMillis());
        return super.initialize();
    }

    @Override
    public void update(float deltaTime) {
        super.updateGUI(deltaTime);

        lastKeyUp = getGame().input.getLastKeyUp();
        if (lastKeyUp == Input.BACK)
            System.out.println("BAck button up!");

        spriteLogo.update(deltaTime);
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

        // появляющееся и исчезающее лого
        spriteLogo.draw(gr, 300, 300);

        //выводим спрайт созданный из полоски
        sprAngelStrip.setARGBColor(0xFEFDFCFB);
        sprAngelStrip.draw(gr, 100, 100);

        // выводим спрайт загруженный из атласа
        spriteFromAtlas.draw(gr, 200,100);

        spriteFromAtlas.draw(gr, 300,100);

//        for (int i = 0; i < 5; i++) {
//            spriteFromAtlas.draw(gr,rand.nextInt(game.getVirtualWidth()), rand.nextInt(game.getVirtualHeight()));
//        }


        // рисуем скелетную модель
        model.draw(gr, 400,200, fangle);

        // выведем FPS загруженным шрифтом
        font.setColor(1,0,0,1);
        font.drawString(gr,"Hello world! FPS: " + getGame().getFPS(), 200,100);

        // draw primitives
        Primitives.drawPoint(gr, 1, 1);
        for (int i=0; i<90; i+=10){
            Primitives.drawLine(gr, 10,10, 50*(float)Math.cos(Math.toRadians(i)), 50*(float)Math.sin(Math.toRadians(i)));
        }
        Primitives.drawCircle(gr, 50,50, 30);

        float[] vert = new float[10];
        vert[0] = 10; vert[1] = 200;
        vert[2] = 50; vert[3] = 200;
        vert[4] = 70; vert[5] = 280;
        vert[6] = 40; vert[7] = 360;
        vert[8] = 0; vert[9] = 250;
        Primitives.drawPolygon(gr, vert);

        // обработка ввода
        if (getGame().input.wasTouched){
            sound.play();
            getGame().input.wasTouched = false;
        }


//        if (game.input.touched[0] && !nowTouched){ // произошла касание
//            nowTouched = true;
//            sound.play();
//        }
//
//        if (!game.input.touched[0]){
//            nowTouched = false;
//        }


        for (int i=0; i<3; i++){
            if (getGame().input.touched[i]) {
                float x = getGame().input.touchX[i];
                float y = getGame().input.touchY[i];
                font.drawString(getGame().graphics,"Pointer "+i, x, y );
            }
        }

        super.drawGUI(gr);

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
