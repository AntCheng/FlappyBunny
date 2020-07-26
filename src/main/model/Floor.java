package model;



import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Floor implements GraphicModel {

    public static final int BASIC_FLOOR =    0;

    private double width;
    private double height;

    private Image normalFloor;
    private Image floor2;
    private File floorFile = new File("src/main/resources/ground_wood.png");
    private double walkStateTime;
    private double positionX;
    private double positionY;
    private double velocityX;
    private double velocityY;
    private double gravityOnFloor;

    //The floor class in this game, which would floating on the game and may carry bunny, cactus and other things
    public Floor(int x, int y, Bunny bunny, int type) throws IOException {
        Random rand = new Random();
        this.positionX = x;
        this.positionY = bunny.getPositionY() + (-1 + rand.nextInt(3)) * 100;
        this.velocityX = 100;
        this.height = 50;
        this.width = 200 + rand.nextInt(300);
        normalFloor = ImageIO.read(floorFile).getScaledInstance((int)width,(int)height,Image.SCALE_SMOOTH);

//        normalFloor = new Image("ground_wood.png",this.width,
//                this.height,false,false);
    }


    public double getPositionX() {
        return this.positionX;
    }

    public double getVelocityX() {
        return this.velocityX;
    }

    public double getPositionY() {
        return this.positionY;
    }

    public double getHeight() {
        return this.height;
    }

    public Image getImage() {
        return normalFloor;
    }

    public double getWidth() {
        return width;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
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
    public Rectangle2D.Double getBoundary() {
        return new java.awt.geom.Rectangle2D.Double(positionX,positionY,width,height);
    }

    @Override
    public boolean intersects(GraphicModel g) {
        return g.getBoundary().intersects(this.getBoundary());
    }
}
