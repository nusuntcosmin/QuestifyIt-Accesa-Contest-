package com.example.questifyit;

import com.example.questifyit.domain.Badge;
import com.example.questifyit.domain.UserBadges;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
        for(UserBadges userBadge : service.getBadgesForUser(getLoggedUser().getUserId())){
            VBox imageVbox = new VBox();
            Badge badge = service.findOneBadge(userBadge.getBadge().getBadgeId());
            imageVbox.setAlignment(Pos.CENTER);
            Text textForBadge = new Text(badge.getBadgeName());
            textForBadge.setFont(Font.font("Rockwell",13));

            if(userBadge.getAchieved().equals(true)){
                textForBadge.setFill(Color.GREEN);
                textForBadge.setFont(Font.font("Rockwell", FontWeight.BOLD,13));
            }


            ImageView badgetoAdd = new ImageView(Objects.requireNonNull(
                    getClass().
                            getResource(badge.getBadgePhotoResourcePath())).toString());


            badgetoAdd.setFitHeight(130);
            badgetoAdd.setFitWidth(130);
            imageVbox.setMaxWidth(140);
            imageVbox.setMaxHeight(140);
            imageVbox.getChildren().add(badgetoAdd);
            imageVbox.getChildren().add(textForBadge);

            scrollFlowPane.getChildren().add(imageVbox);
        }
        /*
        for(Badge badge : service.findAllBadge()){
            VBox imageVbox = new VBox();
            imageVbox.setAlignment(Pos.CENTER);
            Text textForBadge = new Text(badge.getBadgeName());
            textForBadge.setFont(Font.font(14));


            ImageView badgetoAdd = new ImageView(Objects.requireNonNull(
                    getClass().
                            getResource(badge.getBadgePhotoResourcePath())).toString());


            badgetoAdd.setFitHeight(130);
            badgetoAdd.setFitWidth(130);
            imageVbox.setMaxWidth(140);
            imageVbox.setMaxHeight(140);
            imageVbox.getChildren().add(badgetoAdd);
            imageVbox.getChildren().add(textForBadge);

            scrollFlowPane.getChildren().add(imageVbox);
        }

         */

    }
    @FXML
    private void returnButtonClicked() {
        makeFadeOutTransition(rootPane,"badges-view.fxml","main-scene.fxml",1);
    }
}
