package com.dekagames.dongle.gui;


import com.dekagames.dongle.Graphics;

/**
 * Base class for all controls.
 * If at least one of the methods (touchDown, touchUp, touchOver) return true - this control is returning
 * in Gui.update() method for processing. If we do not want process this events this methods should return false.
 * All gui controls must have parent window.
 */
public abstract class Control {
    public      Window      parent;
    protected	float		scale;
    public 		float 		fx,fy, fwidth, fheight;
    /** Flag, if its true, than parent window must process this control  */
    public		boolean		bDone;
    public 		boolean		bVisible;

    public Control(Window p) {
        parent = p;
//        scale = p.manager.scale;
        bVisible = true;
    }

    public abstract void draw(Graphics graphics);

    /**
     * Return screen X coordinate of the control in screen pixels.
     * @return
     */
    public float getScreenPosX(){
        return (fx+parent.getLeft());
    }

    /**
     * Return screen Y coordinate of the control in screen pixels.
     * @return
     */
    public float getScreenPosY(){
        return (fy+parent.getTop());
    }


    public boolean isPointIn(float x, float y) {
        return false;
    }

    public boolean 	update(float delta) {
        if (bVisible)
            return bDone;
        else
            return false;
    }

    /** Called by parent window when touch or detouch event is occured. */
    public abstract void controlTouched(boolean down);

    /** Called by the window when touched pointer is moving above the control  */
    public void touchMove(float x, float y){};
    public void touchIn(){};
    public void touchOut(){};
}
