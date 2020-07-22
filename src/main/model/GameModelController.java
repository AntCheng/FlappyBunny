package model;


import javafx.animation.AnimationTimer;
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
    private double someTime;

    public GameModelController(int x, int y) {
        bunny = new Bunny(50, 750);
        floorList = new ArrayList<Floor>();
        codeList = new ArrayList<>();
        cactusList = new ArrayList<Cactus>();
        setup();
        this.canvasWidth = x;
        this.canvasHeight = y;
    }

    public void setup() {
        floorList.clear();
        isGameOver = false;
    }

    public Bunny getBunny() {
        return bunny;
    }

    public ArrayList<Floor> getFloorList() {
        return floorList;
    }

    public List<Cactus> getCactusList() {
        return cactusList;
    }

    //effect: update all graphic model in the after elapsedTime
    public void update(Double elapsedTime) {
        bunny.update(elapsedTime);
        updateCactusList(elapsedTime);

        pastTime += elapsedTime;
        someTime += elapsedTime;
        if ((floorList.size() < 3 || someTime > 2) && pastTime > 3) {
            Floor floor = new Floor(canvasWidth,canvasHeight, bunny,0);
            cactusList.add(new Cactus(floor));
            floorList.add(floor);
            someTime = 0;
        }
        Iterator<Floor> floorIterator = floorList.iterator();
        while (floorIterator.hasNext()) {
            Floor floor = floorIterator.next();
            floor.update(elapsedTime);
            if (floor.isOut()) {
                floorIterator.remove();
            }
        }
    }

    //effect: do the tings correspond to the Key pressed
    //todo: simplifie the keyevent to string so that it is easier to test
    public void translateKeyEventPressed(KeyEvent e) {
        if (e.getCode().toString() == "UP" && !codeList.contains(e.getCode().toString())) {
            codeList.add(e.getCode().toString());
            bunny.burstFly();
        }
    }

    //effect: do the things correspond to the key released
    public void translateKeyEventReleased(KeyEvent e) {
        if (e.getCode().toString() == "UP" && codeList.contains(e.getCode().toString())) {
            codeList.remove(e.getCode().toString());
        }
    }

    //modifies: this
    //effect: update all the cactus in the cactusList
    public void updateCactusList(Double time) {
        for (Cactus cactus : cactusList) {
            cactus.update(time);
        }
    }

    public void checkHP() {
        if (bunny.getHp() <= 0) {
            isGameOver = true;
        }
    }

    //effect: check on all graphic model collisions and if the game end
    public void check() {
        checkHP();
        bunny.checkStandFloor(floorList);
        bunny.checkTouchCactus(cactusList);
    }
}
