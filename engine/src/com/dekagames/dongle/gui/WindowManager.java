package com.dekagames.dongle.gui;

import com.dekagames.dongle.Game;
import com.dekagames.dongle.Graphics;
import com.dekagames.dongle.Screen;

import java.util.ArrayList;

/**
 * Created by deka on 23.07.14.
 */
public class WindowManager {
    protected   Screen                  screen;
    protected   Game                    game;
    protected   ArrayList<Window>       windows;		// дочерние окна
    protected	boolean					prevTouch;		// предыдущее состояние тача для контроля изменения
    protected 	boolean					prevOnWindow;	// указатель был над окном


    public WindowManager(Game g, Screen s) {
        game = g;
        screen = s;
        windows = new ArrayList<Window>();
    }

    public Screen getScreen() {
        return screen;
    }

    public Game getGame() {
        return game;
    }

    public void addWindow(Window w) {
        windows.add(w);
    }

    public void removeTopWindow() {
        if (windows.size()>0)
            windows.remove(windows.size()-1);
    }

    public Window getTopWindow() {
        return windows.get(windows.size()-1);
    }

    public void draw(Graphics graphics) {
        for (int i=0; i<windows.size();i++)
            windows.get(i).draw(graphics);
    }

    public void update(float delta) {
        if (windows.size()!=0) {		                        // работаем только с верхним окном, так как все окна - модальные
            Window window = windows.get(windows.size()-1);		// получим верхнее окно которому будем посылать сообщения
            window.update(delta);
            boolean bTouch = game.input.touched[0];//Gdx.input.isTouched();
            float touchX = game.input.touchX[0];//Gdx.input.getX()/scale;
            float touchY = game.input.touchY[0];//Gdx.input.getY()/scale;
//			System.out.println("touchX="+touchX+ " touchY="+touchY);
            boolean	isOnWindow = window.isPointIn(touchX, touchY);	// указатель над окном или нет


            // отслеживаем нажатие и отжатие
            if (bTouch != prevTouch) {								// имело место нажатие или отжатие
                prevTouch = bTouch;
                if (isOnWindow) {				// если дело было над окном
                    window.windowTouched(bTouch, touchX-window.getLeft(), touchY-window.getTop());
                }
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
        }
    }


}
