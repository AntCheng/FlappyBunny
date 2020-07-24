package model;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

import java.util.List;

//class Bunny, player would control the instantiation of this object, which is a bunny to play this game.
public class Bunny implements GraphicModel {

    private double width = 50;
    private double height = 50;
    private double gravityOnBunny = 800;


    private Image walk1;
    private Image walk2;
    private double walkStateTime;
    private double positionX;
    private double positionY;
    private double velocityX;
    private double velocityY;

    private boolean walkState = true;
    private boolean onFloor = false;
    private int hp;

    //effect: instantiate a bunny object
    public Bunny(int x, int y) {
        walk1 = new Image("/Players/bunny1_walk1.png",50,50,
                false,false);
        walk2 = new Image("/Players/bunny1_walk2.png",50,50,
                false,false);
        this.walkStateTime = 0;
        this.positionX = x;
        this.positionY = y;
        this.velocityY = 0;
        this.gravityOnBunny = 800;
        hp = 3;
    }


    public void setOnFloor(boolean b) {
        this.onFloor = b;
    }

    public Image getWalk1() {
        return walk1;
    }

    public Image getWalk2() {
        return walk2;
    }

    public double getGravityOnBunny() {
        return gravityOnBunny;
    }

//    public double getWalkStateTime() {
//        return walkStateTime;
//    }

    public boolean getWalkState() {
        return walkState;
    }

    public boolean getOnFloor() {
        return onFloor;
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

//    public double getVelocityX() {
//        return velocityX;
//    }

//    public void setVelocityX(double velocityX) {
//        this.velocityX = velocityX;
//    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public double getWidth() {
        return width;
    }

//    public void setWidth(double width) {
//        this.width = width;
//    }

    public double getHeight() {
        return height;
    }

//    public void setHeight(double height) {
//        this.height = height;
//    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getHp() {
        return hp;
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

    //modified: this
    //effect: boost up the bunny's velocityY
    public void burstFly() {
        if (isCollideGroundFloor() || onFloor) {
            this.velocityY = -500;
        }

    }

//    public Boolean isCollideTopWall() {
//        if (positionY - height <= 0) {
//            return true;
//        }
//        return false;
//    }

    //effect: check if this bunny touch the ground
    public Boolean isCollideGroundFloor() {
        if (positionY + height >= 750) {
            return true;
        }
        return false;
    }

    //effect: if bunny is on ground or floor, there is no gravity, else recover the gravity
//    public void theGravity(double num) {
//        if (positionY == num) {
//            gravityOnBird = 0;
//        } else {
//            gravityOnBird = 1000;
//        }
//    }

    //modifies: this
    //effect: check if the bunny is standing on a floor, if so stand on the floor, gravity and velocityY set to 0;
    public void checkStandFloor(List<Floor> floorList) {
        for (Floor floor : floorList) {
            if (this.intersects(floor) && this.positionY < floor.getPositionY() - 20) {
                standFloor(floor);
                onFloor = true;
                return;
            }
        }
        onFloor = false;
    }

//    public boolean isStandFloor(List<Floor> floorList) {
//        for (Floor floor : floorList) {
//            if (this.intersects(floor) && this.positionY < floor.getPositionY() - 20) {
//                return true;
//            }
//        }
//        return false;
//    }

    //modifies: this
    //effect: stand on the floor, set gravity to zero
    public void standFloor(Floor floor) {
        this.positionY = floor.getPositionY() - 50;
        velocityY = 0;
        gravityOnBunny = 0;
    }


    @Override
    //modifies: this
    //effect: update its position, if go over the top or bottom, make them stay on ground or not over the top.
    public void update(double time) {
        // 750 is the ideal number for bunny to walk on ground
        gravityOnBunny = 800;
        velocityY += gravityOnBunny * time;
        if (positionY + velocityY * time < 0) {
            velocityY = 0;
            positionY = 0;
        } else if (positionY + velocityY * time >= 750) {
            velocityY = 0;
            positionY = 750;
            gravityOnBunny = 0;
        }
        this.positionY = positionY + velocityY * time;


    }

    //modifies: this
    //effect: check if this bunny intersect with any cactus, if so, minus one hp
    public void checkTouchCactus(List<Cactus> cactusList) {
        for (Cactus cactus : cactusList) {
            if (intersects(cactus) && !cactus.getIsTounched()) {
                hp--;
                cactus.setIsTouched(true);
            }
        }
    }



    @Override
    //effect: return the boundary of this object
    public Rectangle2D getBoundary() {
        return new Rectangle2D(positionX,positionY,width,height);
    }

    @Override
    //effect: return if this bunny intersect with the other GraphicModel input g
    public boolean intersects(GraphicModel g) {
        return g.getBoundary().intersects(this.getBoundary());
    }
}
