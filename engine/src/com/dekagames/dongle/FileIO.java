package com.dekagames.dongle;

import java.io.*;

public interface FileIO {

    public InputStream readAsset(String filename);

    public InputStream readFile(String filename);

    public OutputStream writeFile(String filename);

    public boolean isFileExists(String filename);
}

