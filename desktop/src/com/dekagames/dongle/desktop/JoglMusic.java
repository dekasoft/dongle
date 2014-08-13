package com.dekagames.dongle.desktop;

import com.dekagames.dongle.Music;

/**
 * Created by deka on 23.07.14.
 */
public class JoglMusic implements Music {
    private kuusisto.tinysound.Music music;
    private boolean isLooping;

    public JoglMusic(kuusisto.tinysound.Music m) {
        music = m;
    }

    @Override
    public void play() {
        if (music != null) music.play(isLooping);
    }

    @Override
    public void stop() {
        if (music != null) music.stop();
    }

    @Override
    public void pause() {
        if (music != null) music.pause();
    }

    @Override
    public void setLooping(boolean looping) {
        isLooping = looping;
    }

    @Override
    public void setVolume(float volume) {
        if (music!=null) music.setVolume(volume);
    }

    @Override
    public boolean isPlaying() {
        return music.playing();
    }
}
