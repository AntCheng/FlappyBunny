package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static model.FloatingEnemy.State.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class FloatingEnemyTest {

    public Bunny bunny;
    private FloatingEnemy alien;
    private List<FloatingEnemy> alienList;


    @BeforeEach
    void runBefore() {
        try {
            bunny = new Bunny(50,750);
            alien = new FloatingEnemy();
            alienList = new ArrayList<>();

        } catch (IOException e) {
            e.printStackTrace();
        }
       alienList.add(alien);
    }

    @Test
    void constructorTest() {
        assertEquals(60,alien.getHeight());
        assertEquals(100,alien.getVelocityX());
        assertEquals(60,alien.getAlienImage().getHeight(null));
        assertEquals(60,alien.getAlienImage().getWidth(null));
        assertEquals(60,alien.getAlienShootingImage1().getHeight(null));
        assertEquals(900,alien.getAlienShootingImage2().getHeight(null));
        assertEquals(60,alien.getAlienShootingImage3().getHeight(null));
        assertEquals(1000,alien.getPositionX());
        assertEquals(100,alien.getPositionY());
    }

    @Test
    void constructorTest2() {
        try {
            alien = new FloatingEnemy(200,200);
        } catch (IOException e) {
            fail();
        }
        assertEquals(200,alien.getPositionX());
        assertEquals(200,alien.getPositionY());
    }

    @Test
    void observerUpdateTest() {
        alien.update(bunny,null);
        assertEquals(FLYINGIN,alien.getState());
        alien.update(bunny,null);
    }

    @Test
    void graphicUpdateForStateTest() {
        assertEquals(null,alien.getBunny());
        alien.update(bunny,null);
        alien.update(1.0);
        assertEquals(900,alien.getPositionX());
        alien.update(1.0);
        alien.update(7.5);
        assertEquals(SHOOTING,alien.getState());
        alien.update(1.0);
        assertEquals(true,alien.isHitted());
        alien.setState(FLYINGOUT);
        alien.update(1.0);
        assertEquals(0,alien.getPositionY());

        alien.setState(NOTHING);
        alien.update(1.0);
        assertEquals(0,alien.getPositionY());

    }

    @Test
    void graphicUpdateForIntersectTest() {
        alien.setPositionX(50);
        alien.setPositionY(750);
        alien.update(bunny,null);
        alien.setState(NOTHING);
        alien.update(0.1);
        assertEquals(2,bunny.getHp());
        assertEquals(true,alien.isHitted());

        alien.update(0.1);
        assertEquals(2,bunny.getHp());
        assertEquals(true,alien.isHitted());
    }


    @Test
    void updateShootingStateTest() {
        alien.setPositionX(50);
        alien.update(bunny,null);
        alien.setState(SHOOTING);
        alien.updateShootingState(0.5);
        assertEquals(0.5,alien.getDelayTimeForShooting());
        alien.updateShootingState(0.5);
        assertEquals(true,alien.isHitted());
        assertEquals(0.5,alien.getDelayTimeShowingShootingImage());
        assertEquals(2,bunny.getHp());
        assertEquals(FLYINGOUT,alien.getState());


        alien.updateShootingState(0.1);
        assertEquals(2,bunny.getHp());
        assertEquals(1.1,alien.getDelayTimeForShooting());
        assertEquals(0.6,alien.getDelayTimeShowingShootingImage());

        alien.setState(SHOOTING);
        alien.setPositionX(100);
        alien.setDelayTimeShowingShootingImage(0.0);
        alien.updateShootingState(0.1);
        assertEquals(2,bunny.getHp());

        alien.setState(SHOOTING);
        alien.setPositionX(0);
        alien.setDelayTimeShowingShootingImage(0.0);
        alien.updateShootingState(0.1);
        assertEquals(2,bunny.getHp());

    }

    @Test
    void isOutTest() {
        alien.setPositionY(-200);
        assertEquals(true,alien.isOut());
        alien.setPositionY(200);
        assertEquals(false,alien.isOut());
    }

    @Test
    void isShootingTest() {
        assertEquals(false,alien.isShooting());
        alien.setState(SHOOTING);
        assertEquals(true,alien.isShooting());
    }

    @Test
    void isShootingDelayTest() {
        alien.setDelayTimeForShooting(0.5);
        assertEquals(true,alien.isShootingDelay());

        alien.setDelayTimeForShooting(-0.1);
        assertEquals(false,alien.isShootingDelay());

        alien.setDelayTimeForShooting(1.1);
        assertEquals(false,alien.isShootingDelay());
    }

    @Test
    void getBoundaryTest() {
        assertEquals( new Rectangle2D.Double(alien.getPositionX(), alien.getPositionY(), alien.getWidth(),
                alien.getHeight()), alien.getBoundary());
    }

    @Test
    void intersectTest() {
        bunny.setPositionX(100);
        bunny.setPositionY(100);
        alien.setPositionX(100);
        alien.setPositionY(100);
        assertEquals(true,alien.intersects(bunny));
    }
}
