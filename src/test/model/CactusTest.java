package model;

import javafx.embed.swing.JFXPanel;
import javafx.geometry.Rectangle2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CactusTest {

    public GameModelController gmc;
    public Bunny bunny;
    public ArrayList<Floor> floorList;
    private Floor floor1;
    private Floor floor2;
    private Cactus cactus1;
    private Cactus cactus2;
    private ArrayList<Cactus> cactusList;
    private JFXPanel jfxPanel = new JFXPanel();

    @BeforeEach
    void runBefore(){
        bunny = new Bunny(50,750);
        floor1 = new Floor(1000, 800,bunny,0);
        floor2 = new Floor(1000, 800,bunny,0);
        cactus1 = new Cactus(floor1);
        cactus2 = new Cactus(floor2);
        cactusList = new ArrayList<>();
        cactusList.add(cactus1);
        cactusList.add(cactus2);
    }

    @Test
    void getPositionYTest(){
        assertEquals(floor1.getPositionY()-50,cactus1.getPositionY());
        assertEquals(floor2.getPositionY()-50,cactus2.getPositionY());
    }

    @Test
    void setIsTouchedTest() {
        cactus2.setPositionY(750);
        cactus2.setPositionX(50);
        cactus1.setPositionY(750);
        cactus1.setPositionX(75);

        bunny.checkTouchCactus(cactusList);
        assertEquals(1,bunny.getHp());
        assertEquals(true,cactus1.getIsTounched());
        assertEquals(true,cactus1.getIsTounched());
    }

    @Test
    void setIsTouchTest() {
        cactus1.setIsTouched(true);
        assertEquals(true,cactus1.getIsTounched());
    }



    @Test
    void updateTest(){
        cactus1.setPositionX(500);
        cactus1.update(1);
        assertEquals(400,cactus1.getPositionX());
        assertEquals(101,cactus1.getVelocityX());

        cactus1.update(4);
        assertEquals(-4,cactus1.getPositionX());
        assertEquals(102,cactus1.getVelocityX());
    }

    @Test
    void getBoundaryTest() {
        assertEquals(new Rectangle2D(cactus1.getPositionX(),cactus1.getPositionY(),
                cactus1.getWidth() - 5,cactus1.getHeight() - 5), cactus1.getBoundary());
    }

    @Test
    void intersectsTest() {
        cactus1.setPositionX(500);
        cactus1.setPositionY(500);
        bunny.setPositionX(500);
        bunny.setPositionY(455);
        assertEquals(true,cactus1.intersects(bunny));
    }


}