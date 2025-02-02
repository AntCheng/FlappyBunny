package model;

import persistence.PlayerWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// PlayerManager is to manager the players in the file.
public class PlayerManager {

    private String accountsFile;
    private List<Player> players;
    private Player currentPlayer;


    //EFFECTS: initialize the playerManger, the given players would be the players it manage.
    public PlayerManager(List<Player> players) {
        this.players = players;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }


    public List<Player> getPlayers() {
        return players;
    }

    //MODIFIES: this
    //EFFECTS: set currentPlayer to null
    public void setCurrentPlayerNull() {
        this.currentPlayer = null;
    }


    //MODIFIES: this
    //EFFECTS: create an new player account if the account username is not register.
    // the new account would be set to currentPlayer, return true if create account successful,false otherwise.
    public boolean creatAccount(String username, String password) {
        for (Player player : players) {
            if (player.getUsername().equals(username)) {
                System.out.println("this user name already exist, please use others");
                return false;
            }
        }
        currentPlayer = new Player(username,password,0.0);
        players.add(currentPlayer);
        return true;
    }

    //MODIFIES: this
    //EFFECTS: Given username and password, it would search all the players, if it match one of the players,
    // it would return true and set that as currentPlayer, return false otherwise.
    public boolean matchUserAndPassword(String username, String password) {
        for (Player player : players) {
            if (player.getUsername().equals(username) && player.getPassword().equals(password)) {
                currentPlayer = player;
                return true;
            }
        }
        return false;
    }

    public void sortPlayers() {
        Collections.sort(players, new Comparator<Player>() {
            @Override
            public int compare(Player player1, Player player2) {
                return ((int)player2.getRecord() - (int) player1.getRecord());
            }
        }
        );
    }


    //MODIFIES: this
    //EFFECTS: save the player accounts information to the file
    public void saveRecord(Double record, String accountsFile)
            throws FileNotFoundException, UnsupportedEncodingException {
        currentPlayer.setRecord(record);
        PlayerWriter playerWriter = new PlayerWriter(new File(accountsFile));
        for (Player player : players) {
            playerWriter.write(player);
        }
        playerWriter.close();
        System.out.println("record saved to file " + accountsFile);

    }


//    public void sortAccordingRecord() {
//
//    }

}
