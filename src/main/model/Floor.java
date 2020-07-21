package model;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

import java.util.Random;

public class Floor implements GraphicModel {

    public static final int BASIC_FLOOR =    0;

    private double width;
    private double height;

    private Image normalFloor;
    private Image floor2;
    private double walkStateTime;
    private double positionX;
    private double positionY;
    private double velocityX;
    private double velocityY;
    private double gravityOnFloor;


    public Floor(int x, int y, int type) {
        Random rand = new Random();
        this.positionX = x;
        this.positionY = rand.nextInt(y - 1);
        this.velocityX = 100;
        this.height = 50;
        this.width = 50 + rand.nextInt(500);
        normalFloor = new Image("ground_wood.png",this.width,
                this.height,false,false);
    }


    public double getPositionX() {
        return this.positionX;
    }

    public double getPositionY() {
        return this.positionY;
    }

    public Image getImage() {
        return normalFloor;
    }

    public boolean isOut() {
        if (positionX + width < 0) {
            return true;
        }
        return false;
    }

    @Override
    public void update(double time) {
        this.positionX -= velocityX * time;
    }

    @Override
    public Rectangle2D getBoundary() {
        return new Rectangle2D(positionX,positionY,width,height);
    }

    @Override
    public boolean intersects(GraphicModel g) {
        return g.getBoundary().intersects(this.getBoundary());
    }
}
