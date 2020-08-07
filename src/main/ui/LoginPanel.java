package ui;

import model.PlayerManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

//class LoginPanel extends JPanel, which is the Login and Register window that user would see
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
    private ImageIcon backgroundImage;


    private PlayerManager playerManager;

    //EFFECT: initialize a LoginPanel object, it set up the login or register window for users.
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
        setup1();
        setup2();
        addLoginAction();
        addRegisterAction();
    }

    //MODIFIES: this
    //EFFECT: set up the window, how Labels and button are placed
    public void setup1() {

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


    }

    //MODIFIES: this
    //EFFECT: set up the window, how Labels and button are placed
    public void setup2() {
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

    //MODIFIES: this
    //EFFECT: give login button an actionListener, which let him login an account if that the userName
    // and passWord is correct in the file. If Login successful, it will go to activity window.
    public void addLoginAction() {

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
                    controller.gotoActivity();
                } else {
                    loginStatus.setText("Incorrect username and password!");
                    loginStatus.setVisible(true);
                }
            }
        });


    }

    //MODIFIES: this
    //EFFECT: give register button an actionListener, which let him register an account if that username
    // haven't been used. If register successful, it will go to activity window.
    public void addRegisterAction() {
        registerButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String usernameInput = userTextField.getText();;
                String passwordInput = userPassWordField.getText();;


                if (playerManager.creatAccount(usernameInput,passwordInput)) {
                    System.out.println("Register successful!!");
                    System.out.println("The new account is " + usernameInput);
                    System.out.println();
                    try {
                        playerManager.saveRecord(0.0,controller.getAccountsFile());
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (UnsupportedEncodingException ex) {
                        ex.printStackTrace();
                    }
                    controller.gotoActivity();
                } else {
                    loginStatus.setText("This username already exist");
                    loginStatus.setVisible(true);

                }
            }
        });
    }

    @Override
    //MODIFIES: this
    //EFFECT: draw some picture in the background
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage.getImage(),0,0,300,300,null);
    }
}
