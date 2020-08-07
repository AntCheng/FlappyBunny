package model;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.Random;

//class cactus represent the obstacles that would hurt the bunny, it stays on the floor
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

    //EFFECT: initialize a cactus object and initialize the velocity, position and other status of this cactus.
    public Cactus(Floor floor) throws IOException {
        Random rand = new Random();
        this.width = 60;
        this.height = 60;
        this.cactusImage = ImageIO.read(cactusFile).getScaledInstance(width,height,Image.SCALE_SMOOTH);

//        this.cactusImage = new Image("cactus.png", 60,
//                60, false,false);
        this.positionX = floor.getPositionX() + rand.nextInt((int) (floor.getWidth() - 1));
        this.positionY = floor.getPositionY() - 50;
        this.velocityX = 200;
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


    //EFFECT: return true if the cactus is out, false otherwise
    public boolean isOut() {
        if (positionX + width < 0) {
            return true;
        }
        return false;
    }

    @Override
    public void update(double time) {
        this.positionX -= floor.getVelocityX() * time;
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
