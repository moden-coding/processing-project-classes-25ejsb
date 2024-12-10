package main.java.net.eitan;

import controlP5.Button;
import controlP5.CallbackEvent;
import controlP5.ControlP5;
import controlP5.Textfield;
import processing.core.*;
import processing.net.Client;

public class App extends PApplet {
    Client client;
    public static boolean inMenu = true;
    private final int windowX = 1000;
    private final int windowY = 700;
    ControlP5 cp5;

    public static void main(String[] args) {
        PApplet.main("main.java.net.eitan.App");
    }
    
    @Override
    public void setup() {
        PFont pFont = createFont("arial", 40);
        if (inMenu) {
            cp5 = new ControlP5(this);
            Textfield username = cp5.addTextfield("Username")
            .setPosition(500, 500)
                    .setSize(300, 70)
                    .setFont(pFont)
                    .setColor(0)
                    .setColorBackground(color(255, 255, 255))
                    .setColorCursor(color(0,0,0))
                    .setLabel("");
        }
        client = new Client(this, "127.0.0.1", 3000);
    }

    @Override
    public void settings() {
        size(windowX, windowY);
    }

    @Override
    public void draw() {
        if (client.available() > 0) {
            String incoming = client.readString();
            fill(255);
            text(incoming, 100, 100);
            println(incoming);
        }
    }

    @Override
    public void mouseClicked() {
        client.write("Hello!");
    }
}
