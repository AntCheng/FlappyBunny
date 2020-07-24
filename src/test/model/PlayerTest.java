package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    Player player1;

    @BeforeEach
    void runBefore(){
        player1 = new Player("user","pass",0.0);

    }



    @Test
    void getUsernameTest() {
        assertEquals("user",player1.getUsername());
    }

    @Test
    void getPasswordTest() {
        assertEquals("pass",player1.getPassword());
    }

    @Test
    void getRecordTest() {
        assertEquals(0.0,player1.getRecord());
    }

    @Test
    void setRecordTest() {
        player1.setRecord(30.0);
        assertEquals(30,player1.getRecord());
    }

}