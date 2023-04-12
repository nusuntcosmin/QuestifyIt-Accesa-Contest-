package com.example.questifyit;

import com.example.questifyit.domain.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class TopRankingSceneController extends BaseController {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button returnButton;

    @FXML
    private ListView<String> usersListView;

    @FXML
    void returnButtonPressed() {
        makeFadeOutTransition(rootPane,"top-scene.fxml","main-scene.fxml");
    }

    @FXML
    private void initialize(){

        rootPane.setOpacity(0);
        makeFadeIn(rootPane);
        reloadTopList();
    }

    private void reloadTopList() {
        ArrayList<User> userArrayList = (ArrayList<User>) service.findAllUsers();
        ArrayList<String> userStringArrayList = userArrayList.stream().map(User::toString).collect(Collectors.toCollection(ArrayList::new));

        for (int i = 0; i < userStringArrayList.size(); ++i) {
            String prefix = "";
            switch ((i + 1) % 10) {
                case 1:
                    prefix = "st. ";
                    break;
                case 2:
                    prefix = "nd. ";
                    break;
                case 3:
                    prefix = "rd.";
                    break;
                default:
                    prefix = "th. ";
            }
            userStringArrayList.set(i, (i + 1) + prefix + userStringArrayList.get(i));
        }
        usersListView.getItems().setAll(userStringArrayList);
    }
}
