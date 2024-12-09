package main.java.net.eitan;

import processing.core.*;
import processing.net.Client;
import processing.net.Server;
import main.java.net.eitan.components.*;
import main.java.net.eitan.components.types.Button;

public class ProcessingServer extends PApplet {

    public int screenWidth = 700;
    public int screenHeight = 500;

    Server server;
    StartButton button = new StartButton(screenWidth/2, screenHeight/2, 200, 75, this, new int[]{255, 255, 255}, "START GAME", new int[]{255, 0, 0}, 30, 15);

    private boolean inMenu = true;

    public static void main(String[] args) {
        PApplet.main("main.java.net.eitan.ProcessingServer");
    }

    @Override
    public void setup() {
        fill(0);
        server = new Server(this, 3000);
    }

    @Override
    public void settings() {
        size(screenWidth, screenHeight);
    }

    @Override
    public void draw() {
        background(0);
        if (inMenu) {
            button.draw();
        }
        Client client = server.available();
        if (client != null) {
            String incoming = client.readString();
            println(incoming);
        }
        
    }

    @Override
    public void mouseClicked() {
        server.write("Hello!");
    }
}