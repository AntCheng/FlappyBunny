package model;


import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class GameModelController {

    public Bird bird;
    public ArrayList<Floor> floorList;
    public Boolean isGameOver;
    //private double motionTime, elapsedTime;
    private AnimationTimer timer;
    private Long lastNanoTime;
    private ArrayList<String> codeList;
    private int canvasWidth;
    private int canvasHeight;

    public GameModelController(int x, int y) {
        bird = new Bird(50, 200);
        floorList = new ArrayList<Floor>();
        codeList = new ArrayList<>();
        setup();
        this.canvasWidth = x;
        this.canvasHeight =y;
    }

    public void setup() {
        floorList.clear();
        isGameOver = false;
    }

    public Bird getBird() {
        return bird;
    }

    public List<Floor> getFloorList() {
        return floorList;
    }

    //effect: update all graphic model in the after elapsedTime
    public void update(Double elapsedTime) {
        bird.update(elapsedTime);
        if (floorList.size() < 8) {
            floorList.add(new Floor(canvasWidth,canvasHeight,0));
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
    public void translateKeyEventPressed(KeyEvent e) {
        if (e.getCode().toString() == "UP" && !codeList.contains(e.getCode().toString())) {
            codeList.add(e.getCode().toString());
            bird.burstFly();
        }
    }

    //effect: do the things correspond to the key released
    public void translateKeyEventReleased(KeyEvent e) {
        if (e.getCode().toString() == "UP" && codeList.contains(e.getCode().toString())) {
            codeList.remove(e.getCode().toString());
        }
    }

    //effect: check on all graphic model collisions and if the game end
    public void check(){

    }
}
