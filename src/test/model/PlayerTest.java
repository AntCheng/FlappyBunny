package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import system.PlayerReader;
import system.PlayerWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

//    Player player0;
//    Player player1;
//    Player player2;
//    PlayerWriter testPlayerWriter;
//    private static final String TEST_FILE = "./data/testPlayers";
//
//
//    @BeforeEach
//    void runBefore(){
//        player0 = new Player("user","pass",0.0);
//        try {
//            testPlayerWriter = new PlayerWriter(new File(TEST_FILE));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        player1 = new Player("test1", "pass1",0.0);
//        player2 = new Player("test2", "pass2",1.0);
//
//    }
//
//
//
//    @Test
//    void getUsernameTest() {
//        assertEquals("user", player0.getUsername());
//    }
//
//    @Test
//    void getPasswordTest() {
//        assertEquals("pass", player0.getPassword());
//    }
//
//    @Test
//    void getRecordTest() {
//        assertEquals(0.0, player0.getRecord());
//    }
//
//    @Test
//    void setRecordTest() {
//        player0.setRecord(30.0);
//        assertEquals(30, player0.getRecord());
//    }
//
//    @Test
//    void saveTest(){
//        // save chequing and savings accounts to file
//        testPlayerWriter.write(player1);
//        testPlayerWriter.write(player2);
//        testPlayerWriter.close();
//
//        // now read them back in and verify that the accounts have the expected values
//        try {
//            List<Player> players = PlayerReader.readAccounts(new File("./data/testPlayers"));
//            Player player1 = players.get(0);
//            assertEquals("test1", player1.getUsername());
//            assertEquals("pass1", player1.getPassword());
//            assertEquals(0.0, player1.getRecord());
//
//            Player player2 = players.get(1);
//            assertEquals("test2", player2.getUsername());
//            assertEquals("pass2", player2.getPassword());
//            assertEquals(1.0, player2.getRecord());
//        } catch (IOException e) {
//            fail("IOException should not have been thrown");
//        }
//    }

}