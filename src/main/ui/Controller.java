package ui;


import model.*;
import persistence.PlayerReader;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//class controller, it serves to control the windows that the users would see, such as the login window, game window
// and others. Controller control which window users could see.
public class Controller {

    private static final String ACCOUNTS_FILE = "./data/players";


    private PlayerManager playerManager;
    private List<Player> players;


    boolean isGoing;

    JFrame mainFrame;





    // The controller class is used mainly for control which window to use, it start by setting up a login&register
    // window.
    public Controller() {
        initial();
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public String getAccountsFile() {
        return ACCOUNTS_FILE;
    }

    //MODIFIES: this
    //EFFECTS: Initialize all the variables and setup a login window to start this application.
    public void initial() {

        loadAccounts();
        playerManager = new PlayerManager(players);


        mainFrame = new JFrame();
        //mainFrame.setSize(1000, 800);
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);


        loadAccounts();
        playerManager = new PlayerManager(players);



        gotoLogin();

    }

    //MODIFIES: this
    //EFFECTS: Load all acounts from the file
    public void loadAccounts() {
        try {
            players = PlayerReader.readAccounts(new File(ACCOUNTS_FILE));
        } catch (IOException e) {
            System.out.println("there is no account");
        }
    }

    //MODIFIES: this
    //EFFECT: set current player in playerManager to null. Go to login window.
    public void gotoLogin() {
        playerManager.setCurrentPlayerNull();
        LoginPanel loginPanel = new LoginPanel(this);
        mainFrame.add(loginPanel);
        mainFrame.setContentPane(loginPanel);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null); //this need to be call after pack()
        loginPanel.requestFocusInWindow();
    }

    //MODIFIES: this
    //EFFECT: go to Activity window.
    public void gotoActivity() {
        ActivityPanel activityPanel = new ActivityPanel(this);
        mainFrame.add(activityPanel);
        mainFrame.setContentPane(activityPanel);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
    }

    //MODIFIES: this
    //EFFECT: go to game window, which will start the game.
    public void goGamePanel() throws IOException {

        isGoing = false;
        EventQueue.invokeLater(() -> {
            FlappyBunnySwing gamePanel = null;
            try {
                gamePanel = new FlappyBunnySwing(mainFrame, this);
//                gmc = gamePanel.getGameModelController();  //get gmc here
            } catch (IOException e) {
                e.printStackTrace();
            }
            mainFrame.add(gamePanel);
            mainFrame.setContentPane(gamePanel);
            mainFrame.pack();
            mainFrame.setLocationRelativeTo(null);

        });
    }



//    public void runGame() throws IOException {
//        isGoing = true;
//        String command = null;
//
//        while (isGoing) {
//            displayMenu();
//            command = input.next();
//            command = command.toLowerCase();
//
//            if (command.equals("q")) {
//                isGoing = false;
//                mainFrame.dispose();
//            } else {
//                processCommand(command);
//            }
//        }
//
//        //System.out.println("\nGoodbye!");
//        // put initial() here to start another round?
//    }

//    public void displayMenu() {
//        System.out.println("\nSelect from:");
//        System.out.println("\tl -> login");
//        System.out.println("\tr -> register");
//        System.out.println("\tv -> view account");
//        System.out.println("\tq -> quit");
//    }
//
//    // MODIFIES: this
//    // EFFECTS: processes user command
//    private void processCommand(String command) throws IOException {
//        if (command.equals("l")) {
//            login();
//        } else if (command.equals("r")) {
//            register();
//        } else if (command.equals("v")) {
//            viewPlayers();
//        } else {
//            System.out.println("Selection not valid...");
//        }
//    }

//    public void viewPlayers() {
//        playerManager.sortPlayers();
//        for (Player player : players) {
//            System.out.println(player.toString());
//        }
//    }

//    //MODIFIES: this
//    //EFFECTS: Asking the user to login or register, will run again if the use does not choose
//    public void loginOrRigister() {
//        //while (isrun)
//        System.out.println("Do you want to Login or Register, type login or register");
//        String lore = input.next();
//        if (lore.equals("login")) {
//            login();
//        } else if (lore.equals("register")) {
//            register();
//        } else {
//            System.out.println("please try again");
//            loginOrRigister();
//        }
//    }

    //MODIFIES: this
    //EFFECTS: register the user, set the user as current player.will run again if the use does not choose
    // the register username must not be already in the file
//    public void register() throws IOException {
//        String usernameInput;
//        String passwordInput;
//
//        System.out.println("Please type your username: ");
//        usernameInput = input.next();
//        System.out.println("Please type your password: ");
//        passwordInput = input.next();
//
//        if (playerManager.creatAccount(usernameInput,passwordInput)) {
//            System.out.println("Register successful!!");
//            System.out.println("The new account is " + usernameInput);
//            System.out.println();
//            System.out.println();
//            settingForGame();
//        } else {
//            System.out.println("Please try again");
//            register();
//        }
//    }

    //MODIFIES: this
    //EFFECTS: ask to login, user has to provide username and password that match in the file
//    public void login() throws IOException {
//        String usernameInput;
//        String passwordInput;
//
//        System.out.println("Please type your username: ");
//        usernameInput = input.next();
//        System.out.println("Please type your password: ");
//        passwordInput = input.next();
//        if (playerManager.matchUserAndPassword(usernameInput,passwordInput)) {
//            System.out.println("Logging successful!!");
//            System.out.println("Login account is " + usernameInput);
//            System.out.println();
//            System.out.println();
//            settingForGame();
//        } else {
//            System.out.println("incorrect username and password, please try again");
//            login();
//        }
//    }

    //MODIFIES: this
    //EFFECTS: setting up the game, ready to start the game
//    public void settingForGame() throws IOException {
//        boolean ischoose = true;
//        System.out.println("Hi there!");
//        System.out.println("Welcome to the FlappyBunny, this is the fun game you would like to play");
//        System.out.println("Do you lke brown bunny or purple bunny, type \"purple\" or \"brown\" ");
//        while (ischoose) {
//            String inputState = input.next();
//            if (inputState.equals("purple")) {
//                bunnyState = "purple";
//                ischoose = false;
//            } else if (inputState.equals("brown")) {
//                bunnyState = "Brown";
//                ischoose = false;
//            } else {
//                System.out.println("These is no such options, please choose again");
//            }
//        }
//        System.out.println("you choose " + bunnyState + "Bunny, type \"go\" to start the game!");
//        String something = input.next();
//        if (something.equals("go")) {
//            //startGame();
//            goGamePanel();
//        } else {
//            settingForGame();
//        }
//    }

//    public void startGame() throws IOException {
//        basicInfo();
//        while (!gmc.isGameOver) {
//            boolean notChoose = true;
//            gmc.update(5.0);
//            gmc.check();
//            print();
//            while (notChoose) {
//                System.out.println("do you want to jump or keep going? j for jump, k for keep going");
//                String move = input.next();
//                if (move.equals("j")) {
//                    System.out.println("jumping!");
//                    gmc.getBunny().burstFly();
//                    notChoose = false;
//                } else if (move.equals("k")) {
//                    System.out.println("keep going!");
//                    notChoose = false;
//                } else {
//                    System.out.println("you do not choose ... ");
//                }
//            }
//        }
//        saveGame();
//    }

//    public void basicInfo() {
//        System.out.println();
//        System.out.println("Notice that the bunny is 50 meters tall (I know the game should call gigantic bunny),");
//        System.out.println("so it would always be 50 meters height, the cactus that you should avoid is");
//        System.out.println("60 meters height, the gravity on bunny is 800 and each jump would give the bunny");
//        System.out.println("500 velocity up to the sky, do you calculate to save the \"little\" bunny!");
//    }



    //MODIFIES:this
    //EFFECT: save the current information for the reader, write into the file ACCOUNTS_FILE
//    public void saveGame() throws IOException {
//        System.out.println("Your bunny died....");
//        if (gmc.isGameOver) {
//            Double currentRecord = gmc.getPastTime();
//            System.out.println("but you play great!!!");
//            System.out.println("Do you want to save your record? ");
//            System.out.println("your current record is" + currentRecord + ", yes or no");
//            String saveornot = input.next();
//            if (saveornot.equals("yes")) {
//                try {
//                    playerManager.saveRecord(currentRecord, ACCOUNTS_FILE);
//                    System.out.println("see you next time");
//                    mainFrame.dispose();
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//            } else {
//                System.out.println("see you next time");
//                mainFrame.dispose();
//            }
//        }
//    }





//    public void print() {
//        printBunnySituation();
//        for (Cactus cactus : cactusList) {
//            printCactusSituation(cactus);
//        }
//        if (bunny.getHp() < currentHP) {
//            System.out.println("the bunny get hurt!!!");
//            currentHP = bunny.getHp();
//        }
//    }
//
//    public void printBunnySituation() {
//        System.out.println();
//        System.out.println("Bunny currently has " + bunny.getHp() + "HP");
//        String awayGround = String.valueOf(800 - bunny.getPositionY());
//        System.out.println("the bunny is currently " + awayGround + "meters away from ground");
//        System.out.println("the speed of the bunny is 100 m/s");
//    }
//
//    public void printCactusSituation(Cactus cactus) {
//        System.out.println();
//        String meterAway = String.valueOf(cactus.getPositionX() - 50);
//        System.out.println("One cactus is " + meterAway + " meters away");
//        String awayGround = String.valueOf(800 - cactus.getPositionY());
//        System.out.println("it is " + awayGround + "meters from ground");
//    }

    //EFFECTS: start the game, create game panel and set is as contentpane




    // ------------------------------------------------ GUI -------------------------------------------------------
    // ------------------------------------------------ GUI -------------------------------------------------------
    // NOTE: not use in phase one except the game scene: gotoFlapplyBirdScene

//    public void startLoginScene() throws IOException {
//        Parent root = FXMLLoader.load(getClass().getResource("../resources/Login.fxml"));
//        Scene scene = new Scene(root);
//        theStage.setScene(scene);
//        theStage.show();
//    }
//
//    //effect: Check if the username and password is correct and log in
//    public void loginAndGotoMain() throws IOException {
//        if (userName.getText().equals("user") && password.getText().equals("pass")) {
//            status.setText("login success");
//            //((Node)event.getSource()).getScene().getWindow().hide();
//            status.getScene().getWindow().hide();
//            Parent root = FXMLLoader.load(getClass().getResource("../resources/Main.fxml"));
//            Scene mainScene = new Scene(root);
//            theStage.setScene(mainScene);
//            theStage.show();
//        } else {
//            status.setVisible(true);
//        }
//    }
//
//
//    //MODIFIES: this
//    //EFFECT: create flappybird scene and set this scene, which is the game scene and start the game.
//    public void gotoFlappyBirdScene() {
//        flappyBunny = new FlappyBunny(this);
//        Scene flappyBirdScene = flappyBunny.getScene();
//        theStage.setScene(flappyBirdScene);
//        theStage.show();
//        theStage.setResizable(false);
//        //mainScene.getScene().getWindow().hide();     //hide this for now
//    }
//
//    //effect: make the currect window hide and go back to the login window
//    public void logout() throws IOException {
//        Parent root = FXMLLoader.load(getClass().getResource("../resources/Login.fxml"));
//        Scene scene = new Scene(root);
//        mainScene.getScene().getWindow().hide();
//        theStage.setScene(scene);
//        theStage.show();
//    }
}
