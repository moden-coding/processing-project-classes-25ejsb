package main.java.net.eitan.components.types;

import processing.core.*;

public class Button {

    private float x;
    private float y;
    private float width;
    private float height;
    private PApplet pApplet;
    private int[] rgb;
    private String text;
    private int[] textRgb;
    private float fSize;
    private float borderRadius;

    public Button(float x, float y, float width, float height, PApplet pApplet, int[] rgb, String text, int[] textRgb, float fSize, float borderRadius) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.pApplet = pApplet;
        this.rgb = rgb;
        this.text = text;
        this.textRgb = textRgb;
        this.fSize = fSize;
        this.borderRadius = borderRadius;
    }

    protected void onMouseClick() {
        System.out.println(this + ": Button clicked!");
    }

    public void draw() {
        pApplet.fill(rgb[0], rgb[1], rgb[2]);
        pApplet.rect(x, y, width, height, borderRadius);

        pApplet.fill(textRgb[0], textRgb[1], textRgb[2]);
        pApplet.textAlign(pApplet.CENTER, pApplet.CENTER);
        pApplet.textSize(fSize);
        pApplet.text(text, x + width/2, y + height/2 - pApplet.CENTER);

        if (pApplet.mousePressed) {
            if ((float) pApplet.mouseX >= x && pApplet.mouseX <= x+width && pApplet.mouseY >= y && pApplet.mouseY <= y+height) {
                onMouseClick();
            }
        }
    }
}
