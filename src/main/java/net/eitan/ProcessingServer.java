package main.java.net.eitan;

import java.util.ArrayList;

import controlP5.*;
import main.java.net.eitan.util.User;
import processing.core.*;
import processing.net.Client;
import processing.net.Server;

public class ProcessingServer extends PApplet {

    public int screenWidth = 700;
    public int screenHeight = 500;

    public static ArrayList<User> users = new ArrayList<>();

    Server server;

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
                server.write(userList);
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
                server.write(userList);
            }
        }
    }

    @Override
    public void mouseClicked() {
        server.write("Hello!");
    }
}