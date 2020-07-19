package ui;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

public class Practise {
    Scene practiseScene;

    public Practise() {
        initial();
    }

    public Scene getScene() {
        return practiseScene;
    }

    public void initial() {
        Group root = new Group();
        practiseScene = new Scene(root);
        //theStage.setScene( practiseScene );
        Canvas canvas = new Canvas(512 - 64, 256);
        root.getChildren().add(canvas);

        ArrayList<String> input = new ArrayList<String>();

        practiseScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                String code = e.getCode().toString();
                // only add once... prevent duplicates
                if (!input.contains(code)) {
                    input.add(code);
                }
            }
        });

        practiseScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                String code = e.getCode().toString();
                input.remove(code);
            }
        });

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Image left = new Image("file:src/left.png");
        Image leftG = new Image("file:src/leftG.png");

        Image right = new Image("file:src/right.png");
        Image rightG = new Image("file:src/rightG.png");

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                // Clear the canvas
                gc.clearRect(0, 0, 512,512);

                if (input.contains("LEFT"))
                    gc.drawImage(leftG, 64, 64);
                else
                    gc.drawImage(left, 64, 64);

                if (input.contains("RIGHT"))
                    gc.drawImage(rightG, 256, 64);
                else
                    gc.drawImage(right, 256, 64);
            }
        }.start();
    }

}
