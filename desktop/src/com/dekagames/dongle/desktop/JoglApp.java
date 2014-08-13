package com.dekagames.dongle.desktop;

import com.dekagames.dongle.*;
import com.dekagames.dongle.Graphics;
import com.jogamp.opengl.util.FPSAnimator;

import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;

import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by deka on 26.06.14.
 */
public class JoglApp implements Application, GLEventListener, KeyListener, MouseListener, MouseMotionListener{

    public      Game            game;

    private     GLProfile       glProfile;
    private     GLCapabilities  glCapabilities;
    private     GLCanvas        glCanvas;
    private     FPSAnimator     animator;

    private     JFrame          jFrame;


    private     long        startTime = System.nanoTime();


    @Override
    public void start(Game game, String title) {
        this.game = game;

        // модуль работы с файлами
        JoglFileIO fio = new JoglFileIO();
        game.fileIO = fio;

        // модуль работы со вводом (тачскрин и акселерометр)
        JoglInput input = new JoglInput();
        game.input = input;

        // модуль работы со звуком
        game.audio = new JoglAudio(fio);

        glProfile = GLProfile.getDefault();
        glCapabilities = new GLCapabilities( glProfile );
        glCanvas = new GLCanvas( glCapabilities );
        glCanvas.addGLEventListener(this);
        glCanvas.addKeyListener(this);
        glCanvas.addMouseListener(this);
        glCanvas.addMouseMotionListener(this);


        animator = new FPSAnimator(glCanvas, 60);
        animator.start();

        jFrame = new JFrame(title);
        jFrame.addWindowListener( new WindowAdapter() {
            public void windowClosing( WindowEvent windowevent ) {
                jFrame.dispose();
                System.exit( 0 );
            }
        });

        jFrame.getContentPane().add(glCanvas, BorderLayout.CENTER);
        jFrame.setSize( game.getVirtualWidth(), game.getVirtualHeight()); // начальные размеры окна равны виртуальному разрешению
        jFrame.setVisible( true );
    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        Log.info("GLEventListener - init");
        game.graphics = new JoglGraphics(game, glAutoDrawable.getGL());
//        game.graphics.init(width, height);

        game.initialize();
        game.resume();

        startTime = System.nanoTime();
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {
        Log.info("GLEventListener - dispose");
        game.audio.dispose();
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        long  now = System.nanoTime();
        float deltaTime = (now - startTime)/1000000000.0f;      // время в секундах
        startTime = now;

        if (game != null) {
            game.update(deltaTime);
            game.draw();
            // обнулим последние события тачей
            synchronized(game.input){
                game.input.wasTouched = false;
                game.input.wasUnTouched = false;
            }
        }
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int w, int h) {
        Log.info("GLEventListener - reshape to width="+w+", height="+h);
        if (game != null) {
            game.graphics.init(w, h);
        }
        else
            Log.info("game = null!!!!");

    }

    @Override
    public void keyTyped(java.awt.event.KeyEvent e) {
        // nothing to do here
    }

    @Override
    public void keyPressed(java.awt.event.KeyEvent e) {
        int keyCode = e.getKeyCode();
        Log.info("JoglApp keyPressed. keyCode ="+keyCode);
        game.input.lastKeyDown = keyCode;
        game.input.keys.add(keyCode);
    }

    @Override
    public void keyReleased(java.awt.event.KeyEvent e) {
        int keyCode = e.getKeyCode();
        Log.info("JoglApp keyReleased. keyCode ="+keyCode);
        game.input.lastKeyUp = keyCode;
        game.input.keys.remove(keyCode);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // nothing to do here
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Log.info("JoglApp mouse pressed" );
        if (e.getButton() == 1) {               // левая кнопка
            synchronized (game.input) {
                game.input.buttoned[0] = true;
                game.input.touched[0] = true;
                game.input.wasTouched = true;
            }
        }
        if (e.getButton() == 3)                 // правая кнопка
            game.input.buttoned[1] = true;

        if (e.getButton() == 2)                 // средняя кнопка
            game.input.buttoned[2] = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Log.info("JoglApp mouse released" );
        if (e.getButton() == 1) {                   // левая кнопка
            synchronized (game.input) {
                game.input.buttoned[0] = false;
                game.input.touched[0] = false;
                game.input.wasUnTouched = true;
            }
        }
        if (e.getButton() == 3)                     // правая кнопка
            game.input.buttoned[1] = false;
        if (e.getButton() == 2)                     //средняя кнопка
            game.input.buttoned[2] = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // nothing to do here
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // nothing to do here
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // координаты мыши записываются в координаты первого пальца тачскрина
        game.input.touchX[0] = (e.getX()- Graphics.XOFFSET)/Graphics.SCALE;
        game.input.touchY[0] = (e.getY()- Graphics.YOFFSET)/Graphics.SCALE;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // координаты мыши записываются в координаты первого пальца тачскрина
        game.input.touchX[0] = (e.getX()- Graphics.XOFFSET)/Graphics.SCALE;
        game.input.touchY[0] = (e.getY()- Graphics.YOFFSET)/Graphics.SCALE;
    }
}
