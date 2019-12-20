package gui;

import javafx.application.Application;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class Main extends Application{
    private StackPane mainPane = new StackPane();
    @Override
    public void start(Stage primaryStage) {
        SinglePane sp = new SinglePane();
        mainPane.getChildren().add(sp);
        Scene scene = new Scene(mainPane, 530, 360);
        primaryStage.setResizable(false);
        primaryStage.setTitle("QRCode_NianZuochen");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
