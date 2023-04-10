package com.example.questifyit;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class SignInViewController extends BaseController {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signInButton;

    @FXML
    private HBox passwordHBox;

    @FXML
    private TextField emailField;

    @FXML
    private HBox emailHBox;

    @FXML
    private void signInButtonClicked(){
        makeFadeOutTransition(rootPane,"sign-in-view.fxml","main-scene.fxml");
    }
    private void styleTextFields(){
        passwordHBox.setAlignment(Pos.CENTER);
        emailHBox.setAlignment(Pos.CENTER);
    }


    @FXML
    public void initialize(){
        styleTextFields();
        rootPane.setOpacity(0);
        super.makeFadeIn(rootPane,1);

    }

    @FXML
    void registerLinkPressed() { super.makeFadeOutTransition(rootPane,"sign-in-view.fxml","register-view.fxml");
    }


}
