package gui;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

import static javafx.animation.Animation.Status.RUNNING;

public class SuccessPane extends StackPane {
    private ImageView successImage = new ImageView();
    private Label lbSuccess;
    private LoadingImagview loadingImagview = new LoadingImagview();

    public SuccessPane() {
        getChildren().add(successImage);
    }

    // 创建过程
    public void waitingCreate() {
        successImage.setFitWidth(600);
        successImage.setFitHeight(400);
        successImage.setImage(new Image("/images/gif/IMG00001.bmp"));
//        getChildren().clear();
//        loadingImagview.createLoadingAnimation();
//        getChildren().add(loadingImagview);
    }

    // 返回
    public ImageView getSuccessImage () {
        return successImage;
    }

    public Timeline getLoadAnimation() {
        return loadingImagview.getloadingAnimation();
    }

    // 创建完成
    public void finishCreate() {
        getChildren().clear();
        successImage.setFitWidth(200);
        successImage.setFitHeight(200);
        successImage.setImage(new Image("/images/success.png"));
        lbSuccess = new Label("SUCCESS", successImage);
        lbSuccess.setContentDisplay(ContentDisplay.TOP);
        lbSuccess.setTextFill(Color.GREEN);
        getChildren().add(lbSuccess);
    }

    public void setLbSuccess (String content) {
        lbSuccess.setText(content);
    }
}
