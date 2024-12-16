package main.java.net.eitan.util;

import processing.core.PApplet;
import processing.core.PFont;

public class GetFont {
    public static PFont getFont(String ttf_name, float size, PApplet applet) {
        PFont awtfont = null;
        try {
            awtfont = applet.createFont("Montserrat.ttf", size);
        }
        catch(Exception e) {
            applet.println("Failed to load font " + ttf_name);
        }
        return awtfont;
    }

    public static PFont font(int size, PApplet applet) {
        return getFont("Montserrat.ttf", size, applet);
    }
}
