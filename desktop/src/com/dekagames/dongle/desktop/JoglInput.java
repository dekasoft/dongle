package com.dekagames.dongle.desktop;


import com.dekagames.dongle.Input;

/**
 * Created by deka on 26.06.14.
 */
public class JoglInput extends Input {

    // инициализация переменных правильными кодами клавиш
    // TODO - must be filled with right values
    static {
        ANY_KEY = -1;
        NUM_0 = 48;
        NUM_1 = 49;
        NUM_2 = 50;
        NUM_3 = 51;
        NUM_4 = 52;
        NUM_5 = 53;
        NUM_6 = 54;
        NUM_7 = 55;
        NUM_8 = 56;
        NUM_9 = 57;
        A = 65;
        ALT_LEFT = 18;
        ALT_RIGHT = 18;
        APOSTROPHE = 75;
        AT = 77;
        B = 66;
        BACK = 4;
        BACKSLASH = 73;
        C = 67;
        CALL = 5;
        CAMERA = 27;
        CLEAR = 28;
        COMMA = 55;
        D = 68;
        DEL = 127;
        BACKSPACE = 8;
        DOWN = 40;
        LEFT = 37;
        RIGHT = 39;
        UP = 38;
        E = 69;
        ENDCALL = 0;
        ENTER = 10;
        ENVELOPE = 0;
        EQUALS = 70;
        EXPLORER = 64;
        F = 70;
        G = 71;
        GRAVE = 68;
        H = 72;
        HOME = 3;
        I = 73;
        J = 74;
        K = 75;
        L = 76;
        LEFT_BRACKET = 71;
        M = 77;
        MENU = 82;
        MINUS = 69;
        N = 78;
        NOTIFICATION = 83;
        NUM = 78;
        O = 79;
        P = 80;
        PERIOD = 56;
        PLUS = 81;
        POUND = 18;
        POWER = 26;
        Q = 81;
        R = 82;
        RIGHT_BRACKET = 72;
        S = 83;
        SEMICOLON = 74;
        SHIFT_LEFT = 59;
        SHIFT_RIGHT = 60;
        SLASH = 76;
        SPACE = 32;
        STAR = 17;
        SYM = 63;
        T = 84;
        U = 85;
        UNKNOWN = 0;
        V = 86;
        W = 87;
        X = 88;
        Y = 89;
        Z = 90;
        CONTROL_LEFT = 17;
        CONTROL_RIGHT = 17;
        ESCAPE = 27;
        END = 132;
        INSERT = 155;
        PAGE_UP = 92;
        PAGE_DOWN = 93;
        COLON = 243;
        F1 = 112;
        F2 = 113;
        F3 = 114;
        F4 = 115;
        F5 = 116;
        F6 = 117;
        F7 = 118;
        F8 = 119;
        F9 = 120;
        F10 = 121;
        F11 = 122;
        F12 = 123;
    }


    @Override
    public float getAccX() {
        return 0;
    }

    @Override
    public float getAccY() {
        return 0;
    }

    @Override
    public float getAccZ() {
        return 0;
    }

    @Override
    public float getX(int pointer) {
        return touchX[0];
    }

    @Override
    public float getY(int pointer) {
        return touchY[0];
    }

    @Override
    public boolean isTouched(int pointer) {
        if (pointer == 0)
            return touched[0];
        else
            return false;
    }

    @Override
    public boolean isButtonPressed(int button) {
        if (button == Buttons.LEFT)
            return buttoned[0];
        if (button == Buttons.RIGHT)
            return buttoned[1];
        if (button == Buttons.MIDDLE)
            return buttoned[2];

        return false;
    }

//    @Override
//    public boolean isKeyPressed(int key) {
//        return false;
//    }
//
//    @Override
//    public void keyPressed(KeyEvent keyEvent) {
//
//    }
//
//    @Override
//    public void keyReleased(KeyEvent keyEvent) {
//
//    }
//
//    @Override
//    public void keyTyped(KeyEvent e) {
//
//    }
}
