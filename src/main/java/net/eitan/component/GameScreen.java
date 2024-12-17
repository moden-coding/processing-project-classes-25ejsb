package main.java.net.eitan.component;

import controlP5.Textlabel;
import main.java.net.eitan.App;
import main.java.net.eitan.util.GetFont;
import processing.core.PApplet;

public class GameScreen {
    public Textlabel loadingScreen;
    public void loadingScreen(PApplet applet) {
        applet.println("Loading");
        loadingScreen = App.controlP5.addLabel("Waiting for server to load.. :D");
        loadingScreen.setPosition((App.windowX/2)-(loadingScreen.getWidth()/2)-50, (App.windowY/2)-(loadingScreen.getHeight()/2));
        loadingScreen.setColor(0);
        loadingScreen.setFont(GetFont.getFont("Montserrat.ttf", 30, applet));
    }
    public void hide(PApplet pApplet) {
        loadingScreen.setValueLabel("");
    }
}
