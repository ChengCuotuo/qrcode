package gui;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class LoadingImagview extends ImageView {
    private Timeline loadingAnimation;
    private int imageIndex = 0;

    // 创建loading 动画
    public void createLoadingAnimation() {
        setFitWidth(380);
        setFitHeight(380);
        // 创建待加入的图片
        List<Image> imageList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            imageList.add(new Image("/images/gif/IMG0000" + i + ".bmp"));
        }

        for (int i = 10; i < 48; i++) {
            imageList.add(new Image("/images/gif/IMG000" + i + ".bmp"));
        }
        int size = imageList.size();
        EventHandler<ActionEvent> eventEventHandler = e -> {
            setImage(imageList.get(imageIndex++));
            if (imageIndex == size - 1) {
                imageIndex = 0;
            }
        };

        loadingAnimation = new Timeline(new KeyFrame(Duration.millis(80), eventEventHandler));
        //动画执行的次数是无限次
        loadingAnimation.setCycleCount(Timeline.INDEFINITE);
    }

    public Timeline getloadingAnimation() {
        return loadingAnimation;
    }
}
