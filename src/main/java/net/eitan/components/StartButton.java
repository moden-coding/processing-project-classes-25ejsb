package main.java.net.eitan.components;

import main.java.net.eitan.App;
import main.java.net.eitan.components.types.Button;
import processing.core.PApplet;

public class StartButton extends Button {

    public StartButton(float x, float y, float width, float height, PApplet pApplet, int[] rgb, String text, int[] textRgb, float fSize, float borderRadius) {
        super(x, y, width, height, pApplet, rgb, text, textRgb, fSize, borderRadius);
    }

    @Override
    protected void onMouseClick() {
        App.inMenu = false;
    }
}
