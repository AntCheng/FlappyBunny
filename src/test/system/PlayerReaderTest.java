package system;

import model.Player;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class PlayerReaderTest {

//    @Test
//    void testParseAccountsFile1() {
//        try {
//            List<Player> players = PlayerReader.readAccounts(new File("./data/testPlayers2"));
//            Player player1 = players.get(0);
//            assertEquals("test1", player1.getUsername());
//            assertEquals("pass1", player1.getPassword());
//            assertEquals(0.0, player1.getRecord());
//
//            Player player2 = players.get(1);
//            assertEquals("test2", player2.getUsername());
//            assertEquals("pass2", player2.getPassword());
//            assertEquals(1.0, player2.getRecord());
//
//        } catch (IOException e) {
//            fail("IOException should not have been thrown");
//        }
//    }
//
//
//
//    @Test
//    void testIOException() {
//        try {
//            PlayerReader.readAccounts(new File("./path/does/not/exist/testAccount.txt"));
//        } catch (IOException e) {
//            // expected
//        }
//    }
}
