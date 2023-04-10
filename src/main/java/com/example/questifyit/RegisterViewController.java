package com.example.questifyit;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class RegisterViewController extends BaseController {
    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private HBox confirmPasswordHBox;

    @FXML
    private TextField emailField;

    @FXML
    private HBox emailHBox;

    @FXML
    private PasswordField passwordField;

    @FXML
    private HBox passwordHBox;

    @FXML
    private Button returnButton;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Button signUpButton;

    @FXML
    void returnButtonPressed() {
        super.makeFadeOutTransition(rootPane,"register-view.fxml","sign-in-view.fxml");
    }


    @FXML
    private void initialize(){
        super.makeFadeIn(rootPane);
        rootPane.setOpacity(0);
        confirmPasswordHBox.setAlignment(Pos.CENTER);
        passwordHBox.setAlignment(Pos.CENTER);
        emailHBox.setAlignment(Pos.CENTER);
    }
    @FXML
    void signUpButtonClicked() {

    }
}
