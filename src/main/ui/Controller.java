package ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;


public class Controller {


    Practise practise;
    FlappyBird flappyBird;
    Stage theStage;

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
    }

    public void startLoginScene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../resources/Login.fxml"));
        Scene scene = new Scene(root);
        theStage.setScene(scene);
        theStage.show();
    }

    //effect: Check if the username and password is correct and log in
    public void login() throws IOException {
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
    public void gotoPractiseScene() {
        practise = new Practise();
        Scene practiseScene = practise.getScene();
        Stage practiseStage = new Stage();
        //practiseStage.initModality(Modality.WINDOW_MODAL);
        practiseStage.setScene(practiseScene);
        practiseStage.show();
        mainScene.getScene().getWindow().hide();
    }

    public void gotoFlappyBirdScene() {
        flappyBird = new FlappyBird();
        Scene flappyBirdScene = flappyBird.getScene();
        theStage.setScene(flappyBirdScene);
        theStage.show();
        theStage.setResizable(false);
        mainScene.getScene().getWindow().hide();
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
