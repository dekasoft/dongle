package com.dekagames.dongle.android;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import com.dekagames.dongle.Graphics;
import com.dekagames.dongle.Input;
import android.view.View.OnTouchListener;
import com.dekagames.dongle.Log;


/**
 * Created by deka on 20.06.14.
 */
@TargetApi(Build.VERSION_CODES.FROYO)
public class AndroidInput extends Input implements OnTouchListener, SensorEventListener {
    // инициализация переменных правильными кодвми клавиш
    static {
        ANY_KEY = -1;
        NUM_0 = 7;
        NUM_1 = 8;
        NUM_2 = 9;
        NUM_3 = 10;
        NUM_4 = 11;
        NUM_5 = 12;
        NUM_6 = 13;
        NUM_7 = 14;
        NUM_8 = 15;
        NUM_9 = 16;
        A = 29;
        ALT_LEFT = 57;
        ALT_RIGHT = 58;
        APOSTROPHE = 75;
        AT = 77;
        B = 30;
        BACK = 4;
        BACKSLASH = 73;
        C = 31;
        CALL = 5;
        CAMERA = 27;
        CLEAR = 28;
        COMMA = 55;
        D = 32;
        DEL = 67;
        BACKSPACE = 67;
        FORWARD_DEL = 112;
        DPAD_CENTER = 23;
        DPAD_DOWN = 20;
        DPAD_LEFT = 21;
        DPAD_RIGHT = 22;
        DPAD_UP = 19;
        CENTER = 23;
        DOWN = 20;
        LEFT = 21;
        RIGHT = 22;
        UP = 19;
        E = 33;
        ENDCALL = 6;
        ENTER = 66;
        ENVELOPE = 65;
        EQUALS = 70;
        EXPLORER = 64;
        F = 34;
        FOCUS = 80;
        G = 35;
        GRAVE = 68;
        H = 36;
        HEADSETHOOK = 79;
        HOME = 3;
        I = 37;
        J = 38;
        K = 39;
        L = 40;
        LEFT_BRACKET = 71;
        M = 41;
        MEDIA_FAST_FORWARD = 90;
        MEDIA_NEXT = 87;
        MEDIA_PLAY_PAUSE = 85;
        MEDIA_PREVIOUS = 88;
        MEDIA_REWIND = 89;
        MEDIA_STOP = 86;
        MENU = 82;
        MINUS = 69;
        MUTE = 91;
        N = 42;
        NOTIFICATION = 83;
        NUM = 78;
        O = 43;
        P = 44;
        PERIOD = 56;
        PLUS = 81;
        POUND = 18;
        POWER = 26;
        Q = 45;
        R = 46;
        RIGHT_BRACKET = 72;
        S = 47;
        SEARCH = 84;
        SEMICOLON = 74;
        SHIFT_LEFT = 59;
        SHIFT_RIGHT = 60;
        SLASH = 76;
        SOFT_LEFT = 1;
        SOFT_RIGHT = 2;
        SPACE = 62;
        STAR = 17;
        SYM = 63;
        T = 48;
        TAB = 61;
        U = 49;
        UNKNOWN = 0;
        V = 50;
        VOLUME_DOWN = 25;
        VOLUME_UP = 24;
        W = 51;
        X = 52;
        Y = 53;
        Z = 54;
        META_ALT_LEFT_ON = 16;
        META_ALT_ON = 2;
        META_ALT_RIGHT_ON = 32;
        META_SHIFT_LEFT_ON = 64;
        META_SHIFT_ON = 1;
        META_SHIFT_RIGHT_ON = 128;
        META_SYM_ON = 4;
        CONTROL_LEFT = 129;
        CONTROL_RIGHT = 130;
        ESCAPE = 131;
        END = 132;
        INSERT = 133;
        PAGE_UP = 92;
        PAGE_DOWN = 93;
        PICTSYMBOLS = 94;
        SWITCH_CHARSET = 95;
        BUTTON_CIRCLE = 255;
        BUTTON_A = 96;
        BUTTON_B = 97;
        BUTTON_C = 98;
        BUTTON_X = 99;
        BUTTON_Y = 100;
        BUTTON_Z = 101;
        BUTTON_L1 = 102;
        BUTTON_R1 = 103;
        BUTTON_L2 = 104;
        BUTTON_R2 = 105;
        BUTTON_THUMBL = 106;
        BUTTON_THUMBR = 107;
        BUTTON_START = 108;
        BUTTON_SELECT = 109;
        BUTTON_MODE = 110;
        COLON = 243;
        F1 = 244;
        F2 = 245;
        F3 = 246;
        F4 = 247;
        F5 = 248;
        F6 = 249;
        F7 = 250;
        F8 = 251;
        F9 = 252;
        F10 = 253;
        F11 = 254;
        F12 = 255;
    }



    private int action_;
    private int pointerIndex_;
    private int pointerId_;

    public AndroidInput(Context context, View view) {
//        view_ = view;
        view.setOnTouchListener(this);
//        view.setOnKeyListener(this);

        SensorManager manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if (manager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() != 0) {
            Sensor accelerometer = manager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
            manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        }
    }



    @Override
    public float getAccX() {
        return accelX;
    }

    @Override
    public float getAccY() {
        return accelY;
    }

    @Override
    public float getAccZ() {
        return accelZ;
    }

    @Override
    public float getX(int pointer) {
        if (pointer>=20) return 0;
        return touchX[pointer];
    }

    @Override
    public float getY(int pointer) {
        if (pointer>=20) return 0;
        return touchY[pointer];
    }

    @Override
    public boolean isTouched(int pointer) {
        if (pointer>=20)
            return false;
        else
            return touched[pointer];
    }

    @Override
    public boolean isButtonPressed(int button) {
        if (button == Buttons.LEFT)
            return isTouched(0);
        else
            return false;
    }

//
//    @Override
//    public boolean onKey(View v, int keyCode, KeyEvent e) {
//        synchronized (this) {
////            char character = (char)e.getUnicodeChar();
////            // Android doesn't report a unicode char for back space. hrm...
////            if (keyCode == 67) character = '\b';
//
//            KeyEvent event = null;
//            switch (e.getAction()) {
//                case android.view.KeyEvent.ACTION_DOWN:
////                    event = usedKeyEvents.obtain();
////                    event.keyChar = 0;
////                    event.keyCode = e.getKeyCode();
////                    event.type = KeyEvent.KEY_DOWN;
////
//                    // Xperia hack for circle key. gah...
//                    if (keyCode == android.view.KeyEvent.KEYCODE_BACK && e.isAltPressed()) {
//                        keyCode = Keys.BUTTON_CIRCLE;
////                        event.keyCode = keyCode;
//                    }
////
////                    keyEvents.add(event);
//                    keys.add(keyCode);
//                    break;
//                case android.view.KeyEvent.ACTION_UP:
////                    event = usedKeyEvents.obtain();
////                    event.keyChar = 0;
////                    event.keyCode = e.getKeyCode();
////                    event.type = KeyEvent.KEY_UP;
//
//                    // Xperia hack for circle key. gah...
//                    if (keyCode == android.view.KeyEvent.KEYCODE_BACK && e.isAltPressed()) {
//                        keyCode = Keys.BUTTON_CIRCLE;
////                        event.keyCode = keyCode;
//                    }
//
////                    keyEvents.add(event);
////
////                    event = usedKeyEvents.obtain();
////                    event.keyChar = character;
////                    event.keyCode = 0;
////                    event.type = KeyEvent.KEY_TYPED;
////                    keyEvents.add(event);
//
////                    if (keyCode == Keys.BUTTON_CIRCLE)
////                        keys.remove(Keys.BUTTON_CIRCLE);
////                    else
//                        keys.remove(keyCode);
//            }
////            app.getGraphics().requestRendering();
//        }
//
//        // circle button on Xperia Play shouldn't need catchBack == true
//        if (keyCode == Keys.BUTTON_CIRCLE) return true;
//        if (catchBack && keyCode == android.view.KeyEvent.KEYCODE_BACK) return true;
//        if (catchMenu && keyCode == android.view.KeyEvent.KEYCODE_MENU) return true;
//
//        return false;
//    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        synchronized (this){
            action_ = event.getAction() & MotionEvent.ACTION_MASK;
            pointerIndex_ = (event.getAction() & MotionEvent.ACTION_POINTER_ID_MASK) >> MotionEvent.ACTION_POINTER_ID_SHIFT;
            pointerId_ = event.getPointerId(pointerIndex_);

            float x = (event.getX(pointerIndex_) - Graphics.XOFFSET)/Graphics.SCALE;
            float y = (event.getY(pointerIndex_) - Graphics.YOFFSET)/Graphics.SCALE;

            switch (action_){
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN:
                    touchX[pointerId_] = x;
                    touchY[pointerId_] = y;
                    touched[pointerId_] = true;
                    if (pointerId_ == 0)
                        wasTouched = true;
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP:
                case MotionEvent.ACTION_CANCEL:
                    touchX[pointerId_] = x;
                    touchY[pointerId_] = y;
                    touched[pointerId_] = false;
                    if (pointerId_ == 0)
                        wasUnTouched = true;
                    break;
                case MotionEvent.ACTION_MOVE:
                    // из-за глюков переполучим pointerId
                    int nPointer = event.getPointerCount();
                    if (nPointer > 20)
                       nPointer = 20;

                    for (int i=0; i<nPointer; i++ ){
                        pointerIndex_ = i;
                        pointerId_ = event.getPointerId(pointerIndex_);

                        touchX[pointerId_] = (event.getX(pointerIndex_) - Graphics.XOFFSET)/Graphics.SCALE;
                        touchY[pointerId_] = (event.getY(pointerIndex_) - Graphics.YOFFSET)/Graphics.SCALE;
                    }
                    break;
            }
            return true;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        accelX = event.values[0];
        accelY = event.values[1];
        accelZ = event.values[2];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // nothing to do here
    }
}
