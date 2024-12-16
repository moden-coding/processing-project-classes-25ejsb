package main.java.net.eitan.component;

import controlP5.*;
import controlP5.Button;
import main.java.net.eitan.App;
import main.java.net.eitan.util.*;
import processing.core.*;

public class Menu {
    public static Textlabel label;
    public static Textlabel username;
    public static Textfield textField;
    public static Button submitButton;

    public static void drawMenu(PApplet applet) {
        PFont font = GetFont.getFont("Montserrat.ttf", 40, applet);
        label = App.controlP5.addLabel("Knockoff Apples to Apples", 240, 100);
        label.setFont(font);
        label.setColor(applet.color(0, 141, 0));

        username = App.controlP5.addLabel("Enter your username: (Max length is 10)", 330, 280);
        username.setFont(GetFont.font(18, applet));
        username.setColor(0);

        textField = App.controlP5.addTextfield("Username");
        textField.setLabel("");
        textField.setPosition(400, 350);
        textField.setSize(200, 50);
        textField.setFont(GetFont.font(18, applet));
        
        submitButton = App.controlP5.addButton("UserSubmit");
        submitButton.setLabel("Username");
        submitButton.setPosition(400, 410);
        submitButton.setSize(200, 50);

        App.controlP5.getController("UserSubmit").getCaptionLabel().setFont(new ControlFont(GetFont.getFont("Montserrat.ttf", 25, applet)));
    }

    public static void hideMenu(PApplet applet) {
        textField.hide();
        submitButton.hide();
        username.hide();
        label.hide();
    }
}
