package ui;

import model.Player;
import model.PlayerManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

//The activityPanel extends JPanel, it is the window after users sign in, it is also
// the window where user could choose to play the game, view the players or logout
public class ActivityPanel extends JPanel {

    Controller controller;
    PlayerManager playerManager;

    JButton playButton = new JButton("Play");
    JButton viewButton = new JButton("View Players");
    JButton logoutButton = new JButton("Log out");

    GridBagLayout activityLayout;
    private GridBagConstraints constraints;

    //EFFECT: Initialize a activity class, it would set up the activity window for the user.
    public ActivityPanel(Controller controller) {
        this.controller = controller;
        this.playerManager = controller.getPlayerManager();
        activityLayout = new GridBagLayout();
        this.setLayout(activityLayout);
        this.setPreferredSize(new Dimension(400,400));
        constraints = new GridBagConstraints();
        setup();
        addPlayAction();
        addViewAction();
        addLogoutAction();
    }


    public void setup() {
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(playButton,constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        add(viewButton,constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        add(logoutButton,constraints);


    }

    public void addPlayAction() {
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.goGamePanel();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public void addViewAction() {
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame viewFrame = new JFrame();
                viewFrame.setVisible(true);
                //viewFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                viewFrame.setResizable(false);
                JPanel viewPanel = new JPanel();
                playerManager.sortPlayers();
                viewPanel.setLayout(new GridLayout(0,1));
                for (Player player : playerManager.getPlayers()) {
                    JLabel playerText = new JLabel();
                    playerText.setText(player.toString());
                    viewPanel.add(playerText);
                }
                viewFrame.add(viewPanel);
                viewFrame.pack();
                viewFrame.setLocationRelativeTo(null);

            }
        });
    }

    public void addLogoutAction() {
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.gotoLogin();
            }
        });
    }



}
