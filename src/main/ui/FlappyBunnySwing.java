package ui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.io.UnsupportedEncodingException;
import java.util.List;

//class FlappyBunnySwing extends JPanel and it is the game window that the user would see. it shows all graphic game
// model to the users and detect users interaction.
public class FlappyBunnySwing extends JPanel {
    JFrame mainFrame;
    GameModelController gmc;
    Double recordTime;
    Timer timer;
    Controller controller;

    // The flappyBunnySWing class extends JPanel, this is the game Panel where all the game action happen
    // in this class. It set up the timer to start to game and draw all those game models.
    public FlappyBunnySwing(JFrame mainFrame, Controller controller) throws IOException {
        this.mainFrame = mainFrame;
        this.controller = controller;
        initial();
        run();
    }

    //MODIFIES: this
    //EFFECTS: initialize the gameModelController to control the game
    public void initial() throws IOException {
        gmc = new GameModelController(1000,800);
        setPreferredSize(new Dimension(1000,800));
        recordTime = 0.0;

        addKeyListener(new GameEventListener(this));

        setFocusable(true);
        requestFocusInWindow();
    }

    //MODIFIES: this
    //EFFECTS: start the game by setting up the timer and call it start. the timer would record the pass time
    // and update the graphic model and check the events. The timer would stop if the bunny hp is lower than 0
    public void run() {
        timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                requestFocusInWindow();

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
                    gotoSaveGame();
                }


                //draw
                //this seems would call the paintComponent method)
                repaint();


            }
        });
        timer.start();

    }


    public GameModelController getGameModelController() {
        return gmc;
    }


    @Override
    //MODIFIES: this
    //EFFECTS: draw all the game model
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(0.1,g);

    }

    //MODIFIES: this
    //EFFECT: the overall draw method
    public void draw(Double time, Graphics g) {
        g.clearRect(0,0,WIDTH,HEIGHT); //clear canvas
        drawBunny(gmc.getBunny(),time,g);
        drawFloor(gmc.getFloorList(),g);
        drawCactus(gmc.getCactusList(),g);
        drawMetersAndHP(g);
        drawFloatingAlien(gmc.getFloatingEnemy(),g);
        //Toolkit.getDefaultToolkit().sync(); //not sure how it actually work, but it would help display image?
    }

    //MODIFIES: this
    //EFFECT: the overall draw method
    public void drawBunny(Bunny bunny, Double time, Graphics g) {
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
    //EFFECT: the draw method for the alien in the game
    public void drawFloatingAlien(List<FloatingEnemy> alienList, Graphics g) {
        for (FloatingEnemy alien : alienList) {
            g.drawImage(alien.getAlienImage(), (int)alien.getPositionX(), (int)alien.getPositionY(),this);
            if (alien.isShooting()) {
                g.drawImage(alien.getAlienShootingImage1(), (int)alien.getPositionX(),
                        (int)alien.getPositionY() + 30,this);

                if (!alien.isShootingDelay()) {
                    g.drawImage(alien.getAlienShootingImage2(), (int)alien.getPositionX() + 15,
                            (int) (alien.getPositionY() + alien.getHeight()),this);
                    g.drawImage(alien.getAlienShootingImage3(), (int) alien.getPositionX() - 15,
                            750,this);
                }
            }

        }
    }


    //MODIFIES: this
    //EFFECTS: drawHP and the current meters of the game controller canvas
    public void drawMetersAndHP(Graphics g) {
        String time = recordTime.toString();
        g.drawString(time + " meters",950,50);
        g.drawString("HP " + String.valueOf(gmc.getBunny().getHp()), 10,50);
    }

    //MODIFIES: this
    //EFFECTS: pops up a new window asking if the user want to save their game record or not
    public void gotoSaveGame() {
        JFrame saveFrame = new JFrame();
        saveFrame.setVisible(true);
        //viewFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        saveFrame.setResizable(false);
        JPanel savePanel = new JPanel();
        JButton yesButton = new JButton("Yes");
        JButton noButton = new JButton("no");
        JLabel dieStatement = new JLabel("Your bunny died ...");
        JLabel saveStatement = new JLabel("Do you want to save your game? your record is " + recordTime);

        savePanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        setPosition(savePanel, constraints, dieStatement, saveStatement, yesButton, noButton);
        addYesAction(yesButton, saveFrame);
        addNoAction(noButton, saveFrame);

        saveFrame.add(savePanel);
        saveFrame.pack();
        saveFrame.setLocationRelativeTo(null);
    }

    public void setPosition(JPanel savePanel,
                            GridBagConstraints constraints,
                            JLabel dieStatement,
                            JLabel saveStatement,
                            JButton yesButton,
                            JButton noButton) {

        constraints.insets = new Insets(10,10,10,10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        savePanel.add(dieStatement, constraints);


        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        savePanel.add(saveStatement,constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        savePanel.add(yesButton,constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.weightx = 0.5;
        constraints.weightx = 0.5;
        constraints.fill = GridBagConstraints.NONE;
        savePanel.add(noButton,constraints);
    }

    //MODIFIES: this
    //EFFECT: give yesButton actionListener, which allow it to save current record to currentPlayer if the button
    // is pressed. When pressed, it would also go to login window.
    public void addYesAction(JButton yesButton, JFrame saveFrame) {
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.getPlayerManager().saveRecord(recordTime, controller.getAccountsFile());
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                } catch (UnsupportedEncodingException ex) {
                    ex.printStackTrace();
                }
                System.out.println("see you next time");
                controller.gotoLogin();
                saveFrame.dispose();
            }
        });
    }

    //MODIFIES: this
    //EFFECT: give noButton actionListener, when pressed, it would also go to login window.
    public void addNoAction(JButton noButton, JFrame saveFrame) {
        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.getPlayerManager().saveRecord(
                            controller.getPlayerManager().getCurrentPlayer().getRecord(),
                            controller.getAccountsFile());
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                } catch (UnsupportedEncodingException ex) {
                    ex.printStackTrace();
                }
                controller.gotoLogin();
                saveFrame.dispose();
            }
        });
    }

}
