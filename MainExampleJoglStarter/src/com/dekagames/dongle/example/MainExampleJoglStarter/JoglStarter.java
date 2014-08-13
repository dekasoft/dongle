package com.dekagames.dongle.example.MainExampleJoglStarter;

import com.dekagames.dongle.desktop.JoglApp;
import com.dekagames.dongle.example.main.MainGame;

/**
 * Created by deka on 27.06.14.
 */
public class JoglStarter extends JoglApp {

    public static void main(String[] args) {
        new JoglStarter().start(new MainGame(), "Dongle Example");
    }

}
