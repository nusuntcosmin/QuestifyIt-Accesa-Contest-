package com.example.questifyit;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

public class IntroTransitionController extends BaseController {

    @FXML
    private AnchorPane rootPane;
    private void transitionEffect(){
        try {
            AnchorPane pane =FXMLLoader.load(Objects.requireNonNull(getClass().getResource("intro-view.fxml")));
            rootPane.getChildren().setAll(pane);

            FadeTransition fadeInTransition = new FadeTransition(Duration.seconds(2),pane);
            fadeInTransition.setFromValue(0);
            fadeInTransition.setToValue(1);
            fadeInTransition.setCycleCount(1);

            fadeInTransition.play();

            FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(2),pane);
            fadeOutTransition.setFromValue(1);
            fadeOutTransition.setToValue(0);
            fadeOutTransition.setCycleCount(1);

            fadeInTransition.setOnFinished(e-> fadeOutTransition.play());

            fadeOutTransition.setOnFinished(e-> super.changeScene("sign-in-view.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void initialize(){
        transitionEffect();
    }


}
