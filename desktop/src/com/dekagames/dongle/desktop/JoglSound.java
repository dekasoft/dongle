package com.dekagames.dongle.desktop;

import com.dekagames.dongle.Sound;
//import kuusisto.tinysound.TinySound;

/**
 * Created by deka on 23.07.14.
 */
public class JoglSound implements Sound {
    private kuusisto.tinysound.Sound sound;

    JoglSound(kuusisto.tinysound.Sound s){
        sound = s;
    }


    @Override
    public void play() {
        if (sound!= null) sound.play();
    }

    @Override
    public void stop() {
        if (sound!=null) sound.stop();
    }
}
