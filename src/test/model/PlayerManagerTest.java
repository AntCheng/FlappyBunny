package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.PlayerReader;

import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerManagerTest {

    PlayerManager playerManager;
    PlayerReader playerReader;
    List<Player> players;
    List<Player> playersAfterSave;
    File file;
    PrintWriter printWriter;

    @BeforeEach
    void runBefore(){
        file = new File("./data/testPlayers");


        try {
            printWriter = new PrintWriter(file, "UTF-8");
        } catch (FileNotFoundException e) {
            fail();
        } catch (UnsupportedEncodingException e) {
            fail();
        }

        printWriter.print("");
        printWriter.close();

        try {
            players = playerReader.readAccounts(file);
        } catch (IOException e) {
            fail();
        }

        playerManager = new PlayerManager(players);

    }

    @Test
    void creatAccountTest() {
        assertEquals(true,playerManager.creatAccount("test1","pass1"));
        assertEquals(true,playerManager.creatAccount("test2","pass1"));
        assertEquals(2,players.size());
        assertEquals(players.get(1),playerManager.getCurrentPlayer());
        assertEquals(false,playerManager.creatAccount("test1","pass2"));
    }

    @Test
    void matchUserAndPasswordTest() {
        assertEquals(true,playerManager.creatAccount("test1","pass1"));
        assertEquals(true,playerManager.matchUserAndPassword("test1","pass1"));
        assertEquals(false,playerManager.matchUserAndPassword("test2","pass1"));
        assertEquals(false,playerManager.matchUserAndPassword("test1","pass2"));

    }

    @Test
    void saveRecordTest() {
        playerManager.creatAccount("test1","pass1");
        try {
            playerManager.saveRecord(30.0,"./data/testPlayers" );
        } catch (FileNotFoundException e) {
            fail();
        } catch (UnsupportedEncodingException e) {
            fail();
        }

        try {
            playersAfterSave = playerReader.readAccounts(new File("./data/testPlayers"));
        } catch (IOException e) {
            fail();
        }
        assertEquals("test1",playersAfterSave.get(0).getUsername());
        assertEquals("pass1",playersAfterSave.get(0).getPassword());
        assertEquals(30.0,playersAfterSave.get(0).getRecord());
    }

    @Test
    void sortPlayersTest(){
        Player player1 = new Player("test1","user1",2.0);
        Player player2 = new Player("test2","pass2",1.0);
        Player player3 = new Player("test3","pass3",3.0);

        players.add(player1);
        players.add(player2);
        players.add(player3);

        playerManager.sortPlayers();
        assertEquals(player3,players.get(0));
        assertEquals(player1,players.get(1));
        assertEquals(player2,players.get(2));
    }

    @Test
    void getPlayersTest() {
        Player player1 = new Player("test1","user1",2.0);
        Player player2 = new Player("test2","pass2",1.0);
        Player player3 = new Player("test3","pass3",3.0);

        players.add(player1);
        players.add(player2);
        players.add(player3);

        assertEquals(players, playerManager.getPlayers());

    }

    @Test
    void setCurrentPlayerNullTest() {

        playerManager.creatAccount("test1","pass1");
        playerManager.setCurrentPlayerNull();
        assertEquals(null, playerManager.getCurrentPlayer());

    }
}