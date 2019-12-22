package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TestAnimation extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        SuccessPane pane = new SuccessPane();
        pane.waitingCreate();
        Scene scene = new Scene(pane, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
