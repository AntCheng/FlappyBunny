package model;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Bird implements GraphicModel {

    private double width = 50;
    private double height = 50;

    private Image walk1;
    private Image walk2;
    private double walkStateTime;
    private double positionX;
    private double positionY;
    private double velocityX;
    private double velocityY;
    private double gravityOnBird;
    private boolean walkState = true;
    private int hp;


    public Bird(int x, int y) {
        walk1 = new Image("/Players/bunny1_walk1.png",50,50,false,false);
        walk2 = new Image("/Players/bunny1_walk2.png",50,50,false,false);
        this.walkStateTime = 0;
        this.positionX = x;
        this.positionY = y;
        this.velocityY = 100;
        this.gravityOnBird = 1000;
        int hp = 3;
    }

    //effect: return the walk image, changing between two image state;
    public Image getWalkImage(Double time) {
        if (walkStateTime < 0.2) {
            if (!walkState) {
                walkStateTime = 0;
            }
            walkStateTime += time;
            walkState = true;
            return walk1;
        } else {
            if (walkState) {
                walkStateTime = 0.4;
                walkState = false;
            }
            walkStateTime -= time;
            return walk2;
        }
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    //modified: this
    //effect: boost up the bird's velocityY
    public void burstFly() {
        this.velocityY = -500;
    }

    public Boolean isCollideTopWall() {
        if (positionY - height <= 0) {
            return true;
        }
        return false;
    }

    public Boolean isCollideGroundWall() {
        if (positionY + height >= 800) {
            return true;
        }
        return false;
    }

    //effect: if bunny is on ground, there is no gravity, else recover the gravity
    public void theGravity() {
        if (positionY == 750) {
            gravityOnBird = 0;
        } else {
            gravityOnBird = 1000;
        }
    }

    @Override
    //effect: update its position
    public void update(double time) {
        theGravity();
        velocityY += gravityOnBird * time;
        if (positionY + velocityY * time < 0) {
            velocityY = 0;
            positionY = 0;
        } else if (positionY + velocityY * time > 750) {
            velocityY = 0;
            positionY = 750;
            theGravity();
        }
        this.positionY = positionY + velocityY * time;
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
