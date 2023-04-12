package com.example.questifyit;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

public class CreateQuestViewController extends BaseController {

    @FXML
    private TextField answerTextField;

    @FXML
    private Label balanceLabel;

    @FXML
    private Button createQuestButton;

    @FXML
    private TextArea questDescriptionTextField;

    @FXML
    private Button returnButton;

    @FXML
    private TextField rewardTextField;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label warnLabel;

    @FXML
    private void initialize(){
        warnLabel.setFont(Font.font(16));
        questDescriptionTextField.setWrapText(true);
        warnLabel.setWrapText(true);
        rootPane.setOpacity(0);
        makeFadeIn(rootPane,1);
    }

    @FXML
    private void returnButtonPressed(){
        makeFadeOutTransition(rootPane,"create-quest-view.fxml","main-scene.fxml");
    }

    @FXML
    private void createQuestButtonPressed(){
        try{
            service.addQuest(questDescriptionTextField.getText(),answerTextField.getText(),getLoggedUser(),rewardTextField.getText());
            warnLabel.setText("Quest created successfully!");
        }catch (Exception ex){
            warnLabel.setText(ex.getMessage());
        }
    }
}
