package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BunnyTest {

    public GameModelController gmc;
    public Bunny bunny;
    public ArrayList<Floor> floorList;
    private Floor floor1;
    private Floor floor2;
    private Cactus cactus1;
    private Cactus cactus2;
    private ArrayList<Cactus> cactusList;


    @BeforeEach

    public void runBefore(){
//        gmc = new GameModelController(1000,800);
//        bunny = gmc.getBunny();
        try {
            bunny = new Bunny(50,750);
            floor1 = new Floor(1000, 800,bunny,0);
            floor2 = new Floor(1000, 800,bunny,0);
            cactus1 = new Cactus(floor1);
            cactus2 = new Cactus(floor2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        floorList = new ArrayList<>();
        floorList.add(floor1);
        floorList.add(floor2);

        cactusList = new ArrayList<>();
        cactusList.add(cactus1);
        cactusList.add(cactus2);
    }

    @Test
    // RuntimeException: internal graphic not initialized yet
    public void getWalkImageTest() {


        assertEquals(bunny.getWalk1() ,bunny.getWalkImage(0.1));
        //assertEquals(0.1, bunny.getWalkStateTime());
        assertEquals(true,bunny.getWalkState());

        assertEquals(bunny.getWalk1(),bunny.getWalkImage(0.1));
        //assertEquals(0.2,bunny.getWalkStateTime());
        assertEquals(true,bunny.getWalkState());

        assertEquals(bunny.getWalk2(),bunny.getWalkImage(0.1));
        //assertEquals(0.3,bunny.getWalkStateTime());
        assertEquals(false,bunny.getWalkState());

        assertEquals(bunny.getWalk2(),bunny.getWalkImage(0.11));
        //assertEquals(0.19,bunny.getWalkStateTime());
        assertEquals(false,bunny.getWalkState());

        assertEquals(bunny.getWalk1(),bunny.getWalkImage(0.1));
        //assertEquals(0.1,bunny.getWalkStateTime());
        assertEquals(true,bunny.getWalkState());
    }

    @Test
    public void burstFlyTest() {
        bunny.setVelocityY(500);
        bunny.setPositionY(750);
        bunny.burstFly();
        assertEquals(-500,bunny.getVelocityY());

        bunny.setVelocityY(500);
        bunny.setPositionY(500);
        bunny.burstFly();
        assertEquals(500,bunny.getVelocityY());

        bunny.setOnFloor(true);
        bunny.burstFly();
        assertEquals(-500,bunny.getVelocityY());
    }

    @Test
    public void isColllideGroundFloorTest(){
        bunny.setPositionY(500);
        assertEquals(false,bunny.isCollideGroundFloor());
        bunny.setPositionY(750);
        assertEquals(true,bunny.isCollideGroundFloor());
    }

    @Test
    public void checkStandFloorTest(){
        floor1.setPositionY(400);
        floor2.setPositionY(400);
        bunny.checkStandFloor(floorList);
        assertEquals(false,bunny.getOnFloor());

        floor1.setPositionY(780);
        floor1.setPositionX(50);
        floor2.setPositionY(400);
        bunny.checkStandFloor(floorList);
        assertEquals(true,bunny.getOnFloor());
        assertEquals(730,bunny.getPositionY());
        assertEquals(0,bunny.getVelocityY());
        assertEquals(0,bunny.getGravityOnBunny());

        //intersect but not on the floor
        floor1.setPositionY(740);
        floor1.setPositionX(100);
        bunny.setPositionX(100);
        bunny.setOnFloor(false);
        bunny.checkStandFloor(floorList);
        assertEquals(false,bunny.getOnFloor());
    }

    @Test
    public void standFloorTest() {
        floor1.setPositionY(300);
        bunny.standFloor(floor1);
        assertEquals(250,bunny.getPositionY());
        assertEquals(0,bunny.getVelocityY());
        assertEquals(0,bunny.getGravityOnBunny());
    }

    @Test
    public void updateTest(){
        //on ground case
        bunny.update(1);
        assertEquals(0,bunny.getVelocityY());
        assertEquals(0,bunny.getGravityOnBunny());
        assertEquals(750,bunny.getPositionY());

        //middle case
        bunny.setPositionY(100);
        bunny.update(0.5);
        assertEquals(800,bunny.getGravityOnBunny());
        assertEquals(400,bunny.getVelocityY());
        assertEquals(300,bunny.getPositionY());

        //on top case
        bunny.setPositionY(100);
        bunny.setVelocityY(-800);
        bunny.update(0.5);
        assertEquals(0,bunny.getVelocityY());
        assertEquals(0,bunny.getPositionY());
        assertEquals(800,bunny.getGravityOnBunny());

        bunny.setIsMoveLeft(true);
        bunny.setPositionY(750);
        bunny.update(1.0);
        assertEquals(50 - bunny.getVelocityX() * 1, bunny.getPositionX());

        bunny.setIsMoveLeft(false);
        bunny.setIsMoveRight(true);
        bunny.setPositionY(750);
        bunny.setPositionX(50);
        bunny.update(1.0);
        assertEquals(50 + bunny.getVelocityX() * 1, bunny.getPositionX());

    }

    @Test
    public void checkTouchedCactusTest(){

        //expect not touch
        cactus1.setPositionY(400);
        cactus2.setPositionY(400);
        bunny.checkTouchCactus(cactusList);
        assertEquals(3,bunny.getHp());

        //expect touch
        cactus2.setPositionY(750);
        cactus2.setPositionX(50);
        cactus1.setPositionY(750);
        cactus1.setPositionX(75);
        bunny.checkTouchCactus(cactusList);
        assertEquals(1,bunny.getHp());
        assertEquals(true,cactus1.getIsTounched());
        assertEquals(true,cactus1.getIsTounched());

        //
        bunny.checkTouchCactus(cactusList);
        assertEquals(1,bunny.getHp());
    }

    @Test
    public void getBoundaryTest(){
        assertEquals( new Rectangle2D.Double(bunny.getPositionX(), bunny.getPositionY(), bunny.getWidth(),
                bunny.getHeight()), bunny.getBoundary());
    }

    @Test
    public void intersectTest(){
        cactus1.setPositionY(750);
        cactus1.setPositionX(75);
        assertEquals(true, bunny.intersects(cactus1));

        floor1.setPositionY(400);
        assertEquals(false, bunny.intersects(floor1));
    }

    @Test
    public void setHPTest() {
        bunny.setHp(1);
        assertEquals(1,bunny.getHp());
    }

    @Test
    public void setMoveRightandLeftTest() {
        bunny.setIsMoveLeft(true);
        assertEquals(bunny.getIsMoveLeft(), true);
        bunny.setIsMoveRight(true);
        assertEquals(bunny.getIsMoveRight(),true);
    }

    @Test
    public void getHurtTest() {
        bunny.getHurt();
        assertEquals(2,bunny.getHp());
    }

    @Test
    public void changedTest() {
        bunny.changed();
        assertEquals(true,bunny.hasChanged());
    }

    @Test
    void getImageTest() {

        assertEquals(bunny.getWalk1(),bunny.getImage(1.0));

        bunny.getHurt();
        assertEquals(true,bunny.getIsHurting());
        assertEquals(bunny.getHurtImage(),bunny.getImage(1.0));
        bunny.getImage(5.0);
        bunny.getImage(1.0);
        assertEquals(false, bunny.getIsHurting());



    }

//    @Test
//    void testIOException(){
//        bunny.se
//    }


}