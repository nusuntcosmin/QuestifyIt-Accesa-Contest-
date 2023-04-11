package com.example.questifyit;

import com.example.questifyit.domain.Quest;
import com.example.questifyit.domain.User;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class MainViewController extends BaseController {

    private static boolean isMenuShowed;

    private static boolean areLoggedUsersQuestShowed;

    @FXML
    private Button questsSwitchButton;
    @FXML
    private AnchorPane rootPane;


    @FXML
    private ScrollPane mainScrollPane;
    @FXML
    private ImageView backgroundImage;

    @FXML
    private ImageView logoImage;
    @FXML
    private Button menuButton;

    @FXML
    private Label warnLabel;

    @FXML
    private Button badgesButton;

    @FXML
    private VBox menu;

    @FXML
    private Button profileButton;

    @FXML
    private Button rankingButton;
    @FXML
    private Button createQuestButton;

    @FXML
    private Ellipse ellipse1;

    @FXML
    private Button solvedQuests;
    @FXML
    private Ellipse ellipse2;

    @FXML
    private Ellipse ellipse3;

    @FXML
    private Ellipse ellipse4;

    @FXML
    private Text questFeedText;


    @FXML
    private Ellipse ellipse5;

    @FXML
    private TranslateTransition slideTransition;

    private void initializeSlidingMenu(){
        questsSwitchButton.setText("Your quests");
        isMenuShowed = false;
        menu.setTranslateX(200);
        menu.setSpacing(20);
        slideTransition = new TranslateTransition(Duration.seconds(0.4),menu);
        slideTransition.setFromX(200);
        slideTransition.setToX(10);

        menu.setAlignment(Pos.TOP_CENTER);
    }

    @FXML
    private void initialize(){
        areLoggedUsersQuestShowed = false;
        warnLabel.setText("");
        Effect logoEffect = new ColorAdjust(0,0,0,0);
        logoImage.setEffect(logoEffect);
        rootPane.setOpacity(0);
        initializeSlidingMenu();
        super.makeFadeIn(rootPane);
        scrollFlowPane.setPadding(new Insets(10, 10, 10,10));
        scrollFlowPane.setVgap(25);
        scrollFlowPane.setHgap(4);
        scrollFlowPane.setMaxWidth(100);

        addQuestsToFeed();
    }

    private void blurWidgets(){
        Effect effect = new GaussianBlur();
        solvedQuests.setEffect(effect);
        questsSwitchButton.setEffect(effect);
        createQuestButton.setEffect(effect);
        questFeedText.setEffect(effect);
        backgroundImage.setEffect(effect);
        logoImage.setEffect(effect);
        ellipse1.setEffect(effect);
        ellipse2.setEffect(effect);
        ellipse3.setEffect(effect);
        ellipse4.setEffect(effect);
        ellipse5.setEffect(effect);
        mainScrollPane.setEffect(effect);
    }

    private void unblurWidgets(){
        questsSwitchButton.setEffect(null);
        solvedQuests.setEffect(null);
        questFeedText.setEffect(null);
        createQuestButton.setEffect(null);
        backgroundImage.setEffect(null);
        logoImage.setEffect(null);
        ellipse1.setEffect(null);
        ellipse2.setEffect(null);
        ellipse3.setEffect(null);
        ellipse4.setEffect(null);
        ellipse5.setEffect(null);
        mainScrollPane.setEffect(null);
    }
    private void slideMenu(){
        slideTransition.setRate(1);
        slideTransition.play();
    }

    private void slideMenuBack(){
        slideTransition.setRate(-1);
        slideTransition.play();
    }

    @FXML
    void menuButtonClicked() {
        if(isMenuShowed){

            unblurWidgets();
            isMenuShowed = false;
            slideMenuBack();
        }
        else{

            blurWidgets();
            isMenuShowed = true;
            slideMenu();
        }
    }
    @FXML
    void badgesButtonPressed() {
        makeFadeOutTransition(rootPane,"main-scene.fxml","badges-view.fxml",1);
    }

    @FXML
    void profileButtonPressed() {
        makeFadeOutTransition(rootPane,"main-scene.fxml","profile-view.fxml");
    }

    @FXML
    void rankingButtonPressed() {
        makeFadeOutTransition(rootPane,"main-scene.fxml","top-scene.fxml");
    }

    @FXML
    void createQuestButtonPressed() {
        makeFadeOutTransition(rootPane,"main-scene.fxml","create-quest-view.fxml");
    }

    @FXML
    void questsSwitchButtonPressed() {
        if(!areLoggedUsersQuestShowed){
            questFeedText.setText("Your quests");
            areLoggedUsersQuestShowed = true;
            addLoggedUsersQuestsToFeed();
            questsSwitchButton.setText("Quest feed");
        }
        else{
            questFeedText.setText("Quest Feed");
            areLoggedUsersQuestShowed = false;
            addQuestsToFeed();
            questsSwitchButton.setText("Your quests");
        }
    }

    @FXML
    private FlowPane scrollFlowPane;

    private void submitAnswerButtonPressed(Quest quest, String givenAnswer) throws Exception {
        service.checkQuestAnswer(quest,getLoggedUser(),givenAnswer);
        warnLabel.setText("You won " + quest.getCreator().getName() +"'s quest !!!");
        addQuestsToFeed();
    }

    private void addLoggedUsersQuestsToFeed(){
        scrollFlowPane.getChildren().clear();
        service.getQuestsForUser(getLoggedUser().getUserId()).forEach(
                quest -> {
                    VBox questVbox = new VBox();
                    questVbox.setPrefWidth(400);

                    String questHeader = getLoggedUser().getName() + " (" +getLoggedUser().getEmail() +") " + "at " + quest.getDate();

                    Text headerText = new Text(questHeader);
                    Text descriptionText = new Text("★ " + quest.getDescription());

                    descriptionText.setStyle("-fx-word-wrap: break-word");
                    TextFlow headerTextFlow = new TextFlow(headerText);
                    TextFlow descriptionTextFlow = new TextFlow(descriptionText);

                    //Style;
                    descriptionText.setFont(Font.font("Rockwell", FontWeight.BLACK, 15));
                    headerText.setFont(Font.font("Rockwell",13));
                    headerText.setFill(Color.GRAY);

                    questVbox.getChildren().add(headerTextFlow);
                    questVbox.getChildren().add(descriptionTextFlow);
                    questVbox.getChildren().add(new Label(""));

                    scrollFlowPane.getChildren().add(questVbox);

                }
        );
    }

    private void addLoggedUsersQuestsToFeed(Iterable<Quest> questArrayList){
        scrollFlowPane.getChildren().clear();
        questArrayList.forEach(
                quest -> {
                    VBox questVbox = new VBox();
                    questVbox.setPrefWidth(400);

                    String questHeader = quest.getCreator().getName() + " (" +quest.getCreator().getEmail() +") " + "at " + quest.getDate();

                    Text headerText = new Text(questHeader);
                    Text descriptionText = new Text("★ " + quest.getDescription());

                    descriptionText.setStyle("-fx-word-wrap: break-word");
                    TextFlow headerTextFlow = new TextFlow(headerText);
                    TextFlow descriptionTextFlow = new TextFlow(descriptionText);

                    //Style;
                    descriptionText.setFont(Font.font("Rockwell", FontWeight.BLACK, 15));
                    headerText.setFont(Font.font("Rockwell",13));
                    headerText.setFill(Color.GRAY);

                    questVbox.getChildren().add(headerTextFlow);
                    questVbox.getChildren().add(descriptionTextFlow);
                    questVbox.getChildren().add(new Label(""));

                    scrollFlowPane.getChildren().add(questVbox);

                }
        );
    }
    private void addQuestsToFeed(){
        scrollFlowPane.getChildren().clear();
        service.getOtherUsersQuests(getLoggedUser().getUserId()).forEach(
                quest -> {

                    User creator = quest.getCreator();

                    VBox questVbox = new VBox();
                    questVbox.setPrefWidth(500);

                    String questHeader = creator.getName() + " (" +creator.getEmail() +") " + "at " + quest.getDate();

                    Text headerText = new Text(questHeader);
                    Text descriptionText = new Text("★ " + quest.getDescription());

                    descriptionText.setStyle("-fx-word-wrap: break-word");
                    TextFlow headerTextFlow = new TextFlow(headerText);
                    TextFlow descriptionTextFlow = new TextFlow(descriptionText);

                    //Style;
                    descriptionText.setFont(Font.font("Rockwell", FontWeight.BLACK, 15));
                    headerText.setFont(Font.font("Rockwell",13));
                    headerText.setFill(Color.GRAY);

                    //Adding solved header if quest is solved
                    if(quest.getSolved().equals(true))
                    {
                        Text solvedWarning = new Text("This quest was already solved.");
                        TextFlow solvedWarningFlow = new TextFlow(solvedWarning);
                        solvedWarning.setFont(Font.font("Rockwell",13));
                        solvedWarning.setFill(Color.RED);
                        questVbox.getChildren().add(solvedWarningFlow);
                    }



                    questVbox.getChildren().add(headerTextFlow);
                    questVbox.getChildren().add(descriptionTextFlow);
                    questVbox.getChildren().add(new Label(""));

                    // Button and answer part
                    if(quest.getSolved().equals(false)){
                        HBox answerAndButtonHBox = new HBox();
                        TextField answerTextField = new TextField();
                        answerTextField.setPromptText("Answer...");
                        answerTextField.setFont(Font.font("Rockwell"));
                        answerTextField.setFont(Font.font("Rockwell"));

                        Button submitAnswerButton = new Button("Submit");
                        submitAnswerButton.setId("submitAnswerButton");

                        answerAndButtonHBox.setSpacing(5);

                        submitAnswerButton.setOnAction(
                                event -> {
                                    try{
                                        submitAnswerButtonPressed(quest,answerTextField.getText());
                                    }catch (Exception ex){
                                        warnLabel.setText(ex.getMessage());
                                    }
                                }
                        );

                        answerAndButtonHBox.getChildren().add(answerTextField);
                        answerAndButtonHBox.getChildren().add(submitAnswerButton);
                        questVbox.getChildren().add(answerAndButtonHBox);
                        questVbox.getChildren().add(new Label(""));
                    }

                    //Adding the quest to page
                    scrollFlowPane.getChildren().add(questVbox);
                }
        );
    }

    @FXML
    private void logOutButtonPressed() {
        makeFadeOutTransition(rootPane,"main-scene.fxml","sign-in-view.fxml");
    }

    @FXML
    void solvedQuestsButtonPressed() {
        areLoggedUsersQuestShowed = false;
        questFeedText.setText("Quests you've sold");
        addLoggedUsersQuestsToFeed(service.findAllSolvedQuestsForUser(getLoggedUser().getUserId()));

    }

}
