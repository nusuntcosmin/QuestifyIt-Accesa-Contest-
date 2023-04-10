package com.example.questifyit;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.util.Objects;

public class BaseController {

    private static Stage primaryStage;
    private Duration transitionDuration;
    private final FadeTransition fadeInTransition = new FadeTransition();

    protected void makeFadeIn(Pane rootPane){
            fadeInTransition.setDuration(Duration.seconds(1));
            fadeInTransition.setNode(rootPane);
            fadeInTransition.setFromValue(0);
            fadeInTransition.setToValue(1);
            fadeInTransition.setCycleCount(1);
            fadeInTransition.play();
    }
    //Overload
    protected void makeFadeIn(Pane rootPane, float transitionTime){
        transitionDuration = Duration.seconds(transitionTime);
        makeFadeIn(rootPane);
    }
    protected void makeFadeOutTransition(Pane rootPane, String fxmlCurrentScene, String fxmlTransitionToScene){
        try {
            AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlCurrentScene)));
            rootPane.getChildren().setAll(pane);
            FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(1),pane);
            fadeOutTransition.setFromValue(1);
            fadeOutTransition.setToValue(0);

            fadeOutTransition.setOnFinished(event -> changeScene(fxmlTransitionToScene));

            fadeOutTransition.play();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //Overload
    protected void makeFadeOutTransition(Pane rootPane, String fxmlCurrentScene, String fxmlTransitionToScene, float transitionTime){

        try {
            AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlCurrentScene)));
            rootPane.getChildren().setAll(pane);
            transitionDuration = Duration.seconds(transitionTime);
            FadeTransition fadeOutTransition = new FadeTransition(transitionDuration,pane);
            fadeOutTransition.setFromValue(1);
            fadeOutTransition.setToValue(0);

            fadeOutTransition.setOnFinished(event -> changeScene(fxmlTransitionToScene));

            fadeOutTransition.play();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setPrimaryStage(Stage primaryStage) {
        BaseController.primaryStage = primaryStage;
    }

    protected void changeScene(String fxmlFileName){
        try{
            Parent pane = FXMLLoader.load(Objects.requireNonNull(BaseController.class.getResource(fxmlFileName)));
            String cssFilename = fxmlFileName.replaceAll(".fxml",".css");
            primaryStage.getScene().getStylesheets().clear();
            primaryStage.getScene().getStylesheets().add(Objects.requireNonNull(BaseController.class.getResource(cssFilename)).toExternalForm());
            primaryStage.getScene().setRoot(pane);
        }catch (IOException ioException) {
            System.err.println("Error changing scene");
        }
    }

}
