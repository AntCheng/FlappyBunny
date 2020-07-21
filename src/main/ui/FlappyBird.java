package ui;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import model.Bird;
import model.Floor;
import model.GameModelController;

import java.util.List;

public class FlappyBird {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 800;

    Scene falppyBirdScene;
    GameModelController gmc;
    private Long lastNanoTime;
    private AnimationTimer timer;
    Canvas canvas;
    GraphicsContext gc;

    public FlappyBird() {
        initial();
        run();
    }

    //effect: setting up
    public void initial() {
        Group root = new Group();
        falppyBirdScene = new Scene(root);
        //theStage.setScene( practiseScene );
        canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);
        gmc = new GameModelController(WIDTH,HEIGHT);
        gc = canvas.getGraphicsContext2D();
    }

    public void run() {
        //set a timer
        lastNanoTime = (new Long(System.nanoTime()));

        timer = new AnimationTimer() {
            public void handle(long currentNanoTime) {
                // calculate time since last update.
                double elapsedTime = (currentNanoTime - lastNanoTime.longValue()) / 1000000000.0;
                lastNanoTime = currentNanoTime;

                //update the position (give the elapsed time)
                gmc.update(elapsedTime);

                //detect events and give the events to controller
                falppyBirdScene.setOnKeyPressed(e -> gmc.translateKeyEventPressed(e));
                falppyBirdScene.setOnKeyReleased(e -> gmc.translateKeyEventReleased(e));

                //check on collison and game state
                gmc.check();
                if (gmc.isGameOver) {
                    timer.stop();
                }

                //draw graphic
                draw(elapsedTime);
            }
        };
        timer.start();
    }

    public void draw(Double time) {
        gc.clearRect(0,0,WIDTH,HEIGHT); //clear canvas
        drawBird(gmc.getBird(),time);
        drawFloor(gmc.getFloorList());
    }

    public void drawBird(Bird bird, Double time) {
        gc.drawImage(bird.getWalkImage(time), bird.getPositionX(), bird.getPositionY());
    }

    public void drawFloor(List<Floor> floorList) {
        for (Floor floor : floorList) {
            gc.drawImage(floor.getImage(), floor.getPositionX(), floor.getPositionY());
        }
    }

    public Scene getScene() {
        return falppyBirdScene;
    }
}
