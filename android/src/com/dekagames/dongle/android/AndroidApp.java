package com.dekagames.dongle.android;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import com.dekagames.dongle.*;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by deka on 16.06.14.
 */
@TargetApi(Build.VERSION_CODES.FROYO)
public class AndroidApp extends Activity implements Application, GLSurfaceView.Renderer{
    enum GameState {
        RUNNING,
        PAUSED,
        IDLE
    }

    public      Game            game;
    protected   GLSurfaceView   glView;
    protected   PowerManager.WakeLock   wakeLock;

    long                    startTime = System.nanoTime();

    Object                  stateMonitor = new Object();
    GameState               gameState = GameState.RUNNING;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onDestroy(){
        if (game.audio != null){
            game.audio.dispose();
        }
        super.onDestroy();
    }

    @Override
    public void start(Game game, String title) {
        if (game == null) return;
        this.game = game;

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        glView = new GLSurfaceView(this);

        // Проверяем поддерживается ли OpenGL ES 2.0.
        final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000;

        if (supportsEs2)   {
            // Запрос OpenGL ES 2.0 для установки контекста.
            glView.setEGLContextClientVersion(2);
            glView.setRenderer(this);
            setContentView(glView);

            // модуль работы с файлами
            game.fileIO = new AndroidFileIO(getAssets());

            // модуль работы со вводом (тачскрин и акселерометр)
            game.input = new AndroidInput(this, glView);

            // модуль для работы со звуком
            game.audio = new AndroidAudio(this);

            PowerManager pm = (PowerManager)getSystemService(Context.POWER_SERVICE);
            wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK,"Game");
            Log.info("AndroidApp start method ended - game created");
        }
        else  {
            Log.error("Your device does not support OpenGL ES 2.0!");
            throw(new RuntimeException("Unsupported device!"));
        }
    }


    //----------------------------- обработка клавиш -----------------------------------
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        Log.info("AndroidApp onKeyDown. keyCode ="+keyCode);
        game.input.lastKeyDown = keyCode;
        game.input.keys.add(keyCode);
//        game.input.catchBackKey = true;

        if (game.input.catchBackKey && (keyCode == game.input.BACK))
            return true;                // предотвращаем обработку клавиши системой


        if (game.input.catchMenuKey && keyCode == game.input.MENU)
            return true;                // предотвращаем обработку клавиши системой

        return super.onKeyDown(keyCode, event);
    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event){
        Log.info("AndroidApp onKeyUp. keyCode ="+keyCode);
        game.input.lastKeyUp = keyCode;
        game.input.keys.remove(keyCode);
        if (game.input.catchBackKey && keyCode == game.input.BACK)
            return true;                // предотвращаем обработку клавиши системой
        if (game.input.catchMenuKey && keyCode == game.input.MENU)
            return true;                // предотвращаем обработку клавиши системой
        return super.onKeyUp(keyCode, event);
    }


    @Override
    public void onResume(){
        super.onResume();
        Log.info("AndroidApp onResume called");
        glView.onResume();
        // game.resume вызывается в onSurfaceCreated. Тут не нужно.
        //        if (game != null)   game.resume();

        wakeLock.acquire();
        gameState = GameState.RUNNING;
    }


    @Override
    public void onPause(){
        Log.info("AndroidApp onPause called");
        synchronized (stateMonitor){
            gameState = GameState.PAUSED;
            while (true){
                try {
                    stateMonitor.wait();
                    break;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        wakeLock.release();
        glView.onPause();

        if (game != null) {
            game.pause();
            game.setNeedToReloadTextures(true);
        }
        super.onPause();
    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Log.info("AndroidApp onSurfaceCreated called");
        if (game == null) return;
        // запишем модуль для работы с графикой
        game.graphics = new AndroidGraphics(game);

//        synchronized(stateMonitor){
//            game.initialize();
//            game.resume();
//
//            startTime = System.nanoTime();
//        }

    }


    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        Log.info("AndroidApp onSurfaceChanged called. W="+width+"; H="+height+";");
        if (game != null) {
            synchronized(stateMonitor){
                game.initialize();
                game.resume();

                startTime = System.nanoTime();
            }
            game.graphics.init(width, height);

        }
        else
            Log.info("game = null!!!!");
    }


    @Override
    public void onDrawFrame(GL10 gl) {
        GameState state = null;

        synchronized (stateMonitor){
            state = gameState;
        }

        if (state == GameState.RUNNING){
            long  now = System.nanoTime();
            float deltaTime = (now - startTime)/1000000000.0f;
            startTime = now;

            if (game != null) {
//                synchronized (game.input) {
//                    Log.info("Clearing was touched");
//                    game.input.was_touched = false;
//                    game.input.was_untouched = false;
//                }

                game.update(deltaTime);
                game.draw();
            }
        }

        if (state == GameState.PAUSED){
            synchronized (stateMonitor){
                gameState = GameState.IDLE;
                stateMonitor.notifyAll();
            }
        }


    }
}
