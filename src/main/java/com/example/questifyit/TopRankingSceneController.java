package com.example.questifyit;

import com.example.questifyit.domain.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    private void reloadTopList(){
        ArrayList<User> userArrayList = (ArrayList<User>) service.findAllUsers();
        ArrayList<String> userStringArrayList= userArrayList.stream().map(User::toString).collect(Collectors.toCollection(ArrayList::new));
        usersListView.getItems().setAll(userStringArrayList);

    }
}
