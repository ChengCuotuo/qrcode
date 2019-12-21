package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TestAnimation extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        StackPane pane = new StackPane();
        LoadingImagview imagview = new LoadingImagview();
        imagview.createLoadingAnimation();
        imagview.getloadingAnimation().play();
        pane.getChildren().add(imagview);
        Scene scene = new Scene(pane, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
