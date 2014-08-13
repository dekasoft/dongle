package com.dekagames.dongle;

/**
 * Created by deka on 22.07.14.
 */
public interface Music {

    public void play();

    public void stop();

    public void pause();

    public void setLooping(boolean looping);

    public void setVolume(float volume);

    public boolean isPlaying();

}
