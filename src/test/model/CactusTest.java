package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.geom.Rectangle2D;
import java.io.IOException;
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


    @BeforeEach
    void runBefore(){
        try {
            bunny = new Bunny(50,750);
            floor1 = new Floor(1000, 800,bunny,0);
            floor2 = new Floor(1000, 800,bunny,0);
            cactus1 = new Cactus(floor1);
            cactus2 = new Cactus(floor2);
        } catch (IOException e) {
            e.printStackTrace();
        }

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
    void getCactusImage(){
        assertEquals(cactus1.getImage().getWidth(null),cactus2.getImage().getWidth(null));
        assertEquals(cactus2.getImage().getHeight(null),cactus2.getImage().getHeight(null));
    }

    @Test
    void checkIsTouchedTest() {
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
        assertEquals(100,cactus1.getVelocityX());

        cactus1.update(4);
        assertEquals(0,cactus1.getPositionX());
        assertEquals(100,cactus1.getVelocityX());
    }

    @Test
    void getBoundaryTest() {
        assertEquals(new Rectangle2D.Double(cactus1.getPositionX(), cactus1.getPositionY(),
                cactus1.getWidth() - 5, cactus1.getHeight() - 5), cactus1.getBoundary());
    }

    @Test
    void intersectsTest() {
        cactus1.setPositionX(500);
        cactus1.setPositionY(500);
        bunny.setPositionX(500);
        bunny.setPositionY(455);
        assertEquals(true,cactus1.intersects(bunny));
    }

    @Test
    void isOutTest(){
        cactus1.setPositionX(100);
        assertEquals(false,cactus1.isOut());
        cactus1.setPositionX(-70);
        assertEquals(true,cactus1.isOut());
    }

}