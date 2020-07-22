package model;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

import java.util.Random;

public class Cactus implements GraphicModel {

    private double width;
    private double height;

    private Image cactusImage;
    private double positionX;
    private double positionY;
    private double velocityX;
    private double velocityY;
    private Floor floor;
    private boolean isTouched;

    public Cactus(Floor floor) {
        Random rand = new Random();
        this.width = 60;
        this.height = 60;
        this.cactusImage = new Image("cactus.png", 60, 60, false,false);
        this.positionX = floor.getPositionX() + rand.nextInt((int) (floor.getWidth() - 1));
        this.positionY = floor.getPositionY() - 50;
        this.velocityX = 100;
        this.floor = floor;
        this.isTouched = false;

    }

    public Image getImage() {
        return cactusImage;
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setIsTouched(boolean b) {
        this.isTouched = b;
    }

    public boolean getIsTounched() {
        return isTouched;
    }

    @Override
    public void update(double time) {
        this.positionX -= velocityX * time;
        velocityX++;
    }

    @Override
    public Rectangle2D getBoundary() {
        return new Rectangle2D(positionX,positionY,width - 5,height - 5);
    }

    @Override
    public boolean intersects(GraphicModel g) {
        return g.getBoundary().intersects(this.getBoundary());
    }
}
