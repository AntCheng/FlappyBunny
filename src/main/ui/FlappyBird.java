package ui;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Bunny;
import model.Cactus;
import model.Floor;
import model.GameModelController;

import java.util.List;

public class FlappyBird {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 800;

    Scene falppyBirdScene;
    GameModelController gmc;
    private Long lastNanoTime;
    private Double recordTime = 0.0;
    private AnimationTimer timer;
    Canvas canvas;
    GraphicsContext gc;
    Boolean isGameOver;
    Controller controller;
//    Image bunnyWalk1 = new Image("/Players/bunny1_walk1.png",50,50,
//            false,false);
//    Image bunnyWalk2 = new Image("/Players/bunny1_walk2.png",50,50,
//            false,false);
//    Image cactusImage = new Image("cactus.png", 60, 60,
//            false,false);




    //EFFECTS: initialize and setting up a flapply bird scene,
    public FlappyBird(Controller controller) {
        this.controller = controller;
        initial();
        run();
    }

    //MODIFIES: this
    //EFFECTS: setting up the bird scene, the gameController, canvas and graphic
    public void initial() {
        Group root = new Group();
        falppyBirdScene = new Scene(root);
        canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);
        gmc = new GameModelController(WIDTH,HEIGHT);
        gc = canvas.getGraphicsContext2D();
        isGameOver = false;
    }

    //MODIFIES: this
    // EFFECT: run the program, set up a timer, update and draw the graphic model in the canvas
    public void run() {
        //set a timer
        lastNanoTime = (new Long(System.nanoTime()));

        timer = new AnimationTimer() {
            public void handle(long currentNanoTime) {
                // calculate time since last update.
                double elapsedTime = (currentNanoTime - lastNanoTime.longValue()) / 1000000000.0;
                lastNanoTime = currentNanoTime;
                recordTime += elapsedTime;

                //update the position (give the elapsed time)
                gmc.update(elapsedTime);

                //detect events and give the events to controller
                falppyBirdScene.setOnKeyPressed(e -> gmc.translateKeyEventPressed(e.getCode().toString()));
                falppyBirdScene.setOnKeyReleased(e -> gmc.translateKeyEventReleased(e.getCode().toString()));

                //check on collison and if the game ends
                gmc.check();
                if (gmc.isGameOver) {
                    drawGameOver();
                    timer.stop();
                    isGameOver = true;
                    controller.saveGame();
                }

                //draw graphic
                draw(elapsedTime);
            }
        };
        timer.start();
    }

    //MODIFIES: this
    //EFFECT: the overall draw method
    public void draw(Double time) {
        gc.clearRect(0,0,WIDTH,HEIGHT); //clear canvas
        drawBird(gmc.getBunny(),time);
        drawFloor(gmc.getFloorList());
        drawCactus(gmc.getCactusList());
        drawMetersAndHP();
    }

    //MODIFIES: this
    //EFFECT: the overall draw method
    public void drawBird(Bunny bunny, Double time) {
        gc.drawImage(bunny.getWalkImage(time), bunny.getPositionX(), bunny.getPositionY());
    }

    //MODIFIES: this
    //EFFECT: the draw method for the floor in the game
    public void drawFloor(List<Floor> floorList) {
        for (Floor floor : floorList) {
            gc.drawImage(floor.getImage(), floor.getPositionX(), floor.getPositionY());
        }
    }

    //MODIFIES: this
    //EFFECT: the draw method for the cactus in the game
    public void drawCactus(List<Cactus> cactusList) {
        for (Cactus cactus : cactusList) {
            gc.drawImage(cactus.getImage(), cactus.getPositionX(), cactus.getPositionY());
        }
    }

    //MODIFIES: this
    //EFFECTS: drawHP and the current meters of the game controller canvas
    public void drawMetersAndHP() {
        String time = recordTime.toString();
        gc.fillText(time + " meters",950,50);
        gc.fillText("HP " + String.valueOf(gmc.getBunny().getHp()), 10,50);

    }

    //MODIFIES: this
    //EFFECT: to draw the game over when the game ends
    public void drawGameOver() {
        gc.setFill(Color.RED);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        Font theFont = Font.font("Times New Roman", FontWeight.BOLD, 48);
        gc.setFont(theFont);
        gc.fillText("Game Over", 500, 400);
        gc.strokeText("Game Over", 500, 400);
    }

    public Double getTime() {
        return recordTime;
    }

    public boolean getIsGameOver() {
        return isGameOver;
    }

    public Scene getScene() {
        return falppyBirdScene;
    }
}
