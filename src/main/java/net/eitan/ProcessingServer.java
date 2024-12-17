package main.java.net.eitan;

import java.util.ArrayList;

import controlP5.*;
import main.java.net.eitan.util.GetFont;
import main.java.net.eitan.util.User;
import processing.core.*;
import processing.net.Client;
import processing.net.Server;

public class ProcessingServer extends PApplet {

    public int screenWidth = 700;
    public int screenHeight = 500;

    public ArrayList<User> users = new ArrayList<>();

    Server server;
    public static ControlP5 controlP5;
    public int inGame;
    int minPlayers = 2;

    public static void main(String[] args) {
        PApplet.main("main.java.net.eitan.ProcessingServer");
    }

    Button startButton;
    Textlabel playersInServer;
    Textlabel minPlayersLabel;

    @Override
    public void setup() {
        server = new Server(this, 3000);
        controlP5 = new ControlP5(this);

        Textlabel textlabel = controlP5.addLabel("Apples to Apples Server");
        textlabel.setSize(400, 75);
        textlabel.setPosition((screenWidth/2)-175, (screenHeight/2-37.5f)-150);
        textlabel.setFont(GetFont.getFont("Montserrat.ttf", 30, this));
        textlabel.setColor(0);

        playersInServer = controlP5.addLabel("PlayersInServer");
        playersInServer.setSize(200, 75);
        playersInServer.setPosition(250, 350);
        playersInServer.setColor(0);
        playersInServer.setFont(GetFont.getFont("Montserrat.ttf", 20, this));

        minPlayersLabel = controlP5.addLabel("MaxPlayers");
        minPlayersLabel.setSize(200, 75);
        minPlayersLabel.setPosition(250, 375);
        minPlayersLabel.setColor(0);
        minPlayersLabel.setFont(GetFont.getFont("Montserrat.ttf", 20, this));
        minPlayersLabel.setValueLabel("Min Players: " + minPlayers);

        startButton = controlP5.addButton("Start");
        startButton.setSize(200, 75);
        startButton.setPosition((screenWidth/2)-100, screenHeight/2-37.5f);
        controlP5.getController("Start").getCaptionLabel().setFont(GetFont.getFont("Montserrat.ttf", 20, this));
    }

    @Override
    public void settings() {
        size(screenWidth, screenHeight);
    }

    public void controlEvent(ControlEvent event) {
        if (event.isAssignableFrom(Button.class)) {
            if (event.getName() == "Start") {
                if (users.size() >= minPlayers) {
                    server.write("ServerStart");
                    startButton.hide();
                }
            }
        }
    }

    @Override
    public void draw() {
        background(255);
        playersInServer.setValueLabel("Players in Server: " + users.size());
        Client client = server.available();
        if (client != null) {
            String incoming = client.readString();
            if (incoming.split(": ").length > 1 && incoming.split(": ")[1] != null) {
                String username = incoming.split(": ")[1];
                users.add(new User(username, 0));
                String userList = "";
                for (User user: users) {
                    if (users.get(users.size()-1) == user) {
                        userList = userList + user.username;
                    } else {
                        userList = userList + user.username+",";
                    }
                }
                println(userList);
                server.write("Users= " + username + "= " + userList);
            } else if (incoming.split("- ").length > 1 && incoming.split("- ")[1] != null && incoming.split("- ")[1].equals("disconnect")) {
                String name = incoming.split("- ")[0];
                for (User user: users) {
                    User checkuser = user.getUser(name);
                    if (checkuser != null) {
                        users.remove(checkuser);
                        break;
                    }
                }
                String userList = "";
                for (User user2: users) {
                    if (users.get(users.size()-1).equals(user2)) {
                        userList = userList + user2.username;
                    } else {
                        userList = userList + user2.username+",";
                    }
                }
                println(userList);
                server.write("Users= " + name + "= " + userList);
            }
        }
    }

    @Override
    public void mouseClicked() {
        server.write("Hello!");
    }
}