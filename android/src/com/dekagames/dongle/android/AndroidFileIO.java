package com.dekagames.dongle.android;

import android.content.res.AssetManager;
import android.os.Environment;
import com.dekagames.dongle.FileIO;
import com.dekagames.dongle.Game;
import com.dekagames.dongle.Log;

import java.io.*;
import java.util.logging.Level;

/**
 * Created by deka on 17.06.14.
 */
public class AndroidFileIO implements FileIO {
    public static AssetManager assetManager;
    public static String  externalStoragePath;


    public AndroidFileIO(AssetManager assetManager){
        this.assetManager = assetManager;
        this.externalStoragePath = Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator;
    }

    public InputStream readAsset(String filename) {
        InputStream is=null;
        try {
            is = assetManager.open(filename);
        } catch (IOException e) {
            Log.exception("Could not read asset " + filename, e);
        }
        return is;
    }

    public InputStream readFile(String filename) {
        InputStream is = null;
        try {
            is = new FileInputStream(externalStoragePath+filename);
        } catch (FileNotFoundException e) {
            Log.exception("Could not read file " + filename, e);
        }
        return is;
    }

    public OutputStream writeFile(String filename) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(externalStoragePath+filename);
        } catch (FileNotFoundException e) {
            Log.exception("Could not write file " + filename, e);
        }
        return os;
    }

    @Override
    public boolean isFileExists(String filename) {
        File f = new File(externalStoragePath+filename);
        return (!f.isDirectory() && f.exists());
    }


    @Override
    public boolean isDirectoryExists(String dirname){
        File f = new File(externalStoragePath+dirname);
        return (f.isDirectory() && f.exists());
    }

    @Override
    public void createDirectory(String dirname){
        File f = new File(externalStoragePath+dirname);
        f.mkdirs();
    }


}
