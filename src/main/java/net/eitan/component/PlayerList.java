package main.java.net.eitan.component;

import controlP5.ControlFont;
import controlP5.ScrollableList;
import controlP5.Textlabel;
import main.java.net.eitan.App;
import main.java.net.eitan.util.GetFont;
import processing.core.PApplet;

public class PlayerList {
    public static ScrollableList userList;
    public static void drawGameScreen(PApplet applet, String users) {
        userList = App.controlP5.addScrollableList("Players");
        userList.setPosition(20, 20);
        userList.setSize(120, 700);
        userList.setLabelVisible(false);
        userList.setBarHeight(30);
        userList.setItemHeight(30);
        userList.getCaptionLabel().align(App.controlP5.CENTER, App.controlP5.CENTER);
        String[] userListObject = users.split(",");
        App.controlP5.getController("Players").getCaptionLabel().setFont(GetFont.getFont("Montserrat.ttf", 20, applet));
        userList.addItems(userListObject).getValueLabel().setFont(new ControlFont(GetFont.getFont("Montserrat.ttf", 15, applet)));
    }

    public static void clearList(PApplet applet) {
        userList.clear();
    }
}
