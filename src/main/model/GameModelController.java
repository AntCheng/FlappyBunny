package model;


import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class GameModelController {

    public Bunny bunny;
    public ArrayList<Floor> floorList;
    public ArrayList<Cactus> cactusList;
    public Boolean isGameOver;
    //private double motionTime, elapsedTime;
    //private AnimationTimer timer;
   // private Long lastNanoTime;
    private ArrayList<String> codeList;
    private int canvasWidth;
    private int canvasHeight;
    private double pastTime;
    private double floorFrequenceTime;

    public GameModelController(int x, int y) {
        bunny = new Bunny(50, 750);
        floorList = new ArrayList<Floor>();
        codeList = new ArrayList<>();
        cactusList = new ArrayList<Cactus>();
        this.canvasWidth = x;
        this.canvasHeight = y;
        setup();
    }

    //MODIFIES: this
    //EFFECT: set up the game, clear all the floor and cactusList and set the isGameOver to false
    public void setup() {
        floorList.clear();
        cactusList.clear();
        isGameOver = false;
        pastTime = 0;
        floorFrequenceTime = 0;
    }

    public Bunny getBunny() {
        return bunny;
    }

    public ArrayList<String> getCodeList() {
        return codeList;
    }

    public void setPastTime(double pastTime) {
        this.pastTime = pastTime;
    }

    public void setFloorFrequenceTime(double floorFrequenceTime) {
        this.floorFrequenceTime = floorFrequenceTime;
    }

    public double getPastTime() {
        return pastTime;
    }

    public double getFloorFrequenceTime() {
        return floorFrequenceTime;
    }

    public ArrayList<Floor> getFloorList() {
        return floorList;
    }

    public ArrayList<Cactus> getCactusList() {
        return cactusList;
    }

    //MODIFIES: this
    //effect: update all graphic model in the after elapsedTime, add the elapsedTime to the pastTime
    // and floorFrequenceTime
    public void update(Double elapsedTime) {
        bunny.update(elapsedTime);
        updateCactusList(elapsedTime);
        updateFloorList(elapsedTime);

        pastTime += elapsedTime;
        floorFrequenceTime += elapsedTime;

    }

    //MODIFIES: this
    //EFFECTS: generate floor and cactus
    public void checkFloorCactusSize() {
        //floorList.size() < 3 ||
        if ((floorFrequenceTime > 1.5) && pastTime > 3) {
            Floor floor = new Floor(canvasWidth,canvasHeight, bunny,0);
            cactusList.add(new Cactus(floor));
            floorList.add(floor);
            floorFrequenceTime = 0;
        }
    }

    //MODIFIES: this
    //EFFECT: all the floor update
    public void updateFloorList(double time) {
        for (Floor floor : floorList) {
            floor.update(time);
        }
    }

    //MODIFIES: this
    //EFFECTS: check if the floor is out of the scene, if so remove them
    public void checkFloorListOut() {
        Iterator<Floor> floorIterator = floorList.iterator();
        while (floorIterator.hasNext()) {
            Floor floor = floorIterator.next();
            if (floor.isOut()) {
                floorIterator.remove();
            }
        }
    }



    //effect: do the tings correspond to the Key pressed
    public void translateKeyEventPressed(String e) {
        if (e.equals("UP") && !codeList.contains(e)) {
            codeList.add(e);
            bunny.burstFly();
        }
    }

    //effect: do the things correspond to the key released
    public void translateKeyEventReleased(String e) {
        if (e.equals("UP") && codeList.contains(e)) {
            codeList.remove(e);
        }
    }

    //modifies: this
    //effect: update all the cactus in the cactusList
    public void updateCactusList(Double time) {
        for (Cactus cactus : cactusList) {
            cactus.update(time);
        }
    }

    //MODIFIES: this
    //EFFECTS: check if the hp get below 0, if so, change isGameOver to true;
    public void checkHP() {
        if (bunny.getHp() <= 0) {
            isGameOver = true;
        }
    }

    //MODIFIES: this
    //EFFECT: check on all graphic model collisions: checkHP, check if bunny stand on a floor,
    // check if bunny touch any cactus
    // check if generate new floor and cactus
    // check if any floor is out
    public void check() {
        checkHP();
        bunny.checkStandFloor(floorList);
        bunny.checkTouchCactus(cactusList);
        checkFloorCactusSize();
        checkFloorListOut();
    }
}
