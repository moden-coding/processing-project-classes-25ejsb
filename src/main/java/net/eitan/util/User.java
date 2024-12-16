package main.java.net.eitan.util;

public class User {

    public String username;
    public int points;

    public User(String username, int points) {
        this.username = username;
        this.points = points;
    }

    public User getUser(String user) {
        if (user.equals(username)) {
            return this;
        } else {
            return null;
        }
    }
}
