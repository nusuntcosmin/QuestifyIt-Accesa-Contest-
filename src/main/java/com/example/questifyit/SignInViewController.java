package com.example.questifyit;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;

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
        try {
            setLoggedUser(service.loginUser(emailField.getText(),passwordField.getText()));
            makeFadeOutTransition(rootPane,"sign-in-view.fxml","main-scene.fxml");
        } catch (Exception e) {
            warnLabel.setText(e.getMessage());
        }
    }
    private void styleTextFields(){
        passwordHBox.setAlignment(Pos.CENTER);
        emailHBox.setAlignment(Pos.CENTER);
    }

    @FXML
    private Label warnLabel;

    @FXML
    public void initialize(){
        warnLabel.setWrapText(true);
        warnLabel.setTextAlignment(TextAlignment.CENTER);
        styleTextFields();
        rootPane.setOpacity(0);
        super.makeFadeIn(rootPane,1);

    }

    @FXML
    void registerLinkPressed() { super.makeFadeOutTransition(rootPane,"sign-in-view.fxml","register-view.fxml");
    }


}
