package com.dekagames.dongle.desktop;

import com.dekagames.dongle.FileIO;
import com.dekagames.dongle.Game;
import com.dekagames.dongle.Log;

import java.io.*;
import java.util.logging.Level;

/**
 * Created by deka on 26.06.14.
 */
public class JoglFileIO implements FileIO {

    // метод нужен специально для загрузки звука. если загружать звук просто из файла,
    // TinySound зачем-то добавляет впереди имени слэш
    public File getFile(String filename){
        File file = new File(filename);
        if (file.exists()){
            return file;
        }
        else {
            Log.error("File "+filename+ " not found!");
            return null;
        }
    }



    @Override
    public InputStream readAsset(String filename) {
        InputStream is = null;
        File file = new File(filename);
        if (file.exists()){
            try {
                is = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                Log.exception("File "+ filename +" not found! Caused exception ", e);
            }
        }
        else {
            Log.error("File "+filename+ " not found!");
        }
        return is;
    }

    @Override
    public InputStream readFile(String filename) {
        return null;
    }

    @Override
    public OutputStream writeFile(String filename) {
        return null;
    }

    @Override
    public boolean isFileExists(String filename){
        File f = new File(filename);
        return (!f.isDirectory() && f.exists());
    }
}
