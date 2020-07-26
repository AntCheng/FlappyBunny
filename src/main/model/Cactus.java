package model;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Cactus implements GraphicModel {

    private int width;
    private int height;

    private Image cactusImage;
    private double positionX;
    private double positionY;
    private double velocityX;
    private double velocityY;
    private Floor floor;
    private boolean isTouched;
    private File cactusFile = new File("src/main/resources/cactus.png");

    //Class cactus would appear on the floor, it serves to obstacle bunny, if bunny get touch to them, bunny would hurt
    public Cactus(Floor floor) {
        Random rand = new Random();
        this.width = 60;
        this.height = 60;
        try {
            this.cactusImage = ImageIO.read(cactusFile).getScaledInstance(width,height,Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        this.cactusImage = new Image("cactus.png", 60,
//                60, false,false);
        this.positionX = floor.getPositionX() + rand.nextInt((int) (floor.getWidth() - 1));
        this.positionY = floor.getPositionY() - 50;
        this.velocityX = 100;
        this.floor = floor;
        this.isTouched = false;

    }

    public Image getImage() {
        return cactusImage;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
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
        return new Rectangle2D.Double(positionX,positionY,width - 5,height - 5);
    }

    @Override
    public boolean intersects(GraphicModel g) {
        return g.getBoundary().intersects(this.getBoundary());
    }
}
