package com.dekagames.dongle;


import com.dekagames.dongle.gui.Control;
import com.dekagames.dongle.gui.Window;

import java.util.ArrayList;

public abstract  class Screen {

    protected ArrayList<Window> windows;        // gui windows this screen managed. By default one window was created
    protected final Game        game;
    public boolean              isInitialized;
    protected boolean           prevTouch;
    protected boolean			prevOnWindow;	    // указатель был над окном
//    protected boolean           isAnyCtrlPressed;   // right now!!


    public Screen(Game game) {
        this.game = game;
        windows = new ArrayList<Window>();
    }


    public void addWindow(Window window){
        if (window != null){
            window.setScreen(this);
            windows.add(window);
        }
    }


    public void removeTopWindow() {
        if (windows.size()>1){
            windows.get(windows.size()-1).setScreen(null);
            windows.remove(windows.size()-1);
        }
    }


    public Window getTopWindow() {
        return windows.get(windows.size()-1);
    }


    public Game getGame(){
        return game;
    }


    public Control updateGUI(float deltaTime) {
        if (windows.size()<=0) return null;

        Window window = windows.get(windows.size()-1);		// получим верхнее окно которому будем посылать сообщения
        if (window == null) return null;

        boolean bTouch = game.input.touched[0];
        float touchX = game.input.touchX[0];
        float touchY = game.input.touchY[0];

        boolean	isOnWindow = window.isPointIn(touchX, touchY);	// указатель над окном или нет

        // отслеживаем нажатие и отжатие
        if (bTouch != prevTouch) {								// имело место нажатие или отжатие
            prevTouch = bTouch;
            if (isOnWindow) 				// если дело было над окном
                //isAnyCtrlPressed =
                        window.windowTouched(bTouch, touchX-window.getLeft(), touchY-window.getTop());
        }

        // постоянно отслеживаем положение указателя
        if (bTouch) {
            if (isOnWindow)
                window.touchMove(touchX-window.getLeft(), touchY-window.getTop());

            if (prevOnWindow != isOnWindow) {
                prevOnWindow = isOnWindow;
                if (isOnWindow)
                    window.touchIn();
                else
                    window.touchOut();
            }
        }
        return window.update(deltaTime);
    }


    public void drawGUI(Graphics graphics){
        for (Window w:windows) w.draw(graphics);
    }


    public void update(float deltaTime){}


    public abstract void draw (Graphics graphics);


    /**
     * Этот метод нужен для инициализации экрана, требующей обязательного наличия инициализированного OpenGL.
     * Для этого он вызывается из потока рендерера, а не из главного потока, в отличие от конструктора.
     * Перед прорисовкой экрана проверяется был ли он инициализирован, и, если не был, вызывается этот метод,
     * который должен, в случае успешной инициализации экрана устанавливать переменную isInitialized в true.
     * Этот метод используется главным образом для загрузки текстур. Текстуры, будучи однажды загруженными
     * больше не нуждаются в повторной загрузке - их перезагружает класс Game. Поэтому обычно нет необходимости
     * вызывать метод initialize больше одного раза для каждого экземпляра экрана.
     */
    public boolean initialize() {
//        for(Window w:windows)
//            w.initControls();
//
        isInitialized = true;
        return isInitialized;
    }


    public void pause(){}


    public void resume(){}


    public void dispose(){}
}

