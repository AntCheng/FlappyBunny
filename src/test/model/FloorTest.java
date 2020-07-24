package model;

import javafx.embed.swing.JFXPanel;
import javafx.geometry.Rectangle2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FloorTest {

    public GameModelController gmc;
    public Bunny bunny;
    public ArrayList<Floor> floorList;
    private Floor floor1;
    private Floor floor2;

    private ArrayList<Cactus> cactusList;
    private JFXPanel jfxPanel = new JFXPanel();

    @BeforeEach
    void runBefore(){
        bunny = new Bunny(50,750);
        floor1 = new Floor(1000, 800,bunny,0);
        floor2 = new Floor(1000, 800,bunny,0);
        floorList = new ArrayList<>();
        floorList.add(floor1);
        floorList.add(floor2);
    }

    @Test
    void isOut() {
        floor1.setPositionX(-501);
        floor2.setPositionX(-50);

        assertEquals(true, floor1.isOut());
        assertEquals(false,floor2.isOut());

        floor2.update(5);
        assertEquals(true,floor2.isOut());
    }

    @Test
    void update() {
        floor1.setPositionX(500);
        floor1.update(1);
        assertEquals(400,floor1.getPositionX());
        assertEquals(101,floor1.getVelocityX());
    }

    @Test
    void getBoundary() {
        assertEquals( new Rectangle2D(floor1.getPositionX(),floor1.getPositionY(),floor1.getWidth(),
                floor1.getHeight()), floor1.getBoundary());
    }

    @Test
    void intersects() {
        bunny.setPositionX(400);
        bunny.setPositionY(400);
        floor1.setPositionX(300);
        floor1.setPositionY(390);
        assertEquals(true,floor1.intersects(bunny));
    }
}