package com.dekagames.dongle;

/**
 * Created by deka on 22.07.14.
 */
public interface Audio {

    public Music newMusic(String path, boolean isInternal);

    public Sound newSound(String path, boolean isInternal);

    public void pause();

    public void resume();

    public void dispose();

}
