package main.java.net.eitan.component;

import java.util.ArrayList;

import controlP5.Button;
import controlP5.CallbackEvent;
import controlP5.CallbackListener;
import controlP5.Textfield;
import controlP5.Textlabel;
import main.java.net.eitan.App;
import main.java.net.eitan.util.GetFont;
import processing.core.PApplet;
import processing.data.JSONArray;

public class GameScreen {
    public Textlabel loadingScreen;
    public Textlabel waitingForTopic;

    public void loadingScreen(PApplet applet) {
        loadingScreen = App.controlP5.addLabel("Waiting for server to load.. :D");
        loadingScreen.setPosition((App.windowX/2)-(loadingScreen.getWidth()/2)-50, (App.windowY/2)-(loadingScreen.getHeight()/2));
        loadingScreen.setColor(0);
        loadingScreen.getCaptionLabel().align(App.controlP5.CENTER, App.controlP5.CENTER);
        loadingScreen.setFont(GetFont.getFont("Montserrat.ttf", 30, applet));
    }

    public void waitingForTopic(PApplet applet) {
        waitingForTopic = App.controlP5.addLabel("Waiting For Judge to Submit Topic..");
        waitingForTopic.setPosition((App.windowX/2)-(waitingForTopic.getWidth()/2)-150, (App.windowY/2)-(waitingForTopic.getHeight()/2));
        waitingForTopic.setColor(0);
        waitingForTopic.setFont(GetFont.getFont("Montserrat.ttf", 30, applet));
    }


    public void hideLoadingScreen(PApplet pApplet) {
        loadingScreen.setValueLabel("");
    }

    public void hideWaitingForTopic(PApplet pApplet) {
        waitingForTopic.setValueLabel("");
    }

    public void showLoadingScreen(PApplet pApplet) {
        loadingScreen.setText("Waiting for server to load.. :D");
    }

    public void showWaitingForTopic(PApplet pApplet) {
        loadingScreen.setText("Waiting for Judge to Submit Topic..");
    }

    public Textlabel judge;

    public void initializeJudge(PApplet pApplet) {
        judge = App.controlP5.addLabel("Judge");
        judge.setPosition(400, 100);
        judge.setColor(0);
        judge.getValueLabel().align(App.controlP5.CENTER, App.controlP5.CENTER);
        judge.setFont(GetFont.getFont("Montserrat.ttf", 30, pApplet));
        judge.setValueLabel("");
    }

    public void hideJudge() {
        judge.hide();
    }

    public void setJudge(String newjudge, PApplet pApplet) {
        judge.show();
        judge.setValueLabel(newjudge + " is the judge!");
    }

    public Textlabel enterAnswer;
    public Textfield answer;
    public Button answerSubmit;

    public void initializeAnswerScreen(PApplet applet) {
        enterAnswer = App.controlP5.addLabel("EnterAnswer");
        enterAnswer.setPosition(350, 300);
        enterAnswer.setColor(0);
        enterAnswer.setFont(GetFont.getFont("Montserrat.ttf", 30, applet));
        enterAnswer.setValueLabel("Enter Your Answer:");

        answer = App.controlP5.addTextfield("Answer");
        answer.setLabel("");
        answer.setPosition(400, 350);
        answer.setSize(200, 50);
        answer.setFont(GetFont.font(18, applet));

        answerSubmit = App.controlP5.addButton("AnswerSubmit");
        answerSubmit.setLabel("Submit");
        answerSubmit.setPosition(400, 410);
        answerSubmit.setSize(200, 50);
        answerSubmit.getCaptionLabel().setFont(GetFont.getFont("Montserrat.ttf", 20, applet));
    }

    public void hideAnswerScreen() {
        enterAnswer.hide();
        answer.hide();
        answerSubmit.hide();
    }

    public void showAnswerScreen() {
        enterAnswer.show();
        answer.show();
        answerSubmit.show();
    }

    public Textlabel enterTopic;
    public Textfield topic;
    public Button topicSubmit;

    public void initializeTopicScreen(PApplet applet) {
        enterTopic = App.controlP5.addLabel("TopicAnswer");
        enterTopic.setPosition(360, 300);
        enterTopic.setColor(0);
        enterTopic.setFont(GetFont.getFont("Montserrat.ttf", 30, applet));
        enterTopic.setValueLabel("Enter Your Topic:");

        topic = App.controlP5.addTextfield("Topic");
        topic.setLabel("");
        topic.setPosition(400, 350);
        topic.setSize(200, 50);
        topic.setFont(GetFont.font(18, applet));

        topicSubmit = App.controlP5.addButton("TopicSubmit");
        topicSubmit.setLabel("Submit");
        topicSubmit.setPosition(400, 410);
        topicSubmit.setSize(200, 50);
        topicSubmit.getCaptionLabel().setFont(GetFont.getFont("Montserrat.ttf", 20, applet));
    }

    public void hideTopicScreen() {
        enterTopic.hide();
        topic.hide();
        topicSubmit.hide();
    }

    public void showTopicScreen() {
        enterTopic.show();
        topic.show();
        topicSubmit.show();
    }

    public Textlabel topicLabel;

    public void topicLabel(PApplet pApplet) {
        topicLabel = App.controlP5.addLabel("TopicLabel");
        topicLabel.setPosition(400, 200);
        topicLabel.setColor(pApplet.color(0, 141, 0));
        topicLabel.getValueLabel().align(App.controlP5.CENTER, App.controlP5.CENTER);
        topicLabel.setFont(GetFont.getFont("Montserrat.ttf", 30, pApplet));
    }

    public void hideTopicLabel() {
        topicLabel.setVisible(false);
    }

    public void showTopicLabel(String topic) {
        topicLabel.setVisible(true);
        topicLabel.setValueLabel("The Topic is " + topic + "!");
    }

    public Textlabel playerList;
    public void listOutPlayerResponses(PApplet pApplet) {
        
        playerList = App.controlP5.addLabel("PlayerResponses");
        playerList.setPosition(415, 250);
        playerList.setSize(500, 500);
        playerList.setValueLabel("");
        playerList.getValueLabel().align(App.controlP5.CENTER, App.controlP5.CENTER);
        playerList.align(App.controlP5.CENTER, App.controlP5.CENTER, App.controlP5.CENTER, App.controlP5.CENTER);
        playerList.setColor(0);
        playerList.setFont(GetFont.getFont("Montserrat.ttf", 30, pApplet));
    }

    public void hidePlayerResponses() {
        playerList.hide();
    }

    public void showPlayerResponses() {
        playerList.show();
    }

    public void showPlayerResponses(String responses, PApplet pApplet) {
        if (!topicLabel.isVisible()) {
            playerList.show();
            String[] splitter = responses.split(",");
            String text = "";
            for (String item: splitter) {
                text = text + item + "\n";
            }
            pApplet.println("Set The Player Responses Text");
            playerList.setValueLabel(text);
        }
    }

    public Textlabel WaitingForPlayerResponses;
    public void WaitingForResponses(PApplet pApplet) {
        
        WaitingForPlayerResponses = App.controlP5.addLabel("WaitingResponses");
        WaitingForPlayerResponses.setPosition(400, 300);
        WaitingForPlayerResponses.getValueLabel().align(App.controlP5.CENTER, App.controlP5.CENTER);
        WaitingForPlayerResponses.setSize(500, 500);
        WaitingForPlayerResponses.setValueLabel("Waiting for all responses..");
        WaitingForPlayerResponses.setColor(0);
        WaitingForPlayerResponses.setFont(GetFont.getFont("Montserrat.ttf", 30, pApplet));
    }

    public void hideWaitingForResponses() {
        WaitingForPlayerResponses.hide();
    }

    public void showWaitingForResponses() {
        WaitingForPlayerResponses.show();
    }

    public ArrayList<Button> responses = new ArrayList<>();
    public void generateButtons(PApplet pApplet, String answers) {
        int index = 0;
        for (String answer: answers.split("\n")) {
            Button responser = App.controlP5.addButton("Answer" + index);
            responser.setPosition(300, 215+(55*index));
            responser.setSize(400, 50);
            responser.getCaptionLabel().setFont(GetFont.getFont("Montserrat.ttf", 20, pApplet));
            responser.setLabel(answer);
            responser.getCaptionLabel().align(App.controlP5.CENTER, App.controlP5.CENTER);
            responses.add(responser);
            index++;
            responser.onClick(new CallbackListener() {

                @Override
                public void controlEvent(CallbackEvent arg0) {
                    // TODO Auto-generated method stub
                    deleteAllButtons();
                    App.client.write("Winner- " + answer);
                }
                
            });
        }
        hideWaitingForResponses();
    }

    public void deleteAllButtons() {
        for (Button button: responses) {
            button.hide();
        }
        responses.removeAll(responses);
    }

    public Textlabel winner;
    public void initializeWinner(PApplet pApplet) {
        
        winner = App.controlP5.addLabel("GameWinner");
        winner.setPosition(400, 300);
        winner.setValueLabel("");
        winner.getValueLabel().align(App.controlP5.CENTER, App.controlP5.CENTER);
        winner.setColor(0);
        winner.setFont(GetFont.getFont("Montserrat.ttf", 30, pApplet));
    }

    public void hideWinner() {
        winner.hide();
    }

    public void showWinner(String theWinner) {
        hideJudge();
        winner.show();
        winner.setValueLabel(theWinner.split("> ")[1] + " is the winner!");
    }
}
