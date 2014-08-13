package com.dekagames.dongle.desktop;

import com.dekagames.dongle.*;
import kuusisto.tinysound.TinySound;

import java.io.File;

/**
 * Created by deka on 23.07.14.
 */
public class JoglAudio implements Audio {
    private JoglFileIO fileIO;

    public  JoglAudio(JoglFileIO joglFileIO){
        fileIO = joglFileIO;
        TinySound.init();
    }

    @Override
    public Music newMusic(String path, boolean isInternal) {
        kuusisto.tinysound.Music m;
        File f = fileIO.getFile(path);
        m = TinySound.loadMusic(f, true);
        if (m == null){
            Log.error("Could not load music from "+path);
            return null;
        }

        return new JoglMusic(m);
    }

    @Override
    public Sound newSound(String path, boolean isInternal) {
        kuusisto.tinysound.Sound s;
//        if (isInternal){
//            s = TinySound.loadSound(game.fileIO.readAsset(path).)
//        }
        File f = fileIO.getFile(path);
        s = TinySound.loadSound(f);
        if (s == null){
            Log.error("Could not load sound from "+path);
            return null;
        }

        return new JoglSound(s);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        TinySound.shutdown();
    }
}
