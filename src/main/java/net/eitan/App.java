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
    boolean currentlyJudge = false;

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
        gameScreen.initializeJudge(this);

        gameScreen.initializeAnswerScreen(this);
        gameScreen.hideAnswerScreen();

        gameScreen.initializeTopicScreen(this);
        gameScreen.hideTopicScreen();

        gameScreen.loadingScreen(this);
        gameScreen.hideLoadingScreen(this);

        gameScreen.waitingForTopic(this);
        gameScreen.hideWaitingForTopic(this);

        gameScreen.topicLabel(this);
        gameScreen.hideTopicLabel();

        gameScreen.listOutPlayerResponses(this);
        gameScreen.hidePlayerResponses();

        gameScreen.WaitingForResponses(this);
        gameScreen.hideWaitingForResponses();

        gameScreen.initializeWinner(this);
        gameScreen.hideWinner();

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
            if (event.getName().equals("AnswerSubmit")) {
                if (gameScreen.answer.getText().length() > 0 && gameScreen.answer.getText().length() < 20) {
                    client.write("Answer, " + username + ", " + username + "> " + gameScreen.answer.getText());
                    gameScreen.answer.setText("");
                    gameScreen.hideAnswerScreen();
                    gameScreen.showPlayerResponses();
                }
            }
            if (event.getName().equals("TopicSubmit")) {
                if (gameScreen.topic.getText().length() > 0 && gameScreen.topic.getText().length() < 30) {
                    client.write("Topic, " + username + ", " + gameScreen.topic.getText());
                    gameScreen.topic.setText("");
                    gameScreen.hideTopicScreen();
                    gameScreen.showWaitingForResponses();
                }
            }
        }
    }

    @Override
    public void draw() {
        if (client.available() > 0) {
            String message = client.readString();
            if (message != null && message.split("= ").length > 2 && message.split("= ")[0].equals("Users")) {
                if (PlayerList.userList != null) {
                    PlayerList.clearList(this);
                }

                if (message.split("= ")[1].equals(username)) {
                    gameScreen.loadingScreen(this);
                    inMenu = false;
                }
                if (!inMenu && message.split("= ")[2] != null) {
                    PlayerList.drawGameScreen(this, message.split("= ")[2]);
                }
            }
            if (message.equals("ServerStart")) {
                gameScreen.hideLoadingScreen(this);
            }
            if (message.split(", ").length > 1 && message.split(", ")[0].equals("Judge")) {
                gameScreen.hideLoadingScreen(this);
                gameScreen.setJudge(message.split(", ")[1], this);
                if (message.split(", ")[1].equals(username)) {
                    gameScreen.showTopicScreen();
                    currentlyJudge = true;
                } else {
                    gameScreen.waitingForTopic(this);
                }
            }
            if (message.split(", ").length > 1 && message.split(", ")[0].equals("SubmitTopic")) {
                String topic = message.split(", ")[2];
                String user = message.split(", ")[1];
                if (user.equals(username)) {
                    gameScreen.hideTopicScreen();
                } else {
                    gameScreen.showAnswerScreen();
                    gameScreen.showTopicLabel(topic);
                    gameScreen.hideWaitingForTopic(this);
                }
            }
            if (message.split(": ").length > 2 && message.split(": ")[0].equals("Answers")) {
                delay(10);
                gameScreen.playerList.setValueLabel(message.split(": ")[2]);
            }
            if (message.split("- ").length == 2 && message.split("- ")[0].equals("Responses")) {
                delay(100);
                gameScreen.hideWaitingForResponses();
                if (currentlyJudge) {
                    gameScreen.generateButtons(this, message.split("- ")[1]);
                }
            }
            if (message.split("- ").length == 2 && message.split("- ")[0].equals("Winner")) {
                gameScreen.showWinner(message.split("- ")[1]);
                currentlyJudge = false;
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
