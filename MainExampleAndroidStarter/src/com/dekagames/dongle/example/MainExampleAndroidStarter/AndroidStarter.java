package com.dekagames.dongle.example.MainExampleAndroidStarter;

import android.os.Bundle;
import com.dekagames.dongle.android.AndroidApp;
import com.dekagames.dongle.example.main.MainGame;

// "Запускатор" для андроида.
// Должен наследоваться от AndroidApp и вызывать метод start() с передачей ему в качестве
// единственного параметра экземпляра класса-наследника Game.
public class AndroidStarter extends AndroidApp {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        start(new MainGame(), "Dongle Example");
    }
}
