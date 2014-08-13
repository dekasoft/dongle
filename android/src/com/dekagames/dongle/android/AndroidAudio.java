package com.dekagames.dongle.android;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import com.dekagames.dongle.Audio;
import com.dekagames.dongle.Log;
import com.dekagames.dongle.Music;
import com.dekagames.dongle.Sound;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by deka on 22.07.14.
 */
@TargetApi(Build.VERSION_CODES.FROYO)
public class AndroidAudio implements Audio {
    private final SoundPool soundPool;
//    private final AudioManager manager;
    protected final ArrayList<AndroidMusic> musics = new ArrayList<AndroidMusic>();

    public AndroidAudio (Context context) {
        soundPool = new SoundPool(8, AudioManager.STREAM_MUSIC, 0);               // 8 - одновременно звуков
//        manager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        if(context instanceof Activity) {
            ((Activity)context).setVolumeControlStream(AudioManager.STREAM_MUSIC);
        }
    }

    @Override
    public void pause () {
        synchronized (musics) {
            for (AndroidMusic music : musics) {
                if (music.isPlaying()) {
                    music.wasPlaying = true;
                    music.pause();

                } else
                    music.wasPlaying = false;
            }
        }
    }

    @Override
    public void resume () {
        synchronized (musics) {
            for (int i = 0; i < musics.size(); i++) {
                if (musics.get(i).wasPlaying == true) musics.get(i).play();
            }
        }
    }

//    /** {@inheritDoc} */
//    @Override
//    public AudioDevice newAudioDevice (int samplingRate, boolean isMono) {
//        return new AndroidAudioDevice(samplingRate, isMono);
//    }

    /** {@inheritDoc} */
    @Override
    public Music newMusic (String path, boolean isInternal) {
        MediaPlayer mediaPlayer = new MediaPlayer();

        if (isInternal) {
            try {
                AssetFileDescriptor descriptor = AndroidFileIO.assetManager.openFd(path);
                mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
                descriptor.close();
                mediaPlayer.prepare();
                AndroidMusic music = new AndroidMusic(this, mediaPlayer);
                synchronized (musics) {
                    musics.add(music);
                }
                return music;
            } catch (Exception ex) {
                Log.exception("Error loading audio file: " + path
                        + "\nNote: Internal audio files must be placed in the assets directory.", ex);
            }
        } else {
            try {
                mediaPlayer.setDataSource(path);
                mediaPlayer.prepare();
                AndroidMusic music = new AndroidMusic(this, mediaPlayer);
                synchronized (musics) {
                    musics.add(music);
                }
                return music;
            } catch (Exception ex) {
                Log.exception("Error loading audio file: " + path, ex);
            }
        }
        return  null;
    }

    /** {@inheritDoc} */
    @Override
    public Sound newSound (String path, boolean isInternal) {
        if (isInternal) {
            try {
                AssetFileDescriptor descriptor = AndroidFileIO.assetManager.openFd(path);
                AndroidSound sound = new AndroidSound(soundPool, soundPool.load(descriptor, 1));
                descriptor.close();
                return sound;
            }
            catch (IOException ex) {
                Log.exception("Error loading audio file: " + path
                        + "\nNote: Internal audio files must be placed in the assets directory.", ex);
            }
        }
        else {
            try {
                return new AndroidSound(soundPool, soundPool.load(path, 1));
            } catch (Exception ex) {
                Log.exception("Error loading audio file: " + path, ex);
            }
        }
        return null;
    }

    /** Kills the soundpool and all other resources */
    @Override
    public void dispose () {
        synchronized (musics) {
            // gah i hate myself.... music.dispose() removes the music from the list...
            ArrayList<AndroidMusic> musicsCopy = new ArrayList<AndroidMusic>(musics);
            for (AndroidMusic music : musicsCopy) {
                music.dispose();
            }
        }
        soundPool.release();
    }
}
