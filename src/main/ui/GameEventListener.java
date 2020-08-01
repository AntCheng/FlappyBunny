package ui;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameEventListener extends KeyAdapter {

    private FlappyBunnySwing gamePanel;

    public GameEventListener(FlappyBunnySwing jpanel) {
        this.gamePanel = jpanel;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        gamePanel.gmc.translateKeyEventPressed(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        gamePanel.gmc.translateKeyEventReleased(e.getKeyCode());
    }
}
