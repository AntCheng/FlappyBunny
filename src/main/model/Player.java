package model;

import java.io.PrintWriter;

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

    public Double getRecord() {
        return record;
    }

    public void setRecord(Double record) {
        this.record = record;
    }

    //EFFECTS: Save this player information to the file open by printWriter
    public void save(PrintWriter printWriter) {
        printWriter.print(username);
        printWriter.print(",");
        printWriter.print(password);
        printWriter.print(",");
        printWriter.print(record);
        printWriter.println();
    }
}
