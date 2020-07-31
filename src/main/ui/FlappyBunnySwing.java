package ui;

import model.Bunny;
import model.Cactus;
import model.Floor;
import model.GameModelController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FlappyBunnySwing extends JPanel {
    JFrame mainFrame;
    GameModelController gmc;
    Double recordTime;
    Timer timer;


    public FlappyBunnySwing(JFrame mainFrame) throws IOException {
        this.mainFrame = mainFrame;
        initial();
        run();
    }

    public void initial() throws IOException {
        gmc = new GameModelController(1000,800);
        setPreferredSize(new Dimension(1000,800));
        recordTime = 0.0;

        mainFrame.addKeyListener(new GameEventListener(this));

        setFocusable(true);
        requestFocusInWindow();
    }

    public void run() {
        timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //update
                gmc.update(0.01);
                recordTime += 0.01;

                //check
                try {
                    gmc.check();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                if (gmc.isGameOver) {
                    timer.stop();

                }


                //draw
                //this seems would call the paintComponent method)
                repaint();


            }
        }
        );
        timer.start();

    }

//    public KeyAdapter makeKeyListener() {
//        return new KeyAdapter() {
//            @Override
//            public void keyPressed(KeyEvent e) {
//                gmc.translateKeyEventPressed(e.toString());
//                //super.keyPressed(e);
//            }
//
//            @Override
//            public void keyReleased(KeyEvent e) {
//                gmc.translateKeyEventReleased(e.toString());
//                //super.keyReleased(e);
//            }
//        };
//    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(0.1,g);

    }

    //MODIFIES: this
    //EFFECT: the overall draw method
    public void draw(Double time, Graphics g) {
        g.clearRect(0,0,WIDTH,HEIGHT); //clear canvas
        drawBird(gmc.getBunny(),time,g);
        drawFloor(gmc.getFloorList(),g);
        drawCactus(gmc.getCactusList(),g);
        drawMetersAndHP(g);

        //Toolkit.getDefaultToolkit().sync(); //not sure how it actually work, but it would help display image?
    }

    //MODIFIES: this
    //EFFECT: the overall draw method
    public void drawBird(Bunny bunny, Double time, Graphics g) {
        g.drawImage(bunny.getWalkImage(time), (int)bunny.getPositionX(), (int)bunny.getPositionY(), this);
    }

    //MODIFIES: this
    //EFFECT: the draw method for the floor in the game
    public void drawFloor(List<Floor> floorList, Graphics g) {
        for (Floor floor : floorList) {
            g.drawImage(floor.getImage(), (int)floor.getPositionX(), (int)floor.getPositionY(),this);
        }
    }

    //MODIFIES: this
    //EFFECT: the draw method for the cactus in the game
    public void drawCactus(List<Cactus> cactusList, Graphics g) {
        for (Cactus cactus : cactusList) {
            g.drawImage(cactus.getImage(), (int)cactus.getPositionX(), (int)cactus.getPositionY(),this);
        }
    }

    //MODIFIES: this
    //EFFECTS: drawHP and the current meters of the game controller canvas
    public void drawMetersAndHP(Graphics g) {
        String time = recordTime.toString();
        g.drawString(time + " meters",950,50);
        g.drawString("HP " + String.valueOf(gmc.getBunny().getHp()), 10,50);
    }


}
