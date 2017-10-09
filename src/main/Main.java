package main;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sun.reflect.generics.scope.Scope;

public class Main extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainWindow.fxml"));
        Parent root = loader.load();
        MainWindowController controller = loader.getController();
        primaryStage.setTitle("Battery Manager");
        primaryStage.setScene(new Scene(root, 633, 453));
        primaryStage.setOnCloseRequest((WindowEvent windowEvent) ->
                controller.shutdown()
        );
        primaryStage.show();
    }
}
