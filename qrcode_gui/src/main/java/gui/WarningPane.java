package gui;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class WarningPane extends StackPane {
    private ImageView warningImage = new ImageView(new Image("/images/warning.png"));
    private Label lbWarning;

    public WarningPane() {
        warningImage.setFitWidth(200);
        warningImage.setFitHeight(200);
        lbWarning = new Label("WARNING", warningImage);
        lbWarning.setContentDisplay(ContentDisplay.TOP);
        lbWarning.setTextFill(Color.YELLOW);
        getChildren().add(lbWarning);
    }

    public void setLbWarning (String content) {
        lbWarning.setText(content);
    }
}
