package ui;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

//GameEventListener extends KeyAdapter, which detect the key Event the users produce and make corresponding response
public class GameEventListener extends KeyAdapter {

    private FlappyBunnySwing gamePanel;

    //EFFECT: initialize a GameEventListener and set its panel to the given panel
    public GameEventListener(FlappyBunnySwing jpanel) {
        this.gamePanel = jpanel;
    }

    @Override
    //EFFECT: catch the keyEvent e and call the gameModelController with the given event
    public void keyPressed(KeyEvent e) {
        gamePanel.gmc.translateKeyEventPressed(e.getKeyCode());
    }

    @Override
    //EFFECT: catch the keyEvent e and call the gameModelController with the given event
    public void keyReleased(KeyEvent e) {
        gamePanel.gmc.translateKeyEventReleased(e.getKeyCode());
    }
}
