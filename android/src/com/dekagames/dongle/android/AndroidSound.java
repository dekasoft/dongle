package com.dekagames.dongle.android;

import android.media.AudioManager;
import android.media.SoundPool;
import com.dekagames.dongle.Sound;

import java.util.LinkedList;

/**
 * Created by deka on 22.07.14.
 */
public class AndroidSound implements Sound {
    final SoundPool soundPool;
//    final AudioManager manager;
    final int soundId;
    final LinkedList<Integer> streamIds = new LinkedList<Integer>();

    public AndroidSound (SoundPool pool, int soundId) {
        this.soundPool = pool;
        this.soundId = soundId;
    }

//    @Override
//    public void dispose () {
//        soundPool.unload(soundId);
//    }

    @Override
    public void play () {
        if (streamIds.size() == 8) streamIds.removeFirst();
        int streamId = soundPool.play(soundId, 1, 1, 1, 0, 1);
        streamIds.addLast(streamId);
    }

    @Override
    public void stop () {
        for (int i = 0, n = streamIds.size(); i < n; i++)
            soundPool.stop(streamIds.get(i));
    }

}
