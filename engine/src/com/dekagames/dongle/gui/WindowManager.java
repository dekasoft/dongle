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
    protected   boolean                 is_any_ctrl_pressed;    // right now!!


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
        w.setManager(this);
        windows.add(w);
    }

    public void removeTopWindow() {
        if (windows.size()>0) {
            windows.get(windows.size()-1).setManager(null);
            windows.remove(windows.size() - 1);
        }
    }



    public Window getTopWindow() {
        return windows.get(windows.size()-1);
    }


    public boolean isAnyControlPressed(){
        return is_any_ctrl_pressed;
    }


    /**
     * Get last complete action control (for example button was pressed and released)
     * and remove this event from window
     * @return last active control or null
     */
    public Control extractLastDoneControl(){
        Window w = getTopWindow();
        Control c = w.getLastDoneControl();
        w.resetDoneControl();
        return c;
    }


    public void draw(Graphics graphics) {
        for (int i=0; i<windows.size();i++)
            windows.get(i).draw(graphics);
    }

    public void update(float delta) {
        if (windows.size()!=0) {		                        // работаем только с верхним окном, так как все окна - модальные
            Window window = windows.get(windows.size()-1);		// получим верхнее окно которому будем посылать сообщения
            boolean bTouch = game.input.touched[0];
            float touchX = game.input.touchX[0];
            float touchY = game.input.touchY[0];
            boolean	isOnWindow = window.isPointIn(touchX, touchY);	// указатель над окном или нет

            // отслеживаем нажатие и отжатие
            if (bTouch != prevTouch) {								// имело место нажатие или отжатие
                prevTouch = bTouch;
                if (isOnWindow) {				// если дело было над окном
                    is_any_ctrl_pressed = window.windowTouched(bTouch, touchX-window.getLeft(), touchY-window.getTop());
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

            window.update(delta);

        }
    }


}
