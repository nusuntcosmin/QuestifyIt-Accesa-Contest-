package com.example.questifyit;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class RegisterViewController extends BaseController {
    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private HBox confirmPasswordHBox;

    @FXML
    private TextField usernameField;

    @FXML
    private HBox usernameHBox;
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
    private Label warnLabel;

    @FXML
    void returnButtonPressed() {
        super.makeFadeOutTransition(rootPane,"register-view.fxml","sign-in-view.fxml");
    }

    @FXML
    private void initialize(){
        
        warnLabel.setWrapText(true);
        super.makeFadeIn(rootPane);
        rootPane.setOpacity(0);
        confirmPasswordHBox.setAlignment(Pos.CENTER);
        passwordHBox.setAlignment(Pos.CENTER);
        emailHBox.setAlignment(Pos.CENTER);
        usernameHBox.setAlignment(Pos.CENTER);
    }
    @FXML
    void signUpButtonClicked() {
        try{
            service.registerUser(passwordField.getText(),confirmPasswordField.getText(),emailField.getText(),usernameField.getText());
            warnLabel.setText("Account created with success, go back to the login page.");
        }catch (Exception ex){
            warnLabel.setText(ex.getMessage());
        }
    }
}
