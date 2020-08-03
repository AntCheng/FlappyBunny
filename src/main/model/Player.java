package model;

import java.io.PrintWriter;

//class player is the player account of the game, it record the user's username, password and best records.
public class Player {
    String username;
    String password;
    Double record;

    public Player(String username, String password, Double record) {
        this.username = username;
        this.password = password;
        this.record = record;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public double getRecord() {
        return record;
    }

    public void setRecord(Double record) {
        this.record = record;
    }

    //EFFECTS: return the string of this account
    public String toString() {
        return "Username: " + username + "  Record: " + record.toString();
    }

    //EFFECTS: Save this player information to the file open by printWriter
    //         on the format username,password,record
    public void save(PrintWriter printWriter) {
        printWriter.print(username);
        printWriter.print(",");
        printWriter.print(password);
        printWriter.print(",");
        printWriter.print(record);
        printWriter.println();
    }
}
