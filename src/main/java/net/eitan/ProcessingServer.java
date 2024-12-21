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
    boolean playingGame = false;

    public ArrayList<User> users = new ArrayList<>();
    public ArrayList<String> answers = new ArrayList<>();

    Server server;
    public static ControlP5 controlP5;
    public int inGame;
    int minPlayers = 2;

    String topic;

    public static void main(String[] args) {
        PApplet.main("main.java.net.eitan.ProcessingServer");
    }

    Button startButton;
    Textlabel playersInServer;
    Textlabel minPlayersLabel;

    User currentJudge;

    @Override
    public void setup() {
        server = new Server(this,3000,"192.168.1.204");
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
        startButton.setValueLabel("Start Round");
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
            if (event.getName().equals("Start")) {
                if (users.size() >= minPlayers) {
                    server.write("ServerStart");
                    chooseJudge();
                    startButton.hide();
                    playingGame = true;
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
            if (incoming.split(": ").length == 2 && incoming.split(": ")[1] != null) {
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
                server.write("Users= " + name + "= " + userList);
            }
            if (incoming != null && incoming.split(", ").length > 2 && incoming.split(", ")[0].equals("Answer")) {
                delay(10);
                answers.add(incoming.split(", ")[2]);
                String answerList = "";
                String username = incoming.split(", ")[1];
                for (String answer: answers) {
                    if (answers.get(answers.size()-1).equals(answer)) {
                        answerList = answerList + answer;
                    } else {
                        answerList = answerList + answer + "\n";
                    }
                }
                server.write("Answers: " + username + ": " + answerList);
                delay(100);
                if (answers.size() == users.size()-1) {
                    server.write("Responses- " + answerList);
                }
            }
            if (incoming != null && incoming.split(", ").length > 2 && incoming.split(", ")[0].equals("Topic")) {
                topic = incoming.split(", ")[2];
                String username = incoming.split(", ")[1];
                server.write("SubmitTopic, " + username + ", " + topic);
            }
            if (incoming != null && incoming.split("- ").length > 1 && incoming.split("- ")[0].equals("Winner")) {
                server.write("Winner- " + incoming.split("- ")[1]);
                currentJudge = null;
                topic = null;
                startButton.show();
                answers.removeAll(answers);
            }
        }
    }

    private void chooseJudge() {
        currentJudge = users.get((int) Math.floor(Math.random() * users.size()));
        delay(20+users.size());
        server.write("Judge, " + currentJudge.username);
    }
}