package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FloorTest {

    public GameModelController gmc;
    public Bunny bunny;
    public ArrayList<Floor> floorList;
    private Floor floor1;
    private Floor floor2;

    private ArrayList<Cactus> cactusList;
    //private JFXPanel jfxPanel = new JFXPanel();

    @BeforeEach
    void runBefore(){
        try {
            bunny = new Bunny(50,750);
            floor1 = new Floor(1000, 800,bunny,0);
            floor2 = new Floor(1000, 800,bunny,0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        floorList = new ArrayList<>();
        floorList.add(floor1);
        floorList.add(floor2);
    }

    @Test
    void getFloorImage(){
        assertEquals(floor1.getImage().getHeight(null),floor2.getImage().getHeight(null));
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
        assertEquals(300,floor1.getPositionX());
        assertEquals(200,floor1.getVelocityX());
    }

    @Test
    void getBoundary() {
        assertEquals( new Rectangle2D.Double(floor1.getPositionX(), floor1.getPositionY(), floor1.getWidth(),
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

    @Test
    void adjustTest() {
        floor1.setPositionY(800);
        assertEquals(800,floor1.adjust(floor1.getPositionY()));
        floor1.setPositionY(1100);
        floor1.setPositionY(floor1.adjust(floor1.getPositionY()));
        assertEquals(940,floor1.getPositionY());
    }
}