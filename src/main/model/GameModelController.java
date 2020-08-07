package model;


import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

//class GameModelController in this game, it serves to control the graphic model in this game.
public class GameModelController {

    public Bunny bunny;
    public ArrayList<Floor> floorList;
    public ArrayList<Cactus> cactusList;
    public Boolean isGameOver;
    private ArrayList<Integer> codeList;
    private ArrayList<FloatingEnemy> alienList;
    private int canvasWidth;
    private int canvasHeight;
    private double pastTime;
    private double floorFrequenceTime;
    private double floatingAlienFrequenceTime;
    private int gameDifficulty;
    Random random = new Random();

    //EFFECTS: initialize the GameModelController of this game, it would initialize all graphical model object
    // in the game.
    public GameModelController(int x, int y) throws IOException {
        bunny = new Bunny(50, 750);
        floorList = new ArrayList<Floor>();
        codeList = new ArrayList<>();
        cactusList = new ArrayList<Cactus>();
        alienList = new ArrayList<>();
        this.canvasWidth = x;
        this.canvasHeight = y;
        setup();
    }

    //MODIFIES: this
    //EFFECT: set up the game, clear all the floor and cactusList and set the isGameOver to false
    public void setup() {
        floorList.clear();
        cactusList.clear();
        alienList.clear();
        isGameOver = false;
        pastTime = 0;
        floorFrequenceTime = 0;
        floorFrequenceTime = 0;
        gameDifficulty = 1;
    }

    public Bunny getBunny() {
        return bunny;
    }

    public ArrayList<Integer> getCodeList() {
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

    public List<Floor> getFloorList() {
        return floorList;
    }

    public ArrayList<Cactus> getCactusList() {
        return cactusList;
    }

    public List<FloatingEnemy> getFloatingEnemy() {
        return alienList;
    }

    public double getFloatingAlienFrequenceTime() {
        return floatingAlienFrequenceTime;
    }

    public void setFloatingAlienFrequenceTime(double floatingAlienFrequenceTime) {
        this.floatingAlienFrequenceTime = floatingAlienFrequenceTime;
    }

    //MODIFIES: this
    //effect: update all graphic model in the after elapsedTime, add the elapsedTime to the pastTime
    // and floorFrequenceTime, floatingAlienFrequenceTime.
    public void update(Double elapsedTime) {
        bunny.update(elapsedTime);
        updateCactusList(elapsedTime);
        updateFloorList(elapsedTime);
        updateFloatingAlienList(elapsedTime);
        updateGameDifficulty();

        pastTime += elapsedTime;
        floorFrequenceTime += elapsedTime;
        floatingAlienFrequenceTime += elapsedTime;
    }

    //MODIFIES: this
    //EFFECT: update the current game difficulty
    //todo: test
    public void updateGameDifficulty() {
        gameDifficulty = (int)(pastTime / 30) + 1;
    }


    //MODIFIES: this
    //EFFECT: check if a new alien need to be made and if any alien fly out of the scene.
    public void checkFloatingAlien() throws IOException {
        for (int i = 0; i < gameDifficulty; i++) {
            if (floatingAlienFrequenceTime > 8) {
                double positionX = 1000 + random.nextInt(500);
                FloatingEnemy alien = new FloatingEnemy(positionX, 100);
                alienList.add(alien);
                bunny.addObserver(alien);
                bunny.changed();
                bunny.notifyObservers();
            }
        }
        if (floatingAlienFrequenceTime > 8) {
            floatingAlienFrequenceTime = 0;
        }
        Iterator<FloatingEnemy> alienListIterator = alienList.iterator();
        while (alienListIterator.hasNext()) {
            FloatingEnemy alien = alienListIterator.next();
            if (alien.isOut()) {
                alienListIterator.remove();
                bunny.deleteObserver(alien);
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: update each alien
    public void updateFloatingAlienList(double time) {
        for (FloatingEnemy alien : alienList) {
            alien.update(time);
        }
    }

    //MODIFIES: this
    //EFFECTS: generate floor and cactus
    public void checkFloorCactusSize() throws IOException {
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

    //MODIFIES: this
    //EFFECTS check if the cactus in the list is out, if so, remove them
    public void checkCactusListOUt() {
        Iterator<Cactus> cactusIterator = cactusList.iterator();
        while (cactusIterator.hasNext()) {
            Cactus cactus = cactusIterator.next();
            if (cactus.isOut()) {
                cactusIterator.remove();
            }
        }
    }



    //effect: do the tings correspond to the Key pressed
    public void translateKeyEventPressed(int e) {
        //case up
        if (e == 87 && !codeList.contains(e)) {
            codeList.add(e);
            bunny.burstFly();
        }
        //case right
        if (e == 68) {
            bunny.setIsMoveRight(true);
        }
        if (e == 65) {
            bunny.setIsMoveLeft(true);
        }
        //case left
    }

    //effect: do the things correspond to the key released
    public void translateKeyEventReleased(int e) {
        //case up
        if (e == 87 && codeList.contains(e)) {
            Iterator<Integer> codeIterator = codeList.iterator();
            while (codeIterator.hasNext()) {
                Integer i = codeIterator.next();
                if (i == 87) {
                    codeIterator.remove();;
                }
            }
        }
        //case right
        if (e == 68) {
            bunny.setIsMoveRight(false);
        }
        //case left
        if (e == 65) {
            bunny.setIsMoveLeft(false);
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
    // check the alien states
    public void check() throws IOException {
        checkHP();
        bunny.checkStandFloor(floorList);
        bunny.checkTouchCactus(cactusList);
        checkFloorCactusSize();
        checkFloorListOut();
        checkCactusListOUt();
        checkFloatingAlien();

    }



}
