package com.example.questifyit;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage= stage;
        primaryStage.setResizable(false);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("intro-transition-view.fxml"));


        Scene scene = new Scene(fxmlLoader.load(), 648, 402);
        fxmlLoader.<BaseController>getController().setPrimaryStage(primaryStage);
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("questifyit-symbol-removebg-preview-removebg-preview-removebg-preview-removebg-preview.png"))));
        primaryStage.setTitle("QuestifyIt");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
