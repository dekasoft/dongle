package com.dekagames.dongle.example.main;

import com.dekagames.dongle.gui.Control;
import com.dekagames.dongle.gui.Window;
import com.dekagames.dongle.gui.controls.Button;
import com.dekagames.slon.SlonNode;

/**
 * Created by deka on 05.01.15.
 */
public class MainWindow extends Window {
    public Button button;

    public MainWindow(int width, int height) {
        super(width, height);
    }

    @Override
    public void initControls(){
        MainScreen scr = (MainScreen)getScreen();
        SlonNode nodeSprites = scr.slon.getRoot().getChildWithKeyValue("name", "sprites");
        SlonNode nodeButton = nodeSprites.getChildWithKeyValue("name", "sprButton");

        button = new Button(2, scr.textureAtlas, nodeButton, 0, 0);
        addCtrl(button);
    }

    @Override
    public Control update(float deltaTime){
        Control ctrl = super.update(deltaTime);

        if (ctrl == button){
            buttonPressed();
        }
        return ctrl;
    }



    public void buttonPressed(){
        MainScreen scr = (MainScreen)getScreen();
        scr.sound.play();
    }
}
