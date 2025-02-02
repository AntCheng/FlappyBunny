package persistence;

import model.Player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


//ATTRIBUTES: The model of this class comes from the Reader class in TellerAPP
// TellerAPP source: https://github.students.cs.ubc.ca/CPSC210/TellerApp
// PlayerReader is for reading file.
public class PlayerReader {


    // EFFECTS: returns a list of accounts parsed from file; throws
    // IOException if an exception is raised when opening / reading from file
    public static List<Player> readAccounts(File file) throws IOException {
        List<String> fileContent = readFile(file);
        return parseContent(fileContent);
    }

    // EFFECTS: returns content of file as a list of strings, each string
    // containing the player content of one row of the file
    private static List<String> readFile(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    // EFFECTS: returns a list of accounts parsed from list of strings
    // where each string contains data for one player
    private static List<Player> parseContent(List<String> fileContent) {
        List<Player> players = new ArrayList<>();

//        for (String line : fileContent) {
//        }

        for (String line : fileContent) {
            if (line.isEmpty()) {
                continue;
            }
            ArrayList<String> lineComponents = splitString(line);
            players.add(parsePlayers(lineComponents));
        }

        return players;
    }

    // EFFECTS: returns a list of strings obtained by splitting line on ,
    private static ArrayList<String> splitString(String line) {
        String[] splits = line.split(",");
        return new ArrayList<>(Arrays.asList(splits));
    }

    // REQUIRES: components has size 3
    //           element 0 represents the username
    //           element 1 represents the password
    //           elements 2 represents the name
    // EFFECTS: returns an Player constructed from components
    private static Player parsePlayers(List<String> components) {
        String username = components.get(0);
        String password = components.get(1);
        double record = Double.parseDouble(components.get(2));
        return new Player(username, password,record);
    }
}
