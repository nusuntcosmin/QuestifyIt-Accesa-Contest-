package com.example.questifyit;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Ellipse;
import javafx.util.Duration;

public class MainViewController extends BaseController {

    private static boolean isMenuShowed;

    @FXML
    private AnchorPane rootPane;


    @FXML
    private ScrollPane mainScrollPane;
    @FXML
    private ImageView backgroundImage;

    @FXML
    private ImageView logoImage;
    @FXML
    private Button menuButton;

    @FXML
    private Button badgesButton;

    @FXML
    private VBox menu;

    @FXML
    private Button profileButton;


    @FXML
    private Button rankingButton;

    @FXML
    private Ellipse ellipse1;

    @FXML
    private Ellipse ellipse2;

    @FXML
    private Ellipse ellipse3;

    @FXML
    private Ellipse ellipse4;

    @FXML
    private Ellipse ellipse5;

    @FXML
    private TranslateTransition slideTransition;

    private void initializeSlidingMenu(){
        isMenuShowed = false;
        menu.setTranslateX(200);
        menu.setSpacing(20);
        slideTransition = new TranslateTransition(Duration.seconds(0.4),menu);
        slideTransition.setFromX(200);
        slideTransition.setToX(10);

        menu.setAlignment(Pos.TOP_CENTER);
    }

    @FXML
    private void initialize(){
        rootPane.setOpacity(0);
        initializeSlidingMenu();
        super.makeFadeIn(rootPane);
    }

    private void blurWidgets(){
        backgroundImage.setEffect(new GaussianBlur());
        logoImage.setEffect(new GaussianBlur());
        ellipse1.setEffect(new GaussianBlur());
        ellipse2.setEffect(new GaussianBlur());
        ellipse3.setEffect(new GaussianBlur());
        ellipse4.setEffect(new GaussianBlur());
        ellipse5.setEffect(new GaussianBlur());
        mainScrollPane.setEffect(new GaussianBlur());
    }

    private void unblurWidgets(){
        backgroundImage.setEffect(null);
        logoImage.setEffect(null);
        ellipse1.setEffect(null);
        ellipse2.setEffect(null);
        ellipse3.setEffect(null);
        ellipse4.setEffect(null);
        ellipse5.setEffect(null);
        mainScrollPane.setEffect(null);
    }
    private void slideMenu(){
        slideTransition.setRate(1);
        slideTransition.play();

    }

    private void slideMenuBack(){
        slideTransition.setRate(-1);
        slideTransition.play();
    }

    @FXML
    void menuButtonClicked() {
        if(isMenuShowed){
            unblurWidgets();
            isMenuShowed = false;
            slideMenuBack();
        }
        else{
            blurWidgets();
            isMenuShowed = true;
            slideMenu();
        }
    }
    @FXML
    void badgesButtonPressed() {
        makeFadeOutTransition(rootPane,"main-scene.fxml","badges-view.fxml",1);
    }

    @FXML
    void profileButtonPressed() {

    }

    @FXML
    void rankingButtonPressed() {

    }

    @FXML
    private void logOutButtonPressed() {
        makeFadeOutTransition(rootPane,"main-scene.fxml","sign-in-view.fxml");
    }

}
