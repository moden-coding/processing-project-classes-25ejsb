package main.java.net.eitan;

import controlP5.*;
import processing.core.*;
import processing.net.Client;
import java.awt.Font;

import main.java.net.eitan.component.PlayerList;
import main.java.net.eitan.component.GameScreen;
import main.java.net.eitan.component.Menu;
import main.java.net.eitan.util.*;

public class App extends PApplet {
    public static Client client;
    boolean inMenu = true;
    public static final int windowX = 1000;
    public static final int windowY = 700;
    private String username;

    public static ControlP5 controlP5;

    GameScreen gameScreen = new GameScreen();

    public static void main(String[] args) {
        PApplet.main("main.java.net.eitan.App");
    }
    
    @Override
    public void setup() {
        controlP5 = new ControlP5(this);
        if (inMenu) {
            Menu.drawMenu(this);
        }
        client = new Client(this, "127.0.0.1", 3000);
    }

    @Override
    public void settings() {
        size(windowX, windowY);
    }

    public void controlEvent(ControlEvent event) {
        if (event.isAssignableFrom(Button.class)) {
            if (event.getName() == "UserSubmit") {
                if (Menu.textField.getText().length() < 10 && Menu.textField.getText().length() > 0) {
                    client.write("Username: " + Menu.textField.getText());
                    username = Menu.textField.getText();
                    inMenu = false;
                    Menu.hideMenu(this);
                }
            }
        }
    }

    @Override
    public void draw() {
        if (client.available() > 0) {
            String message = client.readString();
            if (message != null && message.split("= ").length > 0 && message.split("= ")[0].equals("Users")) {
                if (PlayerList.userList != null) {
                    PlayerList.clearList(this);
                }

                if (message.split("= ")[1].equals(username)) {
                    gameScreen.loadingScreen(this);
                    inMenu = false;
                }
                if (!inMenu) {
                    PlayerList.drawGameScreen(this, message.split("= ")[2]);
                }
            }
            if (message.equals("ServerStart")) {
                gameScreen.hide(this);
            }
        }
        background(255);
    }

    @Override
    public void exit() {
        println(username + "- disconnect");
        client.write(username + "- disconnect");
        System.exit(0);
    }
}
