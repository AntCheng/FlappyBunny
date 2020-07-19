package ui;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    Controller controller = new Controller();

    @Override
    public void start(Stage theStage) throws Exception {
        controller.startLoginScene();
        //((Node)event.getSource()).getScene().getWindow().hide();

    }


    public static void main(String[] args) {

        launch(args);
    }
}
