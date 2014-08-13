package com.dekagames.dongle.example.main;

import com.dekagames.dongle.Game;

/**
 * Created by deka on 23.06.14.
 */
public class MainGame extends Game {
    private MainScreen mainScreen;

    public MainGame(){
        super(800, 480);
        mainScreen = new MainScreen(this);
        setScreen(mainScreen);
    }
}
