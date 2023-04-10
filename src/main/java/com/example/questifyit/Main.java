package com.example.questifyit;

import com.example.questifyit.repository.database.factory.RepositoryDbFactory;
import com.example.questifyit.repository.interfaces.IBadgeRepository;
import com.example.questifyit.repository.interfaces.IRepository;
import com.example.questifyit.repository.interfaces.IUserBadgesRepository;
import com.example.questifyit.repository.interfaces.IUserRepository;
import com.example.questifyit.service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileReader;
import java.util.Objects;
import java.util.Properties;

public class Main extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage= stage;
        primaryStage.setResizable(false);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("intro-transition-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 648, 402);

        IBadgeRepository badgeRepository = (IBadgeRepository) RepositoryDbFactory.getInstanceOfRepositoryDbFactory().getBadgeRepository();
        IUserRepository userRepository = (IUserRepository) RepositoryDbFactory.getInstanceOfRepositoryDbFactory().getUserRepository();
        IUserBadgesRepository userBadgesRepository =(IUserBadgesRepository) RepositoryDbFactory.getInstanceOfRepositoryDbFactory().getUserBadgesRepository();
        Service service = new Service(userRepository,badgeRepository,null,userBadgesRepository);
        fxmlLoader.<BaseController>getController().setPrimaryStage(primaryStage);
        fxmlLoader.<BaseController>getController().setService(service);
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("questifyit-symbol-removebg-preview-removebg-preview-removebg-preview-removebg-preview.png"))));
        primaryStage.setTitle("QuestifyIt");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
