package ui;


import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    Controller controller = new Controller();

    @Override
    //this method is require by javafx library to start my game.
    public void start(Stage theStage) throws Exception {
        //controller.startLoginScene(); this one is GUI
        //((Node)event.getSource()).getScene().getWindow().hide();
        controller.loginOrRigister();   //this one is console-based
    }

    // start the game !!!!!!!!!
    public static void main(String[] args) {
        launch(args);
    }
}
