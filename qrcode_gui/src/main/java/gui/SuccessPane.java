package gui;


import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class SuccessPane extends StackPane {
    private ImageView successImage = new ImageView(new Image("/images/success.png"));
    private Label lbSuccess;

    public SuccessPane() {
        successImage.setFitWidth(200);
        successImage.setFitHeight(200);
        lbSuccess = new Label("SUCCESS", successImage);
        lbSuccess.setContentDisplay(ContentDisplay.TOP);
        lbSuccess.setTextFill(Color.GREEN);
        getChildren().add(lbSuccess);
    }

    public void setLbSuccess (String content) {
        lbSuccess.setText(content);
    }
}
