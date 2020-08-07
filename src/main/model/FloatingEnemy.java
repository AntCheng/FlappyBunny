package model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import static model.FloatingEnemy.State.*;

//FloatingEnemy is the alien in the game, they would shoot the bunny and hurt the bunny, touch them would
// also hurt the bunny
public class FloatingEnemy implements Observer, GraphicModel {

    private Image alienImage;
    private Image alienShootingImage1;
    private Image alienShootingImage2;
    private Image alienShootingImage3;
    private int velocityX;
    private int velocityY;
    private double positionX;
    private double positionY;
    private double width;
    private double height;
    private double delayTimeForShooting;
    private double delayTimeShowingShootingImage;

    private boolean isActivated;
    private boolean isShooting;
    private boolean isHitted = false;
    private Bunny bunny;
    private State state;

    private File alienImageFile = new File("src/main/resources/shipBlue_manned.png");
    private File alienShootingImageFile1 = new File("src/main/resources/laserBlueReady.png");
    private File alienShootingImageFile2 = new File("src/main/resources/laserBlueShooting.png");
    private File alienShootingImageFile3 = new File("src/main/resources/laserBlue_groundBurst.png");

    //Enum State is the states of these alien
    public enum State {
        NOTHING, FLYINGIN, SHOOTING, FLYINGOUT
    }

    //EFFECT: FloatingEnemy class constructor, set up the basics and images.
    public FloatingEnemy() throws IOException {
        this.positionX = 1000;
        this.positionY = 100;
        this.width = 60;
        this.height = 60;
        this.velocityX = 100;
        this.velocityY = 100;
        state = NOTHING;

        alienImage = ImageIO.read(alienImageFile).getScaledInstance((int)width,(int)height,Image.SCALE_SMOOTH);
        alienShootingImage1 = ImageIO.read(alienShootingImageFile1).getScaledInstance((int)width,(int)height,
                Image.SCALE_SMOOTH);
        alienShootingImage2 = ImageIO.read(alienShootingImageFile2).getScaledInstance((int)width / 2,
                900, Image.SCALE_SMOOTH);
        alienShootingImage3 = ImageIO.read(alienShootingImageFile3).getScaledInstance((int)width * 2,
                (int)height, Image.SCALE_SMOOTH);

    }

    public FloatingEnemy(double positionX, double positionY) throws IOException {
        this();
        this.setPositionX(positionX);
        this.setPositionY(positionY);
    }

    public Image getAlienImage() {
        return alienImage;
    }

    public Image getAlienShootingImage1() {
        return alienShootingImage1;
    }

    public Image getAlienShootingImage2() {
        return alienShootingImage2;
    }

    public Image getAlienShootingImage3() {
        return alienShootingImage3;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setDelayTimeForShooting(double delayTimeForShooting) {
        this.delayTimeForShooting = delayTimeForShooting;
    }

    public boolean isShooting() {
        return (state == SHOOTING);
    }

    public boolean isShootingDelay() {
        return (delayTimeForShooting >= 0 && delayTimeForShooting < 1);
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public Bunny getBunny() {
        return bunny;
    }

    public boolean isHitted() {
        return isHitted;
    }

    public double getDelayTimeForShooting() {
        return delayTimeForShooting;
    }

    public double getDelayTimeShowingShootingImage() {
        return delayTimeShowingShootingImage;
    }

    public void setDelayTimeShowingShootingImage(double delayTimeShowingShootingImage) {
        this.delayTimeShowingShootingImage = delayTimeShowingShootingImage;
    }

    @Override
    //MODIFIES: this
    //EFFECT: set this.bunny = the input bunny and make the stat become FLYINGIN if the original state is NOTHING.
    public void update(Observable o, Object arg) {
        this.bunny = (Bunny) o;
        if (state == NOTHING) {
            state = FLYINGIN;
        }
    }



    @Override
    //MODIFIES: this
    //EFFECTS: Do corresponding action according to the state of the alien.
    public void update(double time) {
        if (state == FLYINGIN) {
            this.positionX -= (velocityX * time);
            if (this.positionX <= bunny.getPositionX()) {
                state = SHOOTING;
            }

        } else if (state == SHOOTING) {
            updateShootingState(time);
        } else if (state == FLYINGOUT) {
            this.positionY += -100 * time;
        }

        if (this.intersects(bunny)) {
            if (!isHitted) {
                bunny.getHurt();
                isHitted = true;
            }
        }
    }

    //MODIFIES: this
    //EFFECT: the action for alien after the alien locate the bunny position, after delayTimeForShooting delay,
    // the alien would shoot a laser and could hurt the bunny, after that, their state become FLYINGOUT.
    public void updateShootingState(double time) {
        delayTimeForShooting += time;
        if (delayTimeForShooting >= 1.0) {
            if ((bunny.getPositionX() <= this.positionX + 20) && (bunny.getPositionX() >= this.positionX - 20)) {
                if (!isHitted) {
                    bunny.getHurt();
                    isHitted = true;
                }
            }
            delayTimeShowingShootingImage += time;
            //drawing imgae
            if (delayTimeShowingShootingImage > 0.3) {
                state = FLYINGOUT;
            }

        }
    }


    public int getVelocityX() {
        return velocityX;
    }

    //EFFECT: return true if this is out of the window, otherwise return false.
    public boolean isOut() {
        if (this.positionY < -100) {
            return true;
        }
        return false;
    }

    @Override
    public Rectangle2D getBoundary() {
        return new Rectangle2D.Double(this.getPositionX(),this.getPositionY(),
                this.getWidth(),this.getHeight());
    }

    @Override
    public boolean intersects(GraphicModel g) {
        return g.getBoundary().intersects(this.getBoundary());
    }


}
