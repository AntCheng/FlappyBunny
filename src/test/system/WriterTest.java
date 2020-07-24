package system;

import model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class WriterTest {
    private static final String TEST_FILE = "./data/testPlayers2";
    private Writer testWriter;
    private Player player1;
    private Player player2;

    @BeforeEach
    void runBefore() throws FileNotFoundException, UnsupportedEncodingException {
        testWriter = new Writer(new File(TEST_FILE));
        player1 = new Player("test1", "pass1",0.0);
        player2 = new Player("test2", "pass2",1.0);
    }

    @Test
    void testWriteAccounts() {
        // save chequing and savings accounts to file
        testWriter.write(player1);
        testWriter.write(player2);
        testWriter.close();

        // now read them back in and verify that the accounts have the expected values
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
