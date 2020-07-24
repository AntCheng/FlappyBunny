package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import system.PlayerReader;

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

        playerManager = new PlayerManager(players);

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
}