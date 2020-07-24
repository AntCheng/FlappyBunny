package ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Player;
import model.PlayerManager;
import system.PlayerReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Scanner;


public class Controller {

    private static final String ACCOUNTS_FILE = "./data/players";

    String usernameC = "username";
    String passwordC = "pass";
    String bunnyState;
    boolean isrun;
    private PlayerManager playerManager;
    private List<Player> players;


    FlappyBird flappyBird;
    Stage theStage;
    private Scanner input;

    @FXML
    private TextField userName;

    @FXML
    private TextField password;

    @FXML
    private Label status;

    @FXML
    private AnchorPane mainScene;


    public Controller() {
        theStage = new Stage();
        loadAccounts();
        playerManager = new PlayerManager(players);
        input = new Scanner(System.in);
    }

    public void loginOrRigister() {
        //while (isrun)
        System.out.println("Do you want to Login or Register, type login or register");
        String lore = input.next();
        if (lore.equals("login")) {
            login();
        } else if (lore.equals("register")) {
            register();
        } else {
            loginOrRigister();
        }
    }

    public void register() {
        String usernameInput;
        String passwordInput;

        System.out.println("Please type your username: ");
        usernameInput = input.next();
        System.out.println("Please type your password: ");
        passwordInput = input.next();

        if (playerManager.creatAccount(usernameInput,passwordInput)) {
            System.out.println("Register successful!!");
            System.out.println("The new account is " + usernameInput);
            System.out.println();
            System.out.println();
            settingForGame();
        } else {
            register();
        }
    }


    public void login() {
        String usernameInput;
        String passwordInput;

        System.out.println("Please type your username: ");
        usernameInput = input.next();
        System.out.println("Please type your password: ");
        passwordInput = input.next();
        if (playerManager.matchUserAndPassword(usernameInput,passwordInput)) {
            System.out.println("Logging successful!!");
            System.out.println("Login account is " + usernameInput);
            System.out.println();
            System.out.println();
            settingForGame();
        } else {
            System.out.println("incorrect username and password, please try again");
            login();
        }
    }

    public void settingForGame() {
        boolean ischoose = true;
        System.out.println("Hi there!");
        System.out.println("Welcome to the FlappyBunny, this is the fun game you would like to play");
        System.out.println("Do you lke brown bunny or purple bunny, type \"purple\" or \"brown\" ");
        while (ischoose) {
            String inputState = input.next();
            if (inputState.equals("purple")) {
                bunnyState = "purple";
                ischoose = false;
            } else if (inputState.equals("brown")) {
                bunnyState = "Brown";
                ischoose = false;
            } else {
                System.out.println("These is no such options, please choose again");
            }
        }
        System.out.println("you choose " + bunnyState + "Bunny, type \"go\" to start the game!");
        String something = input.next();
        if (something.equals("go")) {
            gotoFlappyBirdScene();
        }
    }

    public void saveGame() {
        if (flappyBird.getIsGameOver()) {
            Double currentRecord = flappyBird.getTime();
            System.out.println("you play great!!!");
            System.out.println("Do you want to save your record? ");
            System.out.println("your current record is" + currentRecord + ", yes or no");
            String saveornot = input.next();
            if (saveornot.equals("yes")) {
                try {
                    playerManager.saveRecord(currentRecord, ACCOUNTS_FILE);
                    System.out.println("see you next time");
                    theStage.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("see you next time");
                theStage.close();
            }
        }
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



    // ------------------------------------------------ GUI -------------------------------------------------------
    // ------------------------------------------------ GUI -------------------------------------------------------

    public void startLoginScene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../resources/Login.fxml"));
        Scene scene = new Scene(root);
        theStage.setScene(scene);
        theStage.show();
    }

    //effect: Check if the username and password is correct and log in
    public void loginAndGotoMain() throws IOException {
        if (userName.getText().equals("user") && password.getText().equals("pass")) {
            status.setText("login success");
            //((Node)event.getSource()).getScene().getWindow().hide();
            status.getScene().getWindow().hide();
            Parent root = FXMLLoader.load(getClass().getResource("../resources/Main.fxml"));
            Scene mainScene = new Scene(root);
            theStage.setScene(mainScene);
            theStage.show();
        } else {
            status.setVisible(true);
        }
    }

    //effect: start the game, go to the game window, in the game mode practise.
//    public void gotoPractiseScene() {
//        practise = new Practise();
//        Scene practiseScene = practise.getScene();
//        Stage practiseStage = new Stage();
//        //practiseStage.initModality(Modality.WINDOW_MODAL);
//        practiseStage.setScene(practiseScene);
//        practiseStage.show();
//        mainScene.getScene().getWindow().hide();
//    }

    public void gotoFlappyBirdScene() {
        flappyBird = new FlappyBird(this);
        Scene flappyBirdScene = flappyBird.getScene();
        theStage.setScene(flappyBirdScene);
        theStage.show();
        theStage.setResizable(false);
        //mainScene.getScene().getWindow().hide();     //hide this for now
    }

    //effect: make the currect window hide and go back to the login window
    public void logout() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../resources/Login.fxml"));
        Scene scene = new Scene(root);
        mainScene.getScene().getWindow().hide();
        theStage.setScene(scene);
        theStage.show();
    }
}
