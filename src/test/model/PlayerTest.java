package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import system.Reader;
import system.Writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    Player player0;
    Player player1;
    Player player2;
    Writer testWriter;
    private static final String TEST_FILE = "./data/testPlayers2";


    @BeforeEach
    void runBefore(){
        player0 = new Player("user","pass",0.0);
        try {
            testWriter = new Writer(new File(TEST_FILE));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        player1 = new Player("test1", "pass1",0.0);
        player2 = new Player("test2", "pass2",1.0);

    }



    @Test
    void getUsernameTest() {
        assertEquals("user", player0.getUsername());
    }

    @Test
    void getPasswordTest() {
        assertEquals("pass", player0.getPassword());
    }

    @Test
    void getRecordTest() {
        assertEquals(0.0, player0.getRecord());
    }

    @Test
    void setRecordTest() {
        player0.setRecord(30.0);
        assertEquals(30, player0.getRecord());
    }

    @Test
    void saveTest(){
        testWriter.write(player1); //this would call on player.save method.
        testWriter.write(player2);
        testWriter.close();

        try {
            List<Player> players = Reader.readAccounts(new File("./data/testPlayers2"));
            Player player1 = players.get(0);
            assertEquals("test1", player1.getUsername());
            assertEquals("pass1", player1.getPassword());
            assertEquals(0.0, player1.getRecord());

            Player player2 = players.get(1);
            assertEquals("test2", player2.getUsername());
            assertEquals("pass2", player2.getPassword());
            assertEquals(1.0, player2.getRecord());
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }
}