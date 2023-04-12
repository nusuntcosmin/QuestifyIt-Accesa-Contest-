package com.example.questifyit;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ProfileViewController extends BaseController {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private PasswordField confirmPasswordTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private Label warnLabel;
    @FXML
    private TextField nameTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Label questsLabel;

    @FXML
    private Button returnButtonPressed;

    @FXML
    private Label tokensLabel;

    @FXML
    private Button updateButton;

    @FXML
    void returnButtonPressed() {
        makeFadeOutTransition(rootPane,"profile-view.fxml","main-scene.fxml");
    }

    @FXML
    void updateButtonPressed() {
        try{
            service.updateUser(getLoggedUser().getUserId(), nameTextField.getText(), emailTextField.getText(),passwordTextField.getText(),confirmPasswordTextField.getText());
            warnLabel.setText("Data updated");
        }catch (Exception e){
            warnLabel.setText(e.getMessage());
        }

    }

    @FXML
    private void initialize(){
        rootPane.setOpacity(0);
        makeFadeIn(rootPane);

        questsLabel.setText("Quests solved : " + Integer.toString(getLoggedUser().getNumberOfQuestsSolved()));
        tokensLabel.setText("Tokens solved : " + Integer.toString(getLoggedUser().getNumberOfTokens()));

    }
}
