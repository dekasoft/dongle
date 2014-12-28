package com.dekagames.dongle;


import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 * User: Deka
 * Date: 22.10.13
 * Time: 20:23
 * To change this template use File | Settings | File Templates.
 */
public abstract class Input {
//    private View view_;
    public float touchX[] = new float[5];              // координаты указателя (до 5 одновременно)
    public float touchY[] = new float[5];              //
    public boolean touched[] = new boolean[5];          // и флаг нажатости
    public boolean buttoned[] = new boolean[3];         // три мышиные кнопки

    // для акселерометра
    protected float accelX;
    protected float accelY;
    protected float accelZ;

    public boolean catchBackKey;
    public boolean catchMenuKey;

    // сюда классы потомки должны записывать коды нажатых клавиш
    public HashSet<Integer> keys = new HashSet<Integer>();

    // коды последней нажатой и последней отжатой клавиши. при считывании стираются
    public int lastKeyUp, lastKeyDown;

    /** Флаг, показывающий что с момента обнуления его произошло событие касания
     * нулевого указателя (единственного/последнего пальца или левой кнопки мыши). Требует ручного обнуления */
    public boolean wasTouched;

    /** Флаг, показывающий что с момента обнуления его произошло событие отпускания
     * нулевого указателя (единственного/последнего пальца или левой кнопки мыши). Требует ручного обнуления */
    public boolean wasUntouched;

    // кнопки мыши
    public class Buttons {
        public static final int LEFT = 0;
        public static final int RIGHT = 1;
        public static final int MIDDLE = 2;
    }

    /** @return The value of the accelerometer on its x-axis. ranges between [-10,10]. */
    public abstract float getAccX();

    /** @return The value of the accelerometer on its y-axis. ranges between [-10,10]. */
    public abstract float getAccY();

    /** @return The value of the accelerometer on its y-axis. ranges between [-10,10]. */
    public abstract float getAccZ();

    /** Returns the x coordinate in screen coordinates of the given pointer. Pointers are indexed from 0 to n. The pointer id
     * identifies the order in which the fingers went down on the screen, e.g. 0 is the first finger, 1 is the second and so on.
     * When two fingers are touched down and the first one is lifted the second one keeps its index. If another finger is placed on
     * the touch screen the first free index will be used.
     *
     * @param pointer the pointer id.
     * @return the x coordinate */
    public abstract float getX(int pointer);


    /** Returns the y coordinate in screen coordinates of the given pointer. Pointers are indexed from 0 to n. The pointer id
     * identifies the order in which the fingers went down on the screen, e.g. 0 is the first finger, 1 is the second and so on.
     * When two fingers are touched down and the first one is lifted the second one keeps its index. If another finger is placed on
     * the touch screen the first free index will be used.
     *
     * @param pointer the pointer id.
     * @return the y coordinate */
    public abstract float getY(int pointer);


//    /** @return whether a new touch down event just occured. */
//    public boolean justTouched ();


    /** Whether the screen is currently touched by the pointer with the given index. Pointers are indexed from 0 to n. The pointer
     * id identifies the order in which the fingers went down on the screen, e.g. 0 is the first finger, 1 is the second and so on.
     * When two fingers are touched down and the first one is lifted the second one keeps its index. If another finger is placed on
     * the touch screen the first free index will be used.
     *
     * @param pointer the pointer
     * @return whether the screen is touched by the pointer */
    public abstract boolean isTouched(int pointer);

    /** Whether a given button is pressed or not. Button constants can be found in {@link Buttons}. On Android only the Button#LEFT
     * constant is meaningful.
     * @param button the button to check.
     * @return whether the button is down or not. */
    public abstract boolean isButtonPressed(int button);

    /** Returns whether the key is pressed.
     *
     * @param key The key code as found in {@link Input}.
     * @return true or false. */
    public boolean isKeyPressed(int key) {
        synchronized (this) {
            if (key == Input.ANY_KEY)
                return keys.size() > 0;
            else
                return keys.contains(key);
        }
    }

    /**
     * Возвращает код последней нажатой клавиши и стирает его из буфера, то есть повторный вызов метода
     * вернет уже 0 (если не было других нажатий)
     * @return код последней нажатой клавиши
     */
    public int getLastKeyUp(){
        int k = lastKeyUp;
        lastKeyUp = 0;
        return k;
    }



    /**
     * Возвращает код последней отжатой клавиши и стирает его из буфера, то есть повторный вызов метода
     * вернет уже 0 (если не было других отжатий)
     * @return код последней отжатой клавиши
     */
    public int getLastKeyDown(){
        int k = lastKeyDown;
        lastKeyDown = 0;
        return k;
    }



    public static int ANY_KEY;
    public static int NUM_0;
    public static int NUM_1;
    public static int NUM_2;
    public static int NUM_3;
    public static int NUM_4;
    public static int NUM_5;
    public static int NUM_6;
    public static int NUM_7;
    public static int NUM_8;
    public static int NUM_9;
    public static int A;
    public static int ALT_LEFT;
    public static int ALT_RIGHT;
    public static int APOSTROPHE;
    public static int AT;
    public static int B;
    public static int BACK;
    public static int BACKSLASH;
    public static int C;
    public static int CALL;
    public static int CAMERA;
    public static int CLEAR;
    public static int COMMA;
    public static int D;
    public static int DEL;
    public static int BACKSPACE;
    public static int FORWARD_DEL;
    public static int DPAD_CENTER;
    public static int DPAD_DOWN;
    public static int DPAD_LEFT;
    public static int DPAD_RIGHT;
    public static int DPAD_UP;
    public static int CENTER;
    public static int DOWN;
    public static int LEFT;
    public static int RIGHT;
    public static int UP;
    public static int E;
    public static int ENDCALL;
    public static int ENTER;
    public static int ENVELOPE;
    public static int EQUALS;
    public static int EXPLORER;
    public static int F;
    public static int FOCUS;
    public static int G;
    public static int GRAVE;
    public static int H;
    public static int HEADSETHOOK;
    public static int HOME;
    public static int I;
    public static int J;
    public static int K;
    public static int L;
    public static int LEFT_BRACKET;
    public static int M;
    public static int MEDIA_FAST_FORWARD;
    public static int MEDIA_NEXT;
    public static int MEDIA_PLAY_PAUSE;
    public static int MEDIA_PREVIOUS;
    public static int MEDIA_REWIND;
    public static int MEDIA_STOP;
    public static int MENU;
    public static int MINUS;
    public static int MUTE;
    public static int N;
    public static int NOTIFICATION;
    public static int NUM;
    public static int O;
    public static int P;
    public static int PERIOD;
    public static int PLUS;
    public static int POUND;
    public static int POWER;
    public static int Q;
    public static int R;
    public static int RIGHT_BRACKET;
    public static int S;
    public static int SEARCH;
    public static int SEMICOLON;
    public static int SHIFT_LEFT;
    public static int SHIFT_RIGHT;
    public static int SLASH;
    public static int SOFT_LEFT;
    public static int SOFT_RIGHT;
    public static int SPACE;
    public static int STAR;
    public static int SYM;
    public static int T;
    public static int TAB;
    public static int U;
    public static int UNKNOWN;
    public static int V;
    public static int VOLUME_DOWN;
    public static int VOLUME_UP;
    public static int W;
    public static int X;
    public static int Y;
    public static int Z;
    public static int META_ALT_LEFT_ON;
    public static int META_ALT_ON;
    public static int META_ALT_RIGHT_ON;
    public static int META_SHIFT_LEFT_ON;
    public static int META_SHIFT_ON;
    public static int META_SHIFT_RIGHT_ON;
    public static int META_SYM_ON;
    public static int CONTROL_LEFT;
    public static int CONTROL_RIGHT;
    public static int ESCAPE;
    public static int END;
    public static int INSERT;
    public static int PAGE_UP;
    public static int PAGE_DOWN;
    public static int PICTSYMBOLS;
    public static int SWITCH_CHARSET;
    public static int BUTTON_CIRCLE;
    public static int BUTTON_A;
    public static int BUTTON_B;
    public static int BUTTON_C;
    public static int BUTTON_X;
    public static int BUTTON_Y;
    public static int BUTTON_Z;
    public static int BUTTON_L1;
    public static int BUTTON_R1;
    public static int BUTTON_L2;
    public static int BUTTON_R2;
    public static int BUTTON_THUMBL;
    public static int BUTTON_THUMBR;
    public static int BUTTON_START;
    public static int BUTTON_SELECT;
    public static int BUTTON_MODE;

// public static final int BACKTICK = 0;
// public static final int TILDE = 0;
// public static final int UNDERSCORE = 0;
// public static final int DOT = 0;
// public static final int BREAK = 0;
// public static final int PIPE = 0;
// public static final int EXCLAMATION = 0;
// public static final int QUESTIONMARK = 0;

    // ` | VK_BACKTICK
// ~ | VK_TILDE
// : | VK_COLON
// _ | VK_UNDERSCORE
// . | VK_DOT
// (break) | VK_BREAK
// | | VK_PIPE
// ! | VK_EXCLAMATION
// ? | VK_QUESTION
    public static int COLON;
    public static int F1;
    public static int F2;
    public static int F3;
    public static int F4;
    public static int F5;
    public static int F6;
    public static int F7;
    public static int F8;
    public static int F9;
    public static int F10;
    public static int F11;
    public static int F12;
}
