package ui;

import model.PlayerManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {

    private Controller controller;
    private JLabel userLabel = new JLabel("UserName");
    private JTextField userTextField = new JTextField();
    private JLabel passwordLabel = new JLabel("PassWord");
    private JPasswordField userPassWordField = new JPasswordField();
    private JButton loginButton = new JButton("Login");
    private JButton registerButton = new JButton("Register");
    private JLabel loginStatus = new JLabel("Incorrect username and password!");
    GridBagLayout loginLayout;
    private GridBagConstraints constraints;
    private ImageIcon backGround;
    private ImageIcon backgroundImage;


    private PlayerManager playerManager;

    public LoginPanel(Controller controller) {
        this.controller = controller;
        controller.mainFrame.setTitle("Login Window");
        loginLayout = new GridBagLayout();
        playerManager = controller.getPlayerManager();
        //backGround = new ImageIcon("src/main/resources/Players/bunny1_hurt.png");
        loginStatus.setVisible(false);
        //loginStatus.setFont();
        this.setLayout(loginLayout);
        constraints = new GridBagConstraints();
        this.setPreferredSize(new Dimension(400,400));
        backgroundImage = new ImageIcon("src/main/resources/Players/bunny1_hurt.png");
        setup();
        addAction();

    }

    public void setup() {

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0.5;
        constraints.insets = new Insets(10,10,10,10);
        add(userLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        //constraints.gridwidth = 30;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(userTextField,constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx = 0.5;
        constraints.fill = GridBagConstraints.NONE;
        add(passwordLabel,constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        //constraints.gridwidth = 30;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(userPassWordField,constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        add(loginButton,constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        add(registerButton,constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        add(loginStatus,constraints);

    }

    public void addAction() {

        loginButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String usernameInput = userTextField.getText();
                String passwordInput = userPassWordField.getText();
                System.out.println("click");
                if (playerManager.matchUserAndPassword(usernameInput,passwordInput)) {
                    System.out.println("Logging successful!!");
                    System.out.println("Login account is " + usernameInput);
                    System.out.println();
                    System.out.println();
//                  go to next panel;
                } else {
                    loginStatus.setVisible(true);
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //todo: go to register panel, (similar to this one)
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage.getImage(),0,0,300,300,null);
    }
}
