package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameModelControllerTest {

    public GameModelController gmc;
    public Bunny bunny;
    public ArrayList<Floor> floorList;
    private Floor floor1;
    private Floor floor2;
    private Cactus cactus1;
    private Cactus cactus2;
    private ArrayList<Cactus> cactusList;
    //private JFXPanel jfxPanel = new JFXPanel();

    @BeforeEach
    void runBefore(){
        try {
            gmc = new GameModelController(1000,800);
            bunny = gmc.getBunny();
            floor1 = new Floor(1000, 800,bunny,0);
            floor2 = new Floor(1000, 800,bunny,0);
            cactus1 = new Cactus(floor1);
            cactus2 = new Cactus(floor2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        floorList = gmc.getFloorList();
        floorList.add(floor1);
        floorList.add(floor2);
        cactusList = gmc.getCactusList();
        cactusList.add(cactus1);
        cactusList.add(cactus2);
    }

    @Test
    void setupTest() {
        gmc.setup();
        assertEquals(0,gmc.getFloorList().size());
        assertEquals(0,gmc.getFloorList().size());
        assertEquals(false,gmc.isGameOver);
        assertEquals(0,gmc.getFloorFrequenceTime());
        assertEquals(0,gmc.getPastTime());
    }


    @Test
    void updateTest() {
        cactus1.setPositionX(300);
        cactus2.setPositionX(400);
        floor1.setPositionX(300);
        floor2.setPositionX(400);

        gmc.update(1.0);

        assertEquals(1,gmc.getPastTime());
        assertEquals(1,gmc.getFloorFrequenceTime());
        assertEquals(750, bunny.getPositionY());

    }

    @Test
    void translateKeyEventPressedTest() {
        gmc.translateKeyEventPressed("UP");
        assertEquals("UP",gmc.getCodeList().get(0));
        assertEquals(-500,bunny.getVelocityY());

        gmc.translateKeyEventPressed("UP");
        assertEquals("UP",gmc.getCodeList().get(0));
        assertEquals(-500,bunny.getVelocityY());

        gmc.translateKeyEventPressed("lol");
        assertEquals(1,gmc.getCodeList().size());
        assertEquals(-500,bunny.getVelocityY());
    }

    @Test
    void translateKeyEventReleasedTest() {
        //contain UP, releaseUP
        gmc.translateKeyEventPressed("UP");
        gmc.translateKeyEventReleased("UP");
        assertEquals(0,gmc.getCodeList().size());
        assertEquals(-500,bunny.getVelocityY());

        gmc.translateKeyEventReleased("UP");
        assertEquals(0,gmc.getCodeList().size());
        assertEquals(-500,bunny.getVelocityY());

        //contain UP, release Down
        gmc.getCodeList().add("UP");
        gmc.translateKeyEventReleased("DOWN");
        assertEquals(1,gmc.getCodeList().size());
        assertEquals(-500,bunny.getVelocityY());
    }

    @Test
    void updateCactusListTest() {
        cactus1.setPositionX(300);
        cactus2.setPositionX(400);
        gmc.updateCactusList(1.0);

        assertEquals(200,cactus1.getPositionX());
        assertEquals(300,cactus2.getPositionX());
    }

    @Test
    void updateFloorListTest() {
        floor1.setPositionX(300);
        floor2.setPositionX(400);
        gmc.updateFloorList(1.0);

        assertEquals(200,floor1.getPositionX());
        assertEquals(300,floor2.getPositionX());
    }

    @Test
    void checkHPTest() {
        gmc.checkHP();
        assertEquals(false,gmc.isGameOver);
        bunny.setHp(0);
        gmc.checkHP();
        assertEquals(true,gmc.isGameOver);
    }

    @Test
    void checkFloorCactusSizeTest() throws IOException {
        gmc.setFloorFrequenceTime(2);

        //no effect case
        gmc.setPastTime(2);
        gmc.checkFloorCactusSize();
        assertEquals(2,gmc.getFloorList().size());
        assertEquals(2,gmc.getCactusList().size());

        //effect case
        gmc.setPastTime(4);
        gmc.checkFloorCactusSize();
        assertEquals(3,gmc.getFloorList().size());
        assertEquals(3,gmc.getCactusList().size());

        gmc.setFloorFrequenceTime(1);
        gmc.checkFloorCactusSize();
        assertEquals(3,gmc.getFloorList().size());
        assertEquals(3,gmc.getCactusList().size());

    }

    @Test
    void checkFloorOutTest() {
        floor1.setPositionX(-501);
        floor2.setPositionX(0);
        gmc.checkFloorListOut();
        assertEquals(1,floorList.size());
        assertEquals(floor2,floorList.get(0));
    }

    @Test
    void checkCactusOutTest() {
        cactus1.setPositionX(-70);
        cactus2.setPositionX(0);
        gmc.checkCactusListOUt();
        assertEquals(1,cactusList.size());
        assertEquals(cactus2,cactusList.get(0));
    }


    @Test
    void checkTest() throws IOException {
        cactus1.setPositionX(300);
        cactus2.setPositionX(400);
        floor1.setPositionX(300);
        floor2.setPositionX(400);

        gmc.check();
        assertEquals(3,bunny.getHp());
        assertEquals(2,floorList.size());
        assertEquals(2,cactusList.size());
        assertEquals(false,bunny.getOnFloor());

    }
}