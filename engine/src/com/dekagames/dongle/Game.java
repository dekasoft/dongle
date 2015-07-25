package com.dekagames.dongle;


// основной класс для игры. Этот класс передается как параметр другому классу - стартеру для каждой ОСи.
// Методы этого класса также вызываются классом-стартером.
// также классы-стартеры записывают в Game правильные интерфейсы FileIO, Graphics, Input и т.д

import java.util.ArrayList;

public class Game {

    // требуемое разрешение для игры, так называемое виртуальное разрешение. Оно задается в самом начале и не
    // зависит от физических размеров экрана или окна. Весь вывод графики выводится, используя виртуальное разрешение,
    // а при несоответствии соотношения сторон, по бокам сверху или снизу выводятся черные полосы.
    protected int virtualWidth, virtualHeight;

    private long    nFramesRendered;        // для расчета FPS
    private float   fTimeFromStart;
    private float   fTimeFPS;
    private float   fFPS, nframes;

    public      FileIO          fileIO;

    /** Экземпляр класса {@link com.dekagames.dongle.Graphics} Используется для передачи в методы рисования. */
    public      Graphics        graphics;
    public      Audio           audio;
    public      Input           input;

    protected   Screen          screen;

    // список текстур. конструктор текстуры добавляет сюда создаваемые текстуры, чьтобы потом можно было их перезагрузить
    // в случае потери контекста
    protected ArrayList<Texture>    managedTextures;
    protected   boolean             needToReloadTextures;

    public Game(int virtual_width, int virtual_height){
        virtualWidth = virtual_width;
        virtualHeight = virtual_height;

        managedTextures = new ArrayList<Texture>();
//        needToReloadTextures = true;
    }


    public void setNeedToReloadTextures(boolean need_reload){
        needToReloadTextures = need_reload;
    }


    public boolean getNeedToReloadTextures(){
        return needToReloadTextures;
    }


    public int getVirtualWidth(){
        return virtualWidth;
    }


    public int getVirtualHeight(){
        return virtualHeight;
    }


    public ArrayList<Texture> getManagedTextures(){
        return managedTextures;
    }


    public void reloadTextures(){
        Log.info("Reload texture called. Need to reload "+managedTextures.size()+" textures.");
        if (graphics == null){
            Log.error("Graphics was not created. Reloading of the textures is impossible!");
            return;
        }

        for (int i=0; i<managedTextures.size(); i++) {

            Texture tex = managedTextures.get(i);
            if (tex != null) {
                Log.info("Reloading texture: " + tex.textureId);
                graphics.reload_texture_from_file(tex);//.reload_texture_from_file();
            }
        }
    }


    // этот метод вызывается классом Application (или его наследником) из контекста OpenGL
    public void initialize(){
        Log.info("Game initialize called");
        if (screen != null)
            // необходимо инициализировать экран - возможно ему требуется OpenGL для инициализации
            if (!screen.isInitialized) screen.initialize();
    }


    /**
     * Метод, вызываемый приложением каждую итерацию игрового цикла. Объявлен как final, не
     * нуждается в переопределении.
     * В нем вызывается соответствующий метод класса {@link com.dekagames.dongle.Screen}, и производится
     * расчет FPS.
     * @param deltaTime время в секундах с момента прошлого вызова
     */
    public final void update(float deltaTime){
        if (screen != null) {
            if (!screen.isInitialized) screen.initialize();
            screen.update(deltaTime);
            fTimeFromStart += deltaTime;

            // расчет мгновенного FPS (каждую секунду)
            nframes++;
            fTimeFPS += deltaTime;
            if (fTimeFPS>=1.0f){
                fFPS = nframes;
                nframes = 0;
                fTimeFPS = 0;
            }
        }
    }


    /**
     * Метод, вызываемый приложением каждую итерацию игрового цикла. Объявлен как final, не
     * нуждается в переопределении.
     * В нем вызывается соответствующий метод класса {@link com.dekagames.dongle.Screen}.
     */
    public final void draw(){
        if (screen != null) {
            if (!screen.isInitialized) screen.initialize();
            screen.draw(graphics);
            // запишем данные для расчета FPS
            nFramesRendered++;
        }
    }


    public void resume() {
        Log.info("Game resume method called");
        if (screen != null) screen.resume();
        if (audio != null)  audio.resume();
    }


    public void pause(){
        Log.info("Game pause method called");
        if (screen != null) screen.pause();
        if (audio != null)  audio.pause();
    }


    public void setScreen(Screen scr){
        if (scr != null) {
            if (screen != null) {
                screen.pause();
                screen.dispose();
            }
            scr.resume();
//            scr.update(0);
            screen = scr;
        }
    }

    public Screen getScreen(){
        return screen;
    }

    public float getAverageFPS(){
        return nFramesRendered/fTimeFromStart;
    }


    public float getFPS(){
        return fFPS;
    }
}
