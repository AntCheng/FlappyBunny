package persistence;

import model.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

//ATTRIBUTES: the model of this class comes form the Writer class in TellerAPP
// TellerAPP source: https://github.students.cs.ubc.ca/CPSC210/TellerApp
public class PlayerWriter {
    private PrintWriter printWriter;

    // EFFECTS: constructs a writer that will write data to file
    public PlayerWriter(File file) throws FileNotFoundException, UnsupportedEncodingException {
        printWriter = new PrintWriter(file, "UTF-8");
    }

    // MODIFIES: this
    // EFFECTS: writes saveable to file
    public void write(Player player) {
        player.save(printWriter);
    }

    // MODIFIES: this
    // EFFECTS: close print writer
    // NOTE: you MUST call this method when you are done writing data!
    public void close() {
        printWriter.close();
    }
}
