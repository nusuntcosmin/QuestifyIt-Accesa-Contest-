package com.example.questifyit;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Objects;

public class BadgesViewController extends BaseController {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private ScrollPane mainScrollPane;

    @FXML
    private FlowPane scrollFlowPane;

    @FXML
    private void initialize(){
        mainScrollPane.setFitToWidth(true);
        mainScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        rootPane.setOpacity(0);

        makeFadeIn(rootPane,1);
        addBadges();

    }

    private void addBadges(){
        for(int i = 0 ;i<=10;++i){
            VBox imageVbox = new VBox();
            imageVbox.setAlignment(Pos.CENTER);
            Text textForBadge1 = new Text("Test");
            String st = "1000-tokens.png";
            ImageView badge1toAdd = new ImageView(Objects.requireNonNull(getClass().getResource(st)).toString());
            badge1toAdd.setFitHeight(110);
            badge1toAdd.setFitWidth(110);
            imageVbox.setMaxWidth(110);
            imageVbox.setMaxHeight(110);
            imageVbox.getChildren().add(badge1toAdd);
            imageVbox.getChildren().add(textForBadge1);

            scrollFlowPane.getChildren().add(imageVbox);
        }


    }
    @FXML
    private void returnButtonClicked() {
        makeFadeOutTransition(rootPane,"badges-view.fxml","main-scene.fxml",1);
    }
}
